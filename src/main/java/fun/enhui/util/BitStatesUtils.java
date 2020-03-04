package fun.enhui.util;

/**
 * 用户状态类，记录用户在平台使用系统中所有的状态。
 * 
 * @author Administrator
 */
public class BitStatesUtils {
	public final static Long OP_BIND_PHONE = 1L << 0; // 用户绑定手机状态码1
	public final static Long OP_BIND_EMAIL = 1L << 1; // 用户绑定邮箱2
	public final static Long OP_BASIC_INFO = 1L << 2;// 用户是否填写基本资料4
	public final static Long OP_REAL_AUTH = 1L << 3;// 用户是否实名认证8
	public final static Long OP_HAS_BIDREQUEST_PROCESS = 1L << 5;// 用户是否有一个借款正在处理流程当中32

	public final static Long BID_BORROW_DO = 1L <<1;  //借款人确认
	public final static Long BID_INVEST_DO = 1L <<2;   //投资人确认
	/**
	 * @param states
	 *            所有状态值
	 * @param value
	 *            需要判断状态值
	 * @return 是否存在
	 */
	public static boolean hasState(long states, long value) {
		return (states & value) != 0;
	}

	/**
	 * @param states
	 *            已有状态值
	 * @param value
	 *            需要添加状态值
	 * @return 新的状态值
	 */
	public static long addState(long states, long value) {
		if (hasState(states, value)) {
			return states;
		}
		return (states | value);
	}

	/**
	 * @param states
	 *            已有状态值
	 * @param value
	 *            需要删除状态值
	 * @return 新的状态值
	 */
	public static long removeState(long states, long value) {
		if (!hasState(states, value)) {
			return states;
		}
		return states ^ value;
	}
}
