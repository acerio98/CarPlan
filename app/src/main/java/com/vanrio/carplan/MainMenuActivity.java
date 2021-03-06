package com.vanrio.carplan;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.sql.SQLException;


public class MainMenuActivity extends Activity implements View.OnClickListener{

    UserDB data;
    Button signUpButton;
    Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        signUpButton = (Button)findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(this);

        data = new UserDB(this);
        try {
            data.open();
        }catch(SQLException e){
            System.out.println("Ohno SQL");
        }
    }

    public void onClick(View v) {
        if (v.getId() == R.id.signUpButton) {
            startActivity(new Intent(MainMenuActivity.this, SignUpActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
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
