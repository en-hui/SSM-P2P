package fun.enhui.model;

import fun.enhui.util.BitStatesUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户相关信息
 */
@Getter
@Setter
@ToString
public class Userinfo{
    private Long id;
    private int version;   //版本
    private long bitState = 0;  //用户状态码
    private String realName;   //真实姓名
    private String idNumber;   //身份证号码
    private String phoneNumber;  //电话号码
    private String email;        //邮箱
    private Long realAuthId;    //用户对应的实名认证对象的id

    private SystemDictionary department; // 院系
    private SystemDictionaryItem profession; //专业
    private String studyId;  //学号
    private Integer clazz;    //班级

    /**
     * 添加状态
     * @return
     */
    public void addState(long state){
        this.setBitState(BitStatesUtils.addState(this.bitState,state));
    }


    /**
     * 判断是否绑定手机号
     */
    public boolean getIsBindPhone(){
        return BitStatesUtils.hasState(bitState,BitStatesUtils.OP_BIND_PHONE);
    }
    /**
     * 判断是否绑定邮箱
     */
    public boolean getIsBindEmail(){
        return BitStatesUtils.hasState(bitState,BitStatesUtils.OP_BIND_EMAIL);
    }
    /**
     * 判断用户是否填写了基本资料
     */
    public boolean getIsBasicInfo(){
        return BitStatesUtils.hasState(this.bitState,BitStatesUtils.OP_BASIC_INFO);
    }
    /**
     * 判断是否实名认证
     */
    public boolean getIsRealAuth(){
        return BitStatesUtils.hasState(bitState,BitStatesUtils.OP_REAL_AUTH);
    }

    /**
     * 判断用户是否有一个借款正在处理流程当中
     */
    public boolean getHasBidRequestProcess(){
        return BitStatesUtils.hasState(this.bitState,
                BitStatesUtils.OP_HAS_BIDREQUEST_PROCESS);
    }




}
