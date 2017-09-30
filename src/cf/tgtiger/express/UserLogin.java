package cf.tgtiger.express;

import cf.tgtiger.express.Dao.ExpStationDaoImpl;
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

public class UserLogin extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    // 接受json数据转换为User类
    // 具体传入(所接收)参数为1.phone:帐号名 2.password:密码
//       {
//        "phone":"13503581234",
//        "passwd":"867881879"
//        }
    // 传回(发送到客户端)参数为1. info:中文提示信息  2. success:布尔类型判断成功失败
    //    {
    //    "info":"请检查你的密码",
    //    "success":true,
    //    "expStationNum":"sxll00001",
    //    "name": "王腾腾",
    //    "level":2
    //    }
    //
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        JSONObject json = new JSONObject();
        String receive = IOUtils.toString(req.getInputStream(),"UTF-8");
        User user_rec = JSON.parseObject(receive, User.class);
        UserDao dao = new UserDaoImpl();

        if (dao.usrExist(user_rec.getPhone())) {
            if (dao.getPasswd(user_rec.getPhone()).equals(user_rec.getPasswd())) {
                System.out.printf("密码一样");
                User user = dao.getUserInfo(user_rec.getPhone());
                json.put("expStationNum", user.getExpStationNum());
                json.put("name", user.getName());
                json.put("level", user.getLevel());
                json.put("info", "验证登录成功");
                json.put("success", true);
                json.put("expStationName", new ExpStationDaoImpl().getStaName(user.getExpStationNum()));
            } else {
                json.put("info", "请检查你的密码");
                json.put("success", false);
            }
        } else {
            json.put("info","帐号不存在,请去桌面客户端注册");
            json.put("success", false);
        }
        out.print(json.toString());
        out.flush();
        out.close();
    }
}

