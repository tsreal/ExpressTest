package cf.tgtiger.express;

import cf.tgtiger.express.Dao.DBUtil;
import cf.tgtiger.express.Dao.UserDaoImpl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateExpressman extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接收: 1. phone 2.expressNum
        //发送: 1. info 2. success
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        String receive = IOUtils.toString(req.getInputStream(), "UTF-8");
        JSONObject json_rec = JSON.parseObject(receive);
        String expressman_phone = json_rec.getString("phone");
        String expressNum = json_rec.getString("expressNum");
        JSONObject json = new JSONObject();

        String sql = "UPDATE express.exp_track SET Expressman := ? WHERE expressNum = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        if (new UserDaoImpl().usrExist(expressman_phone)) {
            try {
                conn = DBUtil.getExpConnection();
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, expressman_phone);
                pstmt.setString(2, expressNum);
                pstmt.execute();
                json.put("info", "更新快递员信息成功");
                json.put("success", true);
            } catch (SQLException e) {
                e.printStackTrace();
                json.put("info", "更新派件员信息失败");
                json.put("success", false);
            } finally {
                DBUtil.closeAll(rs, pstmt, conn);
            }
        } else {
            json.put("info", "该手机号不存在,请查证");
            json.put("success", false);
        }

        out.print(json.toString());
        out.flush();
        out.close();
    }
}
