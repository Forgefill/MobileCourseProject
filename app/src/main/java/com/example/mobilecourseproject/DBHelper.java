package com.example.mobilecourseproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import Models.Chicken;

public class DBHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "CHICKEN_TABLE";
    public static final String COLUMN_CHICKEN_BREED = "CHICKEN_BREED";
    public static final String COLUMN_CHICKEN_TYPE = "CHICKEN_TYPE";
    public static final String COLUMN_CHICKEN_PRODUCTION = "CHICKEN_PRODUCTION";
    public static final String COLUMN_ID = "ID";

    public DBHelper(Context context) {
        super(context, "chicken.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CHICKEN_BREED + " TEXT, " + COLUMN_CHICKEN_TYPE + " TEXT, " + COLUMN_CHICKEN_PRODUCTION + " INTEGER)";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {

    }

    public boolean addOne(Chicken chicken)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CHICKEN_BREED, chicken.getBreed());
        cv.put(COLUMN_CHICKEN_TYPE, chicken.getType());
        cv.put(COLUMN_CHICKEN_PRODUCTION, chicken.getProduction());

        long insert = db.insert(TABLE_NAME, null, cv);

        if(insert == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public ArrayList<Chicken> getAll()
    {
        ArrayList<Chicken> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()) {

            do{
                int id = cursor.getInt(0);
                String breed = cursor.getString(1);
                String type = cursor.getString(2);
                int production = cursor.getInt(3);

                Chicken model = new Chicken(id, breed, type, production);
                returnList.add(model);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public ArrayList<Chicken> getMoreThan300()
    {
        ArrayList<Chicken> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_CHICKEN_PRODUCTION + " > 300";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()) {
            do{
                int id = cursor.getInt(0);
                String breed = cursor.getString(1);
                String type = cursor.getString(2);
                int production = cursor.getInt(3);

                Chicken model = new Chicken(id, breed, type, production);
                returnList.add(model);
            }while(cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return returnList;
    }

    public int getAverageProduction()
    {
        ArrayList<Chicken> returnList = new ArrayList<>();

        String queryString = "SELECT AVG("+COLUMN_CHICKEN_PRODUCTION+") FROM " + TABLE_NAME + " WHERE " + COLUMN_CHICKEN_TYPE + " = \"Egg\"";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        int result = 0;
        if(cursor.moveToFirst()) {
            result = cursor.getInt(0);
        }

        cursor.close();
        db.close();
        return result;
    }

    public boolean deleteOne(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String queryString = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + id;

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
