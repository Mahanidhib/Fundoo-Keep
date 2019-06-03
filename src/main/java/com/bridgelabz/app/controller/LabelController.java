package com.bridgelabz.app.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.app.model.Label;
import com.bridgelabz.app.service.NoteService;

@RestController
public class LabelController {

	@Autowired
	NoteService noteService;

	// Create
	@RequestMapping(value = "/createLabel", method = RequestMethod.POST)
	public Label createLabel(@RequestBody Label label, HttpServletRequest request) {

		return noteService.createLabel(request.getHeader("token"), label);
	}

	// update

	@RequestMapping(value = "/updateLabel", method = RequestMethod.PUT)
	public Label updateLabel(@RequestBody Label label, HttpServletRequest request) {

		return noteService.updateLabel(request.getHeader("token"), label);
	}

	// delete

	@RequestMapping(value = "/deleteLabel", method = RequestMethod.DELETE)
	public void deleteLabel(@RequestBody Label label, HttpServletRequest request) {
		System.out.println("I am token at delete method :" + request.getHeader("token"));
		boolean deleteLabel = noteService.deleteLabel(request.getHeader("token"), label);
		// System.out.println("-->" + b);

	}

	// fetch

	@RequestMapping(value = "/fetchLabel", method = RequestMethod.GET)
	public List<Label> fetchLabel(HttpServletRequest request) {
		System.out.println("I am token at get method :" + request.getHeader("token"));
		return noteService.fetchLabel(request.getHeader("token"));
		// System.out.println("-->" + b);

	}

}
