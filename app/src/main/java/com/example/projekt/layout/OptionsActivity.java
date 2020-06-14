package com.example.projekt.layout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.projekt.R;

public class OptionsActivity extends AppCompatActivity
{
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        Intent intent = getIntent();
        user = intent.getStringExtra("user");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        Intent returnedIntent = new Intent();
        setResult(1, returnedIntent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0)
        {
            if(resultCode == RESULT_OK)
            {
                Intent returnedIntent = new Intent();
                setResult(RESULT_OK, returnedIntent);

                finish();
            }
        }
    }

    public void editGoals(View view)
    {
        Intent intent = new Intent(this, GoalActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public void editNotifications(View view)
    {
        Intent intent = new Intent(this, NotificationsActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public void showHistory(View view)
    {
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public void editUser(View view)
    {
        Intent intent = new Intent(this, EditUserActivity.class);
        intent.putExtra("user", user);
        startActivityForResult(intent, 0);
    }
}
