package com.vanrio.carplan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

/**
 * Created by Austin on 5/16/15.
 */
public class UserDB {
    public static final String DATABASE_NAME = "userdb";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_TABLE = "users";

    public static final String KEY_USERNAME = "username"; //primary key
    public static final String KEY_PASSWORD = "password";

    public static final String KEY_NAME = "name";
    public static final String KEY_PICTURE = "picture";
//    public static final String KEY_DOB = "dob";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_CITY = "city";
    public static final String KEY_STATE = "state";

    public static final String[] KEYS_ALL = { UserDB.KEY_USERNAME, UserDB.KEY_PASSWORD, UserDB.KEY_NAME, UserDB.KEY_PICTURE, UserDB.KEY_DOB, UserDB.KEY_ADDRESS,
                                                                   UserDB.KEY_CITY, UserDB.KEY_STATE};

    private Context context;
    private SQLiteDatabase database;
    private DataHelper helper;

    public UserDB(Context context){
        this.context = context;
    }

    public void open() throws SQLException {
        helper = new DataHelper(context);
        database = helper.getWritableDatabase();
    }

    public void close(){
        helper.close();
        helper = null;
        database = null;
    }

    public void addToDB(String user, String pass, String name, String address, String city, String state){
        ContentValues map = new ContentValues();
        map.put(UserDB.KEY_USERNAME, user);
        map.put(UserDB.KEY_PASSWORD, pass);
        map.put(UserDB.KEY_NAME, name);
        map.put(UserDB.KEY_PICTURE, "");
        map.put(UserDB.KEY_USERNAME, user);
        map.put(UserDB.KEY_ADDRESS, address);
        map.put(UserDB.KEY_CITY, city);
        map.put(UserDB.KEY_STATE, state);


        String DATABASE_INSERT = "insert into " + DATABASE_TABLE + "("+
                UserDB.KEY_USERNAME + ", " +
                UserDB.KEY_PASSWORD + ", " +
                UserDB.KEY_NAME + ", " +
                UserDB.KEY_PICTURE + ", " +
                UserDB.KEY_ADDRESS + ", " +
                UserDB.KEY_CITY + ", " +
                UserDB.KEY_STATE + ")"+
                "values (" +
                user +

    }

    public Cursor queryAll(){
        return database.query(DATABASE_TABLE,
                KEYS_ALL,
                null, null, null, null,
                UserDB.KEY_USERNAME + " ASC"); //query(String table, String[] columns, String selection,
                                            //      String[] selectionArgs, String groupBy, String having, String orderBy)
    }



    public Cursor query(long username) throws SQLException{
        Cursor cursor = database.query(true, DATABASE_TABLE,
                KEYS_ALL,
                KEY_USERNAME + "=" + username,
                null, null, null, null, null); // query(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having,
                                               //      String orderBy, String limit, CancellationSignal cancellationSignal)
        cursor.moveToFirst();
        return cursor;
    }

    private static class DataHelper extends SQLiteOpenHelper {

        private static final String DATABASE_CREATE = "create table "+ DATABASE_TABLE+ " ("+
                UserDB.KEY_USERNAME + " text primary key not null, "+
                UserDB.KEY_PASSWORD + " text, "+
                UserDB.KEY_NAME + " text, "+
                UserDB.KEY_PICTURE + " text, "+
                UserDB.KEY_ADDRESS + " text, "+
                UserDB.KEY_CITY + " text, "+
                UserDB.KEY_STATE + " text, "+
                ");";

        public DataHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase database){
            database.execSQL(DATABASE_CREATE);
        }
        public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
            //nothing
        }
    }
}

















