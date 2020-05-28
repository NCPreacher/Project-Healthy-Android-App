package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    TextView login_warning;
    EditText login_username;
    EditText login_password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login_warning = findViewById(R.id.login_warning_text);
        login_username = findViewById(R.id.username);
        login_password = findViewById(R.id.password);
    }

    public void login(View view)
    {
        String userName = login_username.getText().toString();
        String password = login_password.getText().toString();

        DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);

        if(dataBaseHelper.login(userName, password) == true)
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
