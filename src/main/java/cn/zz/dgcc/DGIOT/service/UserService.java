package cn.zz.dgcc.DGIOT.service;

import cn.zz.dgcc.DGIOT.entity.User;

import java.util.List;

/**
 * Created by: LT001
 * Date: 2020/4/23 11:05
 * ClassExplain :
 * ->
 */
public interface UserService {
    int insert(User user);

    List<User> findAll();

    User Login(String username,String password);

    void changePwd(int userId,String password,String expwd);

    int reg(User user);

    User getByUid(Integer userId);

    int getCompanyIdByUserId(int userId);

    int logout(Integer userIdFromSession);
}
