package cf.tgtiger.express.bean;

public class Express {

    /**
     * status : 0
     */

    private int status;

    public Express() {
        super();
    }

    public Express(
            String expressNum,
            int encryptNum, String genTime,
            String receiver, String receiverNum,
            String receiver_province, String receiver_city,
            String receiver_areas, String receiver_details,
            String sender, String senderNum,
            String sender_province, String sender_city,
            String sender_areas, String sender_details,
            String startingNum, String terminalNum,
            double totalFee) {
        this.receiver = receiver;
        this.receiverNum = receiverNum;
        this.sender = sender;
        this.senderNum = senderNum;
        this.startingNum = startingNum;
        this.terminalNum = terminalNum;
        this.totalFee = totalFee;
        this.expressNum = expressNum;
        this.encryptNum = encryptNum;
        this.genTime = genTime;
        this.sender_province = sender_province;
        this.sender_city = sender_city;
        this.sender_areas = sender_areas;
        this.sender_details = sender_details;
        this.receiver_province = receiver_province;
        this.receiver_city = receiver_city;
        this.receiver_areas = receiver_areas;
        this.receiver_details = receiver_details;
    }

    public Express(String expressNum,
                   int encryptNum, String genTime,
                   String receiver, String receiverNum,
                   String receiver_province, String receiver_city,
                   String receiver_areas, String receiver_details,
                   String receiver_zipcode, String receiverAdr,
                   String sender,String senderNum,
                   String sender_province, String sender_city,
                   String sender_areas, String sender_details,
                   String sender_zipcode,  String senderAdr,
                   String startingNum, String terminalNum,
                   int isFile, int isGoods,
                   String goodsName, double goodsWeight, int goodsNums,
                   int isInsured, double insuredValue, double insuredFee,
                   String infoPlus, int payMode, double totalFee
                   ) {

        this.receiver = receiver;
        this.receiverNum = receiverNum;
        this.receiverAdr = receiverAdr;
        this.sender = sender;
        this.senderNum = senderNum;
        this.senderAdr = senderAdr;
        this.startingNum = startingNum;
        this.terminalNum = terminalNum;
        this.isFile = isFile;
        this.isGoods = isGoods;
        this.goodsName = goodsName;
        this.goodsWeight = goodsWeight;
        this.goodsNums = goodsNums;
        this.isInsured = isInsured;
        this.insuredValue = insuredValue;
        this.payMode = payMode;
        this.totalFee = totalFee;
        this.infoPlus = infoPlus;
        this.expressNum = expressNum;
        this.encryptNum = encryptNum;
        this.genTime = genTime;
        this.sender_province = sender_province;
        this.sender_city = sender_city;
        this.sender_areas = sender_areas;
        this.sender_details = sender_details;
        this.sender_zipcode = sender_zipcode;
        this.receiver_province = receiver_province;
        this.receiver_city = receiver_city;
        this.receiver_areas = receiver_areas;
        this.receiver_details = receiver_details;
        this.receiver_zipcode = receiver_zipcode;
        this.insuredFee = insuredFee;
    }

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



    private String receiver;
    private String receiverNum;
    private String receiverAdr;
    private String sender;
    private String senderNum;
    private String senderAdr;
    private String startingNum;
    private String terminalNum;
    private int isFile;
    private int isGoods;
    private String goodsName;
    private double goodsWeight;
    private int goodsNums;
    private int isInsured;
    private double insuredValue;
    private int payMode;
    private double totalFee;
    private String infoPlus;
    private String expressNum;
    private int encryptNum;
    private String genTime;
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

    private String sender_province;
    private String sender_city;
    private String sender_areas;
    private String sender_details;
    private String sender_zipcode;
    private String receiver_province;
    private String receiver_city;
    private String receiver_areas;
    private String receiver_details;
    private String receiver_zipcode;

    /**
     * insuredFee : 19.5
     */

    private double insuredFee;

    public String getExpressNum() {
        return expressNum;
    }

    public void setExpressNum(String expressNum) {
        this.expressNum = expressNum;
    }

    public int getEncryptNum() {
        return encryptNum;
    }

    public void setEncryptNum(int encryptNum) {
        this.encryptNum = encryptNum;
    }

    public String getGenTime() {
        return genTime;
    }

    public void setGenTime(String genTime) {
        this.genTime = genTime;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiverNum() {
        return receiverNum;
    }

    public void setReceiverNum(String receiverNum) {
        this.receiverNum = receiverNum;
    }

    public String getReceiverAdr() {
        return receiverAdr;
    }

    public void setReceiverAdr(String receiverAdr) {
        this.receiverAdr = receiverAdr;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSenderNum() {
        return senderNum;
    }

    public void setSenderNum(String senderNum) {
        this.senderNum = senderNum;
    }

    public String getSenderAdr() {
        return senderAdr;
    }

    public void setSenderAdr(String senderAdr) {
        this.senderAdr = senderAdr;
    }

    public String getStartingNum() {
        return startingNum;
    }

    public void setStartingNum(String startingNum) {
        this.startingNum = startingNum;
    }

    public String getTerminalNum() {
        return terminalNum;
    }

    public void setTerminalNum(String terminalNum) {
        this.terminalNum = terminalNum;
    }

    public int getIsFile() {
        return isFile;
    }

    public void setIsFile(int isFile) {
        this.isFile = isFile;
    }

    public int getIsGoods() {
        return isGoods;
    }

    public void setIsGoods(int isGoods) {
        this.isGoods = isGoods;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public double getGoodsWeight() {
        return goodsWeight;
    }

    public void setGoodsWeight(double goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    public int getGoodsNums() {
        return goodsNums;
    }

    public void setGoodsNums(int goodsNums) {
        this.goodsNums = goodsNums;
    }

    public int getIsInsured() {
        return isInsured;
    }

    public void setIsInsured(int isInsured) {
        this.isInsured = isInsured;
    }

    public double getInsuredValue() {
        return insuredValue;
    }

    public void setInsuredValue(double insuredValue) {
        this.insuredValue = insuredValue;
    }

    public int getPayMode() {
        return payMode;
    }

    public void setPayMode(int payMode) {
        this.payMode = payMode;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }

    public String getInfoPlus() {
        return infoPlus;
    }

    public void setInfoPlus(String infoPlus) {
        this.infoPlus = infoPlus;
    }


    public String getSender_province() {
        return sender_province;
    }

    public void setSender_province(String sender_province) {
        this.sender_province = sender_province;
    }

    public String getSender_city() {
        return sender_city;
    }

    public void setSender_city(String sender_city) {
        this.sender_city = sender_city;
    }

    public String getSender_areas() {
        return sender_areas;
    }

    public void setSender_areas(String sender_areas) {
        this.sender_areas = sender_areas;
    }

    public String getSender_details() {
        return sender_details;
    }

    public void setSender_details(String sender_details) {
        this.sender_details = sender_details;
    }

    public String getSender_zipcode() {
        return sender_zipcode;
    }

    public void setSender_zipcode(String sender_zipcode) {
        this.sender_zipcode = sender_zipcode;
    }

    public String getReceiver_province() {
        return receiver_province;
    }

    public void setReceiver_province(String receiver_province) {
        this.receiver_province = receiver_province;
    }

    public String getReceiver_city() {
        return receiver_city;
    }

    public void setReceiver_city(String receiver_city) {
        this.receiver_city = receiver_city;
    }

    public String getReceiver_areas() {
        return receiver_areas;
    }

    public void setReceiver_areas(String receiver_areas) {
        this.receiver_areas = receiver_areas;
    }

    public String getReceiver_details() {
        return receiver_details;
    }

    public void setReceiver_details(String receiver_details) {
        this.receiver_details = receiver_details;
    }

    public String getReceiver_zipcode() {
        return receiver_zipcode;
    }

    public void setReceiver_zipcode(String receiver_zipcode) {
        this.receiver_zipcode = receiver_zipcode;
    }

    public double getInsuredFee() {
        return insuredFee;
    }

    public void setInsuredFee(double insuredFee) {
        this.insuredFee = insuredFee;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
