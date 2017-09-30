package cf.tgtiger.express;

import cf.tgtiger.express.Dao.ExpStationDao;
import cf.tgtiger.express.Dao.ExpStationDaoImpl;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetFirstSk extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        //输出方式
        PrintWriter out = resp.getWriter();

        JSONObject json = new JSONObject();

        //接受请求命令
        String expStaNum = IOUtils.toString(req.getInputStream(),"UTF-8");
        ExpStationDao expdao = new ExpStationDaoImpl();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String oldTime = expdao.getUpdateTime(expStaNum);
        Date time1 = null;
        try {
            time1 = sdf.parse(oldTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int daysBetween = ExpStationDaoImpl.daysBetween(time1, new Date());
        if (daysBetween > 30) {
            expdao.updateFirstKeys(expStaNum);
            String sk = expdao.getFSK(expStaNum);
            json.put("isUpdated", true);
            json.put("privateKey", sk);
        } else {
            String sk = expdao.getFSK(expStaNum);
            json.put("isUpdated", false);
            json.put("privateKey", sk);
        }
        out.print(json.toString());
        out.flush();
        out.close();


    }
}
