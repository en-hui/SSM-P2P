package fun.enhui.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 存放验证码相关内容，这个对象放在session中
 */
@Getter
@Setter
public class VerifyCodeVO {

    private  String verifyCode;  //验证码
    private  String phoneNumber;  //手机号
    private Date lastSendTime;  //最后一次发送时间
}
