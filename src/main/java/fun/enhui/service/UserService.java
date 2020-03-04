package fun.enhui.service;

import fun.enhui.model.Studentinfo;
import fun.enhui.query.PageResult;
import fun.enhui.query.UserQueryObject;

public interface UserService {

    /**
     * 后台查询用户
     * @param qo
     * @return
     */
    PageResult queryUser(UserQueryObject qo);

    /**
     * 后台管理数据库官方数据
     * @param qo
     * @return
     */
    PageResult querystudent(UserQueryObject qo);

    /**
     * 添加学生
     * @param studentinfo
     */
    void addStudentinfo(Studentinfo studentinfo);
}
