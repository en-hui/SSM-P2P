package fun.enhui.service.impl;

import com.alibaba.fastjson.JSONObject;
import fun.enhui.dao.RealAuthDao;
import fun.enhui.model.RealAuth;
import fun.enhui.model.Userinfo;
import fun.enhui.query.PageResult;
import fun.enhui.query.RealAuthQueryObject;
import fun.enhui.service.RealAuthService;
import fun.enhui.service.UserinfoService;
import fun.enhui.util.BitStatesUtils;
import fun.enhui.util.HttpUtils;
import fun.enhui.util.UserContext;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RealAuthServiceImpl implements RealAuthService {


    @Value("${phone.host}")
    private String host;

    @Value("${phone.path}")
    private String path;

    @Value("${phone.method}")
    private String method;

    @Value("${phone.appcode}")
    private String appcode;

    @Autowired
    private RealAuthDao realAuthDao;

    @Autowired
    private UserinfoService userinfoService;

    @Override
    public RealAuth get(Long id) {
        return this.realAuthDao.selectByPrimaryKey(id);
    }

    /**
     * 实名申请
     * @param realAuth
     */
    @Override
    public void apply(RealAuth realAuth) {
        Userinfo current = userinfoService.get(UserContext.getCurrent().getId());
        if(!current.getIsRealAuth() && current.getRealAuthId() == null){
            //当前用户没有实名认证，且不处于待审核状态
            realAuth.setApplyTime(new Date());
            realAuth.setApplier(UserContext.getCurrent());
            realAuth.setState(RealAuth.STATE_NORMAL);
            realAuthDao.insert(realAuth);
            //设置userinfo的realAuthId
            current.setRealAuthId(realAuth.getId());
            userinfoService.update(current);
        }
    }

    @Override
    public PageResult query(RealAuthQueryObject qo) {
        int count = this.realAuthDao.queryForCount(qo);
        if(count>0){
            List<RealAuth> list = this.realAuthDao.query(qo);
            return new PageResult(list,count,qo.getCurrentPage(),qo.getPageSize());
        }
        return PageResult.empty(qo.getPageSize());
    }

    @Override
    public void audit(Long id, String remark, int state) {
        //根据id得到实名认证对象
        RealAuth realAuth = this.get(id);
        //如果对象存在，并且状态为正常---说明是待审核状态
        if(realAuth != null && realAuth.getState()==RealAuth.STATE_NORMAL){
            //设置通用属性
            realAuth.setAuditor(UserContext.getCurrent());
            realAuth.setAuditTime(new Date());
            realAuth.setState(state);
            realAuth.setRemark(remark);

            //申请人信息
            Userinfo applier = this.userinfoService.get(realAuth.getApplier().getId());
            if(state == RealAuth.STATE_AUDIT){
                //如果本次审核通过
                if(!applier.getIsRealAuth()){//保证用户处于未审核状态
                    //添加审核的状态码，设置userinfo上面的冗余值，重新realauthId
                    applier.addState(BitStatesUtils.OP_REAL_AUTH);
                    applier.setRealName(realAuth.getRealName());
                    applier.setIdNumber(realAuth.getIdNumber());
                    applier.setRealAuthId(realAuth.getId());
                }
            }else{
                //如果状态是审核拒绝
                //userinfo中的realauthId设置为空
                applier.setRealAuthId(null);
            }
            this.userinfoService.update(applier);
            this.realAuthDao.updateByPrimaryKey(realAuth);
        }

    }

    @Override
    public boolean aliPhoneRealAuth(String realName, String phoneNumber, String idNumber) {
        /*String host = "https://phonecheck.market.alicloudapi.com";
        String path = "/phoneAuthentication";
        String method = "POST";
        String appcode = "087f2134bd584c4b9602b3580f926a38";*/
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("idNo", idNumber);
        bodys.put("name", realName);
        bodys.put("phoneNo", phoneNumber);


        try {

            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));


            JSONObject jsonObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity(),"UTF-8"));
            //错误码   0000   为成功
            String respCode = jsonObject.get("respCode").toString();
            if("0000".equals(respCode)){
                //校验成功，实名认证成功
                return true;
            }else{
                //手机号  身份证号   姓名    不一致    实名认证失败
                return false;
            }



        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


}
