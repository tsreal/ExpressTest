package cf.tgtiger.express.Dao;

import cf.tgtiger.express.bean.Express;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class ExpressDAOImpl implements ExpressDao {

    public static void main(String[] args) {
//        ExpressDao expdao = new ExpressDAOImpl();
//        Express exp_post = new Express();
//        Express exp_return = new Express();
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
//
//        exp_return = expdao.addExpress(exp_post);
//        System.out.printf(exp_return.getExpressNum() + "号订单生成"
//                + "\n加密算法编号:" + exp_return.getEncryptNum()
//        );
        ExpressDao dao = new ExpressDAOImpl();
        List<Express> list ;
        list = dao.getExpressByDate("2017-8-22","sxty11110",1,3,0);
        System.out.println(list.size());
        for(int i=0; i<list.size(); i++) {
            int a = i+1;
            System.out.println("--------------------第"+ a +"条-----------------------");
            System.out.println("快递单号: "+list.get(i).getExpressNum());
            System.out.println("收件人: "+list.get(i).getReceiver());
            System.out.println("收件人电话: "+list.get(i).getReceiverNum());
            System.out.println("详细地址: "+list.get(i).getReceiver_details());
            System.out.println();
        }
    }


    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;


    @Override
    public boolean expExist(String expNum) {
        String sql = "SELECT count(*) FROM express.exp_info WHERE expressNum = ?;";

        try {
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, expNum);
            rs = pstmt.executeQuery();
            rs.next();
            if (rs.getInt(1)==1) {
                System.out.println("快递单号与库中匹配成功");
                return true;
            } else {
                System.out.println("库中未找到相同快递单号");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.printf("检索快递信息失败");
            return false;
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);
        }
    }

//    @Override
//    public Express addFullExpress(Express express) {
//        GenExpNumsDao genExpNumsDao = new GenExpNumsDaoImpl();
//        String expnum = genExpNumsDao.getNums();
//        express.setExpressNum(expnum);
//        GenEncryptNumDao genEncryptNumDao = new GenEncryptNumDaoImpl();
//        int encyptnum = genEncryptNumDao.GetNums(express);
//        express.setEncryptNum(encyptnum);
//
//
//        String sql = "INSERT INTO exp_info VALUES \n" +
//                "(?,?,now(),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
//        try {
//            conn = DBUtil.getExpConnection();
//            pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1, expnum);
//            pstmt.setInt(2, encyptnum);
//            pstmt.setString(3, express.getReceiver());
//            pstmt.setString(4, express.getReceiverNum());
//            pstmt.setString(5, express.getReceiver_province());
//            pstmt.setString(6, express.getReceiver_city());
//            pstmt.setString(7, express.getReceiver_areas());
//            pstmt.setString(8, express.getReceiver_details());
//            pstmt.setString(9, express.getReceiver_zipcode());
//            pstmt.setString(10, express.getReceiverAdr());
//            pstmt.setString(11, express.getSender());
//            pstmt.setString(12, express.getSenderNum());
//            pstmt.setString(13, express.getSender_province());
//            pstmt.setString(14, express.getSender_city());
//            pstmt.setString(15, express.getSender_areas());
//            pstmt.setString(16, express.getSender_details());
//            pstmt.setString(17, express.getSender_zipcode());
//            pstmt.setString(18, express.getSenderAdr());
//            pstmt.setString(19, express.getStartingNum());
//            pstmt.setString(20, express.getTerminalNum());
//            pstmt.setInt(21, express.getIsFile());
//            pstmt.setInt(22, express.getIsGoods());
//            pstmt.setString(23, express.getGoodsName());
//            pstmt.setInt(24, express.getGoodsNums());
//            pstmt.setDouble(25, express.getGoodsWeight());
//            pstmt.setString(26, express.getInfoPlus());
//            pstmt.setInt(27, express.getIsInsured());
//            pstmt.setDouble(28, express.getInsuredValue());
//            pstmt.setDouble(29, express.getInsuredFee());
//            pstmt.setInt(30, express.getPayMode());
//            pstmt.setDouble(31, express.getTotalFee());
//            pstmt.execute();
//            express.setStatus(1);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            express.setStatus(0);
//        } finally {
//            DBUtil.closeAll(rs, pstmt, conn);
//            return express;
//        }
//
//    }

    @Override
    public Express addExpress(Express express) {
        GenExpNumsDao genExpNumsDao = new GenExpNumsDaoImpl();
        String expnum = genExpNumsDao.getNums();
        express.setExpressNum(expnum);
        //至此express对象加入了快递单号

        GenEncryptNumDao genEncryptNumDao = new GenEncryptNumDaoImpl();
        int encyptnum = genEncryptNumDao.GetNums(express);
        express.setEncryptNum(encyptnum);

        String sql = "INSERT INTO exp_info(expressNum, encryptNum, " +
                "genTime, receiver, receiverNum, receiver_province," +
                " receiver_city, receiver_areas, receiver_details, receiverAdr," +
                " sender, senderNum, sender_province, sender_city, sender_areas, " +
                "sender_details, senderAdr, startingNum, terminalNum, totalFee) " +
                "VALUES (?,?,now(),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, expnum);
            pstmt.setInt(2, encyptnum);
            pstmt.setString(3, express.getReceiver());
            pstmt.setString(4, express.getReceiverNum());
            pstmt.setString(5, express.getReceiver_province());
            pstmt.setString(6, express.getReceiver_city());
            pstmt.setString(7, express.getReceiver_areas());
            pstmt.setString(8, express.getReceiver_details());
            pstmt.setString(9, express.getReceiverAdr());
            pstmt.setString(10, express.getSender());
            pstmt.setString(11, express.getSenderNum());
            pstmt.setString(12, express.getSender_province());
            pstmt.setString(13, express.getSender_city());
            pstmt.setString(14, express.getSender_areas());
            pstmt.setString(15, express.getSender_details());
            pstmt.setString(16, express.getSenderAdr());
            pstmt.setString(17, express.getStartingNum());
            pstmt.setString(18, express.getTerminalNum());
            pstmt.setDouble(19, express.getTotalFee());
            pstmt.execute();
            express.setStatus(1);
        } catch (SQLException e) {
            e.printStackTrace();
            express.setStatus(0);
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);
        }
        return express;
    }

    @Override
    public void deleteExpress(String expNum) {
        String sql = "DELETE FROM exp_info WHERE expressNum = ?;";

        try {
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, expNum);
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);

        }


    }

    @Override
    public void deleteExpress(Date begin, Date end) {
        String sql = "DELETE FROM exp_info " +
                "WHERE unix_timestamp(genTime) " +
                "BETWEEN unix_timestamp(?) AND unix_timestamp(?);";

        try {
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, begin);
            pstmt.setDate(2, end);
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);

        }

    }

    public int getCounts(String date, String expstationnum, int type) {
        String sql_1 = "SELECT count(*) FROM exp_info WHERE UNIX_TIMESTAMP(genTime) " +
                "BETWEEN unix_timestamp(?) AND unix_timestamp(?)+86400 " +
                "AND startingNum=?";
        String sql_2 = "select count(*) from exp_info WHERE UNIX_TIMESTAMP(genTime) " +
                "BETWEEN unix_timestamp(?) and unix_timestamp(?)+86400 " +
                "and terminalNum=?";

        int totalNum = 0 ;
        conn = DBUtil.getExpConnection();

        if (type == 0) {
            try {
                pstmt = conn.prepareStatement(sql_1);
                pstmt.setString(1, date);
                pstmt.setString(2, date);
                pstmt.setString(3, expstationnum);
                rs = pstmt.executeQuery();
                rs.next();
                totalNum = rs.getInt(1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if(type == 1){
            try {
                pstmt = conn.prepareStatement(sql_2);
                pstmt.setString(1,date);
                pstmt.setString(2,date);
                pstmt.setString(3, expstationnum);
                rs = pstmt.executeQuery();
                rs.next();
                totalNum = rs.getInt(1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        DBUtil.closeAll(rs,pstmt,conn);

        return totalNum;

    }
    @Override
    public List<Express> getExpressByDate(String date,String expstationnum,int pagenum,int pagesize,int type) {
        List<Express> list = new ArrayList<>();
        String sql_1 = "SELECT * FROM express.exp_info" +
                " WHERE unix_timestamp(genTime) BETWEEN unix_timestamp(?) AND unix_timestamp(?)+86400 and startingNum=? ORDER BY id asc LIMIT ?,?;";

        String sql_2 = "SELECT * FROM express.exp_info" +
                " WHERE unix_timestamp(genTime) BETWEEN unix_timestamp(?) AND unix_timestamp(?)+86400 AND terminalNum=? ORDER BY id ASC LIMIT ?,?;";

        try {
            conn = DBUtil.getExpConnection();
            int startpos = (pagenum - 1) * pagesize;
            if (type == 0) {
                pstmt = conn.prepareStatement(sql_1);
            } else {
                pstmt = conn.prepareStatement(sql_2);
            }
            pstmt.setString(1,date);
            pstmt.setString(2,date);
            pstmt.setString(3,expstationnum);
            pstmt.setInt(4, startpos);
            pstmt.setInt(5,pagesize);
            rs = pstmt.executeQuery();

            /**
             * expressNum : 102410241024
             * encryptNum : 2
             * genTime : 2017-8-1 13:13:13
             * receiver : 小明
             * receiverNum : 15512345678
             * receiverAdr : 山西省太原市小店区坞城路山西大学令德十二斋
             * sender : 小李
             * senderNum : 18412341234
             * senderAdr : 山西省吕梁市离市区凤山街道安居小区
             * startingNum : SXTY00001
             * terminalNum : SXLV00001
             * isFile : 0
             * isGoods : 0
             * goodsName : 玩具
             * goodsWeight : 2.5
             * goodsNums : 1
             * isInsured : 0
             * insuredValue : 100.5
             * payMode : 0
             * totalFee : 15.5
             * infoPlus : 该商品易碎,请快递员轻拿轻放
             */
            /**
             * sender_province : 山西省
             * sender_city : 吕梁市
             * sender_areas : 离市区
             * sender_details : 凤山街道安居小区
             * sender_zipcode : 033000
             * receiver_province : 山西省
             * receiver_city : 太原市
             * receiver_areas : 小店区
             * receiver_details : 坞城路山西大学令德餐厅
             * receiver_zipcode : 030000
             */
            while (rs.next()) {
                Express exp = new Express();
                exp.setExpressNum(rs.getString("expressNum"));
                exp.setEncryptNum(rs.getInt("encryptNum"));
                exp.setGenTime(rs.getString("genTime"));
                exp.setReceiver(rs.getString("receiver"));
                exp.setReceiverNum(rs.getString("receiverNum"));
                exp.setReceiver_province(rs.getString("receiver_province"));
                exp.setReceiver_city(rs.getString("receiver_city"));
                exp.setReceiver_areas(rs.getString("receiver_areas"));
                exp.setReceiver_details(rs.getString("receiver_details"));
                exp.setReceiverAdr(rs.getString("receiverAdr"));
                exp.setSender(rs.getString("sender"));
                exp.setSenderNum(rs.getString("senderNum"));
                exp.setSender_province(rs.getString("sender_province"));
                exp.setSender_city(rs.getString("sender_city"));
                exp.setSender_areas(rs.getString("sender_areas"));
                exp.setSender_details(rs.getString("sender_details"));
                exp.setSenderAdr(rs.getString("senderAdr"));
                exp.setStartingNum(rs.getString("startingNum"));
                exp.setTerminalNum(rs.getString("terminalNum"));
                exp.setTotalFee(rs.getDouble("totalFee"));
                list.add(exp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs,pstmt,conn);
        }
        return list;
    }


    @Override

    public List<Express> getAllExp(String expStationNum) {
        ArrayList<Express> list = new ArrayList<Express>();
        String sql = "SELECT * FROM exp_info s WHERE" +
                " s.terminalNum = ? " +
                "OR s.startingNum = ?;";
        try {
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, expStationNum);
            pstmt.setString(2, expStationNum);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Express express = new Express();
                express.setExpressNum(rs.getString("expressNum"));
                express.setEncryptNum(rs.getInt("encryptNum"));
                express.setGenTime(rs.getString("genTime"));
                express.setGoodsName(rs.getString("goodsName"));
                express.setGoodsNums(rs.getInt("goodsNums"));
                express.setGoodsWeight(rs.getDouble("goodsWeight"));
                express.setInfoPlus(rs.getString("infoPlus"));
                express.setInsuredValue(rs.getDouble("insuredValue"));
                express.setIsFile(rs.getInt("isFile"));
                express.setIsGoods(rs.getInt("isGoods"));
                express.setStartingNum(rs.getString("startingNum"));
                express.setTerminalNum(rs.getString("terminalNum"));
                express.setSender(rs.getString("sender"));
                express.setSender_areas(rs.getString("sender_areas"));
                express.setSenderAdr(rs.getString("senderAdr"));
                express.setSender_city(rs.getString("sender_city"));
                express.setSender_province(rs.getString("sender_province"));
                express.setSender_details(rs.getString("sender_details"));
                express.setSenderNum(rs.getString("senderNum"));
                express.setSender_zipcode(rs.getString("sender_zipcode"));
                express.setReceiver(rs.getString("receiver"));
                express.setReceiverAdr(rs.getString("receiverAdr"));
                express.setReceiverNum(rs.getString("receiverNum"));
                express.setReceiver_areas(rs.getString("receiver_areas"));
                express.setReceiver_city(rs.getString("receiver_city"));
                express.setReceiver_province(rs.getString("receiver_province"));
                express.setReceiver_details(rs.getString("receiver_details"));
                express.setReceiver_zipcode(rs.getString("receiver_zipcode"));
                express.setPayMode(rs.getInt("payMode"));
                express.setInsuredFee(rs.getInt("insuredFee"));
                express.setIsInsured(rs.getInt("isInsured"));
                express.setGoodsWeight(rs.getDouble("goodsWeight"));
                express.setInsuredValue(rs.getDouble("insuredValue"));
                list.add(express);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);
        }
        return list;
    }


    @Override
    public Express getExpress(String expNum) {
        Express exp = new Express();
        String sql = "SELECT * FROM exp_info WHERE expressNum = ?;";
        try {
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, expNum);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                exp.setExpressNum(rs.getString("expressNum"));
                exp.setEncryptNum(rs.getInt("encryptNum"));
                exp.setGenTime(rs.getString("genTime"));
                exp.setReceiver(rs.getString("receiver"));
                exp.setReceiverNum(rs.getString("receiverNum"));
                exp.setReceiver_province(rs.getString("receiver_province"));
                exp.setReceiver_city(rs.getString("receiver_city"));
                exp.setReceiver_areas(rs.getString("receiver_areas"));
                exp.setReceiver_details(rs.getString("receiver_details"));
                exp.setReceiverAdr(rs.getString("receiverAdr"));
                exp.setSender(rs.getString("sender"));
                exp.setSenderNum(rs.getString("senderNum"));
                exp.setSender_province(rs.getString("sender_province"));
                exp.setSender_city(rs.getString("sender_city"));
                exp.setSender_areas(rs.getString("sender_areas"));
                exp.setSender_details(rs.getString("sender_details"));
                exp.setSenderAdr(rs.getString("senderAdr"));
                exp.setStartingNum(rs.getString("startingNum"));
                exp.setTerminalNum(rs.getString("terminalNum"));
                exp.setTotalFee(rs.getDouble("totalFee"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);
        }
        return exp;
    }

    @Override
    public List<Express> getAllExp(Date begin, Date end, String expStationNum) {

        return null;
    }

    public String getTerminalNum(String expnum) {
        String sql = "SELECT terminalNum FROM exp_info WHERE expressNum = ?";
        String terminalnum = null;

        try {
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, expnum);
            rs = pstmt.executeQuery();
            rs.next();
            terminalnum = rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);
        }
        return terminalnum;
    }

    public String getStartingNum(String expnum) {
        String sql = "SELECT startingNum FROM exp_info WHERE expressNum = ?";
        String startingnum = null;

        try {
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, expnum);
            rs = pstmt.executeQuery();
            rs.next();
            startingnum = rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("该快递单号有误,查询不到起始站点编号");
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);
        }
        return startingnum;
    }

    @Override
    public List<Express> getDateExp(String date, String expStationNum) {
        return null;
    }


}
