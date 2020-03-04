package fun.enhui.service;

import fun.enhui.query.PageResult;
import fun.enhui.query.PaymentScheduleQueryObject;

/**
 * 还款相关
 */
public interface PaymentScheduleService {


    /**
     * 还款列表   分页查询
     * @param qo
     * @return
     */
    PageResult queryReturn(PaymentScheduleQueryObject qo);

    /**
     * 收款列表   分页查询
     * @param qo
     * @return
     */
    PageResult queryReceive(PaymentScheduleQueryObject qo);


    /**
     * 还款超时
     */
    void update2Overdue();
}
