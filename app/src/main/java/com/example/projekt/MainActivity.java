package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
{
    EditText mUserName;
    EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserName = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
    }

    public void login(View view)
    {
        String userName = mUserName.getText().toString();
        String password = mPassword.getText().toString();

        DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);

        if(dataBaseHelper.login(userName, password) == true)
        {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        }
    }

    public void register(View view)
    {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
