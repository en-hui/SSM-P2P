package fun.enhui.service;

import fun.enhui.model.Userinfo;

/**
 * 用户相关信息服务
 */
public interface UserinfoService {

    /**
     * 乐观锁支持
     */
    void update(Userinfo userinfo);

    /**
     * 用户注册后初始化userinfo信息
     * @param userinfo
     */
    void add(Userinfo userinfo);

    /**
     *获取用户信息
     */
    Userinfo get(Long id);

    /**
     * 手机绑定
     */
    void bindPhone(String phoneNumber, String verifyCode);

    /**
     * 发送绑定邮箱邮件
     * @param email
     */
    void sendVerifyEmail(String email);

    /**
     * 执行绑定邮箱
     * @param uuid
     */
    void bindEmail(String uuid);

    /**
     * 获取当前登录人信息
     * @return
     */
    Userinfo getCurrent();

    /**
     * 更新基本信息
     * @param userinfo
     */
    void updateBasicInfo(Userinfo userinfo);
}
