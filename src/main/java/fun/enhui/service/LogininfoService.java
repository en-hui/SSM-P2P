package fun.enhui.service;

import fun.enhui.model.Logininfo;

/**
 * 登录相关
 */
public interface LogininfoService {
    //注册
    void register(String username,String password,String stuNumber,String realName);

    //检查用户名是否存在
    boolean checkUsername(String username);

    //登录
    Logininfo login(String username, String password, String ip,int userType);

    //初始化第一个管理员
    void initAdmin();

    /**
     * 根据注册信息     学号和姓名    从初始数据中检查是否正确
     * @param stuNumber
     * @param realName
     * @return
     */
    void checkUser(String stuNumber, String realName);
}
