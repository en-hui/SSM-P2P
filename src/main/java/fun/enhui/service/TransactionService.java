package fun.enhui.service;

import fun.enhui.query.PageResult;
import fun.enhui.query.PaymentScheduleQueryObject;
import fun.enhui.query.QueryObject;

/**
 * 交易相关
 */
public interface TransactionService {
    /**
     * 借款交易
     */
    public void borrowTransaction(Long bidRequestId,Integer type);


    /**
     * 还款交易
     * @param id
     * @param type
     */
    void returnTransaction(Long id, Integer type);
}
