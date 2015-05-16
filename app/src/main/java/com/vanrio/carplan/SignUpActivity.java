package com.vanrio.carplan;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.Date;


public class SignUpActivity extends Activity implements View.OnClickListener{

    UserDB data;
    Button createAccountButton;
    EditText usernameField, passwordField, reenterPasswordField, nameField, dobField, addressField, cityField, stateField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        createAccountButton = (Button)findViewById(R.id.createAccountButton);
        createAccountButton.setOnClickListener(this);

        usernameField = (EditText)findViewById(R.id.userText);
        passwordField = (EditText)findViewById(R.id.passText);
        reenterPasswordField = (EditText)findViewById(R.id.reenterPasswordText);
        nameField = (EditText)findViewById(R.id.nameText);
//        dobField = (EditText)findViewById(R.id.dobText);
        addressField = (EditText)findViewById(R.id.addressText);
        cityField = (EditText)findViewById(R.id.cityText);
        stateField = (EditText)findViewById(R.id.stateText);

        data = new UserDB(this);
        try {
            data.open();
        }catch(SQLException e){
            System.out.println("Ohno SQL");
        }
    }

    public void onClick(View v){
        if(v.getId()==R.id.createAccountButton){
            createNewAccount();
        }
    }

    public void createNewAccount(){
        // make sure that the two passwords are the same.
        if(passwordField.getText() != reenterPasswordField.getText()){
            Toast.makeText(getApplicationContext(), "Please make sure that both of the passwords entered are the same.",
                    Toast.LENGTH_SHORT).show();
        }
        else{
            // actually make the account
            String username = usernameField.getText()+"";
            String password = passwordField.getText()+"";
            String name = nameField.getText()+"";
//            Date dob = ;
            String address = addressField.getText()+"";
            String city = cityField.getText()+"";
            String state = stateField.getText()+"";
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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