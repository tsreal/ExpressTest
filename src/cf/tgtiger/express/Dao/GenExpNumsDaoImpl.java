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



//    @Override
//    public String getNums() {
//        int i = 0;
//        String sql1="select * from auto_ins;";
//        String sql2="UPDATE auto_ins SET id := id+1 LIMIT 1;";
//        String expnum = "";
//        try {
//            conn = DBUtil.getExpConnection();
//            pstmt = conn.prepareStatement(sql1);
//            rs = pstmt.executeQuery();
//            rs.next();
//            i = rs.getInt("id");
//            String autoIns = "00000" + i;
//            String expnum2 = autoIns.substring(autoIns.length() - 6);
//            String expnum1 = String.valueOf(System.currentTimeMillis());
//            expnum1 = expnum1.substring(7, expnum1.length());
//            expnum = expnum1 + expnum2;
//            pstmt = conn.prepareStatement(sql2);
//            pstmt.execute();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            DBUtil.closeAll(rs,pstmt,conn);
//        }
//        return expnum;
//    }


    @Override
    public String getNums() {
        String s1="690";
        int num1=0;
        for(int i=0;i<4;i++) {
            num1=num1*10+(int)(( Math.random()+0.1)*10);
        }
        if(num1>9999) {
            num1=num1/10;
        }
        String snum1=String.valueOf(num1);
        int num2=0;
        for(int i=0;i<5;i++) {
            num2=num2*10+(int)( (Math.random()+0.1)*10);
        }
        if(num2>99999) {
            num2=num2/10;
        }
        String snum2=String.valueOf(num2);
        String num=s1+snum1+snum2;

        char[] array=num.toCharArray();
        int sum1=0;
        for(int i=0;i<array.length;i+=2) {
            sum1=sum1+(array[i]-'0');
        }
        int sum2=0;
        for(int i=1;i<array.length;i+=2) {
            sum2=sum2+(array[i]-'0');
        }
        String s3=String.valueOf(10-((sum1+sum2*3)%10));
        if(s3.equals("10")) {
            s3="0";
        }
        return num+s3;
    }
}
