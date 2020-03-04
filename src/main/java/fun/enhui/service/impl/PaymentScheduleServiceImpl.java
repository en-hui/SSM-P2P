package fun.enhui.service.impl;

import fun.enhui.dao.PaymentScheduleDao;
import fun.enhui.model.PaymentSchedule;
import fun.enhui.query.PageResult;
import fun.enhui.query.PaymentScheduleQueryObject;
import fun.enhui.service.PaymentScheduleService;
import fun.enhui.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentScheduleServiceImpl implements PaymentScheduleService {

    @Autowired
    private PaymentScheduleDao paymentScheduleDao;

    /**
     * 还款
     * @param qo
     * @return
     */
    @Override
    public PageResult queryReturn(PaymentScheduleQueryObject qo) {
        Long userId = UserContext.getCurrent().getId();
        qo.setUserId(userId);
        int count = this.paymentScheduleDao.queryReturnForCount(qo);
        if(count>0){
            List<PaymentSchedule> list = this.paymentScheduleDao.queryReturn(qo);
            return new PageResult(list,count,qo.getCurrentPage(),qo.getPageSize());
        }
        return PageResult.empty(qo.getPageSize());
    }

    /**
     * 收款
     * @param qo
     * @return
     */
    @Override
    public PageResult queryReceive(PaymentScheduleQueryObject qo) {
        Long userId = UserContext.getCurrent().getId();
        qo.setUserId(userId);
        int count = this.paymentScheduleDao.queryReceiveForCount(qo);
        if(count>0){
            List<PaymentSchedule> list = this.paymentScheduleDao.queryReceive(qo);
            return new PageResult(list,count,qo.getCurrentPage(),qo.getPageSize());
        }
        return PageResult.empty(qo.getPageSize());
    }

    @Override
    public void update2Overdue() {
        paymentScheduleDao.update2Overdue();
    }
}
