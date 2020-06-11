package com.example.projekt.layout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.projekt.DataBaseHelper;
import com.example.projekt.R;

public class EditPasswordActivity extends AppCompatActivity
{
    String user;

    String currentPassword;
    String newPassword;

    TextView password_warning;
    EditText password_current_password;
    EditText password_new_password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);

        Intent intent = getIntent();
        user = intent.getStringExtra("user");

        getUI();
    }

    public void change(View view)
    {
        DataBaseHelper db = new DataBaseHelper(EditPasswordActivity.this);

        getValues();

        if(db.updateUserPassword(user, currentPassword, newPassword) == true) { finish(); }
        else { password_warning.setVisibility(View.VISIBLE); }
    }

    public void getUI()
    {
        password_warning = findViewById(R.id.password_warning_text);
        password_current_password = findViewById(R.id.password_current_password);
        password_new_password = findViewById(R.id.password_new_password);
    }

    public void getValues()
    {
        currentPassword = password_current_password.getText().toString();
        newPassword = password_new_password.getText().toString();
    }
}
