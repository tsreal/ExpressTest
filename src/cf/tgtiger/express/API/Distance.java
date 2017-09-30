package cf.tgtiger.express.API;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
/*
高德:
渊智园:112.586276,37.805769
山西大学令德十二:112.590866,37.797539
山西大学令德餐厅:112.591339,37.796514
默认以步行测距
 */

/*

ArrayList<ExpStation> eslist;
select expStationNum,geocodes from exp_station_keys where province=? and city=? and district=?;


 */
public class Distance {

    public static void main(String[] args) {
        ArrayList<String> geocodes = null;



        String info = "112.586276,37.805769|112.591339,37.796514";
        String destination = "112.590866,37.797539";
        System.out.println(GetDistance(info,destination));
    }
    public static String GetDistance(String start, String destination) {

        OutputStream out = null;
//        BufferedReader in = null;
        String json_rec = null;

        try {
            String api_distance = "http://restapi.amap.com/v3/distance?type=3&key=2d324f28d8ccf0f6b4a27c8c4615e0cf&origins="
                    + start  +"&destination="+destination;
            URL url = new URL(api_distance);
            //设置通用请求属性
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //发送post必须设置以下两行
//        conn.setDoInput(true);
//        conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("charset", "UTF-8");

            json_rec = IOUtils.toString(conn.getInputStream(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return json_rec;
    }

}
