package com.example.projekt.layout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projekt.DataBaseHelper;
import com.example.projekt.R;

public class EditGoalsActivity extends AppCompatActivity
{
    String user;

    LinearLayout layout;
    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_goals);

        Intent intent = getIntent();
        user = intent.getStringExtra("user");

        db = new DataBaseHelper(EditGoalsActivity.this);

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

    public void getUI() { layout = findViewById(R.id.edit_goal_layout); }

    public void readDatabase()
    {
        Cursor cursor = db.getGoal(user);

        int i = 0;

        while(cursor.moveToNext())
        {
            LinearLayout temp = (LinearLayout)getLayoutInflater().inflate(R.layout.goal_element, (LinearLayout)findViewById(R.id.edit_goal_layout));

            LinearLayout temp_child = (LinearLayout)layout.getChildAt(i); // Element ze schematu (glowny layout schematu)

            LinearLayout temp_child_layout_0 = (LinearLayout)temp_child.getChildAt(0); // Layout

            Button button_edit = (Button)temp_child_layout_0.getChildAt(0); // Przycisk
            button_edit.setTag(cursor.getString(cursor.getColumnIndex("c_id")));
            Button button_remove = (Button)temp_child_layout_0.getChildAt(1); // Przycisk
            button_remove.setTag(cursor.getString(cursor.getColumnIndex("c_id")));

            LinearLayout temp_child_layout_1 = (LinearLayout)temp_child.getChildAt(1); // Layout

            TextView text_0 = (TextView) temp_child_layout_1.getChildAt(0); // Text0
            text_0.setText(db.getDayName(Integer.parseInt(cursor.getString(cursor.getColumnIndex("dzien")))));
            TextView text_1 = (TextView) temp_child_layout_1.getChildAt(1); // Text1
            text_1.setText("Kalorie: " + cursor.getString(cursor.getColumnIndex("kalorie")));
            TextView text_2 = (TextView) temp_child_layout_1.getChildAt(2); // Text2
            text_2.setText("Plyny: " + cursor.getString(cursor.getColumnIndex("nawodnienie")));

            LinearLayout temp_child_layout_2 = (LinearLayout)temp_child.getChildAt(2); // Layout

            TextView text_3 = (TextView) temp_child_layout_2.getChildAt(0); // Text3
            text_3.setText("Bialko: " + cursor.getString(cursor.getColumnIndex("bialko")));
            TextView text_4 = (TextView) temp_child_layout_2.getChildAt(1); // Text4
            text_4.setText("Weglowodany: " + cursor.getString(cursor.getColumnIndex("weglowodany")));
            TextView text_5 = (TextView) temp_child_layout_2.getChildAt(2); // Text5
            text_5.setText("Tluszcze: " + cursor.getString(cursor.getColumnIndex("tluszcze")));

            i++;
        }

        cursor.close();
    }

    public void edit(View view)
    {
        String id = view.getTag().toString();

        Intent returnedIntent = new Intent();
        returnedIntent.putExtra("id", id); // Przekaz ID do poprzedniego activity
        setResult(1, returnedIntent);

        finish();
    }

    public void remove(View view)
    {
        db.deleteGoal(view.getTag().toString());
        layout.removeAllViews();
        readDatabase();
    }
}
