package fun.enhui.controller;

import fun.enhui.query.IplogQueryObject;
import fun.enhui.service.IplogService;
import fun.enhui.util.LoginAnnocation;
import fun.enhui.util.UserContext;
import fun.enhui.util.mgrLoginAnnocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 登录日志
 */
@Controller
public class IplogController {
    @Autowired
    private IplogService iplogService;

    /**
     * 用户个人登录日志列表
     * @param qo
     * @param model
     * @return
     */
    @LoginAnnocation
    @RequestMapping("ipLog")  //@ModelAttribute("qo")相当于  model.addAttribute("qo",qo);
    public String iplogList(@ModelAttribute("qo")IplogQueryObject qo, Model model){
        System.out.println(qo.getBeginDate()+"----"+qo.getEndDate());
        qo.setUserType(1);
        qo.setUsername(UserContext.getCurrent().getUsername());
        model.addAttribute("pageResult",this.iplogService.query(qo));
        return "iplog_list";
    }

    /**
     * 后台查询登录日志
     */
    @mgrLoginAnnocation
    @RequestMapping("mgripLog")
    public String mgriplog(@ModelAttribute("qo")IplogQueryObject qo,Model model){
        model.addAttribute("pageResult",iplogService.query(qo));
        return "mgripLog/list";
    }
}
