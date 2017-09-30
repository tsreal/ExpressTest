package cf.tgtiger.express.Dao;

import cf.tgtiger.express.bean.ExpStation;
import cf.tgtiger.express.bean.Express;
import cf.tgtiger.express.bean.User;

import java.sql.Date;
import java.util.List;

public interface ExpressDao {
    boolean expExist(String expNum);
    //完整增加快递订单
//    Express addFullExpress(Express express);
    //精简增加快递订单
    Express addExpress(Express exp);

    //更新快递似乎不合理,暂时不写
    //public void updateExpress(String expNum);

    //删除快递单by快递单号
    void deleteExpress(String expNum);
    //删除一个时间段创建的快递单
    void deleteExpress(Date begin,Date end);

    int getCounts(String date, String expstationnum, int type);


    List<Express> getExpressByDate(String date, String expstationnum, int pagenum, int pagesize, int type);
//    int getCounts(String date, String expstationnum, int type);
//    public List<Express> getAllExp(String expStationNum);
//    public List<Express> getAllExp(int level);
    //一系列查询方式
    // public Express getExpressBy();

    //根据快递站点获取相应所有快递单
    List<Express> getAllExp(String expStationNum);
    //获取某年某月某日的该点所有快递单
    List<Express> getDateExp(String date,String expStationNum);
    //根据快递单号获取快递信息
    Express getExpress(String expNum);
    List<Express> getAllExp(Date begin,Date end,String expStationNum);

    //根据快递编号获取起始站编号
    String getTerminalNum(String expnum);
    //根据快递编号获取终点站编号
    String getStartingNum(String expnum);

}
