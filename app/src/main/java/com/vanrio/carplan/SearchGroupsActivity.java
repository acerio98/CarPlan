package com.vanrio.carplan;

import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.sql.SQLException;


public class SearchGroupsActivity extends ListActivity {

    GroupDB data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_groups);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);
            try {
                doMySearch(query);
            }
            catch(SQLException e){
                System.out.println("SQLException Search Groups");
            }
        }

        data = new GroupDB(this);
        try {
            data.open();
        }catch(SQLException e){
            System.out.println("Ohno SQL");
        }
    }

    public void doMySearch(String query) throws SQLException{
        Cursor searchCursor = data.searchQuery(query);
        CursorAdapter cursorAdapter = new GroupsCursorAdapter(this, searchCursor);
        setListAdapter(cursorAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_groups, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class GroupsCursorAdapter extends CursorAdapter{
        public GroupsCursorAdapter(Context context, Cursor cursor){
            super(context, cursor, 0);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent){
            return LayoutInflater.from(context).inflate(R.layout.item_search, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor){
            TextView textView1 = (TextView)view.findViewById(R.id.textGroupName);
            TextView textView2 = (TextView)view.findViewById(R.id.textLocation);
            textView1.setText(cursor.getString(0));
            textView2.setText(cursor.getString(5)+", "+cursor.getString(6));
        }
    }
}
