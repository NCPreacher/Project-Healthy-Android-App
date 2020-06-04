package com.example.projekt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DataBaseHelper extends SQLiteOpenHelper
{
    public DataBaseHelper(@Nullable Context context)
    {
        super(context, "project_healthy.db", null, 9);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String createHistory = "CREATE TABLE IF NOT EXISTS \"historia\" (\n" +
                "\t\"h_id\"\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "\t\"uzytkownik\"\tchar(255) NOT NULL,\n" +
                "\t\"data\"\tdate NOT NULL,\n" +
                "\t\"nazwa\"\tchar(255) NOT NULL,\n" +
                "\t\"ilosc\"\tfloat NOT NULL,\n" +
                "\tCONSTRAINT \"historia_ibfk_1\" FOREIGN KEY(\"uzytkownik\") REFERENCES \"uzytkownicy\"(\"uzytkownik\") ON DELETE CASCADE,\n" +
                "\tCONSTRAINT \"historia_ibfk_2\" FOREIGN KEY(\"nazwa\") REFERENCES \"produkty\"(\"nazwa\")\n" +
                ");";

        db.execSQL(createHistory);

        String createGoals = "CREATE TABLE IF NOT EXISTS \"cele\" (\n" +
                "\t\"c_id\"\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "\t\"uzytkownik\"\tchar(255) NOT NULL,\n" +
                "\t\"dzien\"\tint NOT NULL,\n" +
                "\t\"kalorie\"\tfloat NOT NULL,\n" +
                "\t\"bialko\"\tfloat NOT NULL,\n" +
                "\t\"weglowodany\"\tfloat NOT NULL,\n" +
                "\t\"tluszcze\"\tfloat NOT NULL,\n" +
                "\t\"nawodnienie\"\tint DEFAULT NULL,\n" +
                "\tCONSTRAINT \"cele_ibfk_1\" FOREIGN KEY(\"uzytkownik\") REFERENCES \"uzytkownicy\"(\"uzytkownik\") ON DELETE CASCADE\n" +
                ");";

        db.execSQL(createGoals);

        String createNotifications = "CREATE TABLE IF NOT EXISTS \"powiadomienia\" (\n" +
                "\t\"p_id\"\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "\t\"uzytkownik\"\tchar(255) NOT NULL,\n" +
                "\t\"wlaczone\"\ttinyint(1) NOT NULL,\n" +
                "\t\"powtarzaj\"\ttinyint(1) NOT NULL,\n" +
                "\t\"dzien\"\tint NOT NULL,\n" +
                "\t\"godzina\"\ttime NOT NULL,\n" +
                "\t\"tresc\"\tchar(255) NOT NULL,\n" +
                "\tCONSTRAINT \"powiadomienia_ibfk_1\" FOREIGN KEY(\"uzytkownik\") REFERENCES \"uzytkownicy\"(\"uzytkownik\") ON DELETE CASCADE\n" +
                ");";

        db.execSQL(createNotifications);

        String createMeals = "CREATE TABLE IF NOT EXISTS \"produkty\" (\n" +
                "\t\"nazwa\"\tchar(255) NOT NULL,\n" +
                "\t\"kalorie\"\tfloat NOT NULL,\n" +
                "\t\"bialko\"\tfloat NOT NULL,\n" +
                "\t\"weglowodany\"\tfloat NOT NULL,\n" +
                "\t\"tluszcze\"\tfloat NOT NULL,\n" +
                "\t\"plyny\"\tfloat DEFAULT NULL,\n" +
                "\tPRIMARY KEY(\"nazwa\")\n" +
                ");";

        db.execSQL(createMeals);

        String createUsers = "CREATE TABLE IF NOT EXISTS \"uzytkownicy\" (\n" +
                "\t\"uzytkownik\"\tchar(255) NOT NULL,\n" +
                "\t\"haslo\"\tchar(32) NOT NULL,\n" +
                "\tPRIMARY KEY(\"uzytkownik\")\n" +
                ");";

        db.execSQL(createUsers);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    /// Login ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean register(String userName, String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("uzytkownik", userName);
        cv.put("haslo", password);

        try { return db.insert("uzytkownicy", null, cv) > 0; } // Utworz uzytkownika
        catch (Exception e) { return false; }
    }

    public boolean login(String userName, String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;

        try { cursor = db.rawQuery("SELECT haslo FROM uzytkownicy WHERE uzytkownik = ?", new String[] {userName}); }
        catch (Exception e) { return false; }


        if(cursor.moveToFirst()) // Jezeli znalazl uzytkownika
        {
            return cursor.getString(cursor.getColumnIndex("haslo")).equals(password); // Sprawdz poprawnosc hasla
        }

        return false;
    }

    /// Insert ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean addMeal(String name, float calories, float proteins, float carbons, float fats, int fluids)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("nazwa", name);
        cv.put("kalorie", calories);
        cv.put("bialko", proteins);
        cv.put("weglowodany", carbons);
        cv.put("tluszcze", fats);
        cv.put("plyny", fluids);

        return db.insert("produkty", null, cv) > 0; // Dodaj posiłek
    }

    public boolean addNotification(String userName, boolean repeat, String day, String hour, String text)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("uzytkownik", userName);
        cv.put("wlaczone", 1); // Dodane powiadomienie domyślnie aktywne;
        cv.put("powtarzaj", repeat);
        cv.put("dzien", getDay(day));
        cv.put("godzina", hour);
        cv.put("tresc", text);

        return db.insert("powiadomienia", null, cv) > 0; // Dodaj powiadomienie
    }

    public boolean addHistory(String userName, String name, float amount)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("uzytkownik", userName);
        cv.put("data", getDateTime());
        cv.put("nazwa", name);
        cv.put("ilosc", amount);

        return db.insert("historia", null, cv) > 0; // Dodaj historie
    }

    public boolean addGoal(String userName, String day, float calories, float proteins, float carbons, float fats, int fluids)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        int dayNumber = getDay(day);
        Cursor cursor;

        try
        {
            String day_num = String.valueOf(dayNumber);
            cursor = db.rawQuery("SELECT c_id FROM cele WHERE dzien = ? AND uzytkownik = ?", new String[] {day_num, userName});
        }
        catch (Exception e) { return false; }

        if(cursor.moveToFirst()) // Jezeli znalazl cel na dany dzien
        {
            String c_id = String.valueOf(cursor.getString(cursor.getColumnIndex("c_id")));
            return updateGoal(c_id, calories, proteins, carbons, fats, fluids);
        }

        cv.put("uzytkownik", userName);
        cv.put("dzien", dayNumber);
        cv.put("kalorie", calories);
        cv.put("bialko", proteins);
        cv.put("weglowodany", carbons);
        cv.put("tluszcze", fats);
        cv.put("nawodnienie", fluids);

        return db.insert("cele", null, cv) > 0; // Dodaj cel
    }

    /// Delete ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean deleteUser(String userName)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("uzytkownicy", "uzytkownik = ?", new String[] {userName}) > 0;
    }

    public boolean deleteMeal(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("produkty", "nazwa = ?", new String[] {name}) > 0;
    }

    public boolean deleteNotification(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("powiadomienia", "p_id = ?", new String[] {id}) > 0;
    }

    public boolean deleteHistory(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("historia", "h_id = ?", new String[] {id}) > 0;
    }

    public boolean deleteGoal(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("cele", "c_id = ?", new String[] {id}) > 0;
    }

    /// Update ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean updateUserPassword(String userName, String oldPassword, String newPassword)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Cursor cursor;

        try { cursor = db.rawQuery("SELECT haslo FROM uzytkownicy WHERE uzytkownik = ?", new String[] {userName}); }
        catch (Exception e) { return false; }

        cursor.moveToFirst();

        if(!cursor.getString(cursor.getColumnIndex("haslo")).equals(oldPassword)) { return false; } // Jezeli haslo sie nie zgadza

        cv.put("haslo", newPassword);
        return db.update("uzytkownicy", cv, "uzytkownik = ?", new String[] {userName}) > 0;
    }

    public boolean updateMeal(String name, float calories, float proteins, float carbons, float fats, int fluids)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("nazwa", name);
        cv.put("kalorie", calories);
        cv.put("bialko", proteins);
        cv.put("weglowodany", carbons);
        cv.put("tluszcze", fats);
        cv.put("plyny", fluids);

        return db.update("produkty", cv, "nazwa = ?", new String[] {name}) > 0;
    }

    public boolean updateNotification(String id, boolean repeat, String day, String hour, String text)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("powtarzaj", repeat);
        cv.put("dzien", getDay(day));
        cv.put("godzina", hour);
        cv.put("tresc", text);

        return db.update("powiadomienia", cv, "p_id = ?", new String[] {id}) > 0;
    }

    public boolean updateGoal(String id, float calories, float proteins, float carbons, float fats, int fluids)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("kalorie", calories);
        cv.put("bialko", proteins);
        cv.put("weglowodany", carbons);
        cv.put("tluszcze", fats);
        cv.put("nawodnienie", fluids);

        return db.update("cele", cv, "c_id = ?", new String[] {String.valueOf(id)}) > 0;
    }

    /// Get //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Cursor getHistory(String userName)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;

        try { cursor = db.rawQuery("SELECT * FROM historia WHERE uzytkownik = ?", new String[] {userName}); }
        catch (Exception e) { return cursor; }

        return cursor;
    }

    public Cursor getMeal()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;

        try { cursor = db.rawQuery("SELECT * FROM produkty" , null); }
        catch (Exception e) { return cursor; }

        return cursor;
    }

    public Cursor getMeal(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;

        try { cursor = db.rawQuery("SELECT * FROM produkty WHERE nazwa = ?", new String[] {name}); }
        catch (Exception e) { return cursor; }

        return cursor;
    }

    public Cursor getGoal(String userName)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;

        try { cursor = db.rawQuery("SELECT * FROM cele WHERE uzytkownik = ?", new String[] {userName}); }
        catch (Exception e) { return cursor; }

        return cursor;
    }

    public Cursor getGoal(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;

        try { cursor = db.rawQuery("SELECT * FROM cele WHERE c_id = ?", new String[] {String.valueOf(id)}); }
        catch (Exception e) { return cursor; }

        return cursor;
    }

    public Cursor getNotification(String userName)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;

        try { cursor = db.rawQuery("SELECT * FROM powiadomienia WHERE uzytkownik = ?", new String[] {userName}); }
        catch (Exception e) { return cursor; }

        return cursor;
    }

    public Cursor getNotification(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;

        try { cursor = db.rawQuery("SELECT * FROM powiadomienia WHERE p_id = ?", new String[] {String.valueOf(id)}); }
        catch (Exception e) { return cursor; }

        return cursor;
    }

    // Set ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void setNotificationActive(String id, boolean status)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("wlaczone", status);

        db.update("powiadomienia", cv, "p_id = ?", new String[] {id});
    }

    public void setNotificationRepeat(String id, boolean repeat)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("powtarzaj", repeat);

        db.update("powiadomienia", cv, "p_id = ?", new String[] {id});
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private String getDateTime()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();

        return dateFormat.format(date);
    }

    public int getDay(String day)
    {
        if(day.equals("Poniedzialek")) return 0;
        else if(day.equals("Wtorek")) return 1;
        else if(day.equals("Sroda")) return 2;
        else if(day.equals("Czwartek")) return 3;
        else if(day.equals("Piatek")) return 4;
        else if(day.equals("Sobota")) return 5;
        else if(day.equals("Niedziela")) return 6;

        return -1;
    }

    public String getDayName(int day)
    {
        if(day == 0) return "Poniedzialek";
        else if(day == 1) return "Wtorek";
        else if(day == 2) return "Sroda";
        else if(day == 3) return "Czwartek";
        else if(day == 4) return "Piatek";
        else if(day == 5) return "Sobota";
        else if(day == 6) return "Niedziela";

        return "Error";
    }
}