package cf.tgtiger.express.Dao;

import cf.tgtiger.express.bean.User;

import java.util.ArrayList;
import java.util.List;

public interface UserDao {

    boolean usrExist(String phone);

    User loginDao(String account);

    boolean addUsers(User user);

    boolean addAdmins(User user);

    boolean updatePassword(String phone,String passwd);

    ArrayList<User> getAllUsers(String expstationnum);

    ArrayList<User> getAllAdmins(String expstationnum);

    String getPasswd(String phone);

    User getUserInfo(String phone);

    int getLevel(String phone);






}
