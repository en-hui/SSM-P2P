package fun.enhui.service;

import fun.enhui.model.RealAuth;
import fun.enhui.query.PageResult;
import fun.enhui.query.RealAuthQueryObject;

public interface RealAuthService {

    /**
     * 根据 id获取实名认证对象
     */
    public RealAuth get(Long id);

    /**
     * 提交实名验证申请
     */
    void apply(RealAuth realAuth);

    /**
     * 后台实名信息分页
     */
    PageResult query(RealAuthQueryObject qo);

    /**
     *后台实名认证审核
     */
    void audit(Long id, String remark, int state);


    /**
     * 使用阿里接口完成手机号实名认证
     * @param realName   姓名
     * @param phoneNumber   手机号
     * @param idNumber  身份证号
     * @return      成功返回true，失败返回false    阿里成功返回错误码为 0000
     */
    boolean aliPhoneRealAuth(String realName,String phoneNumber,String idNumber);
}
