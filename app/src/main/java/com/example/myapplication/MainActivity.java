package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button sendButton, voiceIPButton;
    private EditText editText;

    private static final int SPEECH_REQUEST_CODE = 0;
    private static final String SAVED_MESSAGE = "savedMessage";
    public static final String EXTRA_MESSAGE = "textMessage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if (savedInstanceState != null) {
//            String msg = savedInstanceState.getString(SAVED_MESSAGE);
//            editText.setText(msg);
//        }

        editText  = (EditText) findViewById(R.id.msg_field);
        voiceIPButton = (Button) findViewById(R.id.voice_button);
        voiceIPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displaySpeechRecognizer();
            }
        });

        sendButton = (Button) findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMessage();
            }
        });
    }

    public void getMessage() {
        Intent intent = new Intent(this, SendActivity.class);
        EditText editText = (EditText) findViewById(R.id.msg_field);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    // Create an intent that can start the Speech Recognizer activity
    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        // This starts the activity and populates the intent with the speech text.
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    // This callback is invoked when the Speech Recognizer returns.
    // This is where you process the intent and extract the speech text from the intent.
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            editText.setText(spokenText);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

//    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState) {
//        super.onSaveInstanceState(savedInstanceState);
//
//        String msg = editText.getText().toString();
//        savedInstanceState.putString(SAVED_MESSAGE, msg);
//    }

//    //system calls onRestoreInstanceState() after the onStart() method.
//    @Override
//    public void onRestoreInstanceState(Bundle savedInstanceState) {
//        // Always call the superclass so it can restore the view hierarchy
//        super.onRestoreInstanceState(savedInstanceState);
//
//        String msg = savedInstanceState.getString(SAVED_MESSAGE);
//        editText.setText(msg);
//    }

}