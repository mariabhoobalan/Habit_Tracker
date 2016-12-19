package com.example.android.habit_tracker.data;

import android.provider.BaseColumns;
public final class HabitContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private HabitContract() {}

    /**
     * Inner class that defines constant values for the Habit database table.
     */
    public static final class HabitEntry implements BaseColumns {
        //Table name
        public final static String TABLE_NAME = "habits";
        //Row Id
        public final static String _ID = BaseColumns._ID;
        //Activity name
        public final static String COLUMN_HABIT_NAME ="name";
        //date
        public final static String COLUMN_HABIT_DATE ="date";
        //total time
        public final static String COLUMN_HABIT_HOURS ="hours";

    }
}
