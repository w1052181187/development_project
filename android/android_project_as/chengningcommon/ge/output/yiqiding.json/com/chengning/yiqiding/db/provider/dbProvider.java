package com.chengning.yiqiding.db.provider;

import com.chengning.yiqiding.db.provider.dbContent.table_login;
import com.chengning.yiqiding.db.provider.dbContent.table_setting;
import com.chengning.yiqiding.db.provider.dbContent.table_local_state;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;

/**
 * This class was generated by the ContentProviderCodeGenerator project made by Foxykeep
 * <p>
 * (More information available https://github.com/foxykeep/ContentProviderCodeGenerator)
 */
public final class dbProvider extends ContentProvider {

    private static final String LOG_TAG = dbProvider.class.getSimpleName();

    /* package */ static final boolean ACTIVATE_ALL_LOGS = false;

    protected static final String DATABASE_NAME = "dbProvider.db";

    public static final String AUTHORITY = "com.chengning.yiqiding.db.provider.dbProvider";

    static {
        Uri.parse("content://" + AUTHORITY + "/integrityCheck");
    }

    // Version 1 : Creation of the database
    public static final int DATABASE_VERSION = 1;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private enum UriType {
        TABLE_LOGIN(table_login.TABLE_NAME, table_login.TABLE_NAME, table_login.TYPE_ELEM_TYPE),
        TABLE_LOGIN_ID(table_login.TABLE_NAME + "/#", table_login.TABLE_NAME, table_login.TYPE_DIR_TYPE),
        TABLE_SETTING(table_setting.TABLE_NAME, table_setting.TABLE_NAME, table_setting.TYPE_ELEM_TYPE),
        TABLE_SETTING_ID(table_setting.TABLE_NAME + "/#", table_setting.TABLE_NAME, table_setting.TYPE_DIR_TYPE),
        TABLE_LOCAL_STATE(table_local_state.TABLE_NAME, table_local_state.TABLE_NAME, table_local_state.TYPE_ELEM_TYPE),
        TABLE_LOCAL_STATE_ID(table_local_state.TABLE_NAME + "/#", table_local_state.TABLE_NAME, table_local_state.TYPE_DIR_TYPE);

        private String mTableName;
        private String mType;

        UriType(String matchPath, String tableName, String type) {
            mTableName = tableName;
            mType = type;
            sUriMatcher.addURI(AUTHORITY, matchPath, ordinal());
        }

        String getTableName() {
            return mTableName;
        }

        String getType() {
            return mType;
        }
    }

    static {
        // Ensures UriType is initialized
        UriType.values();
    }

    private static UriType matchUri(Uri uri) {
        int match = sUriMatcher.match(uri);
        if (match < 0) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        return UriType.class.getEnumConstants()[match];
    }

    private SQLiteDatabase mDatabase;

    @SuppressWarnings("deprecation")
    public synchronized SQLiteDatabase getDatabase(Context context) {
        // Always return the cached database, if we've got one
        if (mDatabase == null || !mDatabase.isOpen()) {
            DatabaseHelper helper = new DatabaseHelper(context, DATABASE_NAME);
            mDatabase = helper.getWritableDatabase();
            if (mDatabase != null) {
                mDatabase.setLockingEnabled(true);
            }
        }

        return mDatabase;
    }

    private class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context, String name) {
            super(context, name, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, "Creating dbProvider database");

            // Create all tables here; each class has its own method
            table_login.createTable(db);
            table_setting.createTable(db);
            table_local_state.createTable(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            // Upgrade all tables here; each class has its own method
            table_login.upgradeTable(db, oldVersion, newVersion);
            table_setting.upgradeTable(db, oldVersion, newVersion);
            table_local_state.upgradeTable(db, oldVersion, newVersion);
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        UriType uriType = matchUri(uri);
        Context context = getContext();

        // Pick the correct database for this operation
        SQLiteDatabase db = getDatabase(context);
        String id;

        if (ACTIVATE_ALL_LOGS) {
            Log.d(LOG_TAG, "delete: uri=" + uri + ", match is " + uriType.name());
        }

        int result = -1;

        switch (uriType) {
            case TABLE_LOGIN_ID:
            case TABLE_SETTING_ID:
            case TABLE_LOCAL_STATE_ID:
                id = uri.getPathSegments().get(1);
                result = db.delete(uriType.getTableName(), whereWithId(selection),
                        addIdToSelectionArgs(id, selectionArgs));
                break;
            case TABLE_LOGIN:
            case TABLE_SETTING:
            case TABLE_LOCAL_STATE:
                result = db.delete(uriType.getTableName(), selection, selectionArgs);
                break;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }

    @Override
    public String getType(Uri uri) {
        return matchUri(uri).getType();
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        UriType uriType = matchUri(uri);
        Context context = getContext();

        // Pick the correct database for this operation
        SQLiteDatabase db = getDatabase(context);
        long id;

        if (ACTIVATE_ALL_LOGS) {
            Log.d(LOG_TAG, "insert: uri=" + uri + ", match is " + uriType.name());
        }

        Uri resultUri;

        switch (uriType) {
            case TABLE_LOGIN:
            case TABLE_SETTING:
            case TABLE_LOCAL_STATE:
                id = db.insert(uriType.getTableName(), "foo", values);
                resultUri = id == -1 ? null : ContentUris.withAppendedId(uri, id);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        // Notify with the base uri, not the new uri (nobody is watching a new
        // record)
        getContext().getContentResolver().notifyChange(uri, null);
        return resultUri;
    }

    @Override
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations) 
            throws OperationApplicationException {
        SQLiteDatabase db = getDatabase(getContext());
        db.beginTransaction();
        try {
            int numOperations = operations.size();
            ContentProviderResult[] results = new ContentProviderResult[numOperations];
            for (int i = 0; i < numOperations; i++) {
                results[i] = operations.get(i).apply(this, results, i);
                db.yieldIfContendedSafely();
            }
            db.setTransactionSuccessful();
            return results;
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {

        UriType uriType = matchUri(uri);
        Context context = getContext();

        // Pick the correct database for this operation
        SQLiteDatabase db = getDatabase(context);

        if (ACTIVATE_ALL_LOGS) {
            Log.d(LOG_TAG, "bulkInsert: uri=" + uri + ", match is " + uriType.name());
        }

        int numberInserted = 0;
        SQLiteStatement insertStmt;

        db.beginTransaction();
        try {
            boolean hasError = false;
            Exception lastE = null;
            switch (uriType) {
                case TABLE_LOGIN:
                	hasError = false;
               		lastE = null;
                    insertStmt = db.compileStatement(table_login.getBulkInsertString());
                    for (ContentValues value : values) {
                        table_login.bindValuesInBulkInsert(insertStmt, value);
                        try {
                            insertStmt.execute();
						} catch (Exception e) {
							hasError = true;
							lastE = e;
						}
                        insertStmt.clearBindings();
                    }
                    insertStmt.close();
                    db.setTransactionSuccessful();
                    numberInserted = values.length;

					if(hasError && lastE != null){
                    	lastE.printStackTrace();
                    }
                    if (ACTIVATE_ALL_LOGS) {
                        Log.d(LOG_TAG, "bulkInsert: uri=" + uri + " | nb inserts : " + numberInserted);
                    }
                    break; 
                case TABLE_SETTING:
                	hasError = false;
               		lastE = null;
                    insertStmt = db.compileStatement(table_setting.getBulkInsertString());
                    for (ContentValues value : values) {
                        table_setting.bindValuesInBulkInsert(insertStmt, value);
                        try {
                            insertStmt.execute();
						} catch (Exception e) {
							hasError = true;
							lastE = e;
						}
                        insertStmt.clearBindings();
                    }
                    insertStmt.close();
                    db.setTransactionSuccessful();
                    numberInserted = values.length;

					if(hasError && lastE != null){
                    	lastE.printStackTrace();
                    }
                    if (ACTIVATE_ALL_LOGS) {
                        Log.d(LOG_TAG, "bulkInsert: uri=" + uri + " | nb inserts : " + numberInserted);
                    }
                    break; 
                case TABLE_LOCAL_STATE:
                	hasError = false;
               		lastE = null;
                    insertStmt = db.compileStatement(table_local_state.getBulkInsertString());
                    for (ContentValues value : values) {
                        table_local_state.bindValuesInBulkInsert(insertStmt, value);
                        try {
                            insertStmt.execute();
						} catch (Exception e) {
							hasError = true;
							lastE = e;
						}
                        insertStmt.clearBindings();
                    }
                    insertStmt.close();
                    db.setTransactionSuccessful();
                    numberInserted = values.length;

					if(hasError && lastE != null){
                    	lastE.printStackTrace();
                    }
                    if (ACTIVATE_ALL_LOGS) {
                        Log.d(LOG_TAG, "bulkInsert: uri=" + uri + " | nb inserts : " + numberInserted);
                    }
                    break; 
                default:
                    throw new IllegalArgumentException("Unknown URI " + uri);
            }
        } finally {
            db.endTransaction();
        }

        // Notify with the base uri, not the new uri (nobody is watching a new
        // record)
        context.getContentResolver().notifyChange(uri, null);
        return numberInserted;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, 
            String sortOrder) {

        Cursor c = null;
        UriType uriType = matchUri(uri);
        Context context = getContext();
        // Pick the correct database for this operation
        SQLiteDatabase db = getDatabase(context);
        String id;

        if (ACTIVATE_ALL_LOGS) {
            Log.d(LOG_TAG, "query: uri=" + uri + ", match is " + uriType.name());
        }

        switch (uriType) {
            case TABLE_LOGIN_ID:
            case TABLE_SETTING_ID:
            case TABLE_LOCAL_STATE_ID:
                id = uri.getPathSegments().get(1);
                c = db.query(uriType.getTableName(), projection, whereWithId(selection),
                        addIdToSelectionArgs(id, selectionArgs), null, null, sortOrder);
                break;
            case TABLE_LOGIN:
            case TABLE_SETTING:
            case TABLE_LOCAL_STATE:
                c = db.query(uriType.getTableName(), projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
        }

        if ((c != null) && !isTemporary()) {
            c.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return c;
    }

    private String whereWithId(String selection) {
        StringBuilder sb = new StringBuilder(256);
        sb.append(BaseColumns._ID);
        sb.append(" = ?");
        if (selection != null) {
            sb.append(" AND (");
            sb.append(selection);
            sb.append(')');
        }
        return sb.toString();
    }

    private String[] addIdToSelectionArgs(String id, String[] selectionArgs) {

        if (selectionArgs == null) {
            return new String[] { id };
        }

        int length = selectionArgs.length;
        String[] newSelectionArgs = new String[length + 1];
        newSelectionArgs[0] = id;
        System.arraycopy(selectionArgs, 0, newSelectionArgs, 1, length);
        return newSelectionArgs;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        UriType uriType = matchUri(uri);
        Context context = getContext();

        // Pick the correct database for this operation
        SQLiteDatabase db = getDatabase(context);

        if (ACTIVATE_ALL_LOGS) {
            Log.d(LOG_TAG, "update: uri=" + uri + ", match is " + uriType.name());
        }

        int result = -1;

        switch (uriType) {
            case TABLE_LOGIN_ID:
            case TABLE_SETTING_ID:
            case TABLE_LOCAL_STATE_ID:
                String id = uri.getPathSegments().get(1);
                result = db.update(uriType.getTableName(), values, whereWithId(selection),
                    addIdToSelectionArgs(id, selectionArgs));
                break;
            case TABLE_LOGIN:
            case TABLE_SETTING:
            case TABLE_LOCAL_STATE:
                result = db.update(uriType.getTableName(), values, selection, selectionArgs);
                break;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }

    @Override
    public boolean onCreate() {
        return true;
    }
}
