package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EditMealsActivity extends AppCompatActivity
{
    String user;

    LinearLayout layout;
    DataBaseHelper db;

    LinearLayout last_selected;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meals);

        Intent intent = new Intent();
        user = intent.getStringExtra("user");

        db = new DataBaseHelper(EditMealsActivity.this);

        getUI();
        readDatabase();
    }

    @Override
    public void onBackPressed()
    {
        Intent returnedIntent = new Intent();
        setResult(0, returnedIntent);

        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1)
        {
            if(resultCode == RESULT_OK)
            {
                layout.removeAllViews();
                readDatabase();
            }
        }
    }

    public void getUI()
    {
        layout = findViewById(R.id.edit_meal_layout);
    }

    public void readDatabase()
    {
        Cursor cursor = db.getMeal();

        int i = 0;

        while(cursor.moveToNext())
        {
            LinearLayout temp = (LinearLayout)getLayoutInflater().inflate(R.layout.meal_element, (LinearLayout)findViewById(R.id.edit_meal_layout));

            LinearLayout temp_child = (LinearLayout)layout.getChildAt(i); // Element ze schematu (glowny layout schematu)

            LinearLayout temp_child_layout_0 = (LinearLayout)temp_child.getChildAt(1); // Layout

            TextView text_0 = (TextView) temp_child_layout_0.getChildAt(0); // Text0
            text_0.setText("Nazwa: " + cursor.getString(cursor.getColumnIndex("nazwa")));
            TextView text_1 = (TextView) temp_child_layout_0.getChildAt(1); // Text1
            text_1.setText("Kalorie: " + cursor.getString(cursor.getColumnIndex("kalorie")));
            TextView text_2 = (TextView) temp_child_layout_0.getChildAt(2); // Text2
            text_2.setText("Plyny: " + cursor.getString(cursor.getColumnIndex("plyny")));

            LinearLayout temp_child_layout_1 = (LinearLayout)temp_child_layout_0.getChildAt(3); // Layout

            TextView text_3 = (TextView) temp_child_layout_1.getChildAt(0); // Text3
            text_3.setText("Bialko: " + cursor.getString(cursor.getColumnIndex("bialko")));
            TextView text_4 = (TextView) temp_child_layout_1.getChildAt(1); // Text4
            text_4.setText("Weglowodany: " + cursor.getString(cursor.getColumnIndex("weglowodany")));
            TextView text_5 = (TextView) temp_child_layout_1.getChildAt(2); // Text5
            text_5.setText("Tluszcze: " + cursor.getString(cursor.getColumnIndex("tluszcze")));

            i++;
        }
    }

    public void add(View view)
    {
        Intent returnedIntent = new Intent();
        setResult(1, returnedIntent);

        finish();
    }

    public void remove(View view)
    {
        LinearLayout child_layout = (LinearLayout)last_selected.getChildAt(1);
        TextView name_text = (TextView)child_layout.getChildAt(0);
        String name = name_text.getText().toString();
        db.deleteMeal(name.substring(7));

        layout.removeView(last_selected);
    }

    public void edit(View view)
    {
        LinearLayout parent = (LinearLayout)view.getParent();
        LinearLayout child_layout = (LinearLayout)parent.getChildAt(1);
        TextView name_text = (TextView)child_layout.getChildAt(0);

        String name = name_text.getText().toString();

        Intent intent = new Intent(this, ChangeMealActivity.class);
        intent.putExtra("productName", name.substring(7));
        startActivityForResult(intent, 1);
    }

    public void select(View view)
    {
        if(last_selected != null) { last_selected.setSelected(false); }

        if(view.isSelected() == true) { view.setSelected(false); }
        else { view.setSelected(true); }

        last_selected = (LinearLayout)view;
    }
}
