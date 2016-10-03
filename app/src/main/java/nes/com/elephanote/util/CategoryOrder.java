package nes.com.elephanote.util;

import java.util.Comparator;

import nes.com.elephanote.model.Note;

public class CategoryOrder implements Comparator<Note> {
    @Override
    public int compare(Note note, Note note2) {
        return (note.getCategory() + "").compareTo(note2.getCategory() + "");
    }
}
