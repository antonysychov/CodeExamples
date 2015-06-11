package com.example.ExpressionCalculator.Activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.ExpressionCalculator.Calculator;
import com.example.ExpressionCalculator.R;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends Activity {
    private TextView tw, twm;
    private Button bRes, b1, b2, b3, b4, b5, b6, b7, b8, b9, b0,
            bMin, bPlus, bMul, bDiv, bLBrace, bRBrace, bPoint, bClear;

    private Calculator c = new Calculator();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        tw = (TextView) findViewById(R.id.textView);
        twm = (TextView) findViewById(R.id.textViewMessage);
        bRes = (Button) findViewById(R.id.buttonRes);
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);
        b5 = (Button) findViewById(R.id.button5);
        b6 = (Button) findViewById(R.id.button6);
        b7 = (Button) findViewById(R.id.button7);
        b8 = (Button) findViewById(R.id.button8);
        b9 = (Button) findViewById(R.id.button9);
        b0 = (Button) findViewById(R.id.button0);
        bMin = (Button) findViewById(R.id.buttonMin);
        bPlus = (Button) findViewById(R.id.buttonPlus);
        bMul = (Button) findViewById(R.id.buttonMul);
        bDiv = (Button) findViewById(R.id.buttonDiv);
        bLBrace = (Button) findViewById(R.id.buttonLBrace);
        bRBrace = (Button) findViewById(R.id.buttonRBrace);
        bPoint = (Button) findViewById(R.id.buttonPoint);
        bClear = (Button) findViewById(R.id.buttonC);

        bRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String expression = tw.getText().toString();
                try {
                    double result = c.calculate(expression);
                    tw.setText(String.valueOf(result));
                } catch (RuntimeException e) {
                    int color = tw.getCurrentTextColor();
                    tw.setTextColor(Color.RED);
                    twm.setText("Incorrect expression");
                    twm.setGravity(Gravity.CENTER_HORIZONTAL);
                    twm.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tw.setTextColor(color);
                            twm.setText("");
                        }
                    }, 500);
                }
            }
        });
        bClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = tw.getText().toString();
                int length = text.length();
                String newText = length == 0 ? "" : text.substring(0, length - 1);
                tw.setText(newText);
            }
        });
        bClear.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tw.setText("");
                return true;
            }
        });

        List<Button> buttons = new LinkedList<Button>() {{
            Collections.addAll(this, b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, bMin, bPlus, bDiv, bMul, bLBrace, bRBrace, bPoint);
        }};

        ButtonClickListener bcl = new ButtonClickListener();
        for (Button button : buttons) {
            button.setOnClickListener(bcl);
        }
    }

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Button b = (Button) (v);
            switch (v.getId()) {
                case R.id.button0:
                case R.id.button1:
                case R.id.button2:
                case R.id.button3:
                case R.id.button4:
                case R.id.button5:
                case R.id.button6:
                case R.id.button7:
                case R.id.button8:
                case R.id.button9:
                case R.id.buttonDiv:
                case R.id.buttonMul:
                case R.id.buttonPlus:
                case R.id.buttonMin:
                case R.id.buttonLBrace:
                case R.id.buttonRBrace:
                case R.id.buttonPoint:
                    tw.append(b.getText().toString());
            }
        }
    }
}
