package com.shenyuan.shengtang.provider;

import com.shenyuan.shengtang.provider.util.ColumnMetadata;

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
    public static final class tablepart extends dbContent {

        private static final String LOG_TAG = tablepart.class.getSimpleName();

        public static final String TABLE_NAME = "tablepart";
        public static final String TYPE_ELEM_TYPE = "vnd.android.cursor.item/db-tablepart";
        public static final String TYPE_DIR_TYPE = "vnd.android.cursor.dir/db-tablepart";

        public static final Uri CONTENT_URI = Uri.parse(dbContent.CONTENT_URI + "/" + TABLE_NAME);

        public static enum Columns implements ColumnMetadata {
            _ID(BaseColumns._ID, "integer"),
            TABLENAME("tablename", "text"),
            TIME("time", "text");

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
                Columns.TABLENAME.getName(),
                Columns.TIME.getName()
        };

        private tablepart() {
            // No private constructor
        }

        public static void createTable(SQLiteDatabase db) {
            if (dbProvider.ACTIVATE_ALL_LOGS) {
                Log.d(LOG_TAG, "tablepart | createTable start");
            }
            db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + Columns._ID.getName() + " " + Columns._ID.getType()+ " PRIMARY KEY AUTOINCREMENT" + ", " + Columns.TABLENAME.getName() + " " + Columns.TABLENAME.getType() + ", " + Columns.TIME.getName() + " " + Columns.TIME.getType() + ");");

            if (dbProvider.ACTIVATE_ALL_LOGS) {
                Log.d(LOG_TAG, "tablepart | createTable end");
            }
        }

        // Version 1 : Creation of the table
        // Version 2 - 5 : No changes
        public static void upgradeTable(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (dbProvider.ACTIVATE_ALL_LOGS) {
                Log.d(LOG_TAG, "tablepart | upgradeTable start");
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
                Log.d(LOG_TAG, "tablepart | upgradeTable end");
            }
        }

        static String getBulkInsertString() {
            return new StringBuilder("REPLACE INTO ").append(TABLE_NAME).append(" ( ").append(Columns.TABLENAME.getName()).append(", ").append(Columns.TIME.getName()).append(" ) VALUES (?, ?)").toString();
        }

        static void bindValuesInBulkInsert(SQLiteStatement stmt, ContentValues values) {
            int i = 1;
            String value;
            value = values.getAsString(Columns.TABLENAME.getName());
            stmt.bindString(i++, value != null ? value : "");
            value = values.getAsString(Columns.TIME.getName());
            stmt.bindString(i++, value != null ? value : "");
        }
    }

    /**
     * Created in version 1
     */
    public static final class tablenews extends dbContent {

        private static final String LOG_TAG = tablenews.class.getSimpleName();

        public static final String TABLE_NAME = "tablenews";
        public static final String TYPE_ELEM_TYPE = "vnd.android.cursor.item/db-tablenews";
        public static final String TYPE_DIR_TYPE = "vnd.android.cursor.dir/db-tablenews";

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
            LINK("link", "text"),
            CONTENT("content", "text"),
            PICS("pics", "text"),
            CLICK("click", "integer"),
            FAVORITE("favorite", "integer"),
            LOADSTATE("loadstate", "integer"),
            TYPE("type", "integer"),
            CHANNEL("channel", "text");

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
                Columns.LINK.getName(),
                Columns.CONTENT.getName(),
                Columns.PICS.getName(),
                Columns.CLICK.getName(),
                Columns.FAVORITE.getName(),
                Columns.LOADSTATE.getName(),
                Columns.TYPE.getName(),
                Columns.CHANNEL.getName()
        };

        private tablenews() {
            // No private constructor
        }

        public static void createTable(SQLiteDatabase db) {
            if (dbProvider.ACTIVATE_ALL_LOGS) {
                Log.d(LOG_TAG, "tablenews | createTable start");
            }
            db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + Columns._ID.getName() + " " + Columns._ID.getType()+ " PRIMARY KEY AUTOINCREMENT" + ", " + Columns.TITLE.getName() + " " + Columns.TITLE.getType() + ", " + Columns.AID.getName() + " " + Columns.AID.getType() + ", " + Columns.DESCRIPTION.getName() + " " + Columns.DESCRIPTION.getType() + ", " + Columns.IMAGE.getName() + " " + Columns.IMAGE.getType() + ", " + Columns.PUB_DATE.getName() + " " + Columns.PUB_DATE.getType() + ", " + Columns.CATEGORY.getName() + " " + Columns.CATEGORY.getType() + ", " + Columns.AUTHOR.getName() + " " + Columns.AUTHOR.getType() + ", " + Columns.COMMENTS.getName() + " " + Columns.COMMENTS.getType() + ", " + Columns.LINK.getName() + " " + Columns.LINK.getType() + ", " + Columns.CONTENT.getName() + " " + Columns.CONTENT.getType() + ", " + Columns.PICS.getName() + " " + Columns.PICS.getType() + ", " + Columns.CLICK.getName() + " " + Columns.CLICK.getType() + ", " + Columns.FAVORITE.getName() + " " + Columns.FAVORITE.getType() + ", " + Columns.LOADSTATE.getName() + " " + Columns.LOADSTATE.getType() + ", " + Columns.TYPE.getName() + " " + Columns.TYPE.getType() + ", " + Columns.CHANNEL.getName() + " " + Columns.CHANNEL.getType() + ", UNIQUE (" + Columns.AID.getName() + ")" + ");");

            if (dbProvider.ACTIVATE_ALL_LOGS) {
                Log.d(LOG_TAG, "tablenews | createTable end");
            }
        }

        // Version 1 : Creation of the table
        // Version 2 - 5 : No changes
        public static void upgradeTable(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (dbProvider.ACTIVATE_ALL_LOGS) {
                Log.d(LOG_TAG, "tablenews | upgradeTable start");
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
                Log.d(LOG_TAG, "tablenews | upgradeTable end");
            }
        }

        static String getBulkInsertString() {
            return new StringBuilder("REPLACE INTO ").append(TABLE_NAME).append(" ( ").append(Columns.TITLE.getName()).append(", ").append(Columns.AID.getName()).append(", ").append(Columns.DESCRIPTION.getName()).append(", ").append(Columns.IMAGE.getName()).append(", ").append(Columns.PUB_DATE.getName()).append(", ").append(Columns.CATEGORY.getName()).append(", ").append(Columns.AUTHOR.getName()).append(", ").append(Columns.COMMENTS.getName()).append(", ").append(Columns.LINK.getName()).append(", ").append(Columns.CONTENT.getName()).append(", ").append(Columns.PICS.getName()).append(", ").append(Columns.CLICK.getName()).append(", ").append(Columns.FAVORITE.getName()).append(", ").append(Columns.LOADSTATE.getName()).append(", ").append(Columns.TYPE.getName()).append(", ").append(Columns.CHANNEL.getName()).append(" ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)").toString();
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
            value = values.getAsString(Columns.LINK.getName());
            stmt.bindString(i++, value != null ? value : "");
            value = values.getAsString(Columns.CONTENT.getName());
            stmt.bindString(i++, value != null ? value : "");
            value = values.getAsString(Columns.PICS.getName());
            stmt.bindString(i++, value != null ? value : "");
            stmt.bindLong(i++, values.getAsLong(Columns.CLICK.getName()));
            stmt.bindLong(i++, values.getAsLong(Columns.FAVORITE.getName()));
            stmt.bindLong(i++, values.getAsLong(Columns.LOADSTATE.getName()));
            stmt.bindLong(i++, values.getAsLong(Columns.TYPE.getName()));
            value = values.getAsString(Columns.CHANNEL.getName());
            stmt.bindString(i++, value != null ? value : "");
        }
    }
}
