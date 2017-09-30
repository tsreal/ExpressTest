package cf.tgtiger.express.Dao;

import cf.tgtiger.express.bean.User;
import com.sun.org.apache.regexp.internal.RE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;


    //用户注册前帐号验证
    @Override
    public boolean usrExist(String phone) {
        String sql = "SELECT count(*) FROM express.exp_station_users WHERE phone = ?;";

        try {
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, phone);
            rs = pstmt.executeQuery();
            rs.next();
            if (rs.getInt(1)==1) {
                System.out.println("号码与库中匹配成功");
                return true;
            } else {
                System.out.println("库中未找到相同号码");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.printf("检索用户失败");
            return false;
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);
        }

    }

    //通过账户帐号返回账户信息作为登录servlet识别
    public User loginDao(String account) {
        User user = null;

        String sql = "SELECT * FROM exp_station_users WHERE account = ?";
        try {
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, account);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setPasswd(rs.getString("passwd"));
                user.setName(rs.getString("name"));
                user.setPhone("phone");
                user.setLevel(rs.getInt("level"));
                user.setExpStationNum("expStationNum");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);
        }
        return user;
    }

    //添加站点普通用户
    public boolean addUsers(User user) {
        String sql = "INSERT INTO exp_station_users(phone, passwd, name, level, expStationNum) VALUES(?,?,?,1,?);";

        try {
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getPhone());
            pstmt.setString(2, user.getPasswd());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getExpStationNum());
            pstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean addAdmins(User user) {
          String sql = "INSERT INTO exp_station_users(phone, passwd, name, level, expStationNum) VALUES(?,?,?,2,?);";

        try {
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getPhone());
            pstmt.setString(2, user.getPasswd());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getExpStationNum());
            pstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }


    //暂时不添加权限查找
    public ArrayList<User> getAllUsers(String expstationnum) {
        ArrayList<User> list = new ArrayList<User>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM exp_station_users WHERE level = 1 AND expStationNum = ? ";

        try {
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, expstationnum);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                User user = new User();
//                user.setPasswd(rs.getString("passwd"));
                user.setName(rs.getString("name"));
                user.setPhone(rs.getString("phone"));
                user.setLevel(rs.getInt("level"));
//                user.setExpStationNum(rs.getString("expStationNum"));

                //添加到用户数组
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);
        }
        return list;
    }

    public ArrayList<User> getAllAdmins(String expstationnum) {
        ArrayList<User> list = new ArrayList<User>();
        String sql = "SELECT * FROM exp_station_users WHERE level = 2 AND expStationNum = ? ";

        try {
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, expstationnum);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                User user = new User();
//                user.setPasswd(rs.getString("passwd"));
                user.setName(rs.getString("name"));
                user.setPhone(rs.getString("phone"));
                user.setLevel(rs.getInt("level"));
//                user.setExpStationNum(rs.getString("expStationNum"));
                //添加到用户数组
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);
        }
        return list;
    }

    public boolean updatePassword(String phone, String passwd) {
        String sql = "UPDATE exp_station_users SET passwd = ? WHERE phone = ?;";
        try {
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, passwd);
            pstmt.setString(2, phone);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);
        }
        return false;
    }
    @Override
    public String getPasswd(String phone) {
        String sql = "SELECT passwd FROM exp_station_users WHERE phone = ?";
        String password = null;
        try {
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, phone);
            rs = pstmt.executeQuery();
            rs.next();
            password = rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);
        }
        return password;
    }

    @Override
    public User getUserInfo(String phone) {
        String password = null;
        String sql = "SELECT phone,passwd,name,level,expStationNum FROM exp_station_users WHERE phone = ?";
        User user = new User();
        try {
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, phone);
            rs = pstmt.executeQuery();
            rs.next();
            user.setPhone(rs.getString(1));
            user.setPasswd(rs.getString(2));
            user.setName(rs.getString(3));
            user.setLevel(rs.getInt(4));
            user.setExpStationNum(rs.getString(5));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);
        }
        return user;
    }

    @Override
    public int getLevel(String phone) {
        int level = 0;
        try {
            String sql = "SELECT level from express.exp_station_users WHERE phone = ?";
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, phone);
            rs = pstmt.executeQuery();
            rs.next();
            level = rs.getInt(1);
            System.out.println("权限查询成功");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("权限查询失败");
        } finally {
            DBUtil.closeAll(rs,pstmt,conn);
        }
        return level;
    }


}
