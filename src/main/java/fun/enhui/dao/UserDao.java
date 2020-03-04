package fun.enhui.dao;

import fun.enhui.model.Studentinfo;
import fun.enhui.model.Userinfo;
import fun.enhui.query.UserQueryObject;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {


    @Select("<script>" +
            "select count(id) from userinfo where realAuthId is not null " +
            "<if test='studyId!=null'>AND studyId = #{studyId}</if>" +
            "</script>")
    int queryUserForCount(UserQueryObject qo);

    @Select("<script>" +
            "select * from userinfo where realAuthId is not null "+
            "<if test='studyId!=null'>AND studyId = #{studyId}</if>"+
            "        LIMIT #{start},#{pageSize}   "+
            "</script>")
    List<Userinfo> queryUser(UserQueryObject qo);




    @Select("<script>" +
            "select count(stuid) from studentinfo where 1=1  " +
             "<if test='studyId!=null'> AND stuid like concat('%',#{studyId},'%')</if>" +
            "</script>")
    int queryStudentForCount(UserQueryObject qo);


    @Select("<script>" +
                "select * from studentinfo where 1=1  "+
                "<if test='studyId!=null'> AND stuid like concat('%',#{studyId},'%') </if> " +
                " order by stuid  LIMIT #{start},#{pageSize}" +
            "</script>")
    List<Studentinfo> queryStudent(UserQueryObject qo);

    @Insert("insert into studentinfo (stuid,stuname) values(#{stuid},#{stuname})")
    void addStudentinfo(Studentinfo studentinfo);

    @Select("select count(stuid) from studentinfo where stuid = #{stuid}")
    int selectByStuId(Studentinfo studentinfo);
}
