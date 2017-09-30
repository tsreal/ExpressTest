package cf.tgtiger.express.Dao;

import cf.tgtiger.express.Encrypt.GenDesKey;
import cf.tgtiger.express.Encrypt.GenRsaKey;
import cf.tgtiger.express.bean.ExpKeys;
import cf.tgtiger.express.bean.Express;
import com.alibaba.fastjson.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ExpKeysDaoImpl implements ExpKeysDao {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public static void main(String[] args) {
//        ExpKeysDao dao = new ExpKeysDaoImpl();
//
//        ExpressDao expdao = new ExpressDAOImpl();
//        Express exp_post = new Express();
//        exp_post.setExpressNum("102411111024");
//        exp_post.setReceiver("王腾腾");
//        exp_post.setReceiverNum("18435189087");
//        exp_post.setReceiverAdr("山西省太原市小店区山西大学令德餐厅");
//        exp_post.setReceiver_province("山西省");
//        exp_post.setReceiver_city("太原市");
//        exp_post.setReceiver_areas("小店区");
//        exp_post.setReceiver_details("山西大学令德餐厅");
//        exp_post.setSender("杜响成");
//        exp_post.setSenderNum("10086");
//        exp_post.setSenderAdr("山西省吕梁市离市区凤山街道安居小区15号");
//        exp_post.setSender_province("山西省");
//        exp_post.setSender_city("吕梁市");
//        exp_post.setSender_areas("离市区");
//        exp_post.setSender_details("凤山街道安居小区15号");
//        exp_post.setStartingNum("SXLL00001");
//        exp_post.setTerminalNum("SXTY00002");
//        exp_post.setTotalFee(8.5);
//        boolean b = false;
//        b=dao.addKeys(exp_post);
//         if (b) {
//            System.out.println("success!");
//        } else {
//            System.out.println("false!");
//        }
        ExpKeys ek;
        ExpKeysDao dao = new ExpKeysDaoImpl();
        ek = dao.getSecondKey("487811000018", 2);
        String info1 = ek.getSecondDesK();
        String info2 = ek.getSecondSk();
        System.out.println(info1 + "\n" + info2);
    }

    @Override
    public boolean addKeys(Express exp) {
        String sql = "insert into exp_keys(expressNum, firstPk, " +
                "firstSk, encryptNum, secondPk, secondSk, secondDesK)" +
                " VALUES (?,?,?,?,?,?,?)";
        //获取终点站编号
        //并根据终点站编号获得第一类公私密钥对
        String terminalnum = exp.getTerminalNum();
        ExpStationDao esd = new ExpStationDaoImpl();
        String fpk = esd.getFPK(terminalnum);
        String fsk = esd.getFSK(terminalnum);

        //生成des密钥
        String deskey = GenDesKey.genKey();
        //生成rsa密钥对
        Map<String, Object> map = new HashMap<String, Object>();
		map = GenRsaKey.init();
        String rsapk = GenRsaKey.getPublicKey(map);
        String rsask = GenRsaKey.getPrivateKey(map);

        //数据库操作,存入exp_keys表中
        try {
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,exp.getExpressNum());
            pstmt.setString(2,fpk);
            pstmt.setString(3,fsk);
            pstmt.setInt(4,exp.getEncryptNum());
            pstmt.setString(5,rsapk);
            pstmt.setString(6,rsask);
            pstmt.setString(7,deskey);
            pstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.closeAll(rs,pstmt,conn);
        }
    }

    @Override
    public String getFirstSK(String expressnum) {
        String sql = "SELECT firstSk FROM exp_keys WHERE expressNum = ?";
        String fsk = "";

        try {
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            rs.next();
            fsk = rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return fsk;
        }
    }

    @Override
    public ExpKeys getSecondKey(String expressnum, int encryptnum) {

        ExpKeys ek = new ExpKeys();
        String sql_1 = "SELECT secondDesK FROM exp_keys WHERE expressNum = ?";
        String sql_2 = "SELECT secondSk FROM exp_keys WHERE expressNum = ?";
        String sql_3 = "SELECT secondDesK,secondSk FROM exp_keys WHERE expressNum = ?";
        String seconddesk = null;
        String secondsk = null;
        try {
            conn = DBUtil.getExpConnection();
            if (encryptnum == 0) {
                pstmt = conn.prepareStatement(sql_1);
                pstmt.setString(1, expressnum);
                rs = pstmt.executeQuery();
                rs.next();
                seconddesk = rs.getString(1);
                ek.setSecondDesK(seconddesk);
            } else if (encryptnum == 1) {
                pstmt = conn.prepareStatement(sql_2);
                pstmt.setString(1, expressnum);
                rs = pstmt.executeQuery();
                rs.next();
                secondsk = rs.getString(1);
                ek.setSecondSk(secondsk);
            } else if (encryptnum == 2) {
                pstmt = conn.prepareStatement(sql_3);
                pstmt.setString(1, expressnum);
                rs = pstmt.executeQuery();
                rs.next();
                seconddesk = rs.getString(1);
                secondsk = rs.getString(2);
                ek.setSecondDesK(seconddesk);
                ek.setSecondSk(secondsk);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
           DBUtil.closeAll(rs,pstmt,conn);
           return ek;
        }
    }
}
