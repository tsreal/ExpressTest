package cf.tgtiger.express.Dao;

import cf.tgtiger.express.bean.Express;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenEncryptNumDaoImpl implements GenEncryptNumDao {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    //测试成功
    public static void main(String[] args) {
        GenEncryptNumDao dao = new GenEncryptNumDaoImpl();
        int i = 0;
        ExpressDao expdao = new ExpressDAOImpl();
        Express exp_post = new Express();
        exp_post.setExpressNum("102411111024");
        exp_post.setReceiver("王腾腾");
        exp_post.setReceiverNum("18435189087");
        exp_post.setReceiverAdr("山西省太原市小店区山西大学令德餐厅");
        exp_post.setReceiver_province("山西省");
        exp_post.setReceiver_city("太原市");
        exp_post.setReceiver_areas("小店区");
        exp_post.setReceiver_details("山西大学令德餐厅");
        exp_post.setSender("杜响成");
        exp_post.setSenderNum("10086");
        exp_post.setSenderAdr("山西省吕梁市离市区凤山街道安居小区15号");
        exp_post.setSender_province("山西省");
        exp_post.setSender_city("吕梁市");
        exp_post.setSender_areas("离市区");
        exp_post.setSender_details("凤山街道安居小区15号");
        exp_post.setStartingNum("SXLL00001");
        exp_post.setTerminalNum("SXTY00110");
        exp_post.setTotalFee(8.5);
        i = dao.GetNums(exp_post);
        System.out.println("success! The selected encrypt number is: " + i);

    }
    @Override
    public int GetNums(Express express) {
        String sql = "select count(encryptNum) from encryption_algorithm;";
        int i = 0;
        try {
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            rs.next();
            i = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);


        }
        i = Choose.decide(express.getExpressNum(),express.getSender_province(),express.getReceiver_province(),i);
        return i;
    }
}
