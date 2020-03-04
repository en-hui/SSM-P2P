package fun.enhui.controller;

import fun.enhui.model.RealAuth;
import fun.enhui.model.Userinfo;
import fun.enhui.query.RealAuthQueryObject;
import fun.enhui.service.RealAuthService;
import fun.enhui.service.UserinfoService;
import fun.enhui.util.JSONResult;
import fun.enhui.util.LoginAnnocation;
import fun.enhui.util.UploadUtil;
import fun.enhui.util.mgrLoginAnnocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;

/**
 * 实名认证
 */
@Controller
public class RealAuthController {

    @Autowired
    private UserinfoService userinfoService;

    @Autowired
    private RealAuthService realAuthService;

    @Autowired
    private ServletContext servletContext;

    @RequestMapping("test")
    public String test(){
        String idNumber = "13012319970421151X";
        String realName = "胡恩会";
        String phoneNumber = "18894961902";
        realAuthService.aliPhoneRealAuth(realName,phoneNumber,idNumber);
        return "";
    }

    /**
     * 进入实名认证页面
     * @param model
     * @return
     */
    @LoginAnnocation
    @RequestMapping("realAuth")
    public String realAuth(Model model){
        //1得到当前userinfo
        Userinfo userinfo = userinfoService.getCurrent();
        //2如果用户已经完成实名认证--有实名认证状态码
        if(userinfo.getIsRealAuth()){
            // 根据userinfo上的realAuthId得到实名认证对象，放到model
            model.addAttribute("realAuth",realAuthService.get(userinfo.getRealAuthId()));
            //将auditing设置为false
            model.addAttribute("auditing",false);
            return "realAuth_result";
        }else {
            //3如果用户没有完成实名认证
            //1 userinfo上有realuthId，将auditing设置为true  待审核
            if(userinfo.getRealAuthId()!=null){
                model.addAttribute("auditing",true);
                return "realAuth_result";
            }else{
                // 2 userinfo上没有realuthId       未认证
                model.addAttribute("userinfo",userinfo);
                return "realAuth";
            }
        }
    }

    /**
     * uploadify文件上传
     */
    @ResponseBody
    @RequestMapping("realAuthUpload")
    public String realAuthUpload(MultipartFile file){
        String basePath = servletContext.getRealPath("/upload");
        String fileName = UploadUtil.upload(file,basePath);
        return "/upload/"+fileName;
    }

    /**
     * 实名认证信息保存
     */
    @LoginAnnocation
    @ResponseBody
    @RequestMapping("realAuth_save")
    public JSONResult realAuth_save(RealAuth realAuth){
        this.realAuthService.apply(realAuth);
        return new JSONResult();
    }


    //后台----实名认证审核相关-----

    /**
     * 后台进入实名认证审核列表
     */
    @mgrLoginAnnocation
    @RequestMapping("mgrrealAuth")
    public String mgrrealAuth(@ModelAttribute("qo")RealAuthQueryObject qo,Model model){
        model.addAttribute("pageResult",this.realAuthService.query(qo));
        return "mgrrealAuth/list";
    }

    /**
     * 后台实名认证审核
     */
    @RequestMapping("mgrrealAuth_audit")
    @ResponseBody
    public JSONResult mgrrealAuthAudit(Long id,String remark,int state){
        this.realAuthService.audit(id,remark,state);
        return new JSONResult();
    }




}
