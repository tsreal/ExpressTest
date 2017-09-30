package cf.tgtiger.express.Dao;

import cf.tgtiger.express.API.GeoCode;
import cf.tgtiger.express.bean.ExpStation;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import static cf.tgtiger.express.Encrypt.GenRsaKey.getPrivateKey;
import static cf.tgtiger.express.Encrypt.GenRsaKey.getPublicKey;
import static cf.tgtiger.express.Encrypt.GenRsaKey.init;

public class ExpStationDaoImpl implements ExpStationDao{
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public static void main(String[] args) {
        ExpStationDao dao = new ExpStationDaoImpl();
        ExpStation es = new ExpStation();
        es.setStaAddress("山西省太原市小店区山西大学家属区");
        es.setExpStationNum("sxll00002");

//        dao.updateGeoCodes(es);

//        ExpStationDao dao = new ExpStationDaoImpl();
//        ExpStation epsta = new ExpStation();
//        epsta.setExpStationName("山西太原杏花岭区圆通中转站");
//        epsta.setExpStationNum("SXTY11110");
//        boolean b = false;
//        b = dao.addStaInfo(epsta);
//        if (b) {
//            System.out.println("success!");
//        } else {
//            System.out.println("false!");
//
//        }
//
//        String fpk = dao.getFPK("SXTY11110");
//        String fsk = dao.getFSK("SXTY11110");
//        String datenow = dao.getUpdateTime("SXTY11110");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        int i = 0;
//        try {
//            Date time1 = sdf.parse(sdf.format(new Date()));
//
//            Date time2 = sdf.parse("2017-8-25");
//           i = daysBetween(time1, time2);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        System.out.println("fpk:" + fpk + "\nfsk:" + fsk + "\ni: " + i);
//        ExpStationDao dao = new ExpStationDaoImpl();
//        ExpStation es = new ExpStation();
//        es.setProvince("山西省");
//        es.setCity("太原市");
//        es.setAreas("小店区");
//
//        List list = new ArrayList<>();
//        list = dao.getGeoCodes(es);
//        String info = "";
//        for(int i=0; i<list.size(); i++) {
//            ExpStation expStation = (ExpStation) list.get(i);
//            info = info + expStation.getGeocodes()+"|";
//        }
//        System.out.println(info);
//        String test = "山西省太原市小店区山西大学家属区18号楼";
//        System.out.println(GeoCode.requestGeoCode(test));
    }




    @Override
    public boolean addStaInfo(ExpStation es) {
        String sql = "insert into exp_station_keys(expStationNum,expStationName,firstPk,firstSk,updatetime,password,expStationAdr,province,city,areas,geocodes) values(?,?,?,?,?,?,?,?,?,?,?)";
        Map<String, Object> map = new HashMap<String, Object>();
		map = init();
        try {

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String nowDate = df.format(new Date());
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1,es.getExpStationNum());
            pstmt.setString(2,es.getExpStationName());
            //存入数据库的是第一类公私密钥对的base64编码
            pstmt.setString(3,getPublicKey(map));
            pstmt.setString(4,getPrivateKey(map));
            pstmt.setString(5, nowDate);
            pstmt.setString(6,es.getPassword());
            pstmt.setString(7,es.getStaAddress());
            pstmt.setString(8,es.getProvince());
            pstmt.setString(9,es.getCity());
            pstmt.setString(10,es.getAreas());
            pstmt.setString(11,es.getGeocodes());

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
    public boolean staExist(String stanum) {
        String sql = "SELECT count(*) FROM exp_station_keys WHERE expStationNum = ?;";

        try {
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, stanum);
            rs = pstmt.executeQuery();
            rs.next();
            if (rs.getInt(1)==1) {
                return true;
            } else {
                System.out.println("未找到相应站点");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.printf("站点检查失败");
            return false;
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);
        }

    }

    public static int daysBetween(Date smdate, Date bgdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            smdate = sdf.parse(sdf.format(smdate));
            bgdate = sdf.parse(sdf.format(bgdate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bgdate);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    @Override
    public String getUpdateTime(String expStaNum) {
        String sql = "SELECT updatetime FROM exp_station_keys WHERE expStationNum = ?";
        String uptime = null;
        try {
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, expStaNum);
            rs = pstmt.executeQuery();
            rs.next();
            uptime = rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);
        }
        return uptime;
    }

    @Override
    public boolean updateFirstKeys(String expstanum) {
         String sql = "UPDATE exp_station_keys set firstPk := ?, firstSk := ?,updatetime := ? where expStationNum =?";
        Map<String, Object> map = new HashMap<String, Object>();
		map = init();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = sdf.format(new Date());
        try {
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,getPublicKey(map));
            pstmt.setString(2,getPrivateKey(map));
            pstmt.setString(3,nowDate);
            pstmt.setString(4,expstanum);

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
    public boolean updatePasswd(ExpStation es) {
        String sql = "UPDATE exp_station_keys SET password := ? WHERE expStationNum = ?";
        try {
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, es.getPassword());
            pstmt.setString(2, es.getExpStationNum());
            pstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);
        }
    }

    @Override
    public String getFPK(String expstanum) {
        String firstpk = null;
        String sql = "SELECT firstPk FROM exp_station_keys WHERE expStationNum = ?";
         try {
             conn = DBUtil.getExpConnection();
             pstmt = conn.prepareStatement(sql);
             pstmt.setString(1, expstanum);
             rs = pstmt.executeQuery();
             rs.next();
             firstpk = rs.getString(1);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs,pstmt,conn);
        }
        return firstpk;
    }

    @Override
    public String getFSK(String expstanum) {
        String firstsk = null;
        String sql = "SELECT firstSk FROM exp_station_keys WHERE expStationNum = ?";
         try {
             conn = DBUtil.getExpConnection();
             pstmt = conn.prepareStatement(sql);
             pstmt.setString(1, expstanum);
             rs = pstmt.executeQuery();
             rs.next();
             firstsk = rs.getString(1);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs,pstmt,conn);
        }
        return firstsk;
    }

    @Override
    public String getStaName(String expstanum) {
        String sql = "select expStationName from exp_station_keys where expStationNum = ?";
        String expstationname = null;
        try {
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, expstanum);
            rs = pstmt.executeQuery();
            rs.next();
            expstationname = rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs,pstmt,conn);
        }
        return expstationname;
    }

    @Override
    public ExpStation getStaInfo(String expstanum) {
        ExpStation es = new ExpStation();
         String sql = "SELECT * FROM exp_station_keys WHERE expStationNum = ?";
         try {
             conn = DBUtil.getExpConnection();
             pstmt = conn.prepareStatement(sql);
             pstmt.setString(1, expstanum);
             rs = pstmt.executeQuery();
             es.setExpStationNum(rs.getString(1));
             es.setExpStationName(rs.getString(2));
             es.setStaFirstPKey(rs.getString(3));
             es.setStaFirstSKey(rs.getString(4));

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs,pstmt,conn);
        }
        return es;
    }

    @Override
    public String getPasswd(String expStaNum) {
        String sql = "SELECT password FROM exp_station_keys WHERE expStationNum = ?";
        String password = null;
        try {
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, expStaNum);
            rs = pstmt.executeQuery();
            rs.next();
            password = rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs, pstmt, conn);
        }
        return password;
    }

    @Override
    public List<ExpStation> getGeoCodes(ExpStation es) {
        String sql = "select expStationNum,expStationName,geocodes from exp_station_keys where province=? and city=? and areas=?;";
        List<ExpStation> eslist = null;
        try {
            conn = DBUtil.getExpConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,es.getProvince());
            pstmt.setString(2,es.getCity());
            pstmt.setString(3,es.getAreas());

            rs = pstmt.executeQuery();
            eslist = (List<ExpStation>)DBUtil.rsToObj(rs, ExpStation.class);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs,pstmt,conn);
        }
        return eslist;
    }

//    public  Boolean updateGeoCodes(ExpStation es) {
//        String sql = "update express.exp_station_keys set geocodes:= ? WHERE express.exp_station_keys.expStationNum = ?";
////        es.setAreas("小店区");
////        es.setProvince("山西省");
////        es.setCity("太原市");
////        es.setPassword("123");
////        es.setStaAddress("山西省太原市小店区山西大学家属区18号楼");
////        es.setExpStationNum("SXTYXD12581");
////        es.setExpStationName("山西大学北家属区中通快递");
////        dao.addStaInfo(es);
//        String geoCode = GeoCode.requestGeoCode(es.getStaAddress());
//        JSONObject jo_1 = JSON.parseObject(geoCode);
//        JSONArray ja_1 = jo_1.getJSONArray("geocodes");
//        JSONObject jo_2 = JSON.parseObject(ja_1.getString(0));
//        String codes = jo_2.getString("location");
//        System.out.println(codes);
//        try {
//            conn = DBUtil.getExpConnection();
//            pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1, codes);
//            pstmt.setString(2, es.getExpStationNum());
//            pstmt.execute();
//            System.out.println("ok");
//            return true;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("no");
//            return false;
//        } finally {
//            DBUtil.closeAll(rs,pstmt,conn);
//        }
//
//    }


}
