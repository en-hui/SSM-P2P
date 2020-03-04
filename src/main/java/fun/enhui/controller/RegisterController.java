package fun.enhui.controller;

import fun.enhui.model.Logininfo;
import fun.enhui.service.LogininfoService;
import fun.enhui.util.JSONResult;
import fun.enhui.util.UserContext;
import fun.enhui.util.mgrLoginAnnocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 用于注册/登录相关
 */
@Controller
public class RegisterController {
    @Autowired
    private LogininfoService logininfoService;

    /**
     *前台注册
     */
    @RequestMapping("register")
    @ResponseBody
    public JSONResult register(String username,String password,String stuNumber,String realName){
        JSONResult json = new JSONResult();
        try{
            logininfoService.checkUser(stuNumber,realName);
            logininfoService.register(username,password,stuNumber,realName);
        }catch (RuntimeException e){
            json.setSuccess(false);
            json.setMsg(e.getMessage());
        }
        return json;
    }

    @RequestMapping("checkUsername")
    @ResponseBody
    public Boolean checkUsername(String username){
        return !this.logininfoService.checkUsername(username);
    }

    /**
     *前台登录
     */
    @RequestMapping("login")
    @ResponseBody
    public JSONResult login(String username, String password, HttpServletRequest request){ //使用HttpServletRequest request获取请求中的信息
        JSONResult json = new JSONResult();
        Logininfo logininfo = logininfoService.login(username,password,
                request.getRemoteAddr(),Logininfo.USER_CLIENT);//request.getRemoteAddr()获取远程地址，即ip
        if(logininfo == null) {
            json.setMsg("用户名或密码错误");
            json.setSuccess(false);
        }
        return json;
    }

    /**
     * 后台登录
     */
    @RequestMapping("mgrlogin")
    @ResponseBody
    public JSONResult mgrlogin(String username,String password,HttpServletRequest request){
        JSONResult json = new JSONResult();
        Logininfo logininfo = this.logininfoService.login(username,password,request.getRemoteAddr(),Logininfo.USER_MANAGER);
        if(logininfo == null) {
            json.setMsg("用户名或密码错误");
            json.setSuccess(false);
        }
        return json;
    }
    /**
     * 后台首页
     */
    @mgrLoginAnnocation
    @RequestMapping("mgrindex")
    public String mgrindex(){
        return "mgrmain";
    }


    /**
     * 用户注销
     */
    @RequestMapping("logout")
    public String logout(){
        UserContext.deleteCurrent();
        return "redirect:index.do";
    }
}
