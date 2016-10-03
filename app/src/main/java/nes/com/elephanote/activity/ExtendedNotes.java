package nes.com.elephanote.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import nes.com.elephanote.R;

public class ExtendedNotes extends Activity {

    String value = "";
    String valueDate = "";
    TextView editText;
    int order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.extended_note);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("note");
            valueDate = extras.getString("date");
            order = extras.getInt("order");
        }
        TextView textView = (TextView) findViewById(R.id.e_title);
        if (value.length() >= 10)
            textView.setText(value.substring(0, 10));
        else
            textView.setText(value);

        TextView textViewDate = (TextView) findViewById(R.id.date_title);
        textViewDate.setText(valueDate);

        editText = (TextView) findViewById(R.id.edit_text);
        editText.setText(value);

        Button btn = (Button) findViewById(R.id.btnActivity);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("note", value);
                intent.putExtra("order", order);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value = editText.getText().toString();
                editText.setText(value);
            }
        });
    }

}
