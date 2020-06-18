package cn.zz.dgcc.DGIOT.service.impl;

import cn.zz.dgcc.DGIOT.entity.User;
import cn.zz.dgcc.DGIOT.mapper.UserMapper;
import cn.zz.dgcc.DGIOT.service.Exception.ISqlException;
import cn.zz.dgcc.DGIOT.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by: YYL
 * Date: 2020/4/23 11:05
 * ClassExplain :
 * ->
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    private final Logger log = Logger.getLogger(this.getClass().getSimpleName());

    public static void main(String[] args) {
        UserServiceImpl u = new UserServiceImpl();
        String i = u.MD5Password("admin", "37C146C743BF");
        System.err.println(i);
    }

    @Resource
    UserMapper userMapper;

    private String MD5Password(String password, String salt) {
        password = DigestUtils.md5DigestAsHex((password + salt).getBytes());
        return password;
    }

    public User getByUserName(String userName) {
        User user = userMapper.selectByUserName(userName);
        return user;
    }

    /**
     * 插入新用户
     *
     * @param user
     * @return
     */
    @Override
    public int insert(User user) {

        String username = user.getUserName();
        User r1 = getByUserName(username);

        if (user.getUserName().equals("")) {
            throw new ISqlException("用户名不能为空");
        }

        if (user.getPassword().equals("")) {
            throw new ISqlException("密码不能为空");
        }

        if (r1 != null) {
            throw new ISqlException("用户名" + username + "已存在");
        }
        String salt = UUID.randomUUID().toString().toUpperCase();
        String password = user.getPassword();
        user.setSalt(salt);
        user.setPassword(MD5Password(password, salt));
        int result = userMapper.insertUser(user);
        if (result != 1) {
            throw new ISqlException("未知错误");
        }
        return result;
    }

    /**
     * 获取用户列表
     *
     * @return
     */
    @Override
    public List<User> findAll() {
        List<User> list = userMapper.getAll();
        return list;
    }

    /**
     * 登陆
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public User Login(String username, String password) {
        User result = getByUserName(username);
        if (result == null) {
            throw new ISqlException("用户未找到");
        }
        if (result.getIsDelete() == 1) {
            throw new ISqlException("用户未找到");
        }
        String salt = result.getSalt();
        if (!MD5Password(password, salt).equals(result.getPassword())) {
            throw new ISqlException("密码错误");
        }
        User user = new User(result.getCompanyId(), result.getType(), result.getUserId());

        result.setSalt(null);
        return result;
    }

    /**
     * 修改密码
     *
     * @param userId
     * @param password
     * @param expwd
     */
    @Override
    public void changePwd(int userId, String password, String expwd) {
        if (password.equals("")) {
            throw new ISqlException("新密码不能为空");
        }
        User result = userMapper.findById(userId);
        if (result == null) {
            throw new ISqlException("用户不存在");
        }
        if (result.getIsDelete() == 1) {
            throw new ISqlException("用户不存在");
        }
        if (!MD5Password(expwd, result.getSalt()).equals(result.getPassword())) {
            throw new ISqlException("原密码错误");
        }
        String salt = result.getSalt();
        Integer rows = userMapper.updatePasswordByUid(
                userId, MD5Password(password, salt));
        if (!(rows == 1)) {
            throw new ISqlException("修改用户密码时，出现未知错误");
        }
    }

    @Override
    public void reg(User user) {
        String username = user.getUserName();
        User result = userMapper.selectByUserName(username);
        log.info(user.getUserName());
        if (user.getUserName().equals("")) {
            throw new ISqlException("用户名不能为空");
        }
        if (user.getPassword().equals("")) {
            throw new ISqlException("密码不能为空");
        }
        if (result != null) {
            throw new ISqlException("用户名" + username + "已存在");
        }

        String salt = UUID.randomUUID().toString().toUpperCase();
        String password = user.getPassword();
        user.setSalt(salt);
        user.setPassword(MD5Password(password, salt));
        user.setIsDelete(0);
        Integer rows = userMapper.insertUser(user);
        if (rows != 1) {
            throw new ISqlException("插入不知道出了啥错");
        }
    }


    public String generatePassword(String password) {
        String salt = UUID.randomUUID().toString().toUpperCase();
        String ps = MD5Password(password, salt);
        return ps;
    }


    @Override
    public User getByUid(Integer userId) {
        User rs = userMapper.selectByUserId(userId);
        return rs;
    }

    @Override
    public int getCompanyIdByUserId(int userId) {
        return userMapper.selectCompanyIdByUserId(userId);
    }

}
