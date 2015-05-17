package com.vanrio.carplan;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.sql.SQLException;
import java.util.ArrayList;


public class FindGroupsActivity extends Activity implements View.OnClickListener{

    LinearLayout listLayout;
    Button newGroupBut;
    GroupDB data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_groups);

        data = new GroupDB(this);
        try {
            data.open();
        }catch(SQLException e){
            System.out.println("Ohno SQL");
        }

        listLayout = (LinearLayout)findViewById(R.id.findGroupsList);
        ArrayList<Button> buttons = new ArrayList<Button>();

        newGroupBut = (Button)findViewById(R.id.newGroupBut);
        newGroupBut.setOnClickListener(this);

        Cursor cursor = data.queryAll();
        String myAddress = "";

        cursor.moveToFirst();
        while(cursor.moveToNext()) {
            Button b = new Button(this);
            final String groupname = cursor.getString(0);
            b.setText(groupname);
            b.setWidth(300);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(FindGroupsActivity.this, GroupInfoActivity.class);
                    i.putExtra("groupname", groupname);
                    startActivity(i);
                }
            });
            buttons.add(b);
        }
        cursor.close();
        for(Button b : buttons){
            listLayout.addView(b);
        }
        System.out.println("~~~~~~~~~~"+buttons.size());
    }

    @Override
    public void onClick(View v){
        if(v.getId()==R.id.newGroupBut){
            startActivity(new Intent(FindGroupsActivity.this, newGroupActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_find_groups, menu);
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
}
