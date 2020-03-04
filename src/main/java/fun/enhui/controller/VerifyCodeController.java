package fun.enhui.controller;

import fun.enhui.service.VerifyCodeService;
import fun.enhui.util.JSONResult;
import fun.enhui.util.LoginAnnocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 验证码相关
 */

@Controller
public class VerifyCodeController {

    @Autowired
    private VerifyCodeService verifyCodeService;

    /**
     * 发送短信验证码
     * @param phoneNumber
     * @return
     */
    @LoginAnnocation
    @RequestMapping("sendVerifyCode")
    @ResponseBody
    public JSONResult sendVerifyCode(String phoneNumber){
        JSONResult json = new JSONResult();
        try{
            verifyCodeService.sendVerifyCode(phoneNumber);
        }catch (RuntimeException e){
            json.setSuccess(false);
            json.setMsg(e.getMessage());
        }
        return json;
    }
}
