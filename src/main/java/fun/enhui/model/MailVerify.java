package fun.enhui.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 邮箱验证对象
 */
@Getter
@Setter
public class MailVerify {
    private Long id;
    private Long userinfoId;   //验证邮箱的用户
    private String email;
    private String uuid;
    private Date sendDate;  //验证邮件发送时间
}
