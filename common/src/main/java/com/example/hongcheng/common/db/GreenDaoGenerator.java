package com.example.hongcheng.common.db;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * @Project CommonProj
 * @Packate com.micky.commonproj.domain.db.manager
 * @Description
 * @Author Micky Liu
 * @Email mickyliu@126.com
 * @Date 2016-01-29 13:33
 * @Version 1.0
 */
public class GreenDaoGenerator {

    /**需要生成model的时候，请设置成true, 用于防止手贱点错了 */
    public static final boolean GENERATE_MODEL = false;

    /**
     * 数据库版本
     * 如果数据库需要升级,请在DaoMaster中的onUpgrade方法中加入:
     * MigrationHelper.getInstance().migrate(db, IpInfoDao.class, PlaceDao.class，...);
     */
    public static final int DB_VERSION = 1;

    public static void main(String[] args) throws Exception {
        if (GENERATE_MODEL) {
            Schema schema = new Schema(DB_VERSION, "com/example/hongcheng/common/model");
            schema.setDefaultJavaPackageDao("com/example/hongcheng/common/db/dao");
            schema.enableKeepSectionsByDefault();
            addIpInfo(schema);
            addPlace(schema);
            new DaoGenerator().generateAll(schema, "./common/src/main/java");
        }
    }

    private static void addIpInfo(Schema schema) {
        Entity ipInfo = schema.addEntity("IpInfo");
        ipInfo.addIdProperty().primaryKey();
        ipInfo.addStringProperty("country");
        ipInfo.addDoubleProperty("country_id");
        ipInfo.addDoubleProperty("area");
        ipInfo.addDoubleProperty("area_id");
        ipInfo.addDoubleProperty("ip");
    }

    private static void addPlace(Schema schema) {
        Entity place = schema.addEntity("Place");
        place.addIdProperty().primaryKey();
        place.addStringProperty("label");
        place.addStringProperty("name");
        place.addStringProperty("pinyin");
        place.addStringProperty("province");
    }
}
