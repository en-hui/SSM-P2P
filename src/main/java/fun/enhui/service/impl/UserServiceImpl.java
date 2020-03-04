package fun.enhui.service.impl;

import fun.enhui.dao.UserDao;
import fun.enhui.model.Studentinfo;
import fun.enhui.model.Userinfo;
import fun.enhui.query.PageResult;
import fun.enhui.query.UserQueryObject;
import fun.enhui.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Override
    public PageResult queryUser(UserQueryObject qo) {

        int count = this.userDao.queryUserForCount(qo);
        if(count>0){
            List<Userinfo> list = this.userDao.queryUser(qo);
            return new PageResult(list,count,qo.getCurrentPage(),qo.getPageSize());
        }
        return PageResult.empty(qo.getPageSize());
    }

    @Override
    public PageResult querystudent(UserQueryObject qo) {
        int count = this.userDao.queryStudentForCount(qo);
        if(count>0){
            List<Studentinfo> list = this.userDao.queryStudent(qo);
            return new PageResult(list,count,qo.getCurrentPage(),qo.getPageSize());
        }
        return PageResult.empty(qo.getPageSize());
    }

    @Override
    public void addStudentinfo(Studentinfo studentinfo) {
        int count = userDao.selectByStuId(studentinfo);
        if(count > 0){//已存在
            throw new RuntimeException("此学生信息已存在");
        }else{
            userDao.addStudentinfo(studentinfo);
        }

    }
}
