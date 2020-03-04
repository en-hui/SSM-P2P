package fun.enhui.service;

/**
 * 手机验证码相关服务
 */
public interface VerifyCodeService {

    /**
     * 给指定的手机发送验证码
     */
    void sendVerifyCode(String phoneNumber);

    /**
     * 验证手机验证码
     */
    boolean verify(String phoneNumber, String verifyCode);
}
