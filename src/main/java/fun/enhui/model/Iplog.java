package fun.enhui.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 登录日志对象
 */
@Getter
@Setter
public class Iplog {
    private Long id;
    public static final int STATE_SUCCESS = 1;  //登录成功状态
    public static final int STATE_FAILED = 2;   //登录失败状态

    private String ip;
    private Date loginTime;
    private String userName;
    private int state;
    private int userType;

    public String getStateDisplay(){
        return state == STATE_SUCCESS?"登录成功":"登录失败";
    }

    public String getUserTypeDisplay(){
        return userType == Logininfo.USER_CLIENT ? "前端用户":"后台管理员";
    }
}
