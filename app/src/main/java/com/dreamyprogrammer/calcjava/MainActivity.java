package com.dreamyprogrammer.calcjava;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class MainActivity extends AppCompatActivity {

    static final String PREF_NAME = "MainActivity.pref";
    static final String TEXT_VIEW_FULL = "text_view_full";
    private int[] buttonNumIds;
    private int[] buttonOperationIds;
    private View.OnClickListener buttonNumClickListener;
    private View.OnClickListener buttonOperationClickListener;
    private TextView textViewFull;
    private TextView textViewEqually;
    private Button buttonClear;
    private Button buttonBackspace;
    private Button buttonParenthesisStart;
    private Button buttonParenthesisEnd;
    private Button buttonPoint;
    private Button buttonEqually;
    private SimpleCalculator calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        setView();
    }

    private void findView() {

        textViewFull = findViewById(R.id.text_view_full);
        buttonBackspace = findViewById(R.id.button_backspace);
        textViewFull = findViewById(R.id.text_view_full);
        textViewEqually = findViewById(R.id.text_view_equally);
        buttonClear = findViewById(R.id.button_clear);
        buttonParenthesisStart = findViewById(R.id.button_parenthesis_start);
        buttonParenthesisEnd = findViewById(R.id.button_parenthesis_end);
        buttonPoint = findViewById(R.id.button_point);
        buttonEqually = findViewById(R.id.button_equally);
        buttonNumIds = new int[]{
                R.id.button_1,
                R.id.button_2,
                R.id.button_3,
                R.id.button_4,
                R.id.button_5,
                R.id.button_6,
                R.id.button_7,
                R.id.button_8,
                R.id.button_9,
                R.id.button_0
        };
        buttonOperationIds = new int[]{
                R.id.button_division,
                R.id.button_minus,
                R.id.button_multiplication,
                R.id.button_plus,
        };
        calculator = new SimpleCalculator();
    }

    private String returnButtonText(View view) {
        Button button = (Button) view;
        return button.getText().toString();
    }

    private void setView() {
        textViewFull.setText(calculator.getText());

        buttonNumClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculator.onButtonNumPressed(returnButtonText(view));
                textViewFull.setText(calculator.getText());
            }
        };
        buttonOperationClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculator.onButtonOperationPressed(returnButtonText(view));
                textViewFull.setText(calculator.getText());
            }
        };

        buttonParenthesisStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculator.onButtonParenthesisStartPressed(returnButtonText(view));
                textViewFull.setText(calculator.getText());
            }
        });
        buttonParenthesisEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculator.onButtonParenthesisEndPressed(returnButtonText(view));
                textViewFull.setText(calculator.getText());
            }
        });
        buttonPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculator.onButtonPointPressed(returnButtonText(view));
                textViewFull.setText(calculator.getText());
            }
        });
        buttonEqually.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculator.onButtonEquallyPressed();
                textViewFull.setText(calculator.getText());
                textViewEqually.setText(calculator.getEqually());
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculator.onResetZero();
                textViewFull.setText(calculator.getText());
                textViewEqually.setText(calculator.getEqually());
            }
        });

        buttonBackspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculator.onBackspace();
                textViewFull.setText(calculator.getText());
                textViewEqually.setText(calculator.getEqually());
            }
        });


        for (int i = 0; i < buttonNumIds.length; i++) {
            findViewById(buttonNumIds[i]).setOnClickListener(buttonNumClickListener);
        }
        for (int i = 0; i < buttonOperationIds.length; i++) {
            findViewById(buttonOperationIds[i]).setOnClickListener(buttonOperationClickListener);
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        String s = getSharedPreferences(PREF_NAME, MODE_PRIVATE).getString(TEXT_VIEW_FULL, null);
        if (s != null && !s.isEmpty()) {
            textViewFull.setText(s);
            calculator.setExpression(s);
        }
    }

    @Override
    protected void onStop() {
        String s = textViewFull.getText().toString();
        if (!s.isEmpty()) {
            getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit().putString(TEXT_VIEW_FULL, s).apply();
        }
        super.onStop();
    }
}