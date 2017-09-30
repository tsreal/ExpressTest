package cf.tgtiger.express.bean;

public class ExpKeys {

    /**
     * expressNum : 123456789012
     * firstPk : dfsfjkdslafjalkdfjlkafjalk
     * firstSk : dfajkladkfjadjkd
     * encryptNum : 1
     * secondPk : daffajdksfjadslfjdlk
     * secondSk : dfjkldasfjklavjvaj
     * secondDesK : dafjlkvvmcxxxfvv
     */

    private String expressNum;
    private String firstPk;
    private String firstSk;
    private int encryptNum;
    private String secondPk;
    private String secondSk;
    private String secondDesK;
    /**
     * encryptInfo : dfasjlkfjalfkjaflk#####djklafj
     */

    private String encryptInfo;
    /**
     * status : 0
     */

    private int status;

    public String getExpressNum() {
        return expressNum;
    }

    public void setExpressNum(String expressNum) {
        this.expressNum = expressNum;
    }

    public String getFirstPk() {
        return firstPk;
    }

    public void setFirstPk(String firstPk) {
        this.firstPk = firstPk;
    }

    public String getFirstSk() {
        return firstSk;
    }

    public void setFirstSk(String firstSk) {
        this.firstSk = firstSk;
    }

    public int getEncryptNum() {
        return encryptNum;
    }

    public void setEncryptNum(int encryptNum) {
        this.encryptNum = encryptNum;
    }

    public String getSecondPk() {
        return secondPk;
    }

    public void setSecondPk(String secondPk) {
        this.secondPk = secondPk;
    }

    public String getSecondSk() {
        return secondSk;
    }

    public void setSecondSk(String secondSk) {
        this.secondSk = secondSk;
    }

    public String getSecondDesK() {
        return secondDesK;
    }

    public void setSecondDesK(String secondDesK) {
        this.secondDesK = secondDesK;
    }

    public String getEncryptInfo() {
        return encryptInfo;
    }

    public void setEncryptInfo(String encryptInfo) {
        this.encryptInfo = encryptInfo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
