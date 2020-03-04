package fun.enhui.service.impl;

import fun.enhui.dao.PaymentScheduleDao;
import fun.enhui.model.Account;
import fun.enhui.model.BidRequest;
import fun.enhui.model.PaymentSchedule;
import fun.enhui.model.Userinfo;
import fun.enhui.service.AccountService;
import fun.enhui.service.BidRequestService;
import fun.enhui.service.TransactionService;
import fun.enhui.service.UserinfoService;
import fun.enhui.util.BidConst;
import fun.enhui.util.BitStatesUtils;
import fun.enhui.util.CalculatetUtil;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private BidRequestService bidRequestService;

    @Autowired
    private UserinfoService userinfoService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PaymentScheduleDao paymentScheduleDao;

    @Override
    public void borrowTransaction(Long bidRequestId, Integer type) {
        BidRequest bidRequest = this.bidRequestService.get(bidRequestId);
        //获取借款人，
        Userinfo borrowUser = userinfoService.get(bidRequest.getCreateUser().getId());
        //获取投资人  --本系统投资人只有一个，直接获取下标0即可
        Userinfo investUser = null;
        if(bidRequest.getBids().size()>0){
            investUser = userinfoService.get(bidRequest.getBids().get(0).getBidUser().getId());
        }
        //取消或两人确定后 移除借款人有借款流程的状态
        Long borrowUserState = BitStatesUtils.removeState(borrowUser.getBitState(), BitStatesUtils.OP_HAS_BIDREQUEST_PROCESS);
        //根据操作   对借款进行状态修改
        switch (type) {
            case 1:  //投资人确认
                Long investDoState = BitStatesUtils.addState(bidRequest.getBidState(), BitStatesUtils.BID_INVEST_DO);
                bidRequest.setBidState(investDoState);
                break;
            case 2:  //投资人取消
                borrowUser.setBitState(borrowUserState);
                bidRequest.setBidRequestState(BidConst.BIDREQUEST_STATE_BID_UNDO);
                break;
            case 3:   //借款人确认
                Long borrowDoState = BitStatesUtils.addState(bidRequest.getBidState(), BitStatesUtils.BID_BORROW_DO);
                bidRequest.setBidState(borrowDoState);
                break;
            case 4:   //借款人取消
                borrowUser.setBitState(borrowUserState);
                bidRequest.setBidRequestState(BidConst.BIDREQUEST_STATE_UNDO);
                break;
        }

        //两个人都确认
        if ((BitStatesUtils.hasState(bidRequest.getBidState(), BitStatesUtils.BID_BORROW_DO))
                && BitStatesUtils.hasState(bidRequest.getBidState(), BitStatesUtils.BID_INVEST_DO)) {

            //修改借款状态为还款中
            bidRequest.setBidRequestState(BidConst.BIDREQUEST_STATE_PAYING_BACK);

            /*对借款人的操作*/
            Account borrowAcount = this.accountService.get(borrowUser.getId());
            //借款人剩余额度
            borrowAcount.setRemainBorrowLimit(borrowAcount.getRemainBorrowLimit().subtract(bidRequest.getBidRequestAmount()));
            //借款人总待还金额
            borrowAcount.setUnReturnAmount(borrowAcount.getUnReturnAmount().add(bidRequest.getBidRequestAmount()).add(bidRequest.getTotalRewardAmount()));
            //移除借款人有借款流程的状态
            borrowUser.setBitState(borrowUserState);

            /*对投资人的操作*/
            Account investAcount = this.accountService.get(investUser.getId());
            //投资人代收利息
            investAcount.setUnReceiveInterest(bidRequest.getTotalRewardAmount());
            //投资人待收本金
            investAcount.setUnReceivePrincipal(bidRequest.getBidRequestAmount());


            /*创建还款计划对象*/
            createPaymentSchedule(bidRequest);
            this.accountService.update(borrowAcount);
            this.accountService.update(investAcount);
            this.userinfoService.update(investUser);
        }

        this.userinfoService.update(borrowUser);
        this.bidRequestService.update(bidRequest);

    }

    @Override
    public void returnTransaction(Long id, Integer type) {
        PaymentSchedule paymentSchedule = paymentScheduleDao.getById(id);
        if(type == 1){
            //借款人还款---设置还款对象状态为   待确认
        paymentSchedule.setState(BidConst.PAYMENT_STATE_CONFIRMING);
        }else{
            //投资人收款---设置还款对象状态为    已还
            paymentSchedule.setState(BidConst.PAYMENT_STATE_DONE);
            paymentSchedule.setPayDate(new Date());
        }
        this.paymentScheduleDao.update(paymentSchedule);
    }


    /**
     * 创建还款计划对象
     */
    private void createPaymentSchedule(BidRequest bidRequest) {
        Date now = new Date();
        //总利息
        BigDecimal totalInterest = BidConst.ZERO;
        //总本金
        BigDecimal totalPrincipal=BidConst.ZERO;
        for (int i = 0; i < bidRequest.getMonthes2Return(); i++) {
            //针对每一期创建一个还款对象
            PaymentSchedule paymentSchedule = new PaymentSchedule();
            paymentSchedule.setBidRequestId(bidRequest.getId());
            paymentSchedule.setBidRequestTitle(bidRequest.getTitle());
            paymentSchedule.setBidRequestType(bidRequest.getBidRequestType());
            paymentSchedule.setBorrowUser(bidRequest.getCreateUser());
            paymentSchedule.setInvestUser(bidRequest.getBids().get(0).getBidUser());
            paymentSchedule.setDeadLine(DateUtils.addMonths(now, i + 1));

            if (i<bidRequest.getMonthes2Return()-1){
                //计算这一期的利息
                paymentSchedule.setInterest(CalculatetUtil.calMonthlyInterest(bidRequest.getReturnType(), bidRequest.getBidRequestAmount(), bidRequest.getCurrentRate(), i + 1, bidRequest.getMonthes2Return()));
                //计算这一期的总还款钱数
                paymentSchedule.setTotalAmount(CalculatetUtil.calMonthToReturnMoney(bidRequest.getReturnType(), bidRequest.getBidRequestAmount(), bidRequest.getCurrentRate(), i + 1, bidRequest.getMonthes2Return()));
                //计算这一期的本金
                paymentSchedule.setPrincipal(paymentSchedule.getTotalAmount().subtract(paymentSchedule.getInterest()));

                totalInterest=totalInterest.add(paymentSchedule.getInterest());
                totalPrincipal=totalPrincipal.add(paymentSchedule.getPrincipal());
            }else{
                //最后一期
                paymentSchedule.setInterest(bidRequest.getTotalRewardAmount().subtract(totalInterest));
                paymentSchedule.setPrincipal(bidRequest.getBidRequestAmount().subtract(totalPrincipal));
                paymentSchedule.setTotalAmount(paymentSchedule.getPrincipal().add(paymentSchedule.getInterest()));
            }


            paymentSchedule.setMonthIndex(i+1);
            paymentSchedule.setReturnType(bidRequest.getReturnType());
            //处于待还状态
            paymentSchedule.setState(BidConst.PAYMENT_STATE_NORMAL);
            //保存
            paymentScheduleDao.insert(paymentSchedule);
        }
    }





}
