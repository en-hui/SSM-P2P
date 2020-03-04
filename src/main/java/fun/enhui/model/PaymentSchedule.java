package fun.enhui.model;

import java.math.BigDecimal;
import java.util.Date;

import fun.enhui.util.BidConst;
import lombok.Getter;
import lombok.Setter;

import org.apache.ibatis.type.Alias;


/**
 * 
 * 还款计划对象
 *
 */
@Getter
@Setter
@Alias("PaymentSchedule")
public class PaymentSchedule {
	private Long id;
	private Long bidRequestId; // 对应借款
	private String bidRequestTitle;//借款名称
	private Logininfo borrowUser; // 还款人
	private Logininfo investUser;  //收款人
	private Date deadLine; // 本期还款截止期限
	private Date payDate;// 还款时间

	private BigDecimal totalAmount = BidConst.ZERO; // 本期还款总金额，利息 +本金
	private BigDecimal principal = BidConst.ZERO; // 本期还款本金
	private BigDecimal interest = BidConst.ZERO; // 本期还款总利息
	private int monthIndex; // 第几期 (即第几个月)
	private int state = BidConst.PAYMENT_STATE_NORMAL; // 本期还款状态（默认正常待还）
	private int bidRequestType; // 借款类型
	private int returnType; // 还款方式，等同借款(BidRequest)中的还款方式


	public String getStateDisplay() {
		switch (state) {
		case BidConst.PAYMENT_STATE_NORMAL:
			return "正常待还";
		case BidConst.PAYMENT_STATE_DONE:
			return "已还";
		case BidConst.PAYMENT_STATE_OVERDUE:
			return "逾期";
		case BidConst.PAYMENT_STATE_CONFIRMING:
			return "待确认";
		default:
			return "未知";
		}
	}
}
