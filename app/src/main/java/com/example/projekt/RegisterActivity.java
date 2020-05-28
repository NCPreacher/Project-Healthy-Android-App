package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity
{
    TextView register_warning;
    EditText register_username;
    EditText register_password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_warning = findViewById(R.id.register_warrning_text);
        register_username = findViewById(R.id.username);
        register_password = findViewById(R.id.password);
    }

    public void register(View view)
    {
        String userName = register_username.getText().toString();
        String password = register_password.getText().toString();

        DataBaseHelper dataBaseHelper = new DataBaseHelper(RegisterActivity.this);

        if(dataBaseHelper.register(userName, password) == true)
        {
            finish();
        }
        else { register_warning.setVisibility(View.VISIBLE); }
    }
}
