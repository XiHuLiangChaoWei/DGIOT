package cn.zz.dgcc.DGIOT.service;

import cn.zz.dgcc.DGIOT.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by: YYL
 * Date: 2020/4/23 11:05
 * ClassExplain :
 * ->
 */
public interface UserService {
    int insert(User user);

    List<User> findAll();

    User Login(String username,String password);

    void changePwd(int userId,String password,String expwd);

    void reg(User user);

    User getByUid(Integer userId);
}
