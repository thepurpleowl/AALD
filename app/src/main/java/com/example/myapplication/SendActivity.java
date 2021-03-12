package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
    }

    private boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        EditText editText = (EditText) findViewById(R.id.msg_field);
        String contact = editText.getText().toString();

        if(isValidMail(contact)){

        }
        else if(isValidMobile(contact)){

        }
        else{
            Toast.makeText(this, "Invalid Contact Detail!!", Toast.LENGTH_LONG).show();
        }
    }
}