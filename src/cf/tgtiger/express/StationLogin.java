package cf.tgtiger.express;

import cf.tgtiger.express.Dao.ExpStationDao;
import cf.tgtiger.express.Dao.ExpStationDaoImpl;
import cf.tgtiger.express.bean.ExpStation;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class StationLogin extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接受json数据转换为ExpStation类
        //具体数据:{"expStationNum":"SXLL00001",
        //          "password":"111111"}
        //传回参数为info:中文提示信息
        //和success:布尔类型判断成功失败
        //{"info":"请检查你的密码",
        // "success":true}
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        JSONObject json = new JSONObject();
        String receive = IOUtils.toString(req.getInputStream(), "UTF-8");
        ExpStation es_rec = JSON.parseObject(receive, ExpStation.class);
        ExpStationDao dao = new ExpStationDaoImpl();
        if (dao.staExist(es_rec.getExpStationNum())) {
            if (dao.getPasswd(es_rec.getExpStationNum()).equals(es_rec.getPassword())) {
                json.put("info", "验证成功");
                json.put("success", true);
            } else {
                json.put("info", "请检查你的密码");
                json.put("success", false);
            }
        } else {
            json.put("info","请检查你的帐号");
            json.put("success", false);
        }
        out.print(json.toString());
        out.flush();
        out.close();

    }
}
