package fun.enhui.service.impl;

import fun.enhui.model.VerifyCodeVO;
import fun.enhui.service.VerifyCodeService;
import fun.enhui.util.BidConst;
import fun.enhui.util.DateUtil;
import fun.enhui.util.UserContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.UUID;


@Service
public class VerifyCodeServiceImpl implements VerifyCodeService {

    @Value("${sms.username}")
    private String username;
    @Value("${sms.password}")
    private String password;
    @Value("${sms.apikey}")
    private String apikey;
    @Value("${sms.url}")
    private String url;

    @Override
    public void sendVerifyCode(String phoneNumber) {
        //判断当前是否能够发送短信
        //从session中获得最后一次成功发送的时间
        VerifyCodeVO vc = UserContext.getCurrentVerifyCode();
        if(vc == null || DateUtil.secondsBetween(new Date(),vc.getLastSendTime())>90){
            //正常发送验证码短信
            //生成一个验证码
            String verifyCode = UUID.randomUUID().toString().substring(0,4);
            //发送短信
            /*-----------------------------------------------------------------------*/
            System.out.println(verifyCode);

            try {
                //创建一个url   相当于输入地址
                System.out.println(url);
                URL url = new URL(this.url);
                //通过URL得到一个HTTPURLConnection连接对象    相当于按了下回车
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //拼接POST请求的内容
                StringBuilder content = new StringBuilder(100)
                        .append("username=").append(username)
                        .append("&password_md5=").append(password)
                        .append("&apikey=").append(apikey).append("&mobile=")
                        .append(phoneNumber).append("&encode=UTF-8").append("&content=")
                        .append("【海滨贫困帮扶平台】验证码是:").append(verifyCode).append(",请在5分钟内使用");
                //发送post请求,请求方式一定要大写   GET或者POST
                conn.setRequestMethod("POST");
                //设置POST请求是有请求体的
                conn.setDoOutput(true);
                //写入POST请求体
                conn.getOutputStream().write(content.toString().getBytes());
                //得到响应流（其实就是已经发送了）
                String response = StreamUtils.copyToString(conn.getInputStream(),Charset.forName("UTF-8"));
                if(response.startsWith("success:")){
                    //发送成功
                    //把手机号，验证码，发送时间装配到vo并保存到session
                    vc = new VerifyCodeVO();
                    vc.setLastSendTime(new Date());
                    vc.setPhoneNumber(phoneNumber);
                    vc.setVerifyCode(verifyCode);
                    UserContext.putVerifyCode(vc);
                }else{
                    //发送失败
                    throw new RuntimeException();
                }


            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("发送短信失败！");
            }
            /*-----------------------------------------------------------------------*/
            //把手机号，验证码，发送时间装配到vo并保存到session
            /*vc = new VerifyCodeVO();
            vc.setLastSendTime(new Date());
            vc.setPhoneNumber(phoneNumber);
            vc.setVerifyCode(verifyCode);
            UserContext.putVerifyCode(vc);*/
        }else{
            throw new RuntimeException("发送过于频繁");
        }
    }

    @Override
    public boolean verify(String phoneNumber, String verifyCode) {
        VerifyCodeVO vc = UserContext.getCurrentVerifyCode();
        if(vc!=null &&
                vc.getPhoneNumber().equals(phoneNumber) &&
                vc.getVerifyCode().equalsIgnoreCase(verifyCode) &&
                DateUtil.secondsBetween(new Date(),vc.getLastSendTime())<=BidConst.VERIFYCODE_VAILDATE_SECOND){
            return true;
        }
        return false;
    }
}
