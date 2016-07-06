package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class DaoExampleGenerator {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(8, "app.example.com.mydemo.greendao");
        addNote(schema);
//        new DaoGenerator().generateAll(schema, "D:\\AndroidStudioWorkSpace\\MyDemo\\app\\src\\main\\java-gen");
        new DaoGenerator().generateAll(schema, "../MyDemo/app/src/main/java-gen");
    }

    /**
     * @param schema
     */
    private static void addNote(Schema schema) {
        schema.enableKeepSectionsByDefault();
        Entity note = schema.addEntity("SprayDetails");
        note.addIdProperty();
        note.addIntProperty("zone");
        note.addIntProperty("adjust");
        note.addIntProperty("intervalOrigin");
        note.addIntProperty("intervalActual");
        note.addIntProperty("sprayType");
        note.addIntProperty("stopType");
        note.addLongProperty("startTimeTheory");
        note.addLongProperty("startTimeActually");
        note.addLongProperty("endTimeActually");
        note.addBooleanProperty("isUpload");
        note.addDateProperty("add_time");
        note.implementsSerializable();

        Entity entity = schema.addEntity("DownloadDBEntity");
        entity.setClassNameDao("DownloadDao");
        entity.setTableName("download");
        entity.addStringProperty("downloadId").primaryKey();
        entity.addLongProperty("toolSize");
        entity.addLongProperty("completedSize");
        entity.addStringProperty("url");
        entity.addStringProperty("saveDirPath");
        entity.addStringProperty("fileName");
        entity.addIntProperty("downloadStatus");
    }
}
