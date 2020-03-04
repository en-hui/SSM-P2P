package fun.enhui.service.impl;

import fun.enhui.dao.BidDao;
import fun.enhui.dao.BidRequestDao;
import fun.enhui.model.Account;
import fun.enhui.model.Bid;
import fun.enhui.model.BidRequest;
import fun.enhui.model.Userinfo;
import fun.enhui.query.BidRequestQueryObject;
import fun.enhui.query.PageResult;
import fun.enhui.service.AccountService;
import fun.enhui.service.BidRequestService;
import fun.enhui.service.UserinfoService;
import fun.enhui.util.BidConst;
import fun.enhui.util.BitStatesUtils;
import fun.enhui.util.CalculatetUtil;
import fun.enhui.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class BidRequestServiceImpl implements BidRequestService {

    @Autowired
    private BidDao bidDao;

    @Autowired
    private BidRequestDao bidRequestDao;

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserinfoService userinfoService;
    @Override
    public void update(BidRequest bidRequest) {
        int ret = bidRequestDao.updateByPrimaryKey(bidRequest);
        if(ret==0){
            throw new RuntimeException("乐观锁失败  bidRequest"+bidRequest.getId());
        }
    }


    @Override
    public boolean canApplyBidRequeset(Long logininfoId) {
        Userinfo userinfo = userinfoService.get(logininfoId);

        return userinfo!=null
                &&userinfo.getIsBasicInfo()   //基本资料
                &&userinfo.getIsRealAuth()    //实名认证
                &&!userinfo.getHasBidRequestProcess();   //没有借款正在流程中
    }

    @Override
    public void apply(BidRequest br) {
        Account account = this.accountService.getCurrent();
        // 首先满足最基本的申请条件;
        if (this.canApplyBidRequeset(UserContext.getCurrent().getId())
                && br.getBidRequestAmount().compareTo(
                BidConst.SMALLEST_BIDREQUEST_AMOUNT) >= 0// 系统最小借款金额<=借款金额
                && br.getBidRequestAmount().compareTo(
                account.getRemainBorrowLimit()) <= 0// 借款金额<=剩余信用额度
                && br.getCurrentRate()
                .compareTo(BidConst.SMALLEST_CURRENT_RATE) >= 0// 5<=利息
                && br.getCurrentRate().compareTo(BidConst.MAX_CURRENT_RATE) <= 0// 利息<=20
                //&& br.getMinBidAmount().compareTo(BidConst.SMALLEST_BID_AMOUNT) >= 0// 最小投标金额>=系统最小投标金额
                ) {
            // ==========进入借款申请
            // 1,创建一个新的BidRequest,设置相关参数;
            BidRequest bidRequest = new BidRequest();
            bidRequest.setBidRequestAmount(br.getBidRequestAmount());
            bidRequest.setCurrentRate(br.getCurrentRate());
            bidRequest.setDescription(br.getDescription());
            bidRequest.setDisableDays(br.getDisableDays());
            //bidRequest.setMinBidAmount(br.getMinBidAmount());
            bidRequest.setReturnType(br.getReturnType());
            bidRequest.setMonthes2Return(br.getMonthes2Return());
            bidRequest.setTitle(br.getTitle());
            // 2,设置相关值;
            bidRequest.setApplyTime(new Date());
            bidRequest.setPublishTime(new Date());
            bidRequest.setDisableDate(new Date(bidRequest.getPublishTime().getTime()+bidRequest.getDisableDays()*24*60*60*1000));
            //bidRequest.setDisableDate(br.getDisableDate());
            bidRequest
                    .setBidRequestState(BidConst.BIDREQUEST_STATE_BIDDING);
            bidRequest.setCreateUser(UserContext.getCurrent());
            bidRequest
                    .setTotalRewardAmount(CalculatetUtil.calTotalInterest(
                            bidRequest.getReturnType(),
                            bidRequest.getBidRequestAmount(),
                            bidRequest.getCurrentRate(),
                            bidRequest.getMonthes2Return()));
            // 3,保存;
            this.bidRequestDao.insert(bidRequest);
            // 4,给借款人添加一个状态码
            Userinfo userinfo = this.userinfoService.getCurrent();
            userinfo.addState(BitStatesUtils.OP_HAS_BIDREQUEST_PROCESS);
            this.userinfoService.update(userinfo);
        }

    }

    @Override
    public List<BidRequest> listIndex() {
        BidRequestQueryObject qo = new BidRequestQueryObject();
        qo.setOrderBy("applyTime");
        qo.setOrderType("DESC");
        return bidRequestDao.query(qo);
    }

    public PageResult query(BidRequestQueryObject qo){
        int count = this.bidRequestDao.queryForCount(qo);
        if(count > 0){
            List<BidRequest> list = this.bidRequestDao.query(qo);
            return new PageResult(list,count,qo.getCurrentPage(),qo.getPageSize());
        }
        return PageResult.empty(qo.getPageSize());
    }

    @Override
    public BidRequest get(Long id) {
        return bidRequestDao.selectByPrimaryKey(id);
    }

    @Override
    public void bid(Long bidRequestId, BigDecimal amount) {
        //检查，得到借款信息
        BidRequest br = this.get(bidRequestId);
        if(br!=null//借款存在
                &&br.getBidRequestState()==BidConst.BIDREQUEST_STATE_BIDDING  //借款状态为招标中
                && !br.getCreateUser().getId().equals(UserContext.getCurrent().getId())  //当前用户不是借款的借款人
                //&& amount.compareTo(br.getMinBidAmount())>=0   //投标金额>=最小投标金额
                && amount.compareTo(br.getRemainAmount())<=0   //投标金额<=借款剩余投标金额

                ){
            //执行投标操作
            //创建一个投标对象
            Bid bid = new Bid();
            bid.setActualRate(br.getCurrentRate());
            bid.setAvailableAmount(amount);
            bid.setBidRequestId(br.getId());
            bid.setBidRequestTitle(br.getTitle());
            bid.setBidTime(new Date());
            bid.setBidUser(UserContext.getCurrent());
            bidDao.insert(bid);
            //修改投标数
            br.setBidCount(br.getBidCount()+1);
            //修改总投标钱数
            br.setCurrentSum(br.getCurrentSum().add(amount));
            //判断当前标是否投满
            if(br.getBidRequestAmount().equals(br.getCurrentSum())) {
                //投满了修改标的状态为待确认
                br.setBidRequestState(BidConst.BIDREQUEST_STATE_WAIT_CONFIRM);
            }
            this.update(br);
        }



    }

    @Override
    public PageResult queryMyBorrow(BidRequestQueryObject qo) {
        Long userId = UserContext.getCurrent().getId();
        qo.setUserId(userId);
        int count = this.bidRequestDao.queryMyBorrowForCount(qo);
        if(count > 0){
            List<BidRequest> list = this.bidRequestDao.queryMyBorrow(qo);
            return new PageResult(list,count,qo.getCurrentPage(),qo.getPageSize());
        }
        return PageResult.empty(qo.getPageSize());
    }

    @Override
    public PageResult queryMyBid(BidRequestQueryObject qo) {
        Long userId = UserContext.getCurrent().getId();
        qo.setUserId(userId);
        int count = this.bidDao.queryMyBidForCount(qo);
        if(count > 0){
            List<Bid> list = this.bidDao.queryMyBid(qo);
            return new PageResult(list,count,qo.getCurrentPage(),qo.getPageSize());
        }
        return PageResult.empty(qo.getPageSize());
    }

    public void updateState2Overdue(){
        bidRequestDao.updateState2Overdue();
    }
}
