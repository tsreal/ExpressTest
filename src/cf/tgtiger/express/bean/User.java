package cf.tgtiger.express.bean;

/**
 * Created by Alex on 2017/8/11.
 */
public class User {

    /**
     * phone : 13503581234
     * passwd : 123passwd
     * name : 张飞
     * level : 1
     * expStationNum : SX000001
     */

    private String phone;
    private String passwd;
    private String name;
    private int level;
    private String expStationNum;
    /**
     * rePassword : 134341343
     */

    private String rePassword;


    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpStationNum() {
        return expStationNum;
    }

    public void setExpStationNum(String expStationNum) {
        this.expStationNum = expStationNum;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }
}
