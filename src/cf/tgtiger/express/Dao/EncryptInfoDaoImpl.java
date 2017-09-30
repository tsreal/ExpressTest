package cf.tgtiger.express.Dao;

import cf.tgtiger.express.Encrypt.DES;
import cf.tgtiger.express.Encrypt.GenDesKey;
import cf.tgtiger.express.Encrypt.GenRsaKey;
import cf.tgtiger.express.Encrypt.RSA;
import cf.tgtiger.express.bean.ExpKeys;
import cf.tgtiger.express.bean.ExpStation;
import cf.tgtiger.express.bean.Express;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class EncryptInfoDaoImpl implements EncryptInfoDao{
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public static void main(String[] args) {
        EncryptInfoDao dao = new EncryptInfoDaoImpl();
        Express exp_post = new Express();
        ExpKeys epkdao = new ExpKeys();
        exp_post.setExpressNum("102411111024");
        exp_post.setEncryptNum(0);
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

        epkdao = dao.addEncryptInfo(exp_post);

        System.out.printf("success. info:" + epkdao.getEncryptInfo());

    }

    @Override
    public ExpKeys addEncryptInfo(Express exp) {

        ExpKeys epk = new ExpKeys();
        //第一类公钥
        String fpk = null;

        //用第一类公钥加密所选取的编号
        String infoafterencrypt_1 = null;
        //第二类加密算法名称
        String encryptname = null;
        //定义加密前信息
        String infobeforeencrypt = "{\"receiver\":\""+exp.getReceiver()+"\",\"phone\":\""+exp.getReceiverNum()+"\"}";
        //定义
        String spk = null;
        String ssk = null;
        String seconddesk = null;
        String infoafterencrypt_2 = null;

        //1
        epk.setExpressNum(exp.getExpressNum());


        String sql_a = "SELECT firstPk,firstSk,secondPk,secondSk,secondDesK FROM exp_keys WHERE expressNum = ?;";
        String sql_b = "SELECT encryptName FROM encryption_algorithm WHERE encryptNum = ?";

        String sql_c = "UPDATE exp_keys SET encryptInfo := ? WHERE expressNum = ?";

        try {
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql_a);
            pstmt.setString(1,exp.getExpressNum());
            rs = pstmt.executeQuery();
            rs.next();

            //2
            fpk = rs.getString(1);
            epk.setFirstPk(fpk);

            //3
            String fsk = rs.getString(2);
            epk.setFirstSk(fsk);

            //4
            epk.setEncryptNum(exp.getEncryptNum());

            //5
            spk = rs.getString(3);
            epk.setSecondPk(spk);

            //6
            ssk = rs.getString(4);
            epk.setSecondSk(ssk);

            //7
            seconddesk = rs.getString(5);
            epk.setSecondDesK(seconddesk);

            infoafterencrypt_1 = RSA.encryptByPublic(String.valueOf(exp.getEncryptNum()),fpk);

            pstmt = conn.prepareStatement(sql_b);
            pstmt.setInt(1,exp.getEncryptNum());
            rs = pstmt.executeQuery();
            rs.next();
            encryptname = rs.getString(1);
            epk.setStatus(1);
        } catch (SQLException e) {
            epk.setStatus(0);
            e.printStackTrace();
        }


        switch (encryptname) {
            case "DES":
                try {
                     infoafterencrypt_2 = DES.encrypt(infobeforeencrypt.getBytes("UTF-8"),seconddesk);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
            case "RSA":
                    infoafterencrypt_2 = RSA.encryptByPublic(infobeforeencrypt,spk);
                break;
            case "DESRSA":
                try {
                    infoafterencrypt_2 = RSA.encryptByPublic(DES.encrypt(infobeforeencrypt.getBytes("UTF-8"), seconddesk), spk);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
        }
        String info = exp.getExpressNum() +"#####"+ infoafterencrypt_1 + "#####" + infoafterencrypt_2;
        epk.setEncryptInfo(info);
        epk.setStatus(1);

        try {
            pstmt = conn.prepareStatement(sql_c);
            pstmt.setString(1,info);
            pstmt.setString(2,exp.getExpressNum());
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            epk.setStatus(0);
        } finally {
            DBUtil.closeAll(rs,pstmt,conn);
            return epk;
        }

    }
}
