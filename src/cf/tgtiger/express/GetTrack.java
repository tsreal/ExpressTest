package cf.tgtiger.express;

import cf.tgtiger.express.Dao.ExpTrackDaoImpl;
import cf.tgtiger.express.Dao.ExpressDAOImpl;
import cf.tgtiger.express.bean.ExpressTrack;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class GetTrack extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        JSONObject json = new JSONObject();
        JSON.toJSONString(json, SerializerFeature.DisableCircularReferenceDetect);
        String receive = IOUtils.toString(req.getInputStream(), "UTF-8");
        System.out.println(receive);
        JSONObject json_rec = JSON.parseObject(receive);
        String expNum = json_rec.getString("expressNum");



        if (new ExpTrackDaoImpl().expExist(expNum)) {
            String trackInfo = new ExpTrackDaoImpl().getFullTrack(expNum);
            List<ExpressTrack.DataBean> data = new ArrayList<>();
            if (new ExpTrackDaoImpl().getExpLocate(expNum).equals(new ExpressDAOImpl().getStartingNum(expNum))) {
                String time = trackInfo.substring(0, 19);
                String context = trackInfo.substring(20);
                ExpressTrack.DataBean db = new ExpressTrack.DataBean();
                db.setContext(context + "已揽件");
                db.setTime(time);
                data.add(db);
                json.put("data", data);
                json.put("info", "轨迹送达");
                json.put("status", true);
                System.out.println("1");
            } else if (new ExpTrackDaoImpl().getExpLocate(expNum).equals(new ExpressDAOImpl().getTerminalNum(expNum))) {
                String[] split = trackInfo.split("->");
                System.out.println(trackInfo);
                System.out.println("length:" + split.length);
                for (int i = 0; i < split.length; i++) {
                    String time = split[i].substring(0, 19);
                    String context = split[i].substring(20);
                    ExpressTrack.DataBean db = new ExpressTrack.DataBean();
                    db.setTime(time);
                    if (i == 0) {
                        db.setContext("[" + context + "]起始站已揽件");
                    } else if (i < (split.length - 1)) {
                        db.setContext("[" + context + "]中转站已收件");
                    } else if (i == split.length - 1) {
                        if (new ExpTrackDaoImpl().existExpressman(expNum)) {
                            //判断是否开始派送
                            db.setContext("[" + context + "]终点站已收件,派件员["
                                    + new ExpTrackDaoImpl().getExpressmanName(expNum)
                                    + "]已出发,联系电话["
                                    + new ExpTrackDaoImpl().getExressmanPhone(expNum) + "]");
                        } else {
                            System.out.println("还未派送");
                            db.setContext("[" + context + "]终点站已收件,等待派送");
                        }
                    }
                    data.add(db);
                }
                System.out.println("2");
                json.put("data", data);
                json.put("info", "信息发出");
                json.put("status", true);
                System.out.println("轨迹信息送达");
            } else {
                String[] split = trackInfo.split("->");
                System.out.println(trackInfo);
                System.out.println("length:" + split.length);
                for (int i = 0; i < split.length; i++) {
                    String time = split[i].substring(0, 19);
                    String context = split[i].substring(20);
                    ExpressTrack.DataBean db = new ExpressTrack.DataBean();
                    db.setTime(time);
                    if (i == 0) {
                        db.setContext("[" + context + "]起始站已揽件");
                    } else if (i <= (split.length - 1)) {
                        db.setContext("[" + context + "]中转站已收件");
                    }
                    data.add(db);
                }
                System.out.println("3");
                json.put("data", data);
                json.put("info", "信息发出");
                json.put("status", true);
                System.out.println("轨迹信息送达");
            }
        } else {
            json.put("info", "未找到该快递单号,请检查你的输入");
            json.put("status", false);
        }
        System.out.println(json.toString());
        out.print(json.toString());
        out.flush();
        out.close();
    }
}
