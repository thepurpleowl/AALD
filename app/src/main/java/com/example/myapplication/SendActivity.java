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
            Intent mailIntent = new Intent(Intent.ACTION_SEND);
            mailIntent.setType("message/rfc822");
            mailIntent.putExtra(Intent.EXTRA_EMAIL  , contact);
            mailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.msg_subject));
            mailIntent.putExtra(Intent.EXTRA_TEXT   , message);
            try {
                startActivity(Intent.createChooser(mailIntent, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }
        }
        else if(isValidMobile(contact)){
            Intent phoneIntent = new Intent(android.content.Intent.ACTION_SEND);
            phoneIntent.setType("text/plain");
            phoneIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.msg_subject));
            phoneIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
            try {
                startActivity(Intent.createChooser(phoneIntent, getString(R.string.share_using)));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "There are no text send apps installed.", Toast.LENGTH_SHORT).show();
            }

        }
        else{
            Toast.makeText(this, "Invalid Contact Detail!!", Toast.LENGTH_LONG).show();
        }
    }
}