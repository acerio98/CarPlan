package com.vanrio.carplan;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.SQLException;


public class newGroupActivity extends Activity implements View.OnClickListener{

    Button newGroupButton;
    TextView nameText, descText, addressText, cityText, stateText;

    GroupDB data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);
        getActionBar().hide();

        newGroupButton = (Button)findViewById(R.id.createAccountButton);
        newGroupButton.setOnClickListener(this);

        nameText = (TextView)findViewById(R.id.nameText);
        descText = (TextView)findViewById(R.id.descText);
        addressText = (TextView)findViewById(R.id.addressText);
        cityText = (TextView)findViewById(R.id.cityText);
        stateText = (TextView)findViewById(R.id.stateText);

        data = new GroupDB(this);
        try {
            data.open();
        }catch(SQLException e){
            System.out.println("Ohno SQL");
        }
    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.createAccountButton){
            String username = getIntent().getStringExtra("my_username");
            data.addToDB(nameText.getText()+"", descText.getText()+"", addressText.getText()+"", cityText.getText()+"", stateText.getText()+"", username);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_group, menu);
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
