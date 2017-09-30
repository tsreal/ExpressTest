package cf.tgtiger.express;

import cf.tgtiger.express.Dao.*;
import cf.tgtiger.express.bean.ExpKeys;
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

public class AddExpress extends HttpServlet{

//    {"receiver":"独享成",
//            "receiverNum":"10086",
//            "receiver_province":"山西省",
//            "receiver_city":"太原市",
//            "receiver_areas":"小店区",
//            "receiver_details":"山西大学令德十二斋",
//            "receiverAdr":"山西省太原市小店区山西大学令德十二斋",
//            "sender":"王腾腾",
//            "senderNum":"18435189097",
//            "sender_province":"山西省",
//            "sender_city":"吕梁市",
//            "sender_areas":"离市区",
//            "sender_details":"安居小区",
//            "senderAdr":"山西省吕梁市离市区安居小区",
//            "startingNum":"SXLL00001",
//            "TerminalNum":"SXTY11110",
//            "totalFee":"15.0"
//    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        //字符流
        PrintWriter out = resp.getWriter();

        String receive = IOUtils.toString(req.getInputStream(),"UTF-8");
        Express exp_receive = JSON.parseObject(receive, Express.class);
        ExpressDao expdao = new ExpressDAOImpl();
        JSONObject json = new JSONObject();
        Express exp;
        ExpKeysDao exkeydao = new ExpKeysDaoImpl();
        EncryptInfoDao epinfodao = new EncryptInfoDaoImpl();
        ExpKeys epk;



        //若添加订单成功,将基本信息与服务器生成的快递单号和根据基本信息生成的二类算法编号存入数据库中
        if ((exp = expdao.addExpress(exp_receive)).getStatus() == 1) {

            json.put("status_1",true);
            json.put("info_1", exp.getExpressNum() + "号订单生成存储成功");

            //往exp_keys加快递单号对应的所有密钥信息
            if (exkeydao.addKeys(exp)) {

                json.put("status_2", true);
                json.put("info_2", "密钥生成及存储成功");

                //往exp_keys加加密后信息,返回该快递的密钥及加密信息
                if ((epk = epinfodao.addEncryptInfo(exp)).getStatus() == 1) {

                    json.put("status_3", true);
                    json.put("info_3", "加密信息生成及存储成功");
                    json.put("expressNum", epk.getExpressNum());
//                    json.put("firstPk", epk.getFirstPk());
//                    json.put("firstSk", epk.getFirstSk());
//                    json.put("encryptNum", epk.getEncryptNum());
//                    json.put("secondPk", epk.getSecondPk());
//                    json.put("secondSk", epk.getSecondSk());
//                    json.put("secondDesK", epk.getSecondDesK());
                    json.put("encryptInfo", epk.getEncryptInfo());
                    System.out.println(epk.getEncryptInfo());

                } else {
                    json.put("status", false);
                    json.put("info_3", "加密信息生成失败");
                }

            } else {
                json.put("status_2", false);
                json.put("info_2","密钥生成失败");
            }

        } else {
            json.put("status_1", false);
            json.put("info_1", "订单创建失败");
            json.put("status_2", false);
            json.put("info_2", "密钥生成失败");
            json.put("status_3", false);
        }
        out.print(json.toString());
        out.flush();
        out.close();
    }
}
