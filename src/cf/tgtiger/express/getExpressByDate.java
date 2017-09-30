package cf.tgtiger.express;

import cf.tgtiger.express.Dao.ExpStationDaoImpl;
import cf.tgtiger.express.Dao.ExpressDAOImpl;
import cf.tgtiger.express.Dao.ExpressDao;
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

public class getExpressByDate extends HttpServlet {

    // 接受json数据
    // 具体传入(所接收)参数为1.date:所查快递列表的日期 2.expStationNum:快递站点
    // 3.pageNum:(实际当前页数)页面编号,目前每页为三条记录
    // 4.type:类型(0为以该站点为初始站点<startingNum>,1为以该站点为终点站<terminalNum>)
    // {
    //         "date":"2017-8-22",
    //         "expStationNum":"sxty11110",
    //         "pageNum":1,
    //         "type":0
    // }
    // 传回(发送到客户端)参数为1. pageNum:当前查询页  2. pageSize:分页大小 3.totalPages:总页数 4.totalRecords: 总记录条数
    // 5.list: Express对象数组 6.success: 布尔型判断
    //
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        JSONObject json = new JSONObject();
        JSONObject json_rec = JSON.parseObject(IOUtils.toString(req.getInputStream(),"UTF-8"));
        String date = json_rec.getString("date");
        String expstationnum = json_rec.getString("expStationNum");
        int pagenum = json_rec.getInteger("pageNum");
        int type = json_rec.getInteger("type");

        if (new ExpStationDaoImpl().staExist(expstationnum)) {
            //当前查询页
            json.put("pageNum", pagenum);


            int pagesize = 3;
         //分页大小
            json.put("pageSize", pagesize);


            ExpressDao dao = new ExpressDAOImpl();
            int totalrecords = new ExpressDAOImpl().getCounts(date, expstationnum, type);
            int totalpages = totalrecords % pagesize == 0 ? (totalrecords / pagesize) : (totalrecords / pagesize + 1);
        //所分页数
            //
            json.put("totalPages", totalpages);
        //总记录条数
            //
            json.put("totalRecords", totalrecords);

            List<Express> list;

            list = dao.getExpressByDate(date, expstationnum, pagenum, pagesize, type);

        //每页的快递信息
            json.put("list", list);

            json.put("success", true);
        }else{
            json.put("info", "该站点不存在");
            json.put("success", false);
        }

        out.print(json.toString());
        out.flush();
        out.close();
    }
}
