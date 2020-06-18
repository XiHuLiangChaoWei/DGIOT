package cn.zz.dgcc.DGIOT.mapper;

import cn.zz.dgcc.DGIOT.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by: YYL
 * Date: 2020/4/23 10:04
 * ClassExplain :
 * ->
 */
@Mapper
@Repository
public interface UserMapper {
    int insertUser(User user);

    List<User> getAll();

    User selectByUserName(String userName);

    User findById(Integer userId);

    Integer updatePasswordByUid(
            @Param("userId") Integer userId,
            @Param("password") String password);

    User selectByUserId(Integer userId);

    int selectCompanyIdByUserId(int userId);
}
