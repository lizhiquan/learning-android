package ca.bcit.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;
import java.util.UUID;

public class SpeakActivity extends AppCompatActivity {
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speak);

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.CANADA);
                } else {
                    Log.i("SpeakActivity", "There was an error initializing the TTS");
                }
            }
        });
    }

    public void handleBack(View view) {
        textToSpeech.shutdown();
        finish();
    }

    public void handleSpeak(View view) {
        EditText editText = findViewById(R.id.textToSpeakEditText);
        String textToSpeak = editText.getText().toString();
        if (textToSpeak.isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.speak_hint, Toast.LENGTH_SHORT).show();
        } else {
            textToSpeech.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}