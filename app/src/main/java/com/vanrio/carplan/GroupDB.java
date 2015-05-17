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
public class GroupDB {
    public static final String DATABASE_NAME = "groupdb";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_TABLE = "groups";

    public static final String KEY_GROUPNAME = "name"; //primary key
    public static final String KEY_PEOPLE_LIST = "peopleList";
    public static final String KEY_CARPOOL_LIST = "carpoolList";
    public static final String KEY_DESC = "description";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_CITY = "city";
    public static final String KEY_STATE = "state";
    public static final String KEY_CREATOR = "creator";

    public static final String[] KEYS_ALL = { GroupDB.KEY_GROUPNAME, GroupDB.KEY_PEOPLE_LIST, GroupDB.KEY_CARPOOL_LIST, GroupDB.KEY_DESC,
                                    GroupDB.KEY_ADDRESS, GroupDB.KEY_CITY, GroupDB.KEY_STATE, GroupDB.KEY_CREATOR};

    private Context context;
    private SQLiteDatabase database;
    private DataHelper helper;

    public GroupDB(Context context){
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

    public void addToDB(String name, String desc, String address, String city, String state, String creatorUsername){
        ContentValues map = new ContentValues();
        map.put(GroupDB.KEY_GROUPNAME, name);
        String[] peopleList = {creatorUsername};
        map.put(GroupDB.KEY_PEOPLE_LIST, convertArrayToString(peopleList));
        String[] carpoolList = {};
        map.put(GroupDB.KEY_CARPOOL_LIST, convertArrayToString(carpoolList));
        map.put(GroupDB.KEY_DESC, desc);
        map.put(GroupDB.KEY_ADDRESS, address);
        map.put(GroupDB.KEY_CITY, city);
        map.put(GroupDB.KEY_STATE, state);
        map.put(GroupDB.KEY_CREATOR, creatorUsername);

        database.insert(DATABASE_TABLE, null, map);
    }

    public Cursor queryAll(){
        return database.query(DATABASE_TABLE,
                KEYS_ALL,
                null, null, null, null,
                GroupDB.KEY_GROUPNAME + " ASC"); //query(String table, String[] columns, String selection,
        //      String[] selectionArgs, String groupBy, String having, String orderBy)
    }

    public Cursor query(String groupnameSearch) throws SQLException{
        Cursor cursor = database.query(true, DATABASE_TABLE,
                KEYS_ALL,
                KEY_GROUPNAME + " like ?",
                new String[] { groupnameSearch+"%" }, null, null, null, null); // query(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having,
        //      String orderBy, String limit, CancellationSignal cancellationSignal)
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor toQuery(String groupname) throws SQLException{
        Cursor cursor = database.query(true, DATABASE_TABLE,
                KEYS_ALL,
                KEY_GROUPNAME + " = \"" + groupname + "\"",
                null, null, null, null, null); // query(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having,
        //      String orderBy, String limit, CancellationSignal cancellationSignal)
        System.out.println("~~~~~~~~~~~"+cursor.toString());
        cursor.moveToFirst();
        return cursor;
    }

    //Thanks to Muhammad Nabeel Arif from stackoverflow.com 1/29/12
    public static String strSeparator = "__,__";
    public static String convertArrayToString(String[] array){
        String str = "";
        for (int i = 0;i<array.length; i++) {
            str = str+array[i];
            // Do not append comma at the end of last element
            if(i<array.length-1){
                str = str+strSeparator;
            }
        }
        return str;
    }
    public static String[] convertStringToArray(String str){
        return str.split(strSeparator);
    }

    private static class DataHelper extends SQLiteOpenHelper {

        private static final String DATABASE_CREATE = "create table "+ DATABASE_TABLE+ " ("+
                KEY_GROUPNAME + " text primary key not null, "+
                KEY_PEOPLE_LIST + " text, "+
                KEY_CARPOOL_LIST + " text, "+
                KEY_DESC + " text, "+
                KEY_ADDRESS + " text, "+
                KEY_CITY + " text, "+
                KEY_STATE + " text, "+
                KEY_CREATOR + " text"+
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

















