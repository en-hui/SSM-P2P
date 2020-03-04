package fun.enhui.util;

import lombok.Getter;
import lombok.Setter;

/**
 * 用于返回数据而自定义的类型
 */
@Setter
@Getter
public class JSONResult {
	private boolean success = true;
	private String msg;

	public JSONResult() {
		super();
	}

	public JSONResult(String msg) {
		super();
		this.msg = msg;
	}

	public JSONResult(Boolean success, String msg) {
		super();
		this.success = success;
		this.msg = msg;
	}

}
