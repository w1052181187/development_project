package com.shenyuan.qiangguo.provider;

import com.shenyuan.qiangguo.provider.util.ColumnMetadata;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * This class was generated by the ContentProviderCodeGenerator project made by Foxykeep
 * <p>
 * (More information available https://github.com/foxykeep/ContentProviderCodeGenerator)
 */
public abstract class dbContent {

    public static final Uri CONTENT_URI = Uri.parse("content://" + dbProvider.AUTHORITY);

    private dbContent() {
    }

    /**
     * Created in version 5
     */
    public static final class table_local_state extends dbContent {

        private static final String LOG_TAG = table_local_state.class.getSimpleName();

        public static final String TABLE_NAME = "table_local_state";
        public static final String TYPE_ELEM_TYPE = "vnd.android.cursor.item/db-table_local_state";
        public static final String TYPE_DIR_TYPE = "vnd.android.cursor.dir/db-table_local_state";

        public static final Uri CONTENT_URI = Uri.parse(dbContent.CONTENT_URI + "/" + TABLE_NAME);

        public static enum Columns implements ColumnMetadata {
            _ID(BaseColumns._ID, "integer"),
            ID("id", "text"),
            LASTTIME("lasttime", "text"),
            READ_STATE("read_state", "text"),
            FAVORITE_STATE("favorite_state", "integer"),
            LIKE_STATE("like_state", "integer"),
            DATA_ITEM("data_item", "text"),
            DATA_ITEM_ARTICLE("data_item_article", "text"),
            TREAD_STATE("tread_state", "integer"),
            COLLECT_TIME("collect_time", "text");

            private final String mName;
            private final String mType;

            private Columns(String name, String type) {
                mName = name;
                mType = type;
            }

            @Override
            public int getIndex() {
                return ordinal();
            }

            @Override
            public String getName() {
                return mName;
            }

            @Override
            public String getType() {
                return mType;
            }
        }

        public static final String[] PROJECTION = new String[] {
                Columns._ID.getName(),
                Columns.ID.getName(),
                Columns.LASTTIME.getName(),
                Columns.READ_STATE.getName(),
                Columns.FAVORITE_STATE.getName(),
                Columns.LIKE_STATE.getName(),
                Columns.DATA_ITEM.getName(),
                Columns.DATA_ITEM_ARTICLE.getName(),
                Columns.TREAD_STATE.getName(),
                Columns.COLLECT_TIME.getName()
        };

        private table_local_state() {
            // No private constructor
        }

        public static void createTable(SQLiteDatabase db) {
            if (dbProvider.ACTIVATE_ALL_LOGS) {
                Log.d(LOG_TAG, "table_local_state | createTable start");
            }
            db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + Columns._ID.getName() + " " + Columns._ID.getType()+ " PRIMARY KEY AUTOINCREMENT" + ", " + Columns.ID.getName() + " " + Columns.ID.getType() + ", " + Columns.LASTTIME.getName() + " " + Columns.LASTTIME.getType() + ", " + Columns.READ_STATE.getName() + " " + Columns.READ_STATE.getType() + ", " + Columns.FAVORITE_STATE.getName() + " " + Columns.FAVORITE_STATE.getType() + ", " + Columns.LIKE_STATE.getName() + " " + Columns.LIKE_STATE.getType() + ", " + Columns.DATA_ITEM.getName() + " " + Columns.DATA_ITEM.getType() + ", " + Columns.DATA_ITEM_ARTICLE.getName() + " " + Columns.DATA_ITEM_ARTICLE.getType() + ", " + Columns.TREAD_STATE.getName() + " " + Columns.TREAD_STATE.getType() + ", " + Columns.COLLECT_TIME.getName() + " " + Columns.COLLECT_TIME.getType() + ", UNIQUE (" + Columns.ID.getName() + ")" + ");");

            if (dbProvider.ACTIVATE_ALL_LOGS) {
                Log.d(LOG_TAG, "table_local_state | createTable end");
            }
        }

        // Version 5 : Creation of the table
        // Version 6 - 7 : No changes
        public static void upgradeTable(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (dbProvider.ACTIVATE_ALL_LOGS) {
                Log.d(LOG_TAG, "table_local_state | upgradeTable start");
            }

            if (oldVersion < 5) {
                Log.i(LOG_TAG, "Upgrading from version " + oldVersion + " to " + newVersion
                        + ", data will be lost!");

                db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
                createTable(db);
                return;
            }

            if (oldVersion < newVersion) {
                // No more changes since version 5 so jump to newVersion
                oldVersion = newVersion;
            }

            if (oldVersion != newVersion) {
                throw new IllegalStateException("Error upgrading the database to version "
                        + newVersion);
            }

            if (dbProvider.ACTIVATE_ALL_LOGS) {
                Log.d(LOG_TAG, "table_local_state | upgradeTable end");
            }
        }

        static String getBulkInsertString() {
            return new StringBuilder("REPLACE INTO ").append(TABLE_NAME).append(" ( ").append(Columns.ID.getName()).append(", ").append(Columns.LASTTIME.getName()).append(", ").append(Columns.READ_STATE.getName()).append(", ").append(Columns.FAVORITE_STATE.getName()).append(", ").append(Columns.LIKE_STATE.getName()).append(", ").append(Columns.DATA_ITEM.getName()).append(", ").append(Columns.DATA_ITEM_ARTICLE.getName()).append(", ").append(Columns.TREAD_STATE.getName()).append(", ").append(Columns.COLLECT_TIME.getName()).append(" ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)").toString();
        }

        static void bindValuesInBulkInsert(SQLiteStatement stmt, ContentValues values) {
            int i = 1;
            String value;
            value = values.getAsString(Columns.ID.getName());
            stmt.bindString(i++, value != null ? value : "");
            value = values.getAsString(Columns.LASTTIME.getName());
            stmt.bindString(i++, value != null ? value : "");
            value = values.getAsString(Columns.READ_STATE.getName());
            stmt.bindString(i++, value != null ? value : "");
            stmt.bindLong(i++, values.getAsLong(Columns.FAVORITE_STATE.getName()));
            stmt.bindLong(i++, values.getAsLong(Columns.LIKE_STATE.getName()));
            value = values.getAsString(Columns.DATA_ITEM.getName());
            stmt.bindString(i++, value != null ? value : "");
            value = values.getAsString(Columns.DATA_ITEM_ARTICLE.getName());
            stmt.bindString(i++, value != null ? value : "");
            stmt.bindLong(i++, values.getAsLong(Columns.TREAD_STATE.getName()));
            value = values.getAsString(Columns.COLLECT_TIME.getName());
            stmt.bindString(i++, value != null ? value : "");
        }
    }

    /**
     * Created in version 7
     */
    public static final class table_setting extends dbContent {

        private static final String LOG_TAG = table_setting.class.getSimpleName();

        public static final String TABLE_NAME = "table_setting";
        public static final String TYPE_ELEM_TYPE = "vnd.android.cursor.item/db-table_setting";
        public static final String TYPE_DIR_TYPE = "vnd.android.cursor.dir/db-table_setting";

        public static final Uri CONTENT_URI = Uri.parse(dbContent.CONTENT_URI + "/" + TABLE_NAME);

        public static enum Columns implements ColumnMetadata {
            _ID(BaseColumns._ID, "integer"),
            IS_PUSH("is_push", "integer"),
            FONTSIZE("fontsize", "integer"),
            IS_NO_PIC_MODEL("is_no_pic_model", "integer"),
            LASTTIME("lasttime", "text"),
            IS_NIGHT_MODEL("is_night_model", "integer");

            private final String mName;
            private final String mType;

            private Columns(String name, String type) {
                mName = name;
                mType = type;
            }

            @Override
            public int getIndex() {
                return ordinal();
            }

            @Override
            public String getName() {
                return mName;
            }

            @Override
            public String getType() {
                return mType;
            }
        }

        public static final String[] PROJECTION = new String[] {
                Columns._ID.getName(),
                Columns.IS_PUSH.getName(),
                Columns.FONTSIZE.getName(),
                Columns.IS_NO_PIC_MODEL.getName(),
                Columns.LASTTIME.getName(),
                Columns.IS_NIGHT_MODEL.getName()
        };

        private table_setting() {
            // No private constructor
        }

        public static void createTable(SQLiteDatabase db) {
            if (dbProvider.ACTIVATE_ALL_LOGS) {
                Log.d(LOG_TAG, "table_setting | createTable start");
            }
            db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + Columns._ID.getName() + " " + Columns._ID.getType()+ " PRIMARY KEY AUTOINCREMENT" + ", " + Columns.IS_PUSH.getName() + " " + Columns.IS_PUSH.getType() + ", " + Columns.FONTSIZE.getName() + " " + Columns.FONTSIZE.getType() + ", " + Columns.IS_NO_PIC_MODEL.getName() + " " + Columns.IS_NO_PIC_MODEL.getType() + ", " + Columns.LASTTIME.getName() + " " + Columns.LASTTIME.getType() + ", " + Columns.IS_NIGHT_MODEL.getName() + " " + Columns.IS_NIGHT_MODEL.getType() + ");");

            if (dbProvider.ACTIVATE_ALL_LOGS) {
                Log.d(LOG_TAG, "table_setting | createTable end");
            }
        }

        // Version 7 : Creation of the table
        public static void upgradeTable(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (dbProvider.ACTIVATE_ALL_LOGS) {
                Log.d(LOG_TAG, "table_setting | upgradeTable start");
            }

            if (oldVersion < 7) {
                Log.i(LOG_TAG, "Upgrading from version " + oldVersion + " to " + newVersion
                        + ", data will be lost!");

                db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
                createTable(db);
                return;
            }


            if (oldVersion != newVersion) {
                throw new IllegalStateException("Error upgrading the database to version "
                        + newVersion);
            }

            if (dbProvider.ACTIVATE_ALL_LOGS) {
                Log.d(LOG_TAG, "table_setting | upgradeTable end");
            }
        }

        static String getBulkInsertString() {
            return new StringBuilder("REPLACE INTO ").append(TABLE_NAME).append(" ( ").append(Columns.IS_PUSH.getName()).append(", ").append(Columns.FONTSIZE.getName()).append(", ").append(Columns.IS_NO_PIC_MODEL.getName()).append(", ").append(Columns.LASTTIME.getName()).append(", ").append(Columns.IS_NIGHT_MODEL.getName()).append(" ) VALUES (?, ?, ?, ?, ?)").toString();
        }

        static void bindValuesInBulkInsert(SQLiteStatement stmt, ContentValues values) {
            int i = 1;
            String value;
            stmt.bindLong(i++, values.getAsLong(Columns.IS_PUSH.getName()));
            stmt.bindLong(i++, values.getAsLong(Columns.FONTSIZE.getName()));
            stmt.bindLong(i++, values.getAsLong(Columns.IS_NO_PIC_MODEL.getName()));
            value = values.getAsString(Columns.LASTTIME.getName());
            stmt.bindString(i++, value != null ? value : "");
            stmt.bindLong(i++, values.getAsLong(Columns.IS_NIGHT_MODEL.getName()));
        }
    }

    /**
     * Created in version 1
     */
    public static final class table_login extends dbContent {

        private static final String LOG_TAG = table_login.class.getSimpleName();

        public static final String TABLE_NAME = "table_login";
        public static final String TYPE_ELEM_TYPE = "vnd.android.cursor.item/db-table_login";
        public static final String TYPE_DIR_TYPE = "vnd.android.cursor.dir/db-table_login";

        public static final Uri CONTENT_URI = Uri.parse(dbContent.CONTENT_URI + "/" + TABLE_NAME);

        public static enum Columns implements ColumnMetadata {
            _ID(BaseColumns._ID, "integer"),
            USERINFO("userinfo", "text"),
            PWD("pwd", "text"),
            COOKIE("cookie", "text"),
            LASTTIME("lasttime", "text");

            private final String mName;
            private final String mType;

            private Columns(String name, String type) {
                mName = name;
                mType = type;
            }

            @Override
            public int getIndex() {
                return ordinal();
            }

            @Override
            public String getName() {
                return mName;
            }

            @Override
            public String getType() {
                return mType;
            }
        }

        public static final String[] PROJECTION = new String[] {
                Columns._ID.getName(),
                Columns.USERINFO.getName(),
                Columns.PWD.getName(),
                Columns.COOKIE.getName(),
                Columns.LASTTIME.getName()
        };

        private table_login() {
            // No private constructor
        }

        public static void createTable(SQLiteDatabase db) {
            if (dbProvider.ACTIVATE_ALL_LOGS) {
                Log.d(LOG_TAG, "table_login | createTable start");
            }
            db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + Columns._ID.getName() + " " + Columns._ID.getType()+ " PRIMARY KEY AUTOINCREMENT" + ", " + Columns.USERINFO.getName() + " " + Columns.USERINFO.getType() + ", " + Columns.PWD.getName() + " " + Columns.PWD.getType() + ", " + Columns.COOKIE.getName() + " " + Columns.COOKIE.getType() + ", " + Columns.LASTTIME.getName() + " " + Columns.LASTTIME.getType() + ");");

            if (dbProvider.ACTIVATE_ALL_LOGS) {
                Log.d(LOG_TAG, "table_login | createTable end");
            }
        }

        // Version 1 : Creation of the table
        // Version 2 - 7 : No changes
        public static void upgradeTable(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (dbProvider.ACTIVATE_ALL_LOGS) {
                Log.d(LOG_TAG, "table_login | upgradeTable start");
            }

            if (oldVersion < 1) {
                Log.i(LOG_TAG, "Upgrading from version " + oldVersion + " to " + newVersion
                        + ", data will be lost!");

                db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
                createTable(db);
                return;
            }

            if (oldVersion < newVersion) {
                // No more changes since version 1 so jump to newVersion
                oldVersion = newVersion;
            }

            if (oldVersion != newVersion) {
                throw new IllegalStateException("Error upgrading the database to version "
                        + newVersion);
            }

            if (dbProvider.ACTIVATE_ALL_LOGS) {
                Log.d(LOG_TAG, "table_login | upgradeTable end");
            }
        }

        static String getBulkInsertString() {
            return new StringBuilder("REPLACE INTO ").append(TABLE_NAME).append(" ( ").append(Columns.USERINFO.getName()).append(", ").append(Columns.PWD.getName()).append(", ").append(Columns.COOKIE.getName()).append(", ").append(Columns.LASTTIME.getName()).append(" ) VALUES (?, ?, ?, ?)").toString();
        }

        static void bindValuesInBulkInsert(SQLiteStatement stmt, ContentValues values) {
            int i = 1;
            String value;
            value = values.getAsString(Columns.USERINFO.getName());
            stmt.bindString(i++, value != null ? value : "");
            value = values.getAsString(Columns.PWD.getName());
            stmt.bindString(i++, value != null ? value : "");
            value = values.getAsString(Columns.COOKIE.getName());
            stmt.bindString(i++, value != null ? value : "");
            value = values.getAsString(Columns.LASTTIME.getName());
            stmt.bindString(i++, value != null ? value : "");
        }
    }

    /**
     * Created in version 6
     */
    public static final class table_channel_item extends dbContent {

        private static final String LOG_TAG = table_channel_item.class.getSimpleName();

        public static final String TABLE_NAME = "table_channel_item";
        public static final String TYPE_ELEM_TYPE = "vnd.android.cursor.item/db-table_channel_item";
        public static final String TYPE_DIR_TYPE = "vnd.android.cursor.dir/db-table_channel_item";

        public static final Uri CONTENT_URI = Uri.parse(dbContent.CONTENT_URI + "/" + TABLE_NAME);

        public static enum Columns implements ColumnMetadata {
            _ID(BaseColumns._ID, "integer"),
            TITLE("title", "text"),
            AID("aid", "integer"),
            DESCRIPTION("description", "text"),
            IMAGE("image", "text"),
            PUB_DATE("pubDate", "text"),
            CATEGORY("category", "text"),
            AUTHOR("author", "text"),
            COMMENTS("comments", "text"),
            CLICK("click", "text"),
            SOURCE("source", "text"),
            SOURCEURL("sourceurl", "text"),
            IMAGE_ARR("image_arr", "text"),
            LOCAL_CHANNEL("local_channel", "text"),
            LOCAL_PAGE("local_page", "integer"),
            PUB_DATE_N("pubDate_n", "text");

            private final String mName;
            private final String mType;

            private Columns(String name, String type) {
                mName = name;
                mType = type;
            }

            @Override
            public int getIndex() {
                return ordinal();
            }

            @Override
            public String getName() {
                return mName;
            }

            @Override
            public String getType() {
                return mType;
            }
        }

        public static final String[] PROJECTION = new String[] {
                Columns._ID.getName(),
                Columns.TITLE.getName(),
                Columns.AID.getName(),
                Columns.DESCRIPTION.getName(),
                Columns.IMAGE.getName(),
                Columns.PUB_DATE.getName(),
                Columns.CATEGORY.getName(),
                Columns.AUTHOR.getName(),
                Columns.COMMENTS.getName(),
                Columns.CLICK.getName(),
                Columns.SOURCE.getName(),
                Columns.SOURCEURL.getName(),
                Columns.IMAGE_ARR.getName(),
                Columns.LOCAL_CHANNEL.getName(),
                Columns.LOCAL_PAGE.getName(),
                Columns.PUB_DATE_N.getName()
        };

        private table_channel_item() {
            // No private constructor
        }

        public static void createTable(SQLiteDatabase db) {
            if (dbProvider.ACTIVATE_ALL_LOGS) {
                Log.d(LOG_TAG, "table_channel_item | createTable start");
            }
            db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + Columns._ID.getName() + " " + Columns._ID.getType()+ " PRIMARY KEY AUTOINCREMENT" + ", " + Columns.TITLE.getName() + " " + Columns.TITLE.getType() + ", " + Columns.AID.getName() + " " + Columns.AID.getType() + ", " + Columns.DESCRIPTION.getName() + " " + Columns.DESCRIPTION.getType() + ", " + Columns.IMAGE.getName() + " " + Columns.IMAGE.getType() + ", " + Columns.PUB_DATE.getName() + " " + Columns.PUB_DATE.getType() + ", " + Columns.CATEGORY.getName() + " " + Columns.CATEGORY.getType() + ", " + Columns.AUTHOR.getName() + " " + Columns.AUTHOR.getType() + ", " + Columns.COMMENTS.getName() + " " + Columns.COMMENTS.getType() + ", " + Columns.CLICK.getName() + " " + Columns.CLICK.getType() + ", " + Columns.SOURCE.getName() + " " + Columns.SOURCE.getType() + ", " + Columns.SOURCEURL.getName() + " " + Columns.SOURCEURL.getType() + ", " + Columns.IMAGE_ARR.getName() + " " + Columns.IMAGE_ARR.getType() + ", " + Columns.LOCAL_CHANNEL.getName() + " " + Columns.LOCAL_CHANNEL.getType() + ", " + Columns.LOCAL_PAGE.getName() + " " + Columns.LOCAL_PAGE.getType() + ", " + Columns.PUB_DATE_N.getName() + " " + Columns.PUB_DATE_N.getType() + ");");

            if (dbProvider.ACTIVATE_ALL_LOGS) {
                Log.d(LOG_TAG, "table_channel_item | createTable end");
            }
        }

        // Version 6 : Creation of the table
        // Version 7 : No changes
        public static void upgradeTable(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (dbProvider.ACTIVATE_ALL_LOGS) {
                Log.d(LOG_TAG, "table_channel_item | upgradeTable start");
            }

            if (oldVersion < 6) {
                Log.i(LOG_TAG, "Upgrading from version " + oldVersion + " to " + newVersion
                        + ", data will be lost!");

                db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
                createTable(db);
                return;
            }

            if (oldVersion < newVersion) {
                // No more changes since version 6 so jump to newVersion
                oldVersion = newVersion;
            }

            if (oldVersion != newVersion) {
                throw new IllegalStateException("Error upgrading the database to version "
                        + newVersion);
            }

            if (dbProvider.ACTIVATE_ALL_LOGS) {
                Log.d(LOG_TAG, "table_channel_item | upgradeTable end");
            }
        }

        static String getBulkInsertString() {
            return new StringBuilder("REPLACE INTO ").append(TABLE_NAME).append(" ( ").append(Columns.TITLE.getName()).append(", ").append(Columns.AID.getName()).append(", ").append(Columns.DESCRIPTION.getName()).append(", ").append(Columns.IMAGE.getName()).append(", ").append(Columns.PUB_DATE.getName()).append(", ").append(Columns.CATEGORY.getName()).append(", ").append(Columns.AUTHOR.getName()).append(", ").append(Columns.COMMENTS.getName()).append(", ").append(Columns.CLICK.getName()).append(", ").append(Columns.SOURCE.getName()).append(", ").append(Columns.SOURCEURL.getName()).append(", ").append(Columns.IMAGE_ARR.getName()).append(", ").append(Columns.LOCAL_CHANNEL.getName()).append(", ").append(Columns.LOCAL_PAGE.getName()).append(", ").append(Columns.PUB_DATE_N.getName()).append(" ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)").toString();
        }

        static void bindValuesInBulkInsert(SQLiteStatement stmt, ContentValues values) {
            int i = 1;
            String value;
            value = values.getAsString(Columns.TITLE.getName());
            stmt.bindString(i++, value != null ? value : "");
            stmt.bindLong(i++, values.getAsLong(Columns.AID.getName()));
            value = values.getAsString(Columns.DESCRIPTION.getName());
            stmt.bindString(i++, value != null ? value : "");
            value = values.getAsString(Columns.IMAGE.getName());
            stmt.bindString(i++, value != null ? value : "");
            value = values.getAsString(Columns.PUB_DATE.getName());
            stmt.bindString(i++, value != null ? value : "");
            value = values.getAsString(Columns.CATEGORY.getName());
            stmt.bindString(i++, value != null ? value : "");
            value = values.getAsString(Columns.AUTHOR.getName());
            stmt.bindString(i++, value != null ? value : "");
            value = values.getAsString(Columns.COMMENTS.getName());
            stmt.bindString(i++, value != null ? value : "");
            value = values.getAsString(Columns.CLICK.getName());
            stmt.bindString(i++, value != null ? value : "");
            value = values.getAsString(Columns.SOURCE.getName());
            stmt.bindString(i++, value != null ? value : "");
            value = values.getAsString(Columns.SOURCEURL.getName());
            stmt.bindString(i++, value != null ? value : "");
            value = values.getAsString(Columns.IMAGE_ARR.getName());
            stmt.bindString(i++, value != null ? value : "");
            value = values.getAsString(Columns.LOCAL_CHANNEL.getName());
            stmt.bindString(i++, value != null ? value : "");
            stmt.bindLong(i++, values.getAsLong(Columns.LOCAL_PAGE.getName()));
            value = values.getAsString(Columns.PUB_DATE_N.getName());
            stmt.bindString(i++, value != null ? value : "");
        }
    }
}

