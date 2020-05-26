package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity
{
    EditText mUserName;
    EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUserName = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
    }

    public void register(View view)
    {
        String userName = mUserName.getText().toString();
        String password = mPassword.getText().toString();

        DataBaseHelper dataBaseHelper = new DataBaseHelper(RegisterActivity.this);

        dataBaseHelper.register(userName, password);
    }
}
