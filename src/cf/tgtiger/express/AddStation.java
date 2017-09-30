package cf.tgtiger.express;



import cf.tgtiger.express.Dao.ExpStationDao;
import cf.tgtiger.express.Dao.ExpStationDaoImpl;
import cf.tgtiger.express.bean.ExpStation;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//添加站点及为其设置第一类密钥对
public class AddStation extends HttpServlet {
    //接受json数据转换为ExpStation类
    //具体传入(所接收)参数为1.expStationNum:快递站点编号 2.expStationName:站点名称 3.staAddress:站点详细地址 4.密码 5.经纬度必须精度在6位以内
    //    {
    //    "expStationNum":"SXLL00001",
    //    "expStationName":"山西吕梁中转站",
    //    "staAddress":"山西吕梁离石凤山街道"
    //    "password":"3553543"
    //    "lon":2342.234234,
    //    "lat":24234.222
    //    }
    //传回(发送到客户端)参数为1. info:中文提示信息  2. success:布尔类型判断成功失败
    //    {
    //    "info":"请检查你的密码",
    //    "success":true
    //    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("utf-8");
        PrintWriter out = resp.getWriter();

        //添加站点信息(名字编号和第一类密钥)
        String receive = org.apache.commons.io.IOUtils.toString(req.getInputStream(), "UTF-8");
        ExpStationDao esdao = new ExpStationDaoImpl();
        ExpStation es_receive = JSON.parseObject(receive, ExpStation.class);
        JSONObject json = new JSONObject();
        if (esdao.staExist(es_receive.getExpStationNum())) {
            json.put("info", "站点已经注册,请登录");
            json.put("success", false);
        } else {
            if (esdao.addStaInfo(es_receive)) {
                json.put("info", es_receive.getExpStationNum() + "站点添加成功");
                json.put("success", true);
            } else {
                json.put("info", es_receive.getExpStationNum() + "站点添加失败");
                json.put("success", false);
            }
        }
        out.print(json.toString());
        out.flush();
        out.close();
    }


}
