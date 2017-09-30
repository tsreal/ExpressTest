package cf.tgtiger.express.bean;

public class ExpStation {

    /**
     * expStationNum : SXLL201701
     * expStationName : 山西吕梁中转站
     * password : 867881879
     * geocodes : 142.12434,423432.23323
     * staFirstPKey : dsafkdasfdskjaflda
     * staFirstSKey : dsafjklsajfkldalfkas
     * staAddress : 山西省吕梁市离市区凤山街道
     * province : 山西省
     * city : 太原市
     * areas : 小店区
     */

    private String expStationNum;
    private String expStationName;
    private String staFirstPKey;
    private String staFirstSKey;
    private String staAddress;
    private String password;
    private String geocodes;
    private String province;
    private String city;
    private String areas;


    //9.26增加经纬度


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

    public String getStaFirstPKey() {
        return staFirstPKey;
    }

    public void setStaFirstPKey(String staFirstPKey) {
        this.staFirstPKey = staFirstPKey;
    }

    public String getStaFirstSKey() {
        return staFirstSKey;
    }

    public void setStaFirstSKey(String staFirstSKey) {
        this.staFirstSKey = staFirstSKey;
    }

    public String getStaAddress() {
        return staAddress;
    }

    public void setStaAddress(String staAddress) {
        this.staAddress = staAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }




    public String getGeocodes() {
        return geocodes;
    }

    public void setGeocodes(String geocodes) {
        this.geocodes = geocodes;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAreas() {
        return areas;
    }

    public void setAreas(String areas) {
        this.areas = areas;
    }
}
