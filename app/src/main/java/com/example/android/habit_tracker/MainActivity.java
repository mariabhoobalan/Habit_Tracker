package com.example.android.habit_tracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.habit_tracker.data.HabitDbHelper;
import com.example.android.habit_tracker.data.HabitContract.HabitEntry;
public class MainActivity extends AppCompatActivity {

    private HabitDbHelper mDbHelper;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Specify the context
        mDbHelper = new HabitDbHelper(this);

        //Call insertHabit with the task details
        String task = "Exercise";
        String date = "11/28/2016";
        String time = "1 hour";
        long newRow = insertHabit(task, date, time);
        if (newRow >= 1) {
            Log.v(LOG_TAG, "Habit saved with id: " + newRow);
        } else {
            Log.v(LOG_TAG, "Error saving habit: " + newRow);
        }

        //generate a projection and call readHabit to restrive the selected columns
        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_HABIT_NAME,
                HabitEntry.COLUMN_HABIT_DATE,
                HabitEntry.COLUMN_HABIT_HOURS,};

        Cursor cursor = readHabit(projection);
        // Figure out the index of each column
        int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
        int habitColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_NAME);
        int dateColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_DATE);
        int hoursColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_HOURS);

        //Log the header
        Log.v(LOG_TAG, "\n" + HabitEntry._ID
                + " - " + HabitEntry.COLUMN_HABIT_NAME
                + " - " + HabitEntry.COLUMN_HABIT_DATE
                + " - " + HabitEntry.COLUMN_HABIT_HOURS);

        while (cursor.moveToNext()) {
            // Use that index to extract the String or Int value of the word
            // at the current row the cursor is on.
            int currentID = cursor.getInt(idColumnIndex);
            String currentName = cursor.getString(habitColumnIndex);
            String currentDate = cursor.getString(dateColumnIndex);
            int currentHours = cursor.getInt(hoursColumnIndex);

            //Log the header
            Log.v(LOG_TAG, "\n" + currentID
                    + " - " + currentName
                    + " - " + currentDate
                    + " - " + currentHours);

        }

        cursor.close();
    }



    private long insertHabit(String mTask, String mDate, String mTime) {
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and Toto's pet attributes are the values.
        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_HABIT_NAME , mTask);
        values.put(HabitEntry.COLUMN_HABIT_DATE, mDate);
        values.put(HabitEntry.COLUMN_HABIT_HOURS, mTime);

        //Writes the entry into the table habits
        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);
        return newRowId;
    }

    private Cursor readHabit(String[] mProjection) {
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        //Query the habits table with the given projection
        Cursor cursor = db.query(
                HabitEntry.TABLE_NAME,
                mProjection,
                null,
                null,
                null,
                null,
                null);
        return cursor;
    }
}
