package com.example;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by IntelliJ IDEA.
 * User: kgalligan
 * Date: 8/27/11
 * Time: 2:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class DbCacheService extends Service
{
    private static boolean shutdownCalled = false;
    private static DatabaseHelper helper;

    public static synchronized DatabaseHelper getHelper(Context c)
    {
        if(shutdownCalled)
            throw new RuntimeException("Shutdown already called");

        if(helper == null)
        {
            helper = new DatabaseHelper(c);
        }

        return helper;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        shutdown();
    }

    private static synchronized void shutdown()
    {
        shutdownCalled = true;
        if(helper != null)
        {
            helper.close();
            helper = null;
        }
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}
