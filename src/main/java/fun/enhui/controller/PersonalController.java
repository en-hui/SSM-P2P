package fun.enhui.controller;

import fun.enhui.model.Logininfo;
import fun.enhui.model.SystemDictionaryItem;
import fun.enhui.model.Userinfo;
import fun.enhui.service.AccountService;
import fun.enhui.service.SystemDictionaryService;
import fun.enhui.service.UserinfoService;
import fun.enhui.util.JSONResult;
import fun.enhui.util.LoginAnnocation;
import fun.enhui.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 个人中心
 */
@Controller
public class PersonalController {

    @Autowired
    private UserinfoService userinfoService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private SystemDictionaryService systemDictionaryService;
    /**
     * 个人主页
     * @param model
     * @return
     */
    @LoginAnnocation
    @RequestMapping("personal")
    public String personalCenter(Model model){
        Logininfo current = UserContext.getCurrent();
        model.addAttribute("userinfo",userinfoService.get(current.getId()));
        model.addAttribute("account",accountService.get(current.getId()));
        return "personal";
    }

    /**
     * 手机号绑定
     */
    @LoginAnnocation
    @RequestMapping("bindPhone")
    @ResponseBody
    public JSONResult bindPhone(String phoneNumber,String verifyCode){
        JSONResult json = new JSONResult();
        try{
            this.userinfoService.bindPhone(phoneNumber,verifyCode);
        }catch (RuntimeException e){
            json.setSuccess(false);
            json.setMsg(e.getMessage());
        }
        return json;
    }

    /**
     * 发送绑定邮箱的邮件
     */

    @LoginAnnocation
    @RequestMapping("sendEmail")
    @ResponseBody
    public JSONResult sendEmail(String email){
        JSONResult json = new JSONResult();
        try{
            this.userinfoService.sendVerifyEmail(email);
        }catch (RuntimeException e){
            json.setSuccess(false);
            json.setMsg(e.getMessage());
        }
        return json;
    }

    /**
     * 执行邮件绑定
     */
    @RequestMapping("bindEmail")
    public String bindEmail(String key,Model model){

        try{
            this.userinfoService.bindEmail(key);
            model.addAttribute("success",true);
        }catch (RuntimeException e){
            model.addAttribute("success",false);
            model.addAttribute("msg",e.getMessage());
        }
        return "checkmail_result";
    }

    /**
     * 用户个人资料填写
     */
    @LoginAnnocation
    @RequestMapping("basicInfo")
    public String basicInfo(Model model){

        //添加当前用户
        model.addAttribute("userinfo",this.userinfoService.getCurrent());
        //添加数据字典--下拉框
        model.addAttribute("departments",this.systemDictionaryService.listAllDics());
        /*model.addAttribute("professions","");*/
        /*model.addAttribute("departments",this.systemDictionaryService.listByParentSn("department"));
        model.addAttribute("professions",this.systemDictionaryService.listByParentSn("profession"));*/
        return "userInfo";
    }

    /**
     * 二级联动
     */
    @LoginAnnocation
    @ResponseBody
    @RequestMapping("ajaxLinkage")
    public List<SystemDictionaryItem> ajaxLinkage(int departmentValue){
        List<SystemDictionaryItem> list = this.systemDictionaryService.listByParentId(departmentValue);
        return list;
    }

    /**
     * 基本资料保存
     */
    @LoginAnnocation
    @RequestMapping("basicInfo_save")
    @ResponseBody
    public JSONResult basicInfoSave(Userinfo userinfo){
        JSONResult json = new JSONResult();
        try{
            this.userinfoService.updateBasicInfo(userinfo);
        }catch (RuntimeException e){
            json.setSuccess(false);
            json.setMsg(e.getMessage());
        }
        return json;

    }

}
