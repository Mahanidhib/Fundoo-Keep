package com.bridgelabz.app.service;

import java.util.List;

import com.bridgelabz.app.model.Label;
import com.bridgelabz.app.model.Note;

public interface NoteService {

	Note updateNote(String header, Note note);

	Note createNote(String header, Note note);

	boolean deleteNote(String token, Note note);

	List<Note> fetchNote(String header);

	Label updateLabel(String header, Label label);

	boolean deleteLabel(String header, Label label);

	List<Label> fetchLabel(String header);

	Label createLabel(String header, Label label);

}
