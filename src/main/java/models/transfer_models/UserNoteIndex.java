package models.transfer_models;

import models.db_models.Note;

import java.util.ArrayList;

public class UserNoteIndex
{
    private ArrayList<Note> notes;

    public UserNoteIndex(ArrayList<Note> notes)
    {
        this.notes = notes;
    }

    public UserNoteIndex(){}

    public ArrayList<Note> getNotes()
    {
        return notes;
    }

    public void setNotes(ArrayList<Note> notes)
    {
        this.notes = notes;
    }
}
