package cf.tgtiger.express;

import cf.tgtiger.express.Dao.*;
import cf.tgtiger.express.bean.ExpStation;
import cf.tgtiger.express.bean.ExpressTrack;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jdk.management.resource.internal.inst.SocketOutputStreamRMHooks;
import sun.misc.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UpdateTrack extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接受参数:ExpressTrack类 : 1.expressNum 2.expStationNum 3.expStationName
        //返回: 1.info 中文提示 2.expFullTrack 中文全部轨迹信息

        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        String receive = org.apache.commons.io.IOUtils.toString(req.getInputStream(),"UTF-8");
        ExpressTrack eptrack;
        eptrack = JSON.parseObject(receive, ExpressTrack.class);

        JSONObject json = new JSONObject();

        ExpressDao expdao = new ExpressDAOImpl();

        ExpTrackDao eptrackdao = new ExpTrackDaoImpl();
        if (expdao.getStartingNum(eptrack.getExpressNum()) != null) {
            //如果传来参数的快递编号对应的初始站点编号与该参数的站点编号一致,则创建一条轨迹
            if (eptrack.getExpStationNum().equals(expdao.getStartingNum(eptrack.getExpressNum()))) {

                eptrackdao.addTrack(eptrack);
                json.put("info", "轨迹创建成功");
                json.put("success", true);
//            json.put("expressNum", eptrack.getExpressNum());
//            json.put("expStaNum", eptrack.getExpStationNum());
//            json.put("expStaName", eptrack.getExpStationName());
                //返回至目前该站点为止的轨迹详情
                json.put("expFullTrack", eptrackdao.getFullTrack(eptrack.getExpressNum()));
                System.out.printf("轨迹创建成功!"
                        + "\n快递单号:" + eptrack.getExpressNum()
                        + "\n当前站点编号:" + eptrack.getExpStationNum()
                        + "\n当前站点名称:" + eptrack.getExpStationName()
                        + "\n目前轨迹:" + eptrackdao.getFullTrack(eptrack.getExpressNum()));

            } else {
                //如果不一致,则说明数据库中已插入数据,需更新操作
                eptrackdao.updateTrack(eptrack);
                json.put("info", "轨迹更新成功");
                json.put("success", true);
//            json.put("expressNum", eptrack.getExpressNum());
//            json.put("expStaNum", eptrack.getExpStationNum());
//            json.put("expStaName", eptrack.getExpStationName());
                json.put("expFullTrack", eptrackdao.getFullTrack(eptrack.getExpressNum()));
                System.out.printf("轨迹已更新!"
                        + "\n快递单号:" + eptrack.getExpressNum()
                        + "\n当前站点编号:" + eptrack.getExpStationNum()
                        + "\n当前站点名称:" + eptrack.getExpStationName()
                        + "\n目前轨迹:" + eptrackdao.getFullTrack(eptrack.getExpressNum()));
            }

        } else {
            json.put("info", "未查找到该快递单号信息");
            json.put("success", false);
        }
        out.print(json.toString());
        out.flush();
        out.close();
    }
}
