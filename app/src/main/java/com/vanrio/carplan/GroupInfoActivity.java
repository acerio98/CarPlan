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


public class GroupInfoActivity extends Activity implements View.OnClickListener{

    TextView nameLabel, numCarpoolsLabel, descLabel, locationLabel, creatorLabel;
    LinearLayout membersListLayout;
    Button joinGroupButton;

    GroupDB gData;
    UserDB uData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);

        gData = new GroupDB(this);
        uData = new UserDB(this);
        try {
            gData.open();
            uData.open();
        }catch(SQLException e){
            System.out.println("Ohno SQL");
        }

        nameLabel = (TextView)findViewById(R.id.nameLabel);
        numCarpoolsLabel = (TextView)findViewById(R.id.numCarpoolsLabel);
        descLabel = (TextView)findViewById(R.id.descLabel);
        locationLabel = (TextView)findViewById(R.id.locationLabel);
        creatorLabel = (TextView)findViewById(R.id.creatorLabel);

        membersListLayout = (LinearLayout)findViewById(R.id.membersListLayout);

        joinGroupButton = (Button)findViewById(R.id.joinGroupButton);
        joinGroupButton.setOnClickListener(this);

        int visib = getIntent().getIntExtra("button_visiblity", 1);
        joinGroupButton.setVisibility(visib);

        String groupname = getIntent().getStringExtra("groupname");
        nameLabel.setText(groupname);

        System.out.println("_____ "+groupname);

        try{
            Cursor cursor = gData.query(groupname);
            if(cursor.moveToFirst()) {
                cursor.moveToFirst();
                String[] peopleList = GroupDB.convertStringToArray(cursor.getString(1));
                String carpoolText = "";
                System.out.println("---> "+cursor.getString(2));
                if(cursor.getString(2).equals("")){
                    carpoolText = 0 + "";
                }
                else{
                    String[] carpoolList = GroupDB.convertStringToArray(cursor.getString(2));
                    carpoolText = carpoolList.length + "";
                }

                String desc = cursor.getString(3);
                String location = cursor.getString(4) + " " + cursor.getString(5) + ", " +cursor.getString(6);
                String creator = cursor.getString(7);
                System.out.println("-->"+cursor.getString(7));
                //peopleList = ["Johny", "other kid", "halp me plz", "work?"];
                for(String person : peopleList){
                    TextView p = new TextView(this);
                    p.setText(person);
                    membersListLayout.addView(p);
                }

                numCarpoolsLabel.setText(carpoolText);
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
    public void onClick(View v){
        if(v.getId()==R.id.joinGroupButton){
            String username = getIntent().getStringExtra("my_username");
            String groupname = getIntent().getStringExtra("groupname");
            try{
                uData.addGroupForUser(username, groupname);
                gData.addUserToGroup(groupname, username);
            }
            catch(SQLException e){
                System.out.println("Ohno SQL GroupInfo Onclick.");
            }
            finish();
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
