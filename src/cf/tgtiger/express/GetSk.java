package cf.tgtiger.express;

import cf.tgtiger.express.Dao.ExpKeysDao;
import cf.tgtiger.express.Dao.ExpKeysDaoImpl;
import cf.tgtiger.express.bean.ExpKeys;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class GetSk extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        //输出方式
        PrintWriter out = resp.getWriter();
        //接受请求命令
        String receive = IOUtils.toString(req.getInputStream(),"UTF-8");
        JSONObject json_receive = JSON.parseObject(receive);
        String expressnum = json_receive.getString("expressNum");
        int encryptnum = json_receive.getInteger("encryptNum");
        ExpKeysDao expkeydao = new ExpKeysDaoImpl();
        ExpKeys ek;
        ek = expkeydao.getSecondKey(expressnum, encryptnum);
        JSONObject json = new JSONObject();
        json.put("secondDesKey", ek.getSecondDesK());
        json.put("secondRsaSK", ek.getSecondSk());

        out.print(json.toString());
        out.flush();
        out.close();




    }
}
