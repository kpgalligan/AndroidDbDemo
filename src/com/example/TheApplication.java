package com.example;

import android.app.Application;

/**
 * Created by IntelliJ IDEA.
 * User: kgalligan
 * Date: 8/27/11
 * Time: 1:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class TheApplication extends Application
{
    private DatabaseHelper databaseHelper;

    @Override
    public void onCreate()
    {
        super.onCreate();
        databaseHelper = new DatabaseHelper(this);
    }

    public DatabaseHelper getDatabaseHelper()
    {
        return databaseHelper;
    }
}
