package com.appmf.net.appmf.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;

import com.appmf.net.appmf.app.MyApp;
import com.appmf.net.appmf.bean.DbConfigBean;
import com.appmf.net.appmf.utils.CommonUtils;
import com.appmf.net.appmf.utils.EncodeUtils;
import com.appmf.net.appmf.utils.FileIOUtils;
import com.appmf.net.appmf.utils.SPUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import static android.database.Cursor.FIELD_TYPE_FLOAT;
import static android.database.Cursor.FIELD_TYPE_INTEGER;
import static android.database.Cursor.FIELD_TYPE_STRING;
import static com.appmf.net.appmf.utils.SPUtils.KEY_FIRST_INIT_DB;

public class DBHelper extends SQLiteOpenHelper {

    private static HashMap<String, Object> mTempMap;
    private final String TAG = DBHelper.class.getSimpleName();

    private static final String DB_NAME = "appmf_dynamic.db";
    private static int DB_VERSIONE = 1;
    private SQLiteDatabase mDataBase;
    private Gson gson;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSIONE);
    }

    public DBHelper(Context context, String name) {
        super(context, name, null, DB_VERSIONE);
    }

    private static DBHelper mInstance = null;

    public synchronized static DBHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DBHelper(context);
            mTempMap = new HashMap<>();
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.mDataBase = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void testCreatTable() {
        String dropSql = "DROP TABLE IF EXISTS qs_members";
        String sql = "CREATE TABLE qs_members (\n" +
                "uid integer NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "utype tinyint(1) NOT NULL DEFAULT '1'," +
                "username varchar(60) NOT NULL DEFAULT '',\n" +
                "mobile varchar(11) NOT NULL,\n" +
                "email varchar(100) NOT NULL,\n" +
                "password varchar(100) NOT NULL DEFAULT ''," +
                "pwd_hash varchar(30) NOT NULL," +
                "reg_time int(10) NOT NULL," +
                "reg_ip varchar(30) DEFAULT NULL," +
                "reg_address varchar(30) NOT NULL," +
                "last_login_time int(10) NOT NULL," +
                "last_login_ip varchar(30) DEFAULT NULL," +
                "status tinyint(3) NOT NULL DEFAULT '1'," +
                "avatars varchar(255) NOT NULL," +
                "consultant int(10) NOT NULL)";
        String mobileIndex = "CREATE INDEX mobile ON qs_members(mobile)";
        String appIdIndex = "CREATE INDEX utype ON qs_members(utype)";
        String userName = "CREATE INDEX username ON qs_members (username)";
        mDataBase = getWriteDatabase();
        mDataBase.execSQL(dropSql);
        mDataBase.execSQL(sql);
        mDataBase.execSQL(mobileIndex);
        mDataBase.execSQL(appIdIndex);
        mDataBase.execSQL(userName);
        handleTableColummsCache("qs_members");
    }

    public boolean creatTable (ArrayList<DbConfigBean> lists) {

        try {
            mDataBase = getWriteDatabase();
            for (DbConfigBean bean : lists) {
                mDataBase.execSQL(bean.getDrop());
                mDataBase.execSQL(bean.getCreate());
                ArrayList<String> indexs = bean.getIndex();
                for (String index : indexs) {
                    mDataBase.execSQL(index);
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            mDataBase.close();
        }

    }

    public boolean initTableData(SPUtils spUtils, ArrayList<String> lists) {
        try {
            mDataBase = getWriteDatabase();
            for (String sql : lists) {
                mDataBase.execSQL(sql);
            }
            spUtils.putBoolean(KEY_FIRST_INIT_DB, true);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            mDataBase.close();
        }

    }

    public void testInsert() {
//        String sql = "INSERT INTO qs_members (uid, utype, username, email, mobile, password, pwd_hash, reg_time, reg_ip, reg_address, last_login_time, last_login_ip, status, avatars, consultant) VALUES " +
//                "(NULL, 1, '荷金来', '1049832979@qq.com', '13505226554', 'a2fc15035a735c7374b295bc243c61d2', '=@dPNm', 1446204579, '60.171.224.211', '', 1431009141, '60.171.224.211', 1, '', 0)," +
//                "(NULL, 1, 'zhengzsy82', '95421470@qq.com', '18788854533', '331aa419595c263877c86d5b4cbe9032', '=@dPNm', 1431943915, '60.171.240.192', '', 1431334058, '60.171.240.192', 1, '', 0)," +
//                "(NULL, 1, 'xjxedu', '595965722@qq.com', '', '198dd8e960a63451d92a204a72d22ee5', '=@dPNm', 1506702834, '183.164.141.219', '', 1506620129, '36.4.70.241', 1, '', 0)," +
//                "(NULL, 1, '宿州环宇', '1302177159@qq.com', '15505575101', '7a3332d5b6af45c0872e5ad3005ab655', '=@dPNm', 1497306860, '114.107.182.145', '', 1431271178, '114.107.136.48', 1, '', 0)," +
//                "(NULL, 1, 'iyhome', 'iyhome@126.com', '18133705656', '6fabb588c287904d5da7b1221bc0e0ce', '=@dPNm', 1432109256, '223.246.3.208', '', 1432037552, '223.246.3.208', 1, '', 0)," +
//                "(NULL, 1, 'ykankan333', '2277912@qq.com', '15715738386', 'd55594bffba9c8e15a5e6355e91dce16', '=@dPNm', 1431596295, '36.57.176.218', '', 1431596295, '36.57.176.218', 1, '', 0)," +
//                "(NULL, 1, 'edanyan', '83902550@qq.com', '13956860176', 'b0cad149aef1fdf811b1df439d82faa3', '=@dPNm', 1520691580, '220.248.175.36', '', 1457626872, '112.26.71.228', 1, '', 0)," +
//                "(NULL, 1, '聚天冷暖', '1241734810@qq.com', '18855788789', '3db31ab219bce5a2c799f11df2aa3225', '=@dPNm', 1431776712, '223.214.97.92', '', 1431776711, '223.214.97.92', 1, '', 0)," +
//                "(NULL, 1, '杨康', 'www.571860391@qq.com', '18705570977', 'c94dc16050453da0f84e388dc617b148', '=@dPNm', 1432208079, '61.191.120.15', '', 1432208079, '61.191.120.15', 1, '', 0)," +
//                "(NULL, 1, '201553019145', 'hy_nanz@163.com', '18133299855', '7a3332d5b6af45c0872e5ad3005ab655', '=@dPNm', 1433013245, '222.209.252.81', '', 0, '', 1, '', 0);";

        ContentValues values = new ContentValues();
//        values.put("id",NULL);
        values.put("uid",2);
//        values.put("type",1);
        values.put("start_time", "1547865839");
        values.put("end_time", 1547965839);
        values.put("card_type","4g");
        values.put("local_mobile","15035189476");
        values.put("client_mobile","15035189476");
        values.put("operator","15035189476");
        values.put("duration",600);
//        String sql = "INSERT INTO app_call_records (id,uid,type,local_mobile,client_mobile,operator,card_type,start_time,end_time,duration) values (NULL,'1','1','13610253652','13910236669','移动','4G','1547865839','1547866437','600')";
//        getWriteDatabase().rawQuery(sql, null);
        long value = getWriteDatabase().insert("app_call_records", null, values);
        Log.e("value", value + "");
    }

    public JsonObject handleDb(String data) {
        gson = new Gson();
        ArrayList<JsonObject> beans = gson.fromJson(data, new TypeToken<ArrayList<JsonObject>>(){}.getType());
        String key;
        try {
            if (mTempMap == null) {
                mTempMap = new HashMap<>();
            } else {
                mTempMap.clear();
            }
            mDataBase = getWriteDatabase();
            mDataBase.beginTransaction();
            for (JsonObject bean : beans) {
                key = bean.getAsJsonPrimitive("key").getAsString();
                mTempMap.put(key,false);
                switch (bean.getAsJsonPrimitive("method").getAsString()) {
                    case "add":
                        insert(mDataBase, bean);
                        break;
                    case "delete":
                        delete(mDataBase, bean);
                        break;
                    case "update":
                        update(mDataBase, bean);
                        break;
                    case "select":
                        select(mDataBase, bean);
                        break;
                    case "find":
                        find(mDataBase, bean);
                        break;
                    case "getfield":
                        getField(mDataBase, bean);
                        break;
                    case "count":
                        count(mDataBase, bean);
                        break;
                    case "min":
                        min(mDataBase, bean);
                        break;
                    case "max":
                        max(mDataBase, bean);
                        break;
                    case "sum":
                        sum(mDataBase, bean);
                        break;
                    case "statistics":
                        statistics(mDataBase, bean);
                        break;
                }
            }
            mDataBase.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("DB_HANDLE", e.getMessage());
        } finally {
            if (mDataBase != null && mDataBase.inTransaction()) {
                mDataBase.endTransaction();
            }
        }
        return gson.fromJson(mTempMap.toString(), JsonObject.class);

    }

    public boolean insert(SQLiteDatabase mDataBase, JsonObject json) {
        boolean result = false;
        if (json == null) {
            return result;
        }
        String key = json.getAsJsonPrimitive("key").getAsString();
        String tableName = json.getAsJsonPrimitive("table").getAsString();
        JsonObject jsonObject = json.getAsJsonObject("data");
//            StringBuilder sql = handleInsertValues(gson, tableName, jsonObject);
//            if (sql.length() == 0) {
//                mTempMap.put(key, false);
//                return false;
//            }
        ContentValues contentValues = new ContentValues();
        contentValues = handleContentValues(gson,tableName,jsonObject,contentValues);
//            mDataBase.execSQL(sql.toString());
////            Cursor cursor = mDataBase.rawQuery(sql.toString(), null);
//            Cursor cursor1 = mDataBase.rawQuery("SELECT LAST_INSERT_ROWID() FROM " + tableName, null);
//            long value = 0;
//            if (cursor1 != null) {
//                if (cursor1.moveToFirst()) {
//                    value = cursor1.getInt(0);
//                }
//            }
        long value = mDataBase.insert(tableName,null, contentValues);
        mTempMap.put(key, value);
        result = true;
        return result;
    }

    private StringBuilder handleInsertValues(Gson gson, String tableName, JsonObject jsonObject) {
        StringBuilder sql = new StringBuilder();
        if (jsonObject == null) {
            return sql;
        }
        JsonObject cacheTableColumn = gson.fromJson(handleTableColummsCache(tableName), JsonObject.class);
        sql.append("INSERT");
        sql.append(" INTO ");
        sql.append(tableName);
        sql.append('(').append(cacheTableColumn.getAsJsonPrimitive("pk").getAsString()).append(",");
        StringBuilder keyBuilder = new StringBuilder();
        StringBuilder valueBuilder = new StringBuilder();

        if (cacheTableColumn == null) {
            return new StringBuilder();
        }
        String filterStrTemp;
        Set<String> keys = jsonObject.keySet();
        int size = keys.size();
        int i = 0;
        for (String str : keys) {
            filterStrTemp = jsonObject.getAsJsonPrimitive(str).getAsString();
            switch (cacheTableColumn.get(str).getAsString()) {
                case "integer":
                    keyBuilder.append(str);
                    valueBuilder.append("'").append(Integer.valueOf(filterValue(filterStrTemp))).append("'");
                    break;
                case "text":
                    keyBuilder.append(str);
                    valueBuilder.append("'").append(filterValue(filterStrTemp)).append("'");
                    break;
            }
            i++;
            if (i != size) {
                keyBuilder.append(",");
                valueBuilder.append(",");
            }
        }
        sql.append(keyBuilder).append(") values (NULL,")
                .append(valueBuilder).append(")");
        return sql;
    }

    private StringBuilder handleUpdateValues(Gson gson, String tableName, JsonObject jsonObject) {
        StringBuilder sql = new StringBuilder();
        if (jsonObject == null) {
            return sql;
        }
        sql.append("UPDATE ");
        sql.append(tableName);
        sql.append(" SET ");
        JsonObject cacheTableColumn = gson.fromJson(handleTableColummsCache(tableName), JsonObject.class);
        if (cacheTableColumn == null) {
            return new StringBuilder();
        }
        String filterStrTemp;
        Set<String> keys = jsonObject.keySet();
        int size = keys.size();
        int i = 0;
        for (String str : keys) {
            filterStrTemp = jsonObject.getAsJsonPrimitive(str).getAsString();
            switch (cacheTableColumn.get(str).getAsString()) {
                case "integer":
                    sql.append(str).append("='").append(Integer.valueOf(filterValue(filterStrTemp))).append("'");
                    break;
                case "text":
                    sql.append(str).append("='").append(filterValue(filterStrTemp)).append("'");
                    break;
            }
            i++;
            if (i != size) {
                sql.append(",");
            }
        }
        sql.append(" ");
        return sql;
    }

    private ContentValues handleContentValues(Gson gson, String tableName, JsonObject jsonObject, ContentValues values) {
        if (jsonObject == null) {
            return values;
        }
        JsonObject cacheTableColumn = gson.fromJson(handleTableColummsCache(tableName), JsonObject.class);
        if (cacheTableColumn == null) {
            return values;
        }
        String filterStrTemp;
        for (String str : jsonObject.keySet()) {
            filterStrTemp = jsonObject.getAsJsonPrimitive(str).getAsString();
            switch (cacheTableColumn.get(str).getAsString()) {
                case "integer":
                    values.put(str, Integer.valueOf(filterValue(filterStrTemp)));
                    break;
                case "text":
                    values.put(str, filterValue(filterStrTemp));
                    break;
            }
        }
        return values;
    }

    /**
     * 修改单个数据
     *
     * @param mDataBase
     * @param json
     * @return
     */
    public boolean update(SQLiteDatabase mDataBase, JsonObject json) {
        boolean result = false;
        if (json == null) {
            return result;
        }
        String key = json.getAsJsonPrimitive("key").getAsString();
        String tableName = json.getAsJsonPrimitive("table").getAsString();
        StringBuilder sql = new StringBuilder();
        JsonObject jsonObject = json.getAsJsonObject("data");
        String str =  handleUpdateValues(gson, tableName, jsonObject).toString();
        if (TextUtils.isEmpty(str)) {
            mTempMap.put(key, false);
            return false;
        }
        sql.append(str);
//            ContentValues contentValues = new ContentValues();
//            contentValues = handleContentValues(gson,tableName, jsonObject, contentValues);

        JsonObject cacheTableColumn = gson.fromJson(handleTableColummsCache(tableName), JsonObject.class);
        boolean isHasOrder = json.has("order") || json.has("limit");
        String id = cacheTableColumn.getAsJsonPrimitive("pk").getAsString();
        if (isHasOrder) {
            sql.append(" where ").append(id).append(" in (select ")
                    .append(id).append(" from ").append(tableName).append(" ");
            sql.append(handleWhere(gson, json, cacheTableColumn));
            sql.append(handleOrder(gson, json, cacheTableColumn));
            sql.append(handleLimit(json));
            sql.append(")");
        } else {
            sql.append(handleWhere(gson, json, cacheTableColumn));
        }

//        Cursor cursor = mDataBase.rawQuery(sql.toString(), null);
        int num = mDataBase.compileStatement(sql.toString()).executeUpdateDelete();
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                num = cursor.getInt(0);
//            }
//        }
//            int num = mDataBase.update(tableName, contentValues, sql.toString(), null);
        mTempMap.put(key, num);
        result = true;
        return result;
    }

    public boolean select(SQLiteDatabase database, JsonObject json) {
        return  select(database,json, false);
    }

    public boolean find(SQLiteDatabase database, JsonObject json) {
        return  select(database,json, true);
    }

    public boolean getField(SQLiteDatabase database, JsonObject json) {
        boolean result = false;
        if (json == null) {
            return result;
        }
        String key = json.getAsJsonPrimitive("key").getAsString();
        String tableName = json.getAsJsonPrimitive("table").getAsString();

        try {
            StringBuilder sql = new StringBuilder("select ");
            JsonObject cacheTableColumn = gson.fromJson(handleTableColummsCache(tableName), JsonObject.class);
            StringBuilder fieldStr = handleField(gson, json, cacheTableColumn, true);
            sql.append(fieldStr.length() == 0 ? "*" : fieldStr);
            sql.append(" from ").append(tableName);

            sql = handleJoin(sql, json, cacheTableColumn);
            sql.append(handleWhere(gson, json, cacheTableColumn));
            sql.append(handleGroup(gson, json, cacheTableColumn));
            sql.append(handleOrder(gson, json, cacheTableColumn));
            sql.append(handleLimit(json));
            mDataBase = getWriteDatabase();
            mDataBase.beginTransaction();
            Cursor cursor = mDataBase.rawQuery(sql.toString(), null);
            if (cursor != null) {
                JsonArray keyArray = new JsonArray();
                int type;
                while (cursor.moveToNext()) {
                    type = cursor.getType(0);
                    switch (type) {
                        case FIELD_TYPE_INTEGER:
                            keyArray.add(cursor.getInt(0));
                            break;
                        case FIELD_TYPE_FLOAT:
                            keyArray.add(cursor.getFloat(0));
                            break;
                        case FIELD_TYPE_STRING:
                            keyArray.add(cursor.getString(0));
                            break;

                    }
                }
                mTempMap.put(key, keyArray);
            } else {
                mTempMap.put(key, null);
            }
            result = true;
            mDataBase.setTransactionSuccessful();
        } catch (Exception e) {
            mTempMap.put(key, false);
        } finally {
            if (mDataBase != null && mDataBase.inTransaction()) {
                mDataBase.endTransaction();
            }
        }
        return  result;
    }

    private boolean select (SQLiteDatabase database, JsonObject json, boolean isSingle) {
        boolean result = false;
        if (json == null) {
            return result;
        }
        String key = json.getAsJsonPrimitive("key").getAsString();
        String tableName = json.getAsJsonPrimitive("table").getAsString();

        StringBuilder sql = new StringBuilder("select ");
        JsonObject cacheTableColumn = gson.fromJson(handleTableColummsCache(tableName), JsonObject.class);
        StringBuilder fieldStr = handleField(gson, json, cacheTableColumn, false);
        sql.append(fieldStr.length() == 0 ? "*" : fieldStr);
        sql.append(" from ").append(tableName);
        sql = handleJoin(sql, json, cacheTableColumn);
        sql.append(handleWhere(gson, json, cacheTableColumn));
        sql.append(handleGroup(gson, json, cacheTableColumn));
        sql.append(handleOrder(gson, json, cacheTableColumn));
        if (isSingle) {
            sql.append(" limit 1");
        } else {
            sql.append(handleLimit(json));
        }
        Cursor cursor = database.rawQuery(sql.toString(), null);
        if (cursor != null) {
            JsonArray keyArray = new JsonArray();
            while (cursor.moveToNext()) {
                int size  = cursor.getColumnCount();
                int type;
                JsonObject keyObject = new JsonObject();
                for (int i = 0;i < size; i++) {
                    type = cursor.getType(i);
                    switch (type) {
                        case FIELD_TYPE_INTEGER:
                            keyObject.addProperty(cursor.getColumnName(i), cursor.getInt(i));
                            break;
                        case FIELD_TYPE_FLOAT:
                            keyObject.addProperty(cursor.getColumnName(i), cursor.getFloat(i));
                            break;
                        case FIELD_TYPE_STRING:
                            keyObject.addProperty(cursor.getColumnName(i), cursor.getString(i));
                            break;
                    }
                }
                if (isSingle) {
                    mTempMap.put(key, keyObject);
                    return true;
                }
                keyArray.add(keyObject);
            }
            mTempMap.put(key, keyArray);
        } else {
            mTempMap.put(key, null);
        }
        result = true;
        return result;
    }
    public boolean count(SQLiteDatabase mDataBase, JsonObject json) {
        return handleCountMinMaxSum(mDataBase, json, "count");
    }
    public boolean min(SQLiteDatabase mDataBase, JsonObject json) {
        return handleCountMinMaxSum(mDataBase, json, "min");
    }
    public boolean max(SQLiteDatabase mDataBase, JsonObject json) {
        return handleCountMinMaxSum(mDataBase, json, "max");
    }
    public boolean sum(SQLiteDatabase mDataBase, JsonObject json) {
        return handleCountMinMaxSum(mDataBase, json, "sum");
    }
    public boolean handleCountMinMaxSum(SQLiteDatabase mDataBase, JsonObject json, String type) {
        boolean result = false;
        if (json == null) {
            return result;
        }
        String key = json.getAsJsonPrimitive("key").getAsString();
        String tableName = json.getAsJsonPrimitive("table").getAsString();

        StringBuilder sql = new StringBuilder("select ");
        JsonObject cacheTableColumn = gson.fromJson(handleTableColummsCache(tableName), JsonObject.class);
        switch (type) {
            case "count":
                sql.append("count(");
                StringBuilder fieldStr = handleField(gson, json, cacheTableColumn, false);
                sql.append(fieldStr.length() == 0 ? "*" : fieldStr);
                sql.append(") as count");
                break;
            case "min":
                sql.append("min(");
                StringBuilder fieldStrMin = handleField(gson, json, cacheTableColumn, true);
                sql.append(fieldStrMin.length() == 0 ? "*" : fieldStrMin);
                sql.append(") as min");
                break;
            case "max":
                sql.append("max(");
                StringBuilder fieldStrMax = handleField(gson, json, cacheTableColumn, true);
                sql.append(fieldStrMax.length() == 0 ? "*" : fieldStrMax);
                sql.append(") as max");
                break;
            case "sum":
                sql.append("sum(");
                StringBuilder fieldStrSum = handleField(gson, json, cacheTableColumn, true);
                sql.append(fieldStrSum.length() == 0 ? "*" : fieldStrSum);
                sql.append(") as sum");
                break;
        }

        sql.append(" from ").append(tableName);
        sql = handleJoin(sql, json, cacheTableColumn);
        sql.append(handleWhere(gson, json, cacheTableColumn));
        sql.append(handleGroup(gson, json, cacheTableColumn));
        sql.append(" limit 1");
        Cursor cursor = mDataBase.rawQuery(sql.toString(), null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int index = cursor.getColumnIndex(type);
                mTempMap.put(key, cursor.getInt(index));
            } else {
                mTempMap.put(key, 0);
            }
        } else {
            mTempMap.put(key, false);
        }
        result = true;
        return  result;
    }

    public boolean statistics(SQLiteDatabase mDataBase, JsonObject json) {
        boolean result = false;
        if (json == null) {
            return result;
        }
        String key = json.getAsJsonPrimitive("key").getAsString();
        String tableName = json.getAsJsonPrimitive("table").getAsString();

        StringBuilder sql = new StringBuilder("select ");
        JsonObject cacheTableColumn = gson.fromJson(handleTableColummsCache(tableName), JsonObject.class);
        StringBuilder fieldStr = handleField(gson, json, cacheTableColumn, true);
        sql.append(fieldStr.length() == 0 ? "*" : fieldStr);
        sql.append(",count(*) as count");

        sql.append(" from ").append(tableName);
        sql = handleJoin(sql, json, cacheTableColumn);
        sql.append(handleWhere(gson, json, cacheTableColumn));
        sql.append(handleGroup(gson, json, cacheTableColumn));
        sql.append(" having count>1");
        sql.append(handleOrder(gson, json, cacheTableColumn));
        sql.append(handleLimit(json));
        Cursor cursor = mDataBase.rawQuery(sql.toString(), null);
        if (cursor != null) {
            JsonArray keyArray = new JsonArray();
            while (cursor.moveToNext()) {
                int size  = cursor.getColumnCount();
                int type;
                JsonObject keyObject = new JsonObject();
                for (int i = 0;i < size; i++) {
                    type = cursor.getType(i);
                    switch (type) {
                        case FIELD_TYPE_INTEGER:
                            keyObject.addProperty(cursor.getColumnName(i), cursor.getInt(i));
                            break;
                        case FIELD_TYPE_FLOAT:
                            keyObject.addProperty(cursor.getColumnName(i), cursor.getFloat(i));
                            break;
                        case FIELD_TYPE_STRING:
                            keyObject.addProperty(cursor.getColumnName(i), cursor.getString(i));
                            break;
                    }
                }
                keyArray.add(keyObject);
            }
            mTempMap.put(key, keyArray);
        } else {
            mTempMap.put(key, false);
        }
        result = true;
        return  result;
    }

    private StringBuilder handleJoin(StringBuilder sql, JsonObject json, JsonObject cacheTableColumn) {
        if (!json.has("join")) {
            return sql;
        }
        sql.append("m ");
        JsonArray join = json.getAsJsonArray("join");
        JsonObject object;
        JsonArray valueArray;
        String filterStrTemp;
        String logic = "and";
        for (int i = 0; i < join.size(); i++) {
            object = join.get(i).getAsJsonObject();
            sql.append(object.getAsJsonPrimitive("type").getAsString()).append(" join ")
                    .append(object.getAsJsonPrimitive("table").getAsString())
                    .append(object.getAsJsonPrimitive("alias").getAsString())
                    .append(" on ");
            JsonObject onObject = object.getAsJsonObject("on");
            Set<String> keys = onObject.keySet();
            ArrayList<String> list = new ArrayList<>();
            for (String keyStr : keys) {
                if (!isHasFieldInCache(keyStr, cacheTableColumn)) {
                    continue;
                }
                list.add(keyStr);
            }
            int size = list.size();
            for (String keyStr : keys) {
                if (TextUtils.equals("_logic", keyStr)) {
                    logic = onObject.getAsJsonPrimitive(keyStr).getAsString();
                    continue;
                }
                valueArray = onObject.getAsJsonArray(keyStr);
                sql.append(keyStr).append(valueArray.get(0));
                filterStrTemp = valueArray.get(1) + "";
                switch (cacheTableColumn.get(keyStr).getAsString()) {
                    case "integer":
                        sql.append(Integer.valueOf(filterValue(filterStrTemp)));
                        break;
                    case "text":
                        sql.append(filterValue(filterStrTemp));
                        break;
                }
                if (list.indexOf(keyStr) != size - 1) {
                    sql.append(" ");
                    sql.append(logic);
                }
            }
            if (i != join.size() - 1) {
                sql.append(" ");
            }
        }
        return sql;
    }

    private StringBuilder handleField(Gson gson, JsonObject json,JsonObject cacheTableColumn, boolean isField) {
        StringBuilder sql = new StringBuilder();
        if (json.has("distinct")) {
            String distinct = json.getAsJsonPrimitive("distinct").getAsString();
            if (isHasFieldInCache(distinct, cacheTableColumn)) {
                sql.append("distinct(").append(distinct)
                        .append(")");
                return sql;
            }
        }

        if (!json.has("field")) {
            if (isField) {
                ArrayList<String> keys = new ArrayList<>(cacheTableColumn.keySet());
                if (!CommonUtils.isListEmpty(keys)) {
                    return sql.append(keys.get(0));
                }
            }
            return sql.append("*");
        }
        JsonArray field = json.getAsJsonArray("field");
        if (isField && field.size() > 0) {
            return sql.append(field.get(0));
        }
        for (int i = 0; i < field.size(); i++) {
            if (!isHasFieldInCache(field.get(i).getAsString(), cacheTableColumn)) {
                continue;
            }
            sql.append(field.get(i).getAsString());
            if (i != field.size() - 1) {
                sql.append(",");
            }
        }

        return sql;
    }

    boolean isHasFieldInCache(String field, JsonObject cachObject) {
        if (cachObject.has(field)) {
            return true;
        }
        return false;
    }

    /**
     * 删除单个数据
     *
     * @param mDataBase
     * @param json
     * @return
     */
    public boolean delete(SQLiteDatabase mDataBase, JsonObject json) {
        boolean result = false;
        if (json == null) {
            return result;
        }
        String key = json.getAsJsonPrimitive("key").getAsString();
        String tableName = json.getAsJsonPrimitive("table").getAsString();
        StringBuffer sql = new StringBuffer("delete from ");
        sql.append(tableName).append(" ");
        Gson gson = new Gson();
        JsonObject cacheTableColumn = gson.fromJson(handleTableColummsCache(tableName), JsonObject.class);
        boolean isHasOrder = json.has("order") || json.has("limit");
        String id = cacheTableColumn.getAsJsonPrimitive("pk").getAsString();
        if (isHasOrder) {
            sql.append("where ").append(id);
            sql.append(" in (select ")
                    .append(id).append(" from ").append(tableName).append(" ");
            sql.append(handleWhere(gson, json, cacheTableColumn));
            sql.append(handleOrder(gson, json, cacheTableColumn));
            sql.append(handleLimit(json));
            sql.append(")");
        } else {
            sql.append(handleWhere(gson, json, cacheTableColumn));
        }
        int num = mDataBase.compileStatement(sql.toString()).executeUpdateDelete();
//        Cursor cursor = mDataBase.rawQuery(sql.toString(), null);
//        int num = 0;
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                num = cursor.getInt(0);
//            }
//        }
//            int num = mDataBase.delete(tableName, sql.toString(), null);
        mTempMap.put(key, num);
        result = true;
        return result;
    }

    private StringBuilder handleLimit(JsonObject json) {
        StringBuilder sql = new StringBuilder();
        if (json.has("page")) {
            int page = json.getAsJsonPrimitive("page").getAsInt();
            if (page > 0) {
                int limit = 20;
                if (json.has("limit")) {
                    limit = json.getAsJsonPrimitive("limit").getAsInt();
                }
                sql.append(" limit ").append((page - 1) * limit).append(",").append(limit);
                return  sql;
            }
        }
        if (json.has("limit")) {
            int limit = json.getAsJsonPrimitive("limit").getAsInt();
            sql.append(" limit ").append(limit);
        }
        return sql;
    }

    private StringBuilder handleOrder(Gson gson, JsonObject json, JsonObject cacheTableColumn) {
        StringBuilder sql = new StringBuilder();
        if (!json.has("order")) {
            return sql;
        }
        JsonObject orderObject = json.getAsJsonObject("order");
        sql.append(" ORDER BY ");
        Set<String> keys = orderObject.keySet();
        ArrayList<String> list = new ArrayList<>();
        for (String keyStr : keys) {
            if (!isHasFieldInCache(keyStr, cacheTableColumn)) {
                continue;
            }
            list.add(keyStr);
        }
        int size = list.size();
        for (String keyStr : list) {
            String orderValue = orderObject.getAsJsonPrimitive(keyStr).getAsString();
            if (!TextUtils.equals(orderValue, "desc") && !TextUtils.equals(orderValue, "asc")) {
                orderValue = "asc";
            }
            sql.append(keyStr).append(" ").append(orderValue);
            if (list.indexOf(keyStr) != size - 1) {
                sql.append(",");
            }
        }
        return sql;
    }

    private StringBuilder handleGroup(Gson gson, JsonObject json, JsonObject cacheTableColumn) {
        StringBuilder sql = new StringBuilder();
        if (!json.has("group")) {
            return sql;
        }
        String group = json.getAsJsonPrimitive("group").getAsString();
        if (isHasFieldInCache(group, cacheTableColumn)) {
            sql.append(" group by ").append(group);
        }
        return sql;
    }

    private StringBuilder handleWhere(Gson gson, JsonObject json, JsonObject cacheTableColumn) {
        return handleWhere(gson,json, cacheTableColumn, true);
    }

    private StringBuilder handleWhere(Gson gson, JsonObject json, JsonObject cacheTableColumn, boolean isAppendWhere) {
        StringBuilder sql = new StringBuilder();
        if (!json.has("where")) {
            return sql;
        }
        if (isAppendWhere) {
            sql.append(" where ");
        }
        JsonObject whereObject = json.getAsJsonObject("where");
        Set<String> keys = whereObject.keySet();
        JsonArray valueArray;

        ArrayList<String> list = new ArrayList<>();
        for (String keyStr : keys) {
            if (!isHasFieldInCache(keyStr, cacheTableColumn)) {
                continue;
            }
            list.add(keyStr);
        }
        String filterStrTemp;
        String logic = "and";
        int size = list.size();
        for (String key : list) {
            if (TextUtils.equals("_logic", key)) {
                logic = whereObject.getAsJsonPrimitive(key).getAsString();
                continue;
            }
            valueArray = whereObject.getAsJsonArray(key);
            sql.append(key).append(valueArray.get(0).getAsString());
            filterStrTemp = valueArray.get(1) + "";
            switch (cacheTableColumn.get(key).getAsString()) {
                case "integer":
                    sql.append(Integer.valueOf(filterValue(filterStrTemp)));
                    break;
                case "text":
                    sql.append(filterValue(filterStrTemp));
                    break;
            }
            if ( list.indexOf(key) != size - 1) {
                sql.append(" ");
                sql.append(logic);
                sql.append(" ");
            }
        }
        return sql;
    }

    private synchronized SQLiteDatabase getWriteDatabase() {
        if (mDataBase == null || !mDataBase.isOpen()) {
            mDataBase = getWritableDatabase();
        }
        return mDataBase;
    }


    /**
     * 读取相应表结构（包括字段名，字段相应类型
     * @param
     * @param tableName
     * @return
     */
    String handleTableColummsCache(String tableName) {
        String data = FileIOUtils.getFromAssets(MyApp.getInstance(), "sql/" + EncodeUtils.md5(tableName) + ".json");
        return data;
    }

    public String filterValue(String str) {
        str = Html.fromHtml(str).toString();
//        // <p>段落替换为换行
//        str = str.replaceAll("<p .*?>", "\r\n");
//        // <br><br/>替换为换行
//        str = str.replaceAll("<br\\s*/?>", "\r\n");
//        // 去掉其它的<>之间的东西
//        str = str.replaceAll("\\<.*?>", "");
//        // 还原HTML
//        // content = HTMLDecoder.decode(content);
//        //&ldquo;&quot;&nbsp;
//        str = str.replaceAll("&.dquo;", "\"");
//        str = str.replaceAll("&nbsp;", " ");
        if (!str.contains("{") || !str.contains("}")) {
            return str;
        }
        String temp = str.substring(str.indexOf("{") + 1, str.indexOf("}"));
        if (!TextUtils.isEmpty(temp)) {
            str = temp;
        }
        return str;
    }

}
