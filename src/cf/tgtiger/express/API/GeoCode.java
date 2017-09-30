package cf.tgtiger.express.API;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.*;
import java.net.MalformedURLException;
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
        String lonLat = GeoCode.getLonLat(address);
        System.out.println(lonLat);
    }


    //获取经纬度
    private static String getLonLat(String address){
        //返回输入地址address的经纬度信息, 格式是 经度,纬度
        String queryUrl = "http://restapi.amap.com/v3/geocode/geo?key=2d324f28d8ccf0f6b4a27c8c4615e0cf&address="+address;
        String queryResult = getResponse(queryUrl);  //高德接口返回的是JSON格式的字符串

        //解析json字段location固定方法
        JSONObject jo = JSON.parseObject(queryResult);
        JSONArray ja = jo.getJSONArray("geocodes");
        return JSON.parseObject(ja.getString(0)).get("location").toString();
    }
    //api查询通用接口,返回请求结果
    private static String getResponse(String serverUrl){
        //用JAVA发起http请求，并返回json格式的结果
        StringBuffer result = new StringBuffer();
        try {
            URL url = new URL(serverUrl);
            URLConnection conn = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;
            while((line = in.readLine()) != null){
                result.append(line);
            }
            in.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

}

