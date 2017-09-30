package cf.tgtiger.express;

import cf.tgtiger.express.Dao.UserDao;
import cf.tgtiger.express.Dao.UserDaoImpl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UpdatePassword extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接受参数: 1.phone 2.oldPassword 3.newPassword 4.rePassword
        //返回参数: 1.info  2.success
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        String receive = IOUtils.toString(req.getInputStream(), "UTF-8");
        JSONObject json_rec = JSON.parseObject(receive);
        String phone = json_rec.getString("phone");
        String oldpassword = json_rec.getString("oldPassword");
        String newPassword = json_rec.getString("newPassword");
        String rePassword = json_rec.getString("rePassword");

        JSONObject json = new JSONObject();
        UserDao dao = new UserDaoImpl();
        if (oldpassword.equals(dao.getPasswd(phone))) {
            System.out.println("密码校验成功");
            if (newPassword.equals(rePassword)) {
                dao.updatePassword(phone, newPassword);
                json.put("info", "密码修改成功");
                json.put("success", true);
                System.out.println("密码修改成功");
            } else {
                System.out.println("请检查两次输入的密码是否一致");
                json.put("info", "请检查两次输入的密码是否一致");
                json.put("success", false);
            }
        } else {
            json.put("info", "原始密码错误");
            json.put("success", false);
            System.out.println("原始密码错误");
        }
        out.print(json.toString());
        out.flush();
        out.close();
    }
}

