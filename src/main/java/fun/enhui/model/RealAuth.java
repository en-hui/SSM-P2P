package fun.enhui.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 实名认证对象
 * 判断认证状态的方式：  未认证   待审核   已认证
 * 再userinfo上添加一个实名认证id
 * 未认证：根据用户的状态码判断
 * 待审核：状态码为未认证，实名认证id不为空
 * 已认证：根据用户的状态码判断
 *
 * 审核拒绝：需要把userinfo的实名认证id置空
 */
@Getter
@Setter
public class RealAuth {
    private Long id;
    public static final int SEX_MALE = 0; //男
    public static final int SEX_FEMAIL = 1;

    public static final int STATE_NORMAL = 0;  //正常
    public static final int STATE_AUDIT = 1;   //审核成功
    public static final int STATE_REJECT = 2;    //审核拒绝



    private String realName;   //真实姓名
    private int sex;     //性别
    private String idNumber;  //证件号码
    private String stuNumber;  //学生号码
    private String card_image1;  //身份证正面地址
    private String card_image2;   //身份证反面地址
    private String stu_image1;   //学生证正面地址
    private String stu_image2;   //学生证反面地址


    private int state;   //状态
    private String remark;  //审核备注
    private Date applyTime;   //申请时间
    private Date auditTime;  //审核时间
    private Logininfo applier; //申请人
    private Logininfo auditor; //审核人


    public String getSexDisplay(){
        return sex==SEX_MALE?"男":"女";
    }

    public String getStateDisplay(){
        switch (state){
            case STATE_NORMAL:return "待审核";
            case STATE_AUDIT:return "审核通过";
            case STATE_REJECT:return "审核拒绝";
            default:return "";
        }
    }

    public String getJsonString(){
        Map<String,Object> json = new HashMap<>();
        json.put("id",id);
        json.put("applier",this.applier.getUsername());
        json.put("realName",realName);
        json.put("idNumber",idNumber);
        json.put("sex",getSexDisplay());
        json.put("stuNumber",stuNumber);
        json.put("card_image1",card_image1);
        json.put("card_image2",card_image2);
        json.put("stu_image1",stu_image1);
        json.put("stu_image2",stu_image2);

        return JSONObject.toJSONString(json);

    }

}
