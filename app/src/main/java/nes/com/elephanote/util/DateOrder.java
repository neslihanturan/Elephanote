package nes.com.elephanote.util;

import java.util.Comparator;

import nes.com.elephanote.model.Note;

public class DateOrder implements Comparator<Note> {
    @Override
    public int compare(Note note1, Note note2) {
        return note1.getDate().compareTo(note2.getDate());

    }
}
