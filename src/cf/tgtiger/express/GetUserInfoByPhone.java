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
import java.util.List;

public class GetUserInfoByPhone extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        String receive = IOUtils.toString(req.getInputStream(), "UTF-8");
        JSONObject json_rec = JSON.parseObject(receive);
        String phone = json_rec.getString("phone");
        String expStationNum = json_rec.getString("expStationNum");
        JSONObject json = new JSONObject();

        UserDao dao = new UserDaoImpl();
        if (dao.usrExist(phone)) {
            if (dao.getLevel(phone) == 2) {

                List<User> users;
                List<User> admins;
                users = dao.getAllUsers(expStationNum);
                admins = dao.getAllAdmins(expStationNum);
                json.put("users", users);
                json.put("admins", admins);
                json.put("info", "已发出用户及管理员数据");
                json.put("success", true);
            } else {
                json.put("info", "权限不够,请检查用户权限");
                json.put("success", false);
            }
        } else {
            json.put("info", "用户不存在");
            json.put("success", false);
        }

        out.print(json.toString());
        out.flush();
        out.close();

    }
}
