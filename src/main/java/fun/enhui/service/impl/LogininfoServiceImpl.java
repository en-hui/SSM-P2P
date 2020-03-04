package fun.enhui.service.impl;

import fun.enhui.dao.IplogDao;
import fun.enhui.dao.LogininfoDao;
import fun.enhui.model.Account;
import fun.enhui.model.Iplog;
import fun.enhui.model.Logininfo;
import fun.enhui.model.Userinfo;
import fun.enhui.service.AccountService;
import fun.enhui.service.LogininfoService;
import fun.enhui.service.UserinfoService;
import fun.enhui.util.BidConst;
import fun.enhui.util.MD5;
import fun.enhui.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LogininfoServiceImpl implements LogininfoService {

    @Autowired
    private LogininfoDao logininfoDao;

    @Autowired
    private UserinfoService userinfoService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private IplogDao iplogDao;

    @Override
    public void register(String username, String password,String stuNumber,String realName) {
        //判断用户名是否存在
        int count = this.logininfoDao.getCountByUsername(username);
        if(count<=0){
            Logininfo li = new Logininfo();
            li.setUsername(username);
            li.setPassword(MD5.encode(password));
            li.setState(Logininfo.STATE_NORMAL);
            li.setUserType(Logininfo.USER_CLIENT);
            this.logininfoDao.insert(li);
            //初始化账户信息和userinfo
            Account account = new Account();
            account.setId(li.getId());
            this.accountService.add(account);

            Userinfo userinfo = new Userinfo();
            userinfo.setId(li.getId());
            userinfo.setRealName(realName);
            userinfo.setStudyId(stuNumber);
            this.userinfoService.add(userinfo);
        }else{
            throw new RuntimeException("用户名已存在");
        }
    }

    @Override
    public boolean checkUsername(String username) {
        return logininfoDao.getCountByUsername(username)>0;
    }

    @Override
    public Logininfo login(String username, String password,String ip,int userType) {
        Logininfo current = this.logininfoDao.login(username,MD5.encode(password),userType);
        Iplog iplog = new Iplog();
        iplog.setIp(ip);
        iplog.setLoginTime(new Date());
        iplog.setUserName(username);
        iplog.setUserType(userType);
        if(current != null){
            iplog.setState(Iplog.STATE_SUCCESS);
            //放到UserContext中
            UserContext.putCurrent(current);
        }else{
            iplog.setState(Iplog.STATE_FAILED);
        }
        this.iplogDao.insert(iplog);
        return current;
    }

    @Override
    public void initAdmin() {
        //查询是否有管理员
        int count = this.logininfoDao.countByUserType(Logininfo.USER_MANAGER);
        //如果没有就创建一个
        if(count == 0){
            Logininfo admin = new Logininfo();
            admin.setUsername(BidConst.DEFAULT_ADMIN_USERNAME);
            admin.setPassword(MD5.encode(BidConst.DEFAULT_ADMIN_PASSWORD));
            admin.setState(Logininfo.STATE_NORMAL);
            admin.setUserType(Logininfo.USER_MANAGER);
            this.logininfoDao.insert(admin);
        }
    }

    @Override
    public void checkUser(String stuNumber, String realName) {
        int count = logininfoDao.checkUser(stuNumber,realName);
        //count==0说明注册人填写信息不对，
        if(count==0){
            throw new RuntimeException("学号和姓名不匹配或官方库没有相关学生信息");
        }
    }
}
