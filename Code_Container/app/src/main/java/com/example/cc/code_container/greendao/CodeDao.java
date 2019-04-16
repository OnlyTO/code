package com.example.cc.code_container.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.cc.code_container.Code;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CODE".
*/
public class CodeDao extends AbstractDao<Code, Long> {

    public static final String TABLENAME = "CODE";

    /**
     * Properties of entity Code.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property MId = new Property(0, Long.class, "mId", true, "_id");
        public final static Property MMainName = new Property(1, String.class, "mMainName", false, "M_MAIN_NAME");
        public final static Property MTitle = new Property(2, String.class, "mTitle", false, "M_TITLE");
        public final static Property MName = new Property(3, String.class, "mName", false, "M_NAME");
        public final static Property MCode = new Property(4, String.class, "mCode", false, "M_CODE");
        public final static Property MDescription = new Property(5, String.class, "mDescription", false, "M_DESCRIPTION");
        public final static Property Letter = new Property(6, String.class, "letter", false, "LETTER");
    };


    public CodeDao(DaoConfig config) {
        super(config);
    }
    
    public CodeDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CODE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: mId
                "\"M_MAIN_NAME\" TEXT," + // 1: mMainName
                "\"M_TITLE\" TEXT," + // 2: mTitle
                "\"M_NAME\" TEXT," + // 3: mName
                "\"M_CODE\" TEXT," + // 4: mCode
                "\"M_DESCRIPTION\" TEXT," + // 5: mDescription
                "\"LETTER\" TEXT);"); // 6: letter
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CODE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Code entity) {
        stmt.clearBindings();
 
        Long mId = entity.getMId();
        if (mId != null) {
            stmt.bindLong(1, mId);
        }
 
        String mMainName = entity.getMMainName();
        if (mMainName != null) {
            stmt.bindString(2, mMainName);
        }
 
        String mTitle = entity.getMTitle();
        if (mTitle != null) {
            stmt.bindString(3, mTitle);
        }
 
        String mName = entity.getMName();
        if (mName != null) {
            stmt.bindString(4, mName);
        }
 
        String mCode = entity.getMCode();
        if (mCode != null) {
            stmt.bindString(5, mCode);
        }
 
        String mDescription = entity.getMDescription();
        if (mDescription != null) {
            stmt.bindString(6, mDescription);
        }
 
        String letter = entity.getLetter();
        if (letter != null) {
            stmt.bindString(7, letter);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Code entity) {
        stmt.clearBindings();
 
        Long mId = entity.getMId();
        if (mId != null) {
            stmt.bindLong(1, mId);
        }
 
        String mMainName = entity.getMMainName();
        if (mMainName != null) {
            stmt.bindString(2, mMainName);
        }
 
        String mTitle = entity.getMTitle();
        if (mTitle != null) {
            stmt.bindString(3, mTitle);
        }
 
        String mName = entity.getMName();
        if (mName != null) {
            stmt.bindString(4, mName);
        }
 
        String mCode = entity.getMCode();
        if (mCode != null) {
            stmt.bindString(5, mCode);
        }
 
        String mDescription = entity.getMDescription();
        if (mDescription != null) {
            stmt.bindString(6, mDescription);
        }
 
        String letter = entity.getLetter();
        if (letter != null) {
            stmt.bindString(7, letter);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Code readEntity(Cursor cursor, int offset) {
        Code entity = new Code( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // mId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // mMainName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // mTitle
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // mName
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // mCode
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // mDescription
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6) // letter
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Code entity, int offset) {
        entity.setMId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setMMainName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setMTitle(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setMName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setMCode(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setMDescription(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setLetter(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Code entity, long rowId) {
        entity.setMId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Code entity) {
        if(entity != null) {
            return entity.getMId();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
