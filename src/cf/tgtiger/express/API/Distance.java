package cf.tgtiger.express.API;


import cf.tgtiger.express.Dao.ExpStationDao;
import cf.tgtiger.express.Dao.ExpStationDaoImpl;
import cf.tgtiger.express.bean.ExpStation;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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



        String info = "112.586276,37.805769|112.591339,37.796514|112.586276,37.805769|112.591339,37.796514|112.586276,37.805769|112.591339,37.796514";
        String destination = "112.590866,37.797539";
//        System.out.println(GetDistance(info,destination));
    }
    public static JSONArray GetDistance(String destination,String province, String city ,String area) {



        ExpStationDao dao = new ExpStationDaoImpl();
        List<ExpStation> list = dao.getGeoCodes(province, city, area);
        StringBuilder start;
        if (list.size() != 0) {
            start = new StringBuilder(list.get(0).getGeocodes());
            for (int i = 1; i < list.size(); i++) {
                start.append("|").append(list.get(i).getGeocodes());
            }
        }else {
            return null;
        }

        String api_distance = "http://restapi.amap.com/v3/distance?type=3&key=2d324f28d8ccf0f6b4a27c8c4615e0cf&origins="
                + start + "&destination=" + destination;

        String json_rec = GetResponse.getResult(api_distance);
        JSONObject jo = JSON.parseObject(json_rec);
        String results = jo.getString("results");
        JSONArray jsonArray = JSON.parseArray(results);

        JSONArray ja = new JSONArray();

        for(int i=0; i<jsonArray.size(); i++) {

            JSONObject json = new JSONObject();
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String distance = jsonObject.getString("distance");
            json.put("expStationNum", list.get(i).getExpStationNum());
            json.put("expStationName", list.get(i).getExpStationName());
            json.put("distance", distance);
            ja.add(json);
        }
        return ja;
    }

}
