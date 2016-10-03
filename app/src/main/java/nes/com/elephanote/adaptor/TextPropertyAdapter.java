package nes.com.elephanote.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import nes.com.elephanote.R;
import nes.com.elephanote.model.Database;
import nes.com.elephanote.model.Note;
import nes.com.elephanote.service.Bubble;
import nes.com.elephanote.service.ClipboardListener;

import static nes.com.elephanote.service.Bubble.counterView;

public class TextPropertyAdapter extends BaseAdapter {

    public static List<Note> selectedNotes;
    Context context;
    private LayoutInflater inflater;


    public TextPropertyAdapter(Context context, List<Note> selectedNotes) {
        inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        this.notifyDataSetChanged();
        this.selectedNotes = selectedNotes;
        this.context = context;
    }

    @Override
    public int getCount() {
        return selectedNotes.size();
    }

    @Override
    public Note getItem(int position) {
        return selectedNotes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View itemView;
        itemView = inflater.inflate(R.layout.popup_content, null);
        TextView textView = (TextView) itemView.findViewById(R.id.text_shortage);
        RadioGroup imageView = (RadioGroup) itemView.findViewById(R.id.radio_group);
        Button saveButton = (Button) itemView.findViewById(R.id.buttonSave);
        Button deleteButton = (Button) itemView.findViewById(R.id.buttonDelete);
        textView.setText(selectedNotes.get(position).getShortage());

        imageView.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.radio_todo) {
                    Toast.makeText(context, "choice: radio address ",
                            Toast.LENGTH_SHORT).show();
                    selectedNotes.get(position).setCategory(0);
                } else if (checkedId == R.id.radio_address) {
                    Toast.makeText(context, "choice: radiio todo",
                            Toast.LENGTH_SHORT).show();
                    selectedNotes.get(position).setCategory(1);
                } else if (checkedId == R.id.radio_quotes) {
                    Toast.makeText(context, "choice: radio quotes",
                            Toast.LENGTH_SHORT).show();
                    selectedNotes.get(position).setCategory(2);
                }
            }

        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Database.getInstance(context).addNote(selectedNotes.get(position).getNote(),
                        selectedNotes.get(position).getDate(),
                        selectedNotes.get(position).getCategory() + "");
                selectedNotes.remove(position);
                notifyDataSetChanged();
                ClipboardListener.selectionCount--;
                counterView.setText(ClipboardListener.selectionCount + "");

                if (ClipboardListener.selectionCount == 0) {
                    Bubble.listPopupWindow.dismiss();
                }


            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedNotes.remove(position);
                notifyDataSetChanged();
                ClipboardListener.selectionCount--;
                counterView.setText(ClipboardListener.selectionCount + "");

                if (ClipboardListener.selectionCount == 0) {
                    Bubble.listPopupWindow.dismiss();
                }
            }
        });

        return itemView;
    }
}