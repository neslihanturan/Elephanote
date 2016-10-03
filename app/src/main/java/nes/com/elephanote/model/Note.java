package nes.com.elephanote.model;

import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable {
    private String note;
    private String shortage;
    private Date date;
    private int category = 0;
    private int id;

    public Note(String selectedText) {
        note = selectedText;

        if (selectedText.length() >= 21)
            shortage = selectedText.substring(0, 20) + "...";
        else
            shortage = selectedText;

        date = new Date();
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getShortage() {
        return shortage;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
