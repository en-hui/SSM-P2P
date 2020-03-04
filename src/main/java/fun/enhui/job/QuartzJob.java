package fun.enhui.job;

import fun.enhui.dao.PaymentScheduleDao;
import fun.enhui.service.BidRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 */
@Component
public class QuartzJob {

    @Autowired
    private BidRequestService bidRequestService;

    @Autowired
    private PaymentScheduleDao paymentScheduleDao;

    /**
     * 定时任务，超过招标时间的借款改为取消状态
     */
    @Scheduled(cron = "0 0 22 ? * *")
    public void bidRequestTimeOut(){
        bidRequestService.updateState2Overdue();
    }

    /**
     * 定时任务，超过还款时间，但未还款的改为逾期状态
     */
    @Scheduled(cron = "0 0 22 ? * *")
    public void bidTimeOut(){
        paymentScheduleDao.update2Overdue();
    }

}
