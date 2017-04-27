package com.yang.m.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.project.yang.m.db.beans.AsocciationRulesDataBeans;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ASOCCIATION_RULES_DATA_BEANS".
*/
public class AsocciationRulesDataBeansDao extends AbstractDao<AsocciationRulesDataBeans, Long> {

    public static final String TABLENAME = "ASOCCIATION_RULES_DATA_BEANS";

    /**
     * Properties of entity AsocciationRulesDataBeans.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Time = new Property(1, Long.class, "time", false, "TIME");
        public final static Property Location = new Property(2, String.class, "location", false, "LOCATION");
        public final static Property AppName = new Property(3, String.class, "appName", false, "APP_NAME");
    }


    public AsocciationRulesDataBeansDao(DaoConfig config) {
        super(config);
    }
    
    public AsocciationRulesDataBeansDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ASOCCIATION_RULES_DATA_BEANS\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"TIME\" INTEGER," + // 1: time
                "\"LOCATION\" TEXT," + // 2: location
                "\"APP_NAME\" TEXT);"); // 3: appName
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ASOCCIATION_RULES_DATA_BEANS\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, AsocciationRulesDataBeans entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long time = entity.getTime();
        if (time != null) {
            stmt.bindLong(2, time);
        }
 
        String location = entity.getLocation();
        if (location != null) {
            stmt.bindString(3, location);
        }
 
        String appName = entity.getAppName();
        if (appName != null) {
            stmt.bindString(4, appName);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, AsocciationRulesDataBeans entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long time = entity.getTime();
        if (time != null) {
            stmt.bindLong(2, time);
        }
 
        String location = entity.getLocation();
        if (location != null) {
            stmt.bindString(3, location);
        }
 
        String appName = entity.getAppName();
        if (appName != null) {
            stmt.bindString(4, appName);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public AsocciationRulesDataBeans readEntity(Cursor cursor, int offset) {
        AsocciationRulesDataBeans entity = new AsocciationRulesDataBeans( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // time
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // location
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // appName
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, AsocciationRulesDataBeans entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTime(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setLocation(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAppName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(AsocciationRulesDataBeans entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(AsocciationRulesDataBeans entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(AsocciationRulesDataBeans entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}