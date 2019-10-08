
package com.chengning.fenghuo.data.access;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.chengning.common.util.SerializeUtil;
import com.chengning.fenghuo.data.bean.MessageEntryBean;

public class MyMessageListServer
{

    private Context mContext;
    private ContentResolver mResolver;
    private static MyMessageListServer mInst;

    public static MyMessageListServer getInst( Context con )
    {
        if ( mInst == null )
        {
            synchronized ( MyMessageListServer.class )
            {
                if ( mInst == null )
                {
                    mInst = new MyMessageListServer( con );
                }
            }
        }
        return mInst;
    }

    private MyMessageListServer( Context con )
    {
        mResolver = con.getContentResolver();
        mContext = con;
    }

    public void delete( MessageEntryBean bean )
    {
        // String where = "local_other_id = ? ";
        // String[] selectionArgs = new String[]
        // {String.valueOf(bean.getLocal_other_id())};
        // try {
        // mResolver.delete(table_my_strangers_message_list.CONTENT_URI, where,
        // selectionArgs);
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
    }

    public void deleteTo( MessageEntryBean bean )
    {
        // String where = "msgtoid = ? ";
        // String[] selectionArgs = new String[]
        // {String.valueOf(bean.getMsgtoid())};
        // try {
        // mResolver.delete(table_my_strangers_message_list.CONTENT_URI, where,
        // selectionArgs);
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
    }

    public void deleteFrom( MessageEntryBean bean )
    {
        // String where = "msgfromid = ? ";
        // String[] selectionArgs = new String[]
        // {String.valueOf(bean.getMsgfromid())};
        // try {
        // mResolver.delete(table_my_strangers_message_list.CONTENT_URI, where,
        // selectionArgs);
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
    }

    public void deleteAll()
    {
        // String where = null;
        // String[] selectionArgs = null;
        // try {
        // mResolver.delete(table_my_message_list.CONTENT_URI, where,
        // selectionArgs);
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
    }

    public int insertMessage( MessageEntryBean bean )
    {
        List<MessageEntryBean> list = new ArrayList<MessageEntryBean>();
        list.add( bean );
        return insertMessageList( list );
    }

    /**
     * 批量插入
     */
    public int insertMessageList( List<MessageEntryBean> list )
    {
        ContentValues[] values = new ContentValues[list.size()];
        for ( int i = 0; i < list.size(); i++ )
        {
            ContentValues cv = new ContentValues();
            cv.put( "msgfrom", list.get( i ).getMsgfrom() );
            cv.put( "msgfromid", list.get( i ).getMsgfromid() );
            cv.put( "msgnickname", list.get( i ).getMsgnickname() );
            cv.put( "msgto", list.get( i ).getMsgto() );
            cv.put( "msgtoid", list.get( i ).getMsgtoid() );
            cv.put( "tonickname", list.get( i ).getTonickname() );
            cv.put( "face", list.get( i ).getFace() );
            cv.put( "dateline", list.get( i ).getDateline() );
            cv.put( "message",
                    SerializeUtil.serialize( list.get( i ).getMessage() ) );
            cv.put( "image_list",
                    SerializeUtil.serialize( list.get( i ).getImage_list() ) );
            cv.put( "is_new", list.get( i ).getIs_new() );
            cv.put( "url", list.get( i ).getUrl() );
            cv.put( "local_state", list.get( i ).getLocal_state() );
            cv.put( "local_stick", list.get( i ).getLocal_stick() );
            cv.put( "local_lasttime", list.get( i ).getLocal_lasttime() );
            cv.put( "local_other_id", list.get( i ).getLocal_other_id() );
            values[i] = cv;
        }
        int temp = 0;
        try
        {
            // temp = mResolver.bulkInsert(table_my_message_list.CONTENT_URI,
            // values);
        } catch ( Exception e )
        {
            e.printStackTrace();
        }
        return temp;
    }

    public List<MessageEntryBean> QueryMessageList()
    {
        return QueryMessage( null, null, null );
    }

    public List<MessageEntryBean> QueryMessage( String selection,
            String[] selectionArgs, String sortOrder )
    {
        Cursor cursor;
        // cursor = mResolver.query(table_my_message_list.CONTENT_URI, null,
        // selection, selectionArgs, sortOrder);
        List<MessageEntryBean> list = new ArrayList<MessageEntryBean>();
        // if (cursor!=null && cursor.moveToFirst()) {
        // do {
        // MessageEntryBean artcile = new MessageEntryBean();
        // artcile.setMsgfrom(cursor.getString(cursor.getColumnIndex("msgfrom")));
        // artcile.setMsgfromid(cursor.getInt(cursor.getColumnIndex("msgfromid")));
        // artcile.setMsgnickname(cursor.getString(cursor.getColumnIndex("msgnickname")));
        // artcile.setMsgto(cursor.getString(cursor.getColumnIndex("msgto")));
        // artcile.setMsgtoid(cursor.getInt(cursor.getColumnIndex("msgtoid")));
        // artcile.setTonickname(cursor.getString(cursor.getColumnIndex("tonickname")));
        // artcile.setFace(cursor.getString(cursor.getColumnIndex("face")));
        // artcile.setDateline(cursor.getString(cursor.getColumnIndex("dateline")));
        // ArrayList<String> sl =
        // SerializeUtil.deSerialize(cursor.getString(cursor.getColumnIndex("message")),
        // ArrayList.class);
        // artcile.setMessage(sl);
        // // TODO(image)
        // // ArrayList<Image> il=
        // SerializeUtil.deSerialize(cursor.getString(cursor.getColumnIndex("image_list")),
        // ArrayList.class);
        // // artcile.setImage_list(il);
        // artcile.setIs_new(cursor.getInt(cursor.getColumnIndex("is_new")));
        // artcile.setUrl(cursor.getString(cursor.getColumnIndex("url")));
        // artcile.setLocal_state(cursor.getString(cursor.getColumnIndex("local_state")));
        // artcile.setLocal_stick(cursor.getString(cursor.getColumnIndex("local_stick")));
        // artcile.setLocal_lasttime(cursor.getString(cursor.getColumnIndex("local_lasttime")));
        // artcile.setLocal_other_id(cursor.getInt(cursor.getColumnIndex("local_other_id")));
        // list.add(artcile);
        // } while (cursor.moveToNext());
        // cursor.close();
        // } else {
        // cursor.close();
        // }
        return list;
    }

}
