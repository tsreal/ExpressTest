package cf.tgtiger.express;

import cf.tgtiger.express.Dao.UserDao;
import cf.tgtiger.express.Dao.UserDaoImpl;
import cf.tgtiger.express.bean.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AddUsers extends HttpServlet {
    // 接受json数据转换为User类
    // 具体传入(所接收)参数为1.expStationNum:快递站点编号 2.phone:待注册人的电话
    // 3.password:待注册人的密码 4.rePassword:第二次重复输入的密码 4.name:姓名
    // 5.level(int类型):权限等级,暂时分为1和2两个等级,若还需再高权限的用户数字可以增加.
    // 其中1为普通用户(派件员等等,用于手机端登陆及扫码)而2为管理员用户可以查询各种快递信息
    //    {
    //    "expStationNum":"SXLL00001",
    //    "phone":"13503581234",
    //    "passwd":"867881879",
    //    "name":"王腾腾",
    //    "rePassword":"867881879",
    //    "level":1
    //    }
    // 传回(发送到客户端)参数为1. info:中文提示信息  2. success:布尔类型判断成功失败
    //    {
    //    "info":"请检查你的密码",
    //    "success":true
    //    }
    //
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        String receive = IOUtils.toString(req.getInputStream(),"UTF-8");
        User user_receive = JSON.parseObject(receive, User.class);
        UserDao userdao = new UserDaoImpl();

        JSONObject json = new JSONObject();
        System.out.println("addUsers操作...");
        if (userdao.usrExist(user_receive.getPhone())) {

            json.put("info", "该手机号已注册.请重新输入新手机号.");
            json.put("success", false);

        }else{
            if (user_receive.getLevel() == 1) {

                System.out.println("准备添加普通用户");
                if (user_receive.getPasswd().equals(user_receive.getRePassword())) {
                    System.out.println("两次密码输入一致");
                    if (userdao.addUsers(user_receive)) {
                        System.out.printf("用户添加成功!");
                        json.put("info", "用户添加成功!");
                        json.put("status", true);
                    } else {
                        System.out.printf("用户添加失败!");
                        json.put("info", "用户添加失败!");
                        json.put("status", false);
                    }
                } else {
                    json.put("info", "两次密码不一样,请检查");
                    json.put("success", false);
                }
            } else if (user_receive.getLevel() == 2) {
                System.out.println("准备添加管理员");
                if (user_receive.getPasswd().equals(user_receive.getRePassword())) {
                    System.out.println("两次密码输入一致");

                    if (userdao.addAdmins(user_receive)) {

                        System.out.printf("管理员添加成功!");
                        json.put("info", "管理员添加成功!");
                        json.put("status", true);
                    } else {
                        System.out.printf("管理员添加失败!");
                        json.put("info", "管理员添加失败!");
                        json.put("status", false);
                    }
                } else {
                    json.put("info", "两次密码不一样,请检查");
                    json.put("success", false);
                }
            } else {
                System.out.printf("Error!");
                json.put("info", "所添加人员的级别异常,请检查后再试");
                json.put("status", false);
            }
        }
        out.print(json.toString());
        out.flush();
        out.close();
    }
}
