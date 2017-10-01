package cf.tgtiger.express;

import cf.tgtiger.express.API.Distance;
import cf.tgtiger.express.Dao.ExpStationDao;
import cf.tgtiger.express.Dao.ExpStationDaoImpl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class GetDistance extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        String receive = IOUtils.toString(req.getInputStream(), "UTF-8");
        JSONObject json_rec = JSON.parseObject(receive);
        String destination = json_rec.getString("destination_geocodes");
        String province = json_rec.getString("province");
        String city = json_rec.getString("city");
        String area = json_rec.getString("area");
        ExpStationDao dao = new ExpStationDaoImpl();

        JSONObject json = new JSONObject();
        json = Distance.GetDistance(destination,province,city,area);
        out.print(json.toString());
        out.flush();
        out.close();

    }
}
