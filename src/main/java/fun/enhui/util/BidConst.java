package fun.enhui.util;

import java.math.BigDecimal;

/**
 * 系统中的常量
 */
public class BidConst {
    /**
     * 定义存储精度
     */
    public static final int STORE_SCALE = 4;
    /**
     * 定义运算精度
     */
    public static final int CAL_SCALE = 8;
    /**
     * 定义显示精度
     */
    public static final int DISPLAY_SCALE = 2;

    /**
     * 定义系统级别的0
     */
    public static final BigDecimal ZERO = new BigDecimal("0.0000");

    /**
     * 定义初始授信额度
     */
    public static final BigDecimal INIT_BORROW_LIMIT = new BigDecimal("3000.0000");


    /**
     * 默认管理员的账号密码
     */
    public static final String DEFAULT_ADMIN_USERNAME = "admin";
    public static final String DEFAULT_ADMIN_PASSWORD = "admin";


    /**
     * 手机验证码的有效期
     */
    public static final int VERIFYCODE_VAILDATE_SECOND = 300;

    /**
     * 验证邮箱的有效期
     */
    public static final int VERIFYEMAIL_VAILDATE_DAY = 5;

    // --------------------还款类型---------------------------
    public final static int RETURN_TYPE_MONTH_INTEREST_PRINCIPAL = 0; // 还款方式
    // 按月分期还款(等额本息)

    // ---------------------标的类型--------------------------
    public final static int BIDREQUEST_TYPE_NORMAL = 0; // 普通信用标

    // ---------------------借款状态---------------------------
    public final static int BIDREQUEST_STATE_WAIT_CONFIRM = 0; // 待确认
    public final static int BIDREQUEST_STATE_BIDDING = 1; // 招标中
    public final static int BIDREQUEST_STATE_UNDO = 2; // 借款人已撤销
    public final static int BIDREQUEST_STATE_BIDDING_OVERDUE = 3; // 超时没人投资
    public final static int BIDREQUEST_STATE_BID_UNDO = 4; //投资人取消
    public final static int BIDREQUEST_STATE_PAYING_BACK = 7; // 还款中
    public final static int BIDREQUEST_STATE_COMPLETE_PAY_BACK = 8; // 已还清
    public final static int BIDREQUEST_STATE_PAY_BACK_OVERDUE = 9; // 逾期

    public static final BigDecimal SMALLEST_BID_AMOUNT = new BigDecimal(
            "50.0000");// 系统规定的最小投标金额
    public static final BigDecimal SMALLEST_BIDREQUEST_AMOUNT = new BigDecimal(
            "500.0000");// 系统规定的最小借款金额
    public static final BigDecimal SMALLEST_CURRENT_RATE = new BigDecimal(
            "5.0000");// 系统最小借款利息
    public static final BigDecimal MAX_CURRENT_RATE = new BigDecimal("20.0000");// 系统最大借款利息


    /** =========还款状态=============== */
    public final static int PAYMENT_STATE_NORMAL = 0; // 正常待还
    public final static int PAYMENT_STATE_DONE = 1; // 已还
    public final static int PAYMENT_STATE_OVERDUE = 2; // 逾期
    public final static int PAYMENT_STATE_CONFIRMING =3;  //待确认
}
