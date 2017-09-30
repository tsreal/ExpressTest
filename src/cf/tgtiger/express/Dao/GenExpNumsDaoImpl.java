package cf.tgtiger.express.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenExpNumsDaoImpl implements GenExpNumsDao{
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    //测试
    public static void main(String[] args) {
        GenExpNumsDao gendao = new GenExpNumsDaoImpl();
        String receive = gendao.getNums();
        System.out.println("success! The num:"+receive);
    }



    @Override
    public String getNums() {
        int i = 0;
        String sql1="select * from auto_ins;";
        String sql2="UPDATE auto_ins SET id := id+1 LIMIT 1;";
        String expnum = "";
        try {
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql1);
            rs = pstmt.executeQuery();
            rs.next();
            i = rs.getInt("id");
            String autoIns = "00000" + i;
            String expnum2 = autoIns.substring(autoIns.length() - 6);
            String expnum1 = String.valueOf(System.currentTimeMillis());
            expnum1 = expnum1.substring(7, expnum1.length());
            expnum = expnum1 + expnum2;
            pstmt = conn.prepareStatement(sql2);
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs,pstmt,conn);
        }
        return expnum;
    }
}
