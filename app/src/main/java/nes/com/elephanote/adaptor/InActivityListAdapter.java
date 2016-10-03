package nes.com.elephanote.adaptor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nes.com.elephanote.R;
import nes.com.elephanote.model.Note;

public class InActivityListAdapter extends BaseAdapter {
    List<Note> myNotes = new ArrayList<Note>();
    List<Note> myFilterList = new ArrayList<Note>();
    private LayoutInflater mInflater;

    public InActivityListAdapter(Activity activity, List<Note> notes) {
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        notifyDataSetChanged();
        myNotes = notes;
        myFilterList = notes;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return myNotes.size();
    }

    @Override
    public Note getItem(int position) {
        return myNotes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View lineView;

        lineView = mInflater.inflate(R.layout.line_layout, null);

        ImageView imageView =
                (ImageView) lineView.findViewById(R.id.simge);

        TextView textView =
                (TextView) lineView.findViewById(R.id.shortage);

        TextView textViewDate =
                (TextView) lineView.findViewById(R.id.dateinfo);

        textView.setText(myNotes.get(position).getShortage());
        textViewDate.setText(String.format("%tB %<te, %<tY", myNotes.get(position).getDate()));
        notifyDataSetChanged();

        if (myNotes.get(position).getCategory() == 0) {
            imageView.setImageResource(R.drawable.todo);
        } else if (myNotes.get(position).getCategory() == 1) {
            imageView.setImageResource(R.drawable.address);
        } else if (myNotes.get(position).getCategory() == 2) {
            imageView.setImageResource(R.drawable.quote);
        }
        return lineView;
    }

}
