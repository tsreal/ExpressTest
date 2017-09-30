package cf.tgtiger.express.Dao;

import cf.tgtiger.express.bean.ExpressTrack;

public interface ExpTrackDao {
    void addTrack(ExpressTrack adr);
    void updateTrack(ExpressTrack adr);
    //根据快递单号查找追踪
    ExpressTrack getExpressTrack(String expnum);
    //返回追踪全部信息
    String getFullTrack(String expnum);

    String getExpLocate(String exprssNum);

    boolean expExist(String expressNum);

    boolean existExpressman(String expressNum);

    String getExpressmanName(String expressNum);

    String getExressmanPhone(String expressNum);

}
