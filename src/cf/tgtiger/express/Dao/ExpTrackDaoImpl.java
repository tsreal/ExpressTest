package cf.tgtiger.express.Dao;

import cf.tgtiger.express.bean.Express;
import cf.tgtiger.express.bean.ExpressTrack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpTrackDaoImpl implements ExpTrackDao{
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    public static void main(String[] args) {
        ExpTrackDao dao = new ExpTrackDaoImpl();
        ExpressTrack ep = new ExpressTrack();
        ep.setExpTrack("HBCD10086");
        ep.setExpStationNum("SXTY11110");
        ep.setExpStationName("山西太原杏花岭区圆通中转站");
        ep.setExpFullTrack("2017-08-15 15:33:08 河北承德快递点");
        ep.setExpressNum("123456781234");
        dao.updateTrack(ep);
    }
    @Override
    public void addTrack(ExpressTrack adr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String locate = adr.getExpStationNum();
        String expnum = adr.getExpressNum();
        String expstaname = adr.getExpStationName();
        String sql = "INSERT INTO exp_track (expressNum, expLocate, expTrack,expFullTrack) VALUES (?,?,?,?);";

        try {
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, expnum);
            pstmt.setString(2, locate);
            pstmt.setString(3, locate);
            pstmt.setString(4, sdf.format(new Date())  + " " + expstaname);
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);
        }
    }
    //到达某一站点更新轨迹
    //快递站点信息打包为address传入
    public void updateTrack(ExpressTrack adr) {
        String locate = adr.getExpStationNum();
        String expnum = adr.getExpressNum();
        String expstaname;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String sql1 = "update exp_track t set" +
                " t.expTrack :=concat(t.expTrack,'->',?)  " +
                "where t.expressNum = ?;";
        String sql2 = "update exp_track set expLocate := ? where expressNum = ?;";
        String sql3 = "SELECT expStationName FROM exp_station_keys WHERE expStationNum = ?;";
        String sql4 = "update exp_track t set t.expFullTrack :=concat(t.expFullTrack,'->',?,' ',?)  where t.expressNum = ?;";
        try {
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql1);
            pstmt.setString(1, locate);
            pstmt.setString(2, expnum);
            pstmt.execute();
            pstmt = conn.prepareStatement(sql2);
            pstmt.setString(1, locate);
            pstmt.setString(2,expnum);
            pstmt.execute();
            pstmt = conn.prepareStatement(sql3);
            pstmt.setString(1,locate);
            rs = pstmt.executeQuery();
            rs.next();
            expstaname = rs.getString(1);
            pstmt = conn.prepareStatement(sql4);
            java.util.Date date = new java.util.Date();
            pstmt.setString(1,sdf.format(date));
            pstmt.setString(2,expstaname);
            pstmt.setString(3,expnum);
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);
        }


    }

    @Override
    public String getFullTrack(String expnum) {

        String expfulltrack = null;
        String sql = "SELECT expFullTrack FROM exp_track WHERE expressNum = ?;";
        try {
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, expnum);
            rs = pstmt.executeQuery();
            rs.next();
            expfulltrack = rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);
        }
        return expfulltrack;
    }

    @Override
    public String getExpLocate(String exprssNum) {
        String sql = "SELECT expLocate FROM express.exp_track WHERE expressNum = ?;";
        try {
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, exprssNum);
            rs = pstmt.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs,pstmt,conn);
        }
        return null;
    }

    @Override
    public boolean expExist(String expNum) {
        String sql = "SELECT count(*) FROM express.exp_track WHERE expressNum = ?;";

        try {
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, expNum);
            rs = pstmt.executeQuery();
            rs.next();
            if (rs.getInt(1)==1) {
                System.out.println("快递单号与track库中匹配成功");
                return true;
            } else {
                System.out.println("track库中未找到相同快递单号");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.printf("from track base检索快递信息失败");
            return false;
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);
        }
    }

    @Override
    public boolean existExpressman(String expressNum) {
        String sql = "SELECT count(expressman) FROM exp_track WHERE expressNum = ?;";

        try {
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, expressNum);
            rs = pstmt.executeQuery();
            rs.next();
            if (rs.getInt(1) == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);
        }
        return false;
    }

    @Override
    public String getExpressmanName(String expressNum) {
        String sql_1 = "SELECT expressman FROM express.exp_track WHERE expressNum = ?";
        String sql_2 = "Select name FROM express.exp_station_users WHERE phone = ?";
        String name = null;
        try {
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql_1);
            pstmt.setString(1, expressNum);
            rs = pstmt.executeQuery();
            rs.next();
            String expressman = rs.getString(1);

            pstmt = conn.prepareStatement(sql_2);
            pstmt.setString(1, expressman);
            rs = pstmt.executeQuery();
            rs.next();
            name = rs.getString(1);
            return name;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);
        }
        System.out.println("查询失败");
        return null;
    }

    @Override
    public String getExressmanPhone(String expressNum) {
        String sql_1 = "SELECT expressman FROM express.exp_track WHERE expressNum = ?";
        String phone = null;
        try {
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql_1);
            pstmt.setString(1, expressNum);
            rs = pstmt.executeQuery();
            rs.next();
            phone = rs.getString(1);
            return phone;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);
        }
        System.out.println("查询失败");
        return null;
    }

    @Override
    public ExpressTrack getExpressTrack(String expnum) {
        ExpressTrack et = new ExpressTrack();
        et.setExpressNum(expnum);
        String expfulltrack = new ExpTrackDaoImpl().getFullTrack(expnum);
        et.setExpFullTrack(expfulltrack);
        String exptrack;

        String sql = "select expTrack from exp_track where expressNum = ?";
        try {
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,expnum);
            rs = pstmt.executeQuery();
            exptrack = rs.getString(1);
            et.setExpTrack(exptrack);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs,pstmt,conn);
        }
        return et;
    }


    public List<Express> getDeliverInfo(String date, String expressman_phone) {
        List<Express> list = new ArrayList<>();
        String sql = "SELECT expressNum FROM express.exp_track WHERE unix_timestamp(curDate) " +
                "BETWEEN unix_timestamp(?) AND unix_timestamp(?)+86400 AND expressman = ?";
        try {
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, date);
            pstmt.setString(2, date);
            pstmt.setString(3, expressman_phone);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String expressnum = rs.getString(1);
                Express exp = new ExpressDAOImpl().getExpress(expressnum);
                list.add(exp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);
        }
        return list;
    }
}


