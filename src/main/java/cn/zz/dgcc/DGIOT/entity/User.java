package cn.zz.dgcc.DGIOT.entity;

import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by: YYL
 * Date: 2020/4/23 10:07
 * ClassExplain :
 * ->
 */
public class User{
    private int userId;
    private String userName;
    private String password;
    private int companyId;
    private String type;
    private String salt;
    private String phone;
    private String name;
    private int isDeleted;
    public User() {

    }

    public User(int companyId, String type, int userId) {
        this.companyId = companyId;
        this.type = type;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public int getIsDelete() {
        return isDeleted;
    }

    public void setIsDelete(int isDelete) {
        this.isDeleted = isDelete;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", companyId='" + companyId + '\'' +
                ", type='" + type + '\'' +
                ", salt='" + salt + '\'' +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(password, user.password) &&
                Objects.equals(companyId, user.companyId) &&
                Objects.equals(type, user.type) &&
                Objects.equals(salt, user.salt) &&
                Objects.equals(phone, user.phone) &&
                Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, password, companyId, type, salt, phone, name);
    }
}
