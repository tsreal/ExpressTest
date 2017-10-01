package cf.tgtiger.express.Dao;

import cf.tgtiger.express.bean.ExpStation;

import java.util.List;

public interface ExpStationDao {
    //添加站点所有信息
    boolean addStaInfo(ExpStation es);



    //更新公私密钥对
    boolean updateFirstKeys(String expstanum);

    //根据快递站点编号获取该站点第一类公钥
    String getFPK(String expstanum);

    //根据快递站点编号获取该站点第一类私钥
    String getFSK(String expstanum);

    //根据快递站点编号获取站点名称
    String getStaName(String expstanum);

    //获取快递信息
    ExpStation getStaInfo(String expstanum);

    String getUpdateTime(String expStaNum);

    boolean updatePasswd(ExpStation es);

    boolean staExist(String stanum);

    String getPasswd(String expStaNum);

    List<ExpStation> getGeoCodes(String province, String city, String area);

//     Boolean updateGeoCodes(ExpStation es);
}
