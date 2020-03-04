package fun.enhui.service;

/**
 * 专门用来发邮件的服务
 */
public interface MailService {

    /**
     * 发送邮件
     * @param target     接受邮件的地址
     * @param title      标题
     * @param content    内容
     */
    public void sendMail(String target,String title,String content);
}
