package com.example;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: kgalligan
 * Date: 8/27/11
 * Time: 1:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "test.db";
    private static final int DATABASE_VERSION = 1;
    public static final String[] SESSION_COLS = new String[]{
            "id",
            "name"
    };

  /*  public synchronized static DatabaseHelper getHelper(Context context)
    {
        if(helper == null)
        {
            helper = new DatabaseHelper(context);
        }

        return helper;
    }

    public synchronized static void closeHelper()
    {
        if(helper != null)
        {
            helper.close();
            helper = null;
        }
    }*/

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
            String createSessionTable =
                    "CREATE TABLE `session` " +
                            "(" +
                            "`id` INTEGER PRIMARY KEY AUTOINCREMENT , " +
                            "`name` VARCHAR " +
                            ") ";
            sqLiteDatabase.execSQL(createSessionTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL("drop table session");
        onCreate(sqLiteDatabase);
    }

    public long createSession(String name)
    {
        final SQLiteDatabase writableDatabase = getWritableDatabase();
        final ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);

        return writableDatabase.insertOrThrow("session", null, contentValues);
    }

    public static class Session
    {
        Integer id;
        String name;

        public Session(Integer id, String name)
        {
            this.id = id;
            this.name = name;
        }

        public Integer getId()
        {
            return id;
        }

        public String getName()
        {
            return name;
        }
    }

    public List<Session> loadAllSessions()
    {
        final SQLiteDatabase readableDatabase = getReadableDatabase();
        List<Session> sessions;
        final Cursor sessionCursor = readableDatabase.query("session", SESSION_COLS, null, null, null, null, "id", null);

        try
        {
            sessions = new ArrayList<Session>();

            while(sessionCursor.moveToNext())
            {
                final Session session = sessionFromCursor(sessionCursor);
                sessions.add(session);
            }
        }
        finally
        {
            sessionCursor.close();
        }

        return sessions;
    }

    private Session sessionFromCursor(Cursor cursor)
    {
        return new Session(cursor.getInt(0), cursor.getString(1));
    }


}
