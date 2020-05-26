package com.example.projekt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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
                "\t\"uzytkownik\"\tchar(30) NOT NULL,\n" +
                "\t\"data\"\tdate NOT NULL,\n" +
                "\t\"nazwa\"\tchar(30) NOT NULL,\n" +
                "\t\"ilosc\"\tint NOT NULL,\n" +
                "\tCONSTRAINT \"historia_ibfk_1\" FOREIGN KEY(\"uzytkownik\") REFERENCES \"uzytkownicy\"(\"uzytkownik\") ON DELETE CASCADE,\n" +
                "\tCONSTRAINT \"historia_ibfk_2\" FOREIGN KEY(\"nazwa\") REFERENCES \"produkty\"(\"nazwa\")\n" +
                ");";

        db.execSQL(createHistory);

        String createGoals = "CREATE TABLE IF NOT EXISTS \"cele\" (\n" +
                "\t\"c_id\"\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "\t\"uzytkownik\"\tchar(30) NOT NULL,\n" +
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
                "\t\"uzytkownik\"\tchar(30) NOT NULL,\n" +
                "\t\"wlaczone\"\ttinyint(1) NOT NULL,\n" +
                "\t\"dzien\"\tint NOT NULL,\n" +
                "\t\"godzina\"\ttime NOT NULL,\n" +
                "\t\"tresc\"\tchar(255) NOT NULL,\n" +
                "\tCONSTRAINT \"powiadomienia_ibfk_1\" FOREIGN KEY(\"uzytkownik\") REFERENCES \"uzytkownicy\"(\"uzytkownik\") ON DELETE CASCADE\n" +
                ");";

        db.execSQL(createNotifications);

        String createMeals = "CREATE TABLE IF NOT EXISTS \"produkty\" (\n" +
                "\t\"nazwa\"\tchar(30) NOT NULL,\n" +
                "\t\"kalorie\"\tfloat NOT NULL,\n" +
                "\t\"bialko\"\tfloat NOT NULL,\n" +
                "\t\"weglowodany\"\tfloat NOT NULL,\n" +
                "\t\"tluszcze\"\tfloat NOT NULL,\n" +
                "\t\"plyny\"\tfloat DEFAULT NULL,\n" +
                "\tPRIMARY KEY(\"nazwa\")\n" +
                ");";

        db.execSQL(createMeals);

        String createUsers = "CREATE TABLE IF NOT EXISTS \"uzytkownicy\" (\n" +
                "\t\"uzytkownik\"\tchar(30) NOT NULL,\n" +
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

    public boolean addMeal(String name, float calories, float proteins, float carbohydrates, float fats, int lotions)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("nazwa", name);
        cv.put("kalorie", calories);
        cv.put("bialko", proteins);
        cv.put("weglowodany", carbohydrates);
        cv.put("tluszcze", fats);
        cv.put("plyny", lotions);

        return db.insert("produkty", null, cv) > 0; // Dodaj posiłek
    }

    public boolean addNotification(String userName, int day, String hour, String text) // ??
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("uzytkownik", userName);
        cv.put("wlaczone", 1); // Dodane powiadomienie domyślnie aktywne;
        cv.put("dzien", day);
        cv.put("godzina", hour); //???
        cv.put("tresc", text); //???

        return db.insert("powiadomienia", null, cv) > 0; // Dodaj cel
    }

    public boolean addHistory(String userName, String date, String name, float amount) // ??
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("uzytkownik", userName);
        cv.put("data", date); //???
        cv.put("nazwa", name);
        cv.put("ilosc", amount);

        return db.insert("historia", null, cv) > 0; // Dodaj cel
    }

    public boolean addGoal(String userName, int day, float calories, float proteins, float carbohydrates, float fats, int lotions)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("uzytkownik", userName);
        cv.put("dzien", day);
        cv.put("kalorie", calories);
        cv.put("bialko", proteins);
        cv.put("weglowodany", carbohydrates);
        cv.put("tluszcze", fats);
        cv.put("nawodnienie", lotions);

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

    public boolean updateUserPassword(String userName, String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("haslo", password);

        return db.update("uzytkownicy", cv, "uzytkownik = ?", new String[] {userName}) > 0;
    }

    public boolean updateMeal(String name, float calories, float proteins, float carbohydrates, float fats, int lotions)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("nazwa", name);
        cv.put("kalorie", calories);
        cv.put("bialko", proteins);
        cv.put("weglowodany", carbohydrates);
        cv.put("tluszcze", fats);
        cv.put("plyny", lotions);

        return db.update("produkty", cv, "nazwa = ?", new String[] {name}) > 0;
    }

    public boolean updateNotification(String id, int status, int day, String hour, String text) // ??
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("wlaczone", status);
        cv.put("dzien", day);
        cv.put("godzina", hour); //???
        cv.put("tresc", text); //???

        return db.update("powiadomienia", cv, "p_id = ?", new String[] {id}) > 0;
    }

    public boolean updateGoal(String id, int day, float calories, float proteins, float carbohydrates, float fats, int lotions)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("dzien", day);
        cv.put("kalorie", calories);
        cv.put("bialko", proteins);
        cv.put("weglowodany", carbohydrates);
        cv.put("tluszcze", fats);
        cv.put("nawodnienie", lotions);

        return db.update("cele", cv, "c_id = ?", new String[] {id}) > 0;
    }
}