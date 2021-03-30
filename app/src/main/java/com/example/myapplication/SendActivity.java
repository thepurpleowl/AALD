package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class SendActivity extends AppCompatActivity {
    private Button confirmButton;
    private EditText editText;
    private static final String TAG = "DEBUG_MSG";
    public static final String REGEX_PHONE = "^(0|\\+91|91)?[-\\s]?\\d{10}";
    public static final String REGEX_EMAIL = "^([\\w\\.]+)@([\\w]+)((\\.(\\w){2,3})+)$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "in method onCreate: SendActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        editText = (EditText) findViewById(R.id.contact_field);
        confirmButton = (Button) findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    private boolean isValidMail(String email) {
        return (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
                && Pattern.matches(REGEX_EMAIL, email));
    }

    private boolean isValidMobile(String phone) {
        return (android.util.Patterns.PHONE.matcher(phone).matches()
                && Pattern.matches(REGEX_PHONE, phone));
    }

    /** Called when the user taps the Send button */
    public void sendMessage() {
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        String contact = editText.getText().toString();

        if(isValidMail(contact)){
//            Intent mailIntent = new Intent(Intent.ACTION_SEND);
//            mailIntent.setType("message/rfc822");
            Intent mailIntent = new Intent(Intent.ACTION_SENDTO);
            mailIntent.setData(Uri.parse("mailto:"));
            mailIntent.putExtra(Intent.EXTRA_EMAIL  , new String[] {contact});
            mailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.msg_subject));
            mailIntent.putExtra(Intent.EXTRA_TEXT   , message);
            try {
                startActivity(Intent.createChooser(mailIntent, getString(R.string.mail_share)));
                Toast.makeText(this, getString(R.string.mail_share),Toast.LENGTH_SHORT).show();
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, R.string.no_mail_app, Toast.LENGTH_SHORT).show();
            }
        }
        else if(isValidMobile(contact)){
//            Intent phoneIntent = new Intent(android.content.Intent.ACTION_SEND);
//            phoneIntent.setType("text/plain");
            Intent phoneIntent = new Intent(Intent.ACTION_SENDTO);
            phoneIntent.setData(Uri.parse("smsto:"+contact));
            phoneIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.msg_subject));
            phoneIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
            try {
                startActivity(Intent.createChooser(phoneIntent, getString(R.string.phone_share)));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, R.string.no_chat_app, Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(this, getString(R.string.phone_share),Toast.LENGTH_SHORT).show();
        }
        else{
//            Toast.makeText(this, getString(R.string.contact_error), Toast.LENGTH_LONG).show();
            editText.setText(R.string.contact_error);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "in method onStart: SendActivity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "in method onResume: SendActivity");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "in method onRestart: SendActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "in method onPause: SendActivity");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "in method onStop: SendActivity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "in method onDestroy: SendActivity");
    }
}