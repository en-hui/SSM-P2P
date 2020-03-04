package fun.enhui.model;

import fun.enhui.util.BidConst;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 用户对应的账户信息
 */
@Getter
@Setter
public class Account {
    private Long id;
    private int version; //对象版本信息（用作乐观锁）
    private String tradePassword;  //交易密码
    private BigDecimal usableAmount = BidConst.ZERO;   //可用金额
    private BigDecimal freezedAmount= BidConst.ZERO;  //冻结金额
    private BigDecimal unReceiveInterest = BidConst.ZERO;  //待收利息
    private BigDecimal unReceivePrincipal = BidConst.ZERO;    //待收本金
    private BigDecimal unReturnAmount = BidConst.ZERO;     //待还金额
    private BigDecimal remainBorrowLimit = BidConst.INIT_BORROW_LIMIT;    //剩余授信额度
    private BigDecimal borrowLimit = BidConst.INIT_BORROW_LIMIT;     //账户最高的授信额度

    public BigDecimal getTotalAmount(){
        //账户总额 = 可用金额 + 冻结金额 + 待收本金
        return usableAmount.add(this.freezedAmount).add(this.unReceivePrincipal);
    }
}
