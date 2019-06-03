package com.bridgelabz.app.serviceimpl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.app.model.Label;
import com.bridgelabz.app.model.Note;
import com.bridgelabz.app.repository.LabelRepository;
import com.bridgelabz.app.repository.NoteRepository;
import com.bridgelabz.app.service.NoteService;
import com.bridgelabz.app.util.JsonToken;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {

	@Autowired
	public NoteRepository noteRep;
	@Autowired
	public LabelRepository labelRep;
	
	// create Note

	@Override
	public Note createNote(String token, Note note) {
		int varifiedUserId = JsonToken.tokenVerification(token);
		System.out.println("note creation :" + varifiedUserId);
		note.setUserId(varifiedUserId);
		return noteRep.save(note);
	}

	// update note
	@Override
	public Note updateNote(String token, Note note) {
		int varifiedUserId = JsonToken.tokenVerification(token);
		System.out.println("varifiedUserId :" + varifiedUserId);
		Optional<Note> maybeNote = noteRep.findByUserIdAndNoteId(varifiedUserId, note.getNoteId());
		System.out.println("maybeNote :" + maybeNote);
		Note presentNote = maybeNote.map(existingNote -> {
			System.out.println("noteee here");
			existingNote.setDiscription(
					note.getDiscription() != null ? note.getDiscription() : maybeNote.get().getDiscription());
			existingNote.setTitle(note.getTitle() != null ? note.getTitle() : maybeNote.get().getTitle());
			return existingNote;
		}).orElseThrow(() -> new RuntimeException("Note Not Found"));

		return noteRep.save(presentNote);
	}

	// delete note
	@Override
	public boolean deleteNote(String token, Note note) {
		int varifiedUserId = JsonToken.tokenVerification(token);
		noteRep.deleteByUserIdAndNoteId(varifiedUserId, note.getNoteId());
		return true;
	}

	// fetch note
	@Override
	public List<Note> fetchNote(String token) {
		int varifiedUserId = JsonToken.tokenVerification(token);
		System.out.println("i m in fetch :" + varifiedUserId);
		// public List getAllNote() {
		// return (List) noteRep.findAll();
		// }
		List<Note> notes = (List<Note>) noteRep.findByUserId(varifiedUserId);

		return notes;
	}

	// CREATE label
	@Override
	public Label createLabel(String token, Label label) {
		int varifiedUserId = JsonToken.tokenVerification(token);
		System.out.println("label creation :" + varifiedUserId);
		label.setUserId(varifiedUserId);
		return labelRep.save(label);
	}

	// update label

	@Override
	public Label updateLabel(String token, Label label) {
		int varifiedUserId = JsonToken.tokenVerification(token);
		System.out.println("varifiedUserId :" + varifiedUserId);
		Optional<Label> maybeLabel = labelRep.findByUserIdAndLabelId(varifiedUserId, label.getLabelId());
		System.out.println("maybeLabel :" + maybeLabel);
		Label presentLabel = maybeLabel.map(existingLabel -> {
			System.out.println("label here");
			existingLabel.setLabelName(
					label.getLabelName() != null ? label.getLabelName() : maybeLabel.get().getLabelName());
			// existingNote.setTitle(note.getTitle() != null ? note.getTitle() :
			// maybeNote.get().getTitle());
			return existingLabel;
		}).orElseThrow(() -> new RuntimeException("Label Not Found"));

		return labelRep.save(presentLabel);
	}

	// delete label

	@Override
	public boolean deleteLabel(String token, Label label) {
		int varifiedUserId = JsonToken.tokenVerification(token);
		labelRep.deleteByUserIdAndLabelId(varifiedUserId, label.getLabelId());
		return true;
	}

	@Override
	public List<Label> fetchLabel(String token) {
		int varifiedUserId = JsonToken.tokenVerification(token);
		System.out.println("i m in fetch :" + varifiedUserId);
		// public List getAllNote() {
		// return (List) noteRep.findAll();
		// }
		List<Label> labels = labelRep.findByUserId(varifiedUserId);

		return labels;
	}

}
