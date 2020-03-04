package fun.enhui.controller;

import fun.enhui.model.Studentinfo;
import fun.enhui.query.UserQueryObject;
import fun.enhui.service.UserService;
import fun.enhui.util.JSONResult;
import fun.enhui.util.mgrLoginAnnocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 后台对通过实名认证的用户的操作
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 后台查看所有实名用户
     * @param qo
     * @param model
     * @return
     */
    @mgrLoginAnnocation
    @RequestMapping("real_auth_list")
    public String userList(@ModelAttribute("qo")UserQueryObject qo, Model model){
        model.addAttribute("pageResult",this.userService.queryUser(qo));
        return "mgruser/mgruserlist";
    }


    /**
     * 后台对数据库学生官方数据进行操作
     */
    @mgrLoginAnnocation
    @RequestMapping("studentinfo")
    public String studentinfo(@ModelAttribute("qo")UserQueryObject qo, Model model){
        model.addAttribute("pageResult",this.userService.querystudent(qo));
        return "mgruser/mgrstudentinfo";
    }

    /**
     * 后台添加学生信息
     */
    @ResponseBody
    @mgrLoginAnnocation
    @RequestMapping("addstudentinfo")
    public JSONResult addStudentInfo(Studentinfo studentinfo){
        JSONResult jsonResult = new JSONResult();
        try{
            userService.addStudentinfo(studentinfo);
        }catch (Exception e){
            jsonResult.setSuccess(false);
            jsonResult.setMsg(e.getMessage());
        }

        return jsonResult;
    }
}
