package com.example.projekt.layout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.projekt.DataBaseHelper;
import com.example.projekt.R;

public class MainActivity extends AppCompatActivity
{
    String userName;
    String password;

    TextView login_warning;
    EditText login_username;
    EditText login_password;

    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DataBaseHelper(MainActivity.this);

        getUI();
    }

    public void getUI()
    {
        login_warning = findViewById(R.id.login_warning_text);
        login_username = findViewById(R.id.login_username);
        login_password = findViewById(R.id.login_password);
    }

    public void getValues()
    {
        userName = login_username.getText().toString();
        password = login_password.getText().toString();
    }

    public void login(View view)
    {
        getValues();

        if(db.login(userName, password) == true)
        {
            resetActivity();

            Intent intent = new Intent(this, MenuActivity.class);
            intent.putExtra("user", userName);
            startActivity(intent);
        }
        else { login_warning.setVisibility(View.VISIBLE); }
    }

    public void register(View view)
    {
        resetActivity();

        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void resetActivity()
    {
        if(login_warning.getVisibility() == View.VISIBLE) { login_warning.setVisibility(View.INVISIBLE); }

        login_username.setText("");
        login_password.setText("");
    }
}
