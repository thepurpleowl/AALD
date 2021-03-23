package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "DEBUG_MSG";
    private Button sendButton, voiceIPButton;
    private EditText editText;

    private static final int SPEECH_REQUEST_CODE = 0;
    private static final String SAVED_MESSAGE = "savedMessage";
    public static final String EXTRA_MESSAGE = "textMessage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "in method onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "in method onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "in method onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "in method onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "in method onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "in method onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "in method onDestroy");
    }

}