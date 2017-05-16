package cn.songguohulian.cschool.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 *
 * @author Ziv
 * @data 2017/5/7
 * @time 18:11
 */
public class Timeline extends BmobObject {

    private CShcoolUser fromUser; // 用户

    private BmobRelation allDynamic; //所有动态

    public CShcoolUser getFromUser() {
        return fromUser;
    }

    public BmobRelation getAllDynamic() {
        return allDynamic;
    }

    public void setFromUser(CShcoolUser fromUser) {
        this.fromUser = fromUser;
    }

    public void setAllDynamic(BmobRelation allDynamic) {
        this.allDynamic = allDynamic;
    }
}
