package com.bridgelabz.app.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.app.model.Note;
import com.bridgelabz.app.service.NoteService;


@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NoteController {

	@Autowired
	NoteService noteService;

	// Create
	@RequestMapping(value = "/createNote", method = RequestMethod.POST)
	public Note createNote(@RequestBody Note note, HttpServletRequest request) {

		return noteService.createNote(request.getHeader("token"), note);
	}

	// update

	@RequestMapping(value = "/updateNote", method = RequestMethod.PUT)
	public Note updateNote(@RequestBody Note note, HttpServletRequest request) {

		return noteService.updateNote(request.getHeader("token"), note);
	}

	// delete

	@RequestMapping(value = "/deleteNote", method = RequestMethod.DELETE)
	public void deleteNote(@RequestBody Note note, HttpServletRequest request) {
		System.out.println("I am token at delete method :" + request.getHeader("token"));
		boolean deleteNote = noteService.deleteNote(request.getHeader("token"), note);
		// System.out.println("-->" + b);

	}

	// fetch

	@RequestMapping(value = "/viewnotes", method = RequestMethod.GET)
	public List<Note> fetchNote(HttpServletRequest request) {
		System.out.println("I am token at get method :" + request.getHeader("token"));
		return noteService.fetchNote(request.getHeader("token"));
		// System.out.println("-->" + b);

	}

}