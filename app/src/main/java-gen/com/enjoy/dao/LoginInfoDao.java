package com.enjoy.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.enjoy.entity.LoginInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "LOGIN_INFO".
*/
public class LoginInfoDao extends AbstractDao<LoginInfo, Long> {

    public static final String TABLENAME = "LOGIN_INFO";

    /**
     * Properties of entity LoginInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Username = new Property(1, String.class, "username", false, "USERNAME");
        public final static Property Userpasswd = new Property(2, String.class, "userpasswd", false, "USERPASSWD");
        public final static Property Movie_preference = new Property(3, String.class, "movie_preference", false, "MOVIE_PREFERENCE");
        public final static Property Music_preference = new Property(4, String.class, "music_preference", false, "MUSIC_PREFERENCE");
        public final static Property User_photo_url = new Property(5, String.class, "user_photo_url", false, "USER_PHOTO_URL");
        public final static Property Nickname = new Property(6, String.class, "nickname", false, "NICKNAME");
        public final static Property Sex = new Property(7, String.class, "sex", false, "SEX");
        public final static Property Age = new Property(8, String.class, "age", false, "AGE");
    }


    public LoginInfoDao(DaoConfig config) {
        super(config);
    }
    
    public LoginInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"LOGIN_INFO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"USERNAME\" TEXT," + // 1: username
                "\"USERPASSWD\" TEXT," + // 2: userpasswd
                "\"MOVIE_PREFERENCE\" TEXT," + // 3: movie_preference
                "\"MUSIC_PREFERENCE\" TEXT," + // 4: music_preference
                "\"USER_PHOTO_URL\" TEXT," + // 5: user_photo_url
                "\"NICKNAME\" TEXT," + // 6: nickname
                "\"SEX\" TEXT," + // 7: sex
                "\"AGE\" TEXT);"); // 8: age
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"LOGIN_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, LoginInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(2, username);
        }
 
        String userpasswd = entity.getUserpasswd();
        if (userpasswd != null) {
            stmt.bindString(3, userpasswd);
        }
 
        String movie_preference = entity.getMovie_preference();
        if (movie_preference != null) {
            stmt.bindString(4, movie_preference);
        }
 
        String music_preference = entity.getMusic_preference();
        if (music_preference != null) {
            stmt.bindString(5, music_preference);
        }
 
        String user_photo_url = entity.getUser_photo_url();
        if (user_photo_url != null) {
            stmt.bindString(6, user_photo_url);
        }
 
        String nickname = entity.getNickname();
        if (nickname != null) {
            stmt.bindString(7, nickname);
        }
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(8, sex);
        }
 
        String age = entity.getAge();
        if (age != null) {
            stmt.bindString(9, age);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, LoginInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(2, username);
        }
 
        String userpasswd = entity.getUserpasswd();
        if (userpasswd != null) {
            stmt.bindString(3, userpasswd);
        }
 
        String movie_preference = entity.getMovie_preference();
        if (movie_preference != null) {
            stmt.bindString(4, movie_preference);
        }
 
        String music_preference = entity.getMusic_preference();
        if (music_preference != null) {
            stmt.bindString(5, music_preference);
        }
 
        String user_photo_url = entity.getUser_photo_url();
        if (user_photo_url != null) {
            stmt.bindString(6, user_photo_url);
        }
 
        String nickname = entity.getNickname();
        if (nickname != null) {
            stmt.bindString(7, nickname);
        }
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(8, sex);
        }
 
        String age = entity.getAge();
        if (age != null) {
            stmt.bindString(9, age);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public LoginInfo readEntity(Cursor cursor, int offset) {
        LoginInfo entity = new LoginInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // username
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // userpasswd
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // movie_preference
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // music_preference
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // user_photo_url
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // nickname
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // sex
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8) // age
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, LoginInfo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUsername(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setUserpasswd(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setMovie_preference(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setMusic_preference(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setUser_photo_url(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setNickname(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setSex(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setAge(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(LoginInfo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(LoginInfo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(LoginInfo entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
