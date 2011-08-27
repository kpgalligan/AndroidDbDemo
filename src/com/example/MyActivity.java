package com.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MyActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViewById(R.id.addSession).setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                grabHelper().createSession(Long.toString(System.currentTimeMillis()));
                showSessions();
            }
        });

        findViewById(R.id.exceptionParty).setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                exceptionParty();
            }
        });

        findViewById(R.id.exceptionParty2).setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                new DatabaseHelper(MyActivity.this).createSession(Long.toString(System.currentTimeMillis()));
                showSessions();
            }
        });

        showSessions();
    }

    private DatabaseHelper grabHelper()
    {
        return ((TheApplication)getApplication()).getDatabaseHelper();
    }

    private void exceptionParty()
    {
        DatabaseHelper helper = new DatabaseHelper(this);
        showSessions(helper.loadAllSessions());
    }

    private void showSessions()
    {
        List<DatabaseHelper.Session> sessions = grabHelper().loadAllSessions();
        showSessions(sessions);
    }

    private void showSessions(List<DatabaseHelper.Session> sessions)
    {
        StringBuilder stringBuilder = new StringBuilder();

        for (DatabaseHelper.Session session : sessions)
        {
            stringBuilder.append("id: ").append(session.getId()).append(", name: ").append(session.getName()).append("\n");
        }

        ((TextView)findViewById(R.id.output)).setText(stringBuilder.toString());
    }
}
