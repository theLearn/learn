package com.example.hongcheng.common.db;

import com.example.hongcheng.common.db.dao.PlaceDao;
import com.example.hongcheng.common.db.manager.PlaceDaoManager;

/**
 * @Project CommonProj
 * @Packate com.micky.commonproj.domain.db
 *
 * @Description
 *
 * @Author Micky Liu
 * @Email mickyliu@126.com
 * @Date 2016-01-29 14:29
 * @Version 1.0
 */
public class DaoManagerFactory {

    private static PlaceDaoManager sPlaceDaoManager;

    public static PlaceDaoManager getPlaceDaoManager() {
        if (sPlaceDaoManager == null) {
            PlaceDao userDao = DBCore.getDaoSession().getPlaceDao();
            sPlaceDaoManager = new PlaceDaoManager(userDao);
        }
        return sPlaceDaoManager;
    }

}
