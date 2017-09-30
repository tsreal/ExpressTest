package cf.tgtiger.express.Dao;

import cf.tgtiger.express.bean.ExpKeys;
import cf.tgtiger.express.bean.Express;

public interface ExpKeysDao {
    boolean addKeys(Express exp);

    String getFirstSK(String expressnum);

    ExpKeys getSecondKey(String expressnum,int encryptnum);


}
