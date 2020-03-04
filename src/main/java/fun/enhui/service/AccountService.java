package fun.enhui.service;

import fun.enhui.model.Account;

/**
 * 账户相关服务
 */
public interface AccountService {

    /**
     * 写完dao之后立刻写service，因为这个update是支持乐观锁的
     * @param account
     */
    public void update(Account account);

    /**
     * 用户注册后，初始化账户信息
     * @param account
     */
    void add(Account account);

    /**
     *获取账户信息
     */
    Account get(Long id);

    /**
     * 得到当前登录人的账户信息
     * @return
     */
    Account getCurrent();
}
