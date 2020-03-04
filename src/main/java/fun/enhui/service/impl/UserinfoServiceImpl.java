package fun.enhui.service.impl;

import fun.enhui.dao.MailVerifyDao;
import fun.enhui.dao.UserinfoDao;
import fun.enhui.model.MailVerify;
import fun.enhui.model.Userinfo;
import fun.enhui.service.MailService;
import fun.enhui.service.UserinfoService;
import fun.enhui.service.VerifyCodeService;
import fun.enhui.util.BidConst;
import fun.enhui.util.BitStatesUtils;
import fun.enhui.util.DateUtil;
import fun.enhui.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class UserinfoServiceImpl implements UserinfoService {

    @Value("${mail.hostUrl}")
    private String hostUrl;

    @Autowired
    private UserinfoDao userinfoDao;

    @Autowired
    private VerifyCodeService verifyCodeService;

    @Autowired
    private MailService mailService;

    @Autowired
    private MailVerifyDao mailVerifyDao;

    @Override
    public void update(Userinfo userinfo) {
        int ret = this.userinfoDao.updateByPrimaryKey(userinfo);
        if(ret == 0){
            throw new RuntimeException("乐观锁失败，Userinfo:"+userinfo.getId());
        }
    }

    @Override
    public void add(Userinfo userinfo) {
        this.userinfoDao.insert(userinfo);
    }

    @Override
    public Userinfo get(Long id) {
        return this.userinfoDao.selectByPrimaryKey(id);
    }

    @Override
    public void bindPhone(String phoneNumber, String verifyCode) {
        //判断用户是否已经绑定了手机
        Userinfo current = this.get(UserContext.getCurrent().getId());
        if(!current.getIsBindPhone()){
            //验证码验证合法
            boolean ret = this.verifyCodeService.verify(phoneNumber,verifyCode);
            if(ret) {
                //如果合法，给用户绑定手机
                current.addState(BitStatesUtils.OP_BIND_PHONE);
                current.setPhoneNumber(phoneNumber);
                this.update(current);
            }else{
                //抛出异常
                throw new RuntimeException("绑定手机失败");
            }

        }
    }

    @Override
    public void sendVerifyEmail(String email) {
        //判断当前用户没有绑定邮箱
        Userinfo userinfo = this.get(UserContext.getCurrent().getId());
        if(!userinfo.getIsBindEmail()){
            String uuid = UUID.randomUUID().toString().replace("-","");
            //构造一份要发送的邮件
            StringBuilder content = new StringBuilder(100)
                    .append("【海滨贫困帮扶平台】点击<a href='").append(this.hostUrl)
                    .append("bindEmail.do?key=").append(uuid)
                    .append("'>这里</a>完成邮箱绑定，有效期为")
                    .append(BidConst.VERIFYEMAIL_VAILDATE_DAY).append("天");
            try {
                //执行邮件发送
               /* System.out.println("邮件内容为"+content);*/
                mailService.sendMail(email,"邮件认证",content.toString());

                //构造一个MailVerify对象
                MailVerify mailVerify = new MailVerify();
                mailVerify.setEmail(email);
                mailVerify.setSendDate(new Date());
                mailVerify.setUserinfoId(userinfo.getId());
                mailVerify.setUuid(uuid);
                this.mailVerifyDao.insert(mailVerify);

            }catch (Exception e){
                e.printStackTrace();
                throw  new RuntimeException("验证邮箱发送失败！");
            }

        }

    }

    @Override
    public void bindEmail(String uuid) {
        //通过uuid得到mailverify对象
        MailVerify mailVerify = this.mailVerifyDao.selectByUUID(uuid);
        if(mailVerify != null) {
            //判断用户没有绑定邮箱
            Userinfo userinfo = this.get(mailVerify.getUserinfoId());
            if (!userinfo.getIsBindEmail()) {
                //判断有效期     验证邮箱发送时间距离当前时间  小于5天
                if (DateUtil.secondsBetween(mailVerify.getSendDate(), new Date())
                        <= BidConst.VERIFYEMAIL_VAILDATE_DAY * 24 * 60 * 60) {
                    //修改用户状态码，给用户设置邮箱
                    userinfo.addState(BitStatesUtils.OP_BIND_EMAIL);
                    userinfo.setEmail(mailVerify.getEmail());
                    this.update(userinfo);
                    return;
                }
            }
        }
        throw new RuntimeException("绑定邮箱失败！");
    }

    @Override
    public Userinfo getCurrent() {
        return this.get(UserContext.getCurrent().getId());
    }

    @Override
    public void updateBasicInfo(Userinfo userinfo) {
        Userinfo old = this.getCurrent();
        //要修改的内容
        old.setDepartment(userinfo.getDepartment());
        old.setProfession(userinfo.getProfession());
        old.setStudyId(userinfo.getStudyId());
        old.setClazz(userinfo.getClazz());
        //设置用户状态码
        if(!old.getIsBasicInfo()){
            old.addState(BitStatesUtils.OP_BASIC_INFO);
        }
        this.update(old);

    }


}
