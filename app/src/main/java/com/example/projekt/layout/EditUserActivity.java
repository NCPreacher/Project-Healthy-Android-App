package com.example.projekt.layout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.projekt.DataBaseHelper;
import com.example.projekt.R;

public class EditUserActivity extends AppCompatActivity
{
    String user;
    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        Intent intent = getIntent();
        user = intent.getStringExtra("user");

        db = new DataBaseHelper(EditUserActivity.this);
    }

    public void deleteAccount(View view)
    {
        db.deleteUser(user);

        Intent returnedIntent = new Intent();
        setResult(0, returnedIntent);

        finish();
    }

    public void editPassword(View view)
    {
        Intent intent = new Intent(this, EditPasswordActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}
