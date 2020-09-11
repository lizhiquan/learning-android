package ca.bcit.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void reverseText(View view) {
        EditText textToReverseEditText = findViewById(R.id.editTextTextToReverse);
        TextView resultTextView = findViewById(R.id.textViewReversedText);

        String textToReverse = textToReverseEditText.getText().toString();
        if (textToReverse.isEmpty()) {
            resultTextView.setTextColor(Color.RED);
            resultTextView.setText(R.string.missing_text_message);
        } else {
            resultTextView.setTextColor(Color.BLACK);
            String reversedText = reverseString(textToReverse);
            resultTextView.setText(reversedText);
        }
    }

    private String reverseString(String str) {
        StringBuilder builder = new StringBuilder(str);
        builder = builder.reverse();
        return builder.toString();
    }
}