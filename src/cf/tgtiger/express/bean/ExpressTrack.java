package cf.tgtiger.express.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class ExpressTrack {

    /**
     * expressNum : 201720172017
     * expStationNum : SXLL00002
     * expStationName : 山西吕梁中转站
     * expTrack : SXTY00001->BJCY00001
     * expFullTrack : 2017-08-15 15:33:08 河北承德中转站->2017-8-16 00:00:00 山西吕梁中转站
     */

    private String expressNum;
    private String expStationNum;
    private String expStationName;
    private String expTrack;
    private String expFullTrack;
    private List<DataBean> data;
//    {"data":[
//            {       "time":"2017-09-04 08:29:03",
//                    "context": "【山西省太原市坞城路公司】 派件人: 李保宾 派件中 派件员电话13653653742"
//            },
//            {           "time":"2017-09-04 08:29:03",
//                    "context": "【山西省太原市坞城路公司】 派件人: 李保宾 派件中 派件员电话13653653742"
//            }
//            ]
//    }

    public String getExpressNum() {
        return expressNum;
    }

    public void setExpressNum(String expressNum) {
        this.expressNum = expressNum;
    }

    public String getExpStationNum() {
        return expStationNum;
    }

    public void setExpStationNum(String expStationNum) {
        this.expStationNum = expStationNum;
    }

    public String getExpStationName() {
        return expStationName;
    }

    public void setExpStationName(String expStationName) {
        this.expStationName = expStationName;
    }

    public String getExpTrack() {
        return expTrack;
    }

    public void setExpTrack(String expTrack) {
        this.expTrack = expTrack;
    }

    public String getExpFullTrack() {
        return expFullTrack;
    }

    public void setExpFullTrack(String expFullTrack) {
        this.expFullTrack = expFullTrack;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * time : 2017-09-04 08:29:03
         * context : 【山西省太原市坞城路公司】 派件人: 李保宾 派件中 派件员电话13653653742
         */

        private String time;
        private String context;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }
    }
}
