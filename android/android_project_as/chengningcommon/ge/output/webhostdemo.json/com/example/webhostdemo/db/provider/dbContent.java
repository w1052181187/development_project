package com.example.webhostdemo.db.provider;

import com.example.webhostdemo.db.provider.util.ColumnMetadata;

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
     * Created in version 1
     */
    public static final class table_name_cache extends dbContent {

        private static final String LOG_TAG = table_name_cache.class.getSimpleName();

        public static final String TABLE_NAME = "table_name_cache";
        public static final String TYPE_ELEM_TYPE = "vnd.android.cursor.item/db-table_name_cache";
        public static final String TYPE_DIR_TYPE = "vnd.android.cursor.dir/db-table_name_cache";

        public static final Uri CONTENT_URI = Uri.parse(dbContent.CONTENT_URI + "/" + TABLE_NAME);

        public static enum Columns implements ColumnMetadata {
            _ID(BaseColumns._ID, "integer"),
            NAME_STR("name_str", "text"),
            TIME_STR("time_str", "text");

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
                Columns.NAME_STR.getName(),
                Columns.TIME_STR.getName()
        };

        private table_name_cache() {
            // No private constructor
        }

        public static void createTable(SQLiteDatabase db) {
            if (dbProvider.ACTIVATE_ALL_LOGS) {
                Log.d(LOG_TAG, "table_name_cache | createTable start");
            }
            db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + Columns._ID.getName() + " " + Columns._ID.getType()+ " PRIMARY KEY AUTOINCREMENT" + ", " + Columns.NAME_STR.getName() + " " + Columns.NAME_STR.getType() + ", " + Columns.TIME_STR.getName() + " " + Columns.TIME_STR.getType() + ", UNIQUE (" + Columns.NAME_STR.getName() + ")" + ");");

            if (dbProvider.ACTIVATE_ALL_LOGS) {
                Log.d(LOG_TAG, "table_name_cache | createTable end");
            }
        }

        // Version 1 : Creation of the table
        public static void upgradeTable(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (dbProvider.ACTIVATE_ALL_LOGS) {
                Log.d(LOG_TAG, "table_name_cache | upgradeTable start");
            }

            if (oldVersion < 1) {
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
                Log.d(LOG_TAG, "table_name_cache | upgradeTable end");
            }
        }

        static String getBulkInsertString() {
            return new StringBuilder("REPLACE INTO ").append(TABLE_NAME).append(" ( ").append(Columns.NAME_STR.getName()).append(", ").append(Columns.TIME_STR.getName()).append(" ) VALUES (?, ?)").toString();
        }

        static void bindValuesInBulkInsert(SQLiteStatement stmt, ContentValues values) {
            int i = 1;
            String value;
            value = values.getAsString(Columns.NAME_STR.getName());
            stmt.bindString(i++, value != null ? value : "");
            value = values.getAsString(Columns.TIME_STR.getName());
            stmt.bindString(i++, value != null ? value : "");
        }
    }

    /**
     * Created in version 1
     */
    public static final class table_host_cache extends dbContent {

        private static final String LOG_TAG = table_host_cache.class.getSimpleName();

        public static final String TABLE_NAME = "table_host_cache";
        public static final String TYPE_ELEM_TYPE = "vnd.android.cursor.item/db-table_host_cache";
        public static final String TYPE_DIR_TYPE = "vnd.android.cursor.dir/db-table_host_cache";

        public static final Uri CONTENT_URI = Uri.parse(dbContent.CONTENT_URI + "/" + TABLE_NAME);

        public static enum Columns implements ColumnMetadata {
            _ID(BaseColumns._ID, "integer"),
            HOST_STR("host_str", "text"),
            TIME_STR("time_str", "text");

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
                Columns.HOST_STR.getName(),
                Columns.TIME_STR.getName()
        };

        private table_host_cache() {
            // No private constructor
        }

        public static void createTable(SQLiteDatabase db) {
            if (dbProvider.ACTIVATE_ALL_LOGS) {
                Log.d(LOG_TAG, "table_host_cache | createTable start");
            }
            db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + Columns._ID.getName() + " " + Columns._ID.getType()+ " PRIMARY KEY AUTOINCREMENT" + ", " + Columns.HOST_STR.getName() + " " + Columns.HOST_STR.getType() + ", " + Columns.TIME_STR.getName() + " " + Columns.TIME_STR.getType() + ", UNIQUE (" + Columns.HOST_STR.getName() + ")" + ");");

            if (dbProvider.ACTIVATE_ALL_LOGS) {
                Log.d(LOG_TAG, "table_host_cache | createTable end");
            }
        }

        // Version 1 : Creation of the table
        public static void upgradeTable(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (dbProvider.ACTIVATE_ALL_LOGS) {
                Log.d(LOG_TAG, "table_host_cache | upgradeTable start");
            }

            if (oldVersion < 1) {
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
                Log.d(LOG_TAG, "table_host_cache | upgradeTable end");
            }
        }

        static String getBulkInsertString() {
            return new StringBuilder("REPLACE INTO ").append(TABLE_NAME).append(" ( ").append(Columns.HOST_STR.getName()).append(", ").append(Columns.TIME_STR.getName()).append(" ) VALUES (?, ?)").toString();
        }

        static void bindValuesInBulkInsert(SQLiteStatement stmt, ContentValues values) {
            int i = 1;
            String value;
            value = values.getAsString(Columns.HOST_STR.getName());
            stmt.bindString(i++, value != null ? value : "");
            value = values.getAsString(Columns.TIME_STR.getName());
            stmt.bindString(i++, value != null ? value : "");
        }
    }
}

