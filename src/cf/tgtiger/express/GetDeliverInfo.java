package cf.tgtiger.express;

import cf.tgtiger.express.Dao.ExpTrackDaoImpl;
import cf.tgtiger.express.Dao.UserDaoImpl;
import cf.tgtiger.express.bean.Express;
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

public class GetDeliverInfo extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接受参数: 1. date 2. phone
        //返回参数: 1. expressList 2. success 3. info
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        String receive = IOUtils.toString(req.getInputStream());
        JSONObject json_rec = JSON.parseObject(receive);
        String date = json_rec.getString("date");
        String expressman = json_rec.getString("phone");
        List<Express> list;
        JSONObject json = new JSONObject();

        if (new UserDaoImpl().usrExist(expressman)) {
            list = new ExpTrackDaoImpl().getDeliverInfo(date, expressman);
            if (list.isEmpty()) {

                json.put("success", false);
                json.put("info", "未找到派送订单");
            } else {
                json.put("info", "查询成功");
                json.put("expressList", list);
                json.put("success", true);
            }
        } else {
            json.put("info","用户不存在");
            json.put("success", false);
        }
        out.print(json.toString());
        out.flush();
        out.close();
    }
}
