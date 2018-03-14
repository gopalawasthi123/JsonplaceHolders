package com.example.gopalawasthi.jsonplaceholders;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Gopal Awasthi on 13-03-2018.
 */

public class ItemOpenHelper extends SQLiteOpenHelper{

    private static ItemOpenHelper itemOpenHelper;
    private ItemOpenHelper(Context context) {
        super(context, Contracts.DATABASE_NAME,null,Contracts.ITEM_VERSION );
    }

    public static ItemOpenHelper getInstance (Context context){
        if(itemOpenHelper == null){
            itemOpenHelper = new ItemOpenHelper(context.getApplicationContext());
        }
        return itemOpenHelper;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String use = " CREATE TABLE "  +  Contracts.UserdataBase.TABLE_NAME + " ( " +
                    Contracts.UserdataBase.USER_ID + " INTEGER PRIMARY KEY, " +
                    Contracts.UserdataBase.USER_NAME + " TEXT," + " UNIQUE (" + Contracts.UserdataBase.USER_ID + ") ON CONFLICT REPLACE )" ;

        db.execSQL(use);

        String postsuse = " CREATE TABLE " + Contracts.Posts.TABLE_NAME + " ( " +
                            Contracts.Posts.POST_ID + " INTEGER PRIMARY KEY, " +
                            Contracts.Posts.POST + " TEXT, " +
                            Contracts.Posts.USER_ID + " INTEGER " +
                            " FOREIGN KEY (" + Contracts.Posts.USER_ID + ") REFERENCES " + Contracts.UserdataBase.TABLE_NAME + " (" + Contracts.UserdataBase.USER_ID + ") ON DELETE CASCADE )";

            db.execSQL(postsuse);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
