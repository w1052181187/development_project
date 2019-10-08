注意事项：

=======================================================================

1.	数据插入重复
当数据库遇到插入数据重复时，通过Replace来实现插入和更新

1) 找到 包.类#方法：
xxpackge.dbContent.java#getBulkInsertString()

2) 代码
由：
        static String getBulkInsertString() {
            return new StringBuilder("INSERT INTO ").append(TABLE_NAME).append(" ( ").append(Columns.UID.getName()).append(", ").append(Columns.USERNAME.getName()).append(", ").append(Columns.NICKNAME.getName()).append(", ").append(Columns.FACE.getName()).append(", ").append(Columns.FACE_SMALL.getName()).append(", ").append(Columns.FACE_ORIGINAL.getName()).append(", ").append(Columns.GENDER.getName()).append(", ").append(Columns.TOPIC_COUNT.getName()).append(", ").append(Columns.AT_COUNT.getName()).append(", ").append(Columns.FOLLOW_COUNT.getName()).append(", ").append(Columns.FANS_COUNT.getName()).append(", ").append(Columns.EMAIL.getName()).append(", ").append(Columns.PHONE.getName()).append(", ").append(Columns.ABOUTME.getName()).append(", ").append(Columns.ABOUTMETIME.getName()).append(", ").append(Columns.PROVINCE.getName()).append(", ").append(Columns.CITY.getName()).append(", ").append(Columns.AREA.getName()).append(", ").append(Columns.FROM_AREA.getName()).append(", ").append(Columns.GENDER_TA.getName()).append(", ").append(Columns.VIP_INFO.getName()).append(", ").append(Columns.VIP_PIC.getName()).append(", ").append(Columns.PWD.getName()).append(", ").append(Columns.SINA_TOKEN.getName()).append(", ").append(Columns.QQ_TOKEN.getName()).append(", ").append(Columns.WEIXIN_TOKEN.getName()).append(", ").append(Columns.FONTSIZE.getName()).append(" ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)").toString();
        }
改为：
        static String getBulkInsertString() {
            return new StringBuilder("REPLACE INTO ").append(TABLE_NAME).append(" ( ").append(Columns.DATELINE.getName()).append(", ").append(Columns.FACE.getName()).append(", ").append(Columns.TID.getName()).append(", ").append(Columns.NICKNAME.getName()).append(", ").append(Columns.THUMB.getName()).append(", ").append(Columns.TITLE.getName()).append(", ").append(Columns.TYPE.getName()).append(", ").append(Columns.USERNAME.getName()).append(", ").append(Columns.DIGCOUNTS.getName()).append(", ").append(Columns.FORWARDS.getName()).append(", ").append(Columns.LOADSTATE.getName()).append(", ").append(Columns.REPLYS.getName()).append(", ").append(Columns.UID.getName()).append(", ").append(Columns.CONTENT.getName()).append(", ").append(Columns.IMAGE_LIST_JSON.getName()).append(", ").append(Columns.PARENT_LIST_JSON.getName()).append(", ").append(Columns.FAVORITE.getName()).append(", ").append(Columns.LIKE.getName()).append(", ").append(Columns.CHANNEL.getName()).append(" ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)").toString();
        }
即：由 INSERT INTO 改为 REPLCAE INTO

=======================================================================

2.	数据库升级
当database的"version"为newVersion时，需要指定数据库升级的表

1) 在input文件夹xxx.json文件中，"database"的"version"为值A，指定"database"的"version"为值A+1
2) 改动某个表，则指定"tables"相应的"table_name"表的"version"为值A+1。即在xxpackge.dbContent.java#upgradeTable()中，表版本小于A+1，重新create table

=======================================================================

3.	界面分页 - 数据
1) bean数据，属性分为net、local两类，net类（网络获取的数据属性即网络属性、page属性），local类（本地设置的属性即本地属性，local_edited属性标记是否设置过本地属性）
2) 网络刷新数据，当page=1，删除数据库中local_edited为0的行（本地未编辑），将数据库中数据的page设为-1，将网络数据的page设为1，插入网络数据的net类
3) 网络刷新数据，当page>1，将网络数据的page设为n，插入网络数据的net类
4) 读取数据，按page 1-n依次读取分页数据，当没有对应page的内容时，从网络获取数据

=======================================================================

// 以下已删除 start //
del_1.
当数据库项属性为unique时，修改动生成的代码，操作如下：

1) 找到 包.类#方法：
xxpackge.dbProvider.java#bulkInsert(Uri uri, ContentValues[] values)

2) 代码
由：
                    insertStmt = db.compileStatement(tablepart.getBulkInsertString());
                    for (ContentValues value : values) {
                        tablepart.bindValuesInBulkInsert(insertStmt, value);
                        insertStmt.execute();
                        insertStmt.clearBindings();
                    }
                    insertStmt.close();
                    db.setTransactionSuccessful();
                    numberInserted = values.length;

                    if (ACTIVATE_ALL_LOGS) {
                        Log.d(LOG_TAG, "bulkInsert: uri=" + uri + " | nb inserts : " + numberInserted);
                    }


改为（注意：tablenews 表名根据实际修改）：
                    boolean hasError = false;
                    Exception lastE = null;
                    insertStmt = db.compileStatement(tablenews.getBulkInsertString());
                    for (ContentValues value : values) {
                        tablenews.bindValuesInBulkInsert(insertStmt, value);
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
// 以上已删除 end //

=======================================================================

// 以下已删除 start //
del_2.	数据库升级
当database的"version"为newVersion时，需要指定数据库升级代码

1) 找到 包.类#方法：
xxpackge.dbContent.java#upgradeTable()

2) 代码
由：
        if (oldVersion < newVersion) {
            // No more changes since version 1 so jump to newVersion
            oldVersion = newVersion;
        }
改为：
        if (oldVersion < newVersion) {
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
                createTable(db);
                oldVersion = newVersion;
        }
即：重新create table
// 以上已删除 end //

=======================================================================