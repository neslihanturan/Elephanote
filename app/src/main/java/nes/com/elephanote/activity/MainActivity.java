package nes.com.elephanote.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.Collections;
import java.util.List;

import nes.com.elephanote.R;
import nes.com.elephanote.adaptor.InActivityListAdapter;
import nes.com.elephanote.model.Database;
import nes.com.elephanote.model.Note;
import nes.com.elephanote.service.ClipboardListener;
import nes.com.elephanote.util.AlphabeticallyOrder;
import nes.com.elephanote.util.CategoryOrder;
import nes.com.elephanote.util.DateOrder;

public class MainActivity extends AppCompatActivity {
    public static List<Note> notes;
    public static ListView noteList;
    public InActivityListAdapter adaptor;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        Intent intent = new Intent(MainActivity.this, ClipboardListener.class);
        startService(intent);

        getDataFromDB();
        initView();
        addOrderOptions();
    }

    private void getDataFromDB() {
        Database.getInstance(getApplicationContext());
        SharedPreferences settings = getSharedPreferences("SQL", 0);
        boolean firstTime = settings.getBoolean("firstTime", true);

        if (firstTime) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("firstTime", false);
            editor.commit();
        }

        notes = Database.getInstance(getApplicationContext()).getAllNotes();
        Database.getInstance(getApplicationContext()).close();

    }

    private void initView() {
        noteList = (ListView) findViewById(R.id.liste);
        adaptor = new InActivityListAdapter(this, notes);
        noteList.setAdapter(adaptor);

        noteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, ExtendedNotes.class);
                intent.putExtra("note", notes.get(i).getNote());
                intent.putExtra("date", notes.get(i).getDate());
                intent.putExtra("order", i);
                startActivityForResult(intent, 2);

            }
        });
    }

    public void addOrderOptions() {
        final Spinner dropdown = (Spinner) findViewById(R.id.spinner1);
        String[] items = new String[]{"Order by Alphabetically", "Order by category", "Order by Date"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View v,
                                       int position, long id) {
                // On selecting a spinner item
                if (dropdown.getSelectedItemPosition() == 0) {
                    Collections.sort(notes, new AlphabeticallyOrder());
                } else if (dropdown.getSelectedItemPosition() == 1) {
                    Collections.sort(notes, new CategoryOrder());
                } else if (dropdown.getSelectedItemPosition() == 2) {
                    Collections.sort(notes, new DateOrder());


                }
                adaptor.notifyDataSetChanged();
                // Showing selected spinner item
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                dropdown.setPrompt("select orders");
                // TODO Auto-generated method stub
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        notes.clear();
        notes = Database.getInstance(getApplicationContext()).getAllNotes();
        noteList.setAdapter(new InActivityListAdapter(this, notes));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 2) {
            if (data.hasExtra("order") && data.hasExtra("note"))
                notes.get(data.getExtras().getInt("order")).setNote(data.getExtras().getString("note"));
        }
    }


}
