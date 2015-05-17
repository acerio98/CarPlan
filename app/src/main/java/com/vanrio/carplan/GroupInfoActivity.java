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
import android.widget.TextView;

import java.sql.SQLException;


public class GroupInfoActivity extends Activity {

    TextView nameLabel, numCarpoolsLabel, descLabel, locationLabel, creatorLabel;
    LinearLayout membersListLayout;

    GroupDB data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);

        data = new GroupDB(this);
        try {
            data.open();
        }catch(SQLException e){
            System.out.println("Ohno SQL");
        }

        nameLabel = (TextView)findViewById(R.id.nameLabel);
        numCarpoolsLabel = (TextView)findViewById(R.id.numCarpoolsLabel);
        descLabel = (TextView)findViewById(R.id.descLabel);
        locationLabel = (TextView)findViewById(R.id.addressLabel);
        creatorLabel = (TextView)findViewById(R.id.creatorLabel);

        membersListLayout = (LinearLayout)findViewById(R.id.membersListLayout);

        String groupname = getIntent().getStringExtra("groupname");
        nameLabel.setText(groupname);

        try{
            Cursor cursor = data.query(groupname);
            if(cursor.moveToFirst()) {
                cursor.moveToFirst();
                String[] peopleList = data.convertStringToArray(cursor.getString(1));
                String[] carpoolList = data.convertStringToArray(cursor.getString(2));
                String desc = cursor.getString(3);
                String location = cursor.getString(4) + " " + cursor.getString(5) + ", " +cursor.getString(6);
                String creator = cursor.getString(7);

                for(String person : peopleList){
                    TextView p = new TextView(this);
                    p.setText(person);
                    membersListLayout.addView(p);
                }

                numCarpoolsLabel.setText(carpoolList.length+"");
                descLabel.setText(desc);
                locationLabel.setText(location);
                creatorLabel.setText(creator);
            }
            cursor.close();
        }
        catch(SQLException e){
            System.out.println("Group Info SQLException.");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_group_info, menu);
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
