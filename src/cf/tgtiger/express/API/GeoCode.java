package cf.tgtiger.express.API;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import java.io.*;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLConnection;
///*
//GeoCode由pc端执行。功能为查询具体地理位置的经纬度返回给服务器
//pc: 1) 在站点注册时调用传回站点具体信息(新加了经纬度)
//    2) 填写快递单时候查询目的地具体位置的经纬度信息也需要调用
//提交目的地经纬度（这里适合用一个检索的button）,服务器接收后根据目的地省市区固定后选取所有快递点,利用api列出与目的地各自的步行距离。
//传回快递点和距离数据。由桌面端进行排序。选择合适站点，最后才是提交快递
//单信息
// */
//public class GeoCode {
//    public static void main(String[] args) {
//        String address = "山西省太原市小店区山西大学家属区18号楼";
//        String lonLat = GeoCode.getLonLat(address);
//        System.out.println(lonLat);
//    }
//
//
//    //获取经纬度
//    private static String getLonLat(String address){
//        //返回输入地址address的经纬度信息, 格式是 经度,纬度
//        String queryUrl = "http://restapi.amap.com/v3/geocode/geo?key=2d324f28d8ccf0f6b4a27c8c4615e0cf&address="+address;
//        String queryResult = getResponse(queryUrl);  //高德接口返回的是JSON格式的字符串
//
//        //解析json字段location固定方法
//        JSONObject jo = JSON.parseObject(queryResult);
//        JSONArray ja = jo.getJSONArray("geocodes");
//        return JSON.parseObject(ja.getString(0)).get("location").toString();
//    }
//    //api查询通用接口,返回请求结果
//    private static String getResponse(String serverUrl){
//        //用JAVA发起http请求，并返回json格式的结果
//        StringBuffer result = new StringBuffer();
//        try {
//            URL url = new URL(serverUrl);
//            URLConnection conn = url.openConnection();
//            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//
//            String line;
//            while((line = in.readLine()) != null){
//                result.append(line);
//            }
//            in.close();
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return result.toString();
//    }
//
//}

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/*
GeoCode由pc端执行。功能为查询具体地理位置的经纬度返回给服务器
pc: 1) 在站点注册时调用传回站点具体信息(新加了经纬度)
    2) 填写快递单时候查询目的地具体位置的经纬度信息也需要调用
提交目的地经纬度（这里适合用一个检索的button）,服务器接收后根据目的地省市区固定后选取所有快递点,利用api列出与目的地各自的步行距离。
传回快递点和距离数据。由桌面端进行排序。选择合适站点，最后才是提交快递
单信息
 */
public class GeoCode {
    public static void main(String[] args) {
        String address = "山西省太原市小店区山西大学家属区18号楼";
        String queryResult = GeoCode.getLonLat(address);

    }


    //获取经纬度
    public static String getLonLat(String address){
        //返回输入地址address的经纬度信息, 格式是 经度,纬度
        String queryUrl = "http://restapi.amap.com/v3/geocode/geo?key=2d324f28d8ccf0f6b4a27c8c4615e0cf&address="+address;
        String queryResult = getResponse(queryUrl);  //高德接口返回的是JSON格式的字符串
        System.out.println(queryResult);
        JSONObject jo = JSON.parseObject(queryResult);
        String geocodes = jo.getString("geocodes");
        JSONArray jsonArray = JSONArray.parseArray(geocodes);
        JSONObject jsonArray_0 = jsonArray.getJSONObject(0);
        String location = jsonArray_0.getString("location");
        System.out.println(location);
        return location;

        //解析json字段location固定方法
       /*
       {
    "status": "1",
    "info": "OK",
    "infocode": "10000",
    "count": "1",
    "geocodes": [
        {
            "formatted_address": "北京市朝阳区阜通东大街|6号楼",
            "province": "北京市",
            "citycode": "010",
            "city": "北京市",
            "district": "朝阳区",
            "township": [],
            "neighborhood": {
                "name": [],
                "type": []
            },
            "building": {
                "name": [],
                "type": []
            },
            "adcode": "110105",
            "street": "阜通东大街",
            "number": "6号楼",
            "location": "116.484546,39.990064",
            "level": "门牌号"
        }
    ]
}
1，JSONObject
 json对象，就是一个键对应一个值，使用的是大括号{ }，如：{key:value}
2，JSONArray
  json数组，使用中括号[ ],只不过数组里面的项也是json键值对格式的

  Json对象中添加的是键值对，JSONArray中添加的是Json对象
        */

    }
    //api查询通用接口,返回请求结果
    public static String getResponse(String serverUrl){
        System.out.println(serverUrl);
        //用JAVA发起http请求，并返回json格式的结果

        //返回结果集
        StringBuffer document = new StringBuffer();
        //链接URL
        try {

            URL url = new URL(serverUrl);
            //创建链接
            URLConnection conn = url.openConnection();
            //读取返回结果集
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));

            String line;
            while((line = reader.readLine()) != null){
                document.append(line);
                System.out.println(document);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document.toString();
    }

}
