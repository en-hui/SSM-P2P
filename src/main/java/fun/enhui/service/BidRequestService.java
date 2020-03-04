package fun.enhui.service;

import fun.enhui.model.BidRequest;
import fun.enhui.query.BidRequestQueryObject;
import fun.enhui.query.PageResult;

import java.math.BigDecimal;
import java.util.List;

/**
 * 借款相关
 */
public interface BidRequestService {

    public void update(BidRequest bidRequest);

    /**
     * 判断用户是否具有申请借款的权力
     */
    boolean canApplyBidRequeset(Long logininfoId);

    /**
     * 申请借款
     * @param bidRequest
     */
    void apply(BidRequest bidRequest);


    /**
     * 首页相关数据
     * @return
     */
    List<BidRequest> listIndex();

    /**
     * 分页查询--全部借款
     */
    PageResult query(BidRequestQueryObject qo);

    /**
     * 根据id获取借款对象
     * @param id
     * @return
     */
    BidRequest get(Long id);

    /**
     * 投资
     * @param bidRequestId
     * @param amount
     */
    void bid(Long bidRequestId, BigDecimal amount);

    /**
     * 分页--查询自己的借款
     * @param qo
     * @return
     */
    PageResult queryMyBorrow(BidRequestQueryObject qo);

    /**
     * 分页--查询自己的投资
     * @param qo
     * @return
     */
    PageResult queryMyBid(BidRequestQueryObject qo);


    /**
     * 定时任务--借款超时修改状态
     */
    public void updateState2Overdue();
}
