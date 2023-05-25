package com.example.mobileprojectwagba.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mobileprojectwagba.Models.UserTable;

@Database(entities ={UserTable.class},exportSchema = false,version = 1)
public abstract class UserDatabase extends RoomDatabase {

    public static final String DATABASE_NAME ="USER_DATABASE";
    private static UserDatabase userDatabaseInstance;
    private static final Object LOCK = new Object();

    public abstract UserDao userDao();

    public static UserDatabase getInstance(Context context)
    {
        if(userDatabaseInstance == null)
        {
            synchronized (LOCK)
            {
                if (userDatabaseInstance == null)
                {
                    userDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                                    UserDatabase.class, DATABASE_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }

        }
        return userDatabaseInstance;
    }


}
