package com.example.projekt.layout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projekt.DataBaseHelper;
import com.example.projekt.R;

public class EditNotificationsActivity extends AppCompatActivity
{
    String user;

    LinearLayout layout;
    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notifications);

        Intent intent = getIntent();
        user = intent.getStringExtra("user");

        db = new DataBaseHelper(EditNotificationsActivity.this);

        getUI();
        readDatabase();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0)
        {
            if(resultCode == RESULT_OK)
            {
                db.startService(EditNotificationsActivity.this, user);
                layout.removeAllViews();
                readDatabase();
            }
        }
    }

    public void getUI()
    {
        layout = (LinearLayout)findViewById(R.id.edit_notifications_layout);
    }

    public void readDatabase()
    {
        Cursor cursor = db.getNotification(user);

        int i = 0;

        while(cursor.moveToNext())
        {
            LinearLayout temp = (LinearLayout)getLayoutInflater().inflate(R.layout.notification_element, (LinearLayout)findViewById(R.id.edit_notifications_layout));

            LinearLayout temp_child = (LinearLayout)layout.getChildAt(i); // Element ze schematu (glowny layout schematu)

            LinearLayout temp_child_layout_0 = (LinearLayout)temp_child.getChildAt(0); // Layout

            Button button_edit = (Button)temp_child_layout_0.getChildAt(0); // Przycisk
            button_edit.setTag(cursor.getString(cursor.getColumnIndex("p_id")));
            Button button_remove = (Button)temp_child_layout_0.getChildAt(1); // Przycisk
            button_remove.setTag(cursor.getString(cursor.getColumnIndex("p_id")));

            LinearLayout temp_child_layout_1 = (LinearLayout)temp_child.getChildAt(1); // Layout

            TextView text_0 = (TextView) temp_child_layout_1.getChildAt(0); // Text0
            text_0.setText("Tresc: " + cursor.getString(cursor.getColumnIndex("tresc")));
            TextView text_1 = (TextView) temp_child_layout_1.getChildAt(1); // Text1
            text_1.setText("Dzien: " + db.getDayName(Integer.parseInt(cursor.getString(cursor.getColumnIndex("dzien")))));
            TextView text_2 = (TextView) temp_child_layout_1.getChildAt(2); // Text2
            text_2.setText("Godzina: " + cursor.getString(cursor.getColumnIndex("godzina")));

            final CheckBox active = (CheckBox)temp_child.getChildAt(2); // CheckBox;
            active.setTag(cursor.getInt(cursor.getColumnIndex("p_id")));
            active.setChecked(cursor.getInt(cursor.getColumnIndex("wlaczone")) > 0);

            active.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    db.setNotificationActive(active.getTag().toString(), active.isChecked());
                }
            });

            final CheckBox repeat = (CheckBox)temp_child.getChildAt(3); // CheckBox;
            repeat.setTag(cursor.getInt(cursor.getColumnIndex("p_id")));
            repeat.setChecked(cursor.getInt(cursor.getColumnIndex("powtarzaj")) > 0);

            repeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    db.setNotificationRepeat(repeat.getTag().toString(), repeat.isChecked());
                }
            });

            i++;
        }

        cursor.close();
    }

    public void edit(View view)
    {
        Intent intent = new Intent(this, ChangeNotificationActivity.class);
        intent.putExtra("id", view.getTag().toString());
        startActivityForResult(intent, 0);
    }

    public void remove(View view)
    {
        db.deleteNotification(view.getTag().toString());
        db.startService(EditNotificationsActivity.this, user);
        layout.removeAllViews();
        readDatabase();
    }
}
