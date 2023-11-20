package com.rikkathewrold.rikkamusic.greendao.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.rikkathewrold.rikkamusic.main.bean.DRGreenDaoBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "DRGREEN_DAO_BEAN".
*/
public class DRGreenDaoBeanDao extends AbstractDao<DRGreenDaoBean, Void> {

    public static final String TABLENAME = "DRGREEN_DAO_BEAN";

    /**
     * Properties of entity DRGreenDaoBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property SongId = new Property(0, String.class, "songId", false, "SONG_ID");
        public final static Property Duration = new Property(1, long.class, "duration", false, "DURATION");
        public final static Property SongCover = new Property(2, String.class, "songCover", false, "SONG_COVER");
        public final static Property Artist = new Property(3, String.class, "artist", false, "ARTIST");
        public final static Property SongName = new Property(4, String.class, "songName", false, "SONG_NAME");
        public final static Property SongUrl = new Property(5, String.class, "songUrl", false, "SONG_URL");
        public final static Property ArtistId = new Property(6, String.class, "artistId", false, "ARTIST_ID");
        public final static Property ArtistAvatar = new Property(7, String.class, "artistAvatar", false, "ARTIST_AVATAR");
    }


    public DRGreenDaoBeanDao(DaoConfig config) {
        super(config);
    }
    
    public DRGreenDaoBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"DRGREEN_DAO_BEAN\" (" + //
                "\"SONG_ID\" TEXT," + // 0: songId
                "\"DURATION\" INTEGER NOT NULL ," + // 1: duration
                "\"SONG_COVER\" TEXT," + // 2: songCover
                "\"ARTIST\" TEXT," + // 3: artist
                "\"SONG_NAME\" TEXT," + // 4: songName
                "\"SONG_URL\" TEXT," + // 5: songUrl
                "\"ARTIST_ID\" TEXT," + // 6: artistId
                "\"ARTIST_AVATAR\" TEXT);"); // 7: artistAvatar
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DRGREEN_DAO_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, DRGreenDaoBean entity) {
        stmt.clearBindings();
 
        String songId = entity.getSongId();
        if (songId != null) {
            stmt.bindString(1, songId);
        }
        stmt.bindLong(2, entity.getDuration());
 
        String songCover = entity.getSongCover();
        if (songCover != null) {
            stmt.bindString(3, songCover);
        }
 
        String artist = entity.getArtist();
        if (artist != null) {
            stmt.bindString(4, artist);
        }
 
        String songName = entity.getSongName();
        if (songName != null) {
            stmt.bindString(5, songName);
        }
 
        String songUrl = entity.getSongUrl();
        if (songUrl != null) {
            stmt.bindString(6, songUrl);
        }
 
        String artistId = entity.getArtistId();
        if (artistId != null) {
            stmt.bindString(7, artistId);
        }
 
        String artistAvatar = entity.getArtistAvatar();
        if (artistAvatar != null) {
            stmt.bindString(8, artistAvatar);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, DRGreenDaoBean entity) {
        stmt.clearBindings();
 
        String songId = entity.getSongId();
        if (songId != null) {
            stmt.bindString(1, songId);
        }
        stmt.bindLong(2, entity.getDuration());
 
        String songCover = entity.getSongCover();
        if (songCover != null) {
            stmt.bindString(3, songCover);
        }
 
        String artist = entity.getArtist();
        if (artist != null) {
            stmt.bindString(4, artist);
        }
 
        String songName = entity.getSongName();
        if (songName != null) {
            stmt.bindString(5, songName);
        }
 
        String songUrl = entity.getSongUrl();
        if (songUrl != null) {
            stmt.bindString(6, songUrl);
        }
 
        String artistId = entity.getArtistId();
        if (artistId != null) {
            stmt.bindString(7, artistId);
        }
 
        String artistAvatar = entity.getArtistAvatar();
        if (artistAvatar != null) {
            stmt.bindString(8, artistAvatar);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public DRGreenDaoBean readEntity(Cursor cursor, int offset) {
        DRGreenDaoBean entity = new DRGreenDaoBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // songId
            cursor.getLong(offset + 1), // duration
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // songCover
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // artist
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // songName
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // songUrl
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // artistId
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7) // artistAvatar
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, DRGreenDaoBean entity, int offset) {
        entity.setSongId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setDuration(cursor.getLong(offset + 1));
        entity.setSongCover(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setArtist(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setSongName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setSongUrl(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setArtistId(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setArtistAvatar(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(DRGreenDaoBean entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(DRGreenDaoBean entity) {
        return null;
    }

    @Override
    public boolean hasKey(DRGreenDaoBean entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
