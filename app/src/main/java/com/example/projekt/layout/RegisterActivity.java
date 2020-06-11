package com.example.projekt.layout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.projekt.DataBaseHelper;
import com.example.projekt.R;

public class RegisterActivity extends AppCompatActivity
{
    String userName;
    String password;

    TextView register_warning;
    EditText register_username;
    EditText register_password;

    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DataBaseHelper(RegisterActivity.this);

        getUI();
    }

    public void getUI()
    {
        register_warning = findViewById(R.id.register_warrning_text);
        register_username = findViewById(R.id.register_username);
        register_password = findViewById(R.id.register_password);
    }

    public void getValues()
    {
        userName = register_username.getText().toString();
        password = register_password.getText().toString();
    }

    public void register(View view)
    {
        getValues();

        if(db.register(userName, password) == true) { finish(); }
        else { register_warning.setVisibility(View.VISIBLE); }
    }
}
