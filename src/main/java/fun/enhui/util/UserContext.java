package fun.enhui.util;

import fun.enhui.model.Logininfo;
import fun.enhui.model.VerifyCodeVO;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * 用于存放当前用户的上下文
 */
public class UserContext {

    public static final String USER_IN_SESSION = "logininfo";
    public static final String VERIFYCODE_IN_SESSION = "verifycode";

    /**
     * 反向获取request方法，请查看RequestContextListener.requestInitialized打包过程
     * @return
     */
    private static HttpSession getSession(){
        //因为配置了请求监听器，所以可以用RequestContextHolder获取请求的信息
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
    }
    //放入
    public static void putCurrent(Logininfo current){
        getSession().setAttribute(USER_IN_SESSION,current);
    }
    //取出
    public static Logininfo getCurrent(){
        //得到session，获取logininfo
        return (Logininfo) getSession().getAttribute(USER_IN_SESSION);
    }

    public static void deleteCurrent(){
        getSession().invalidate();
        //getSession().removeAttribute("USER_IN_SESSION");
    }


    /**
     * 手机验证码相关信息存到session
     * @param vc
     */
    public static void putVerifyCode(VerifyCodeVO vc){
        getSession().setAttribute(VERIFYCODE_IN_SESSION,vc);
    }
    /**
     * 获取session中的验证码
     * @return
     */
    public static VerifyCodeVO getCurrentVerifyCode(){
        return (VerifyCodeVO)getSession().getAttribute(VERIFYCODE_IN_SESSION);
    }
}
