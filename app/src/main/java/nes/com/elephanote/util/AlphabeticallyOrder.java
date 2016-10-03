package nes.com.elephanote.util;

import java.util.Comparator;

import nes.com.elephanote.model.Note;

public class AlphabeticallyOrder implements Comparator<Note> {
    @Override
    public int compare(Note note1, Note note2) {
        return note1.getShortage().compareToIgnoreCase(note2.getShortage());

    }
}
