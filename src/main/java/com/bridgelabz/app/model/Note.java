package com.bridgelabz.app.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "note")
public class Note {

	@Id
	@Column(name = "noteid")
	private int noteId;
	private String title;
	private String discription;
	private Timestamp creadtedtime;
	private Timestamp updatetime;
	private boolean isarchive;
	private boolean ispinned;
	private boolean intrash;
	@Column(name = "id")
	private int userId;

	public int getNoteId() {
		return noteId;
	}

	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}

	public Timestamp getCreadtedtime() {
		return creadtedtime;
	}

	public void setCreadtedtime(Timestamp creadtedtime) {
		this.creadtedtime = creadtedtime;
	}

	public Timestamp getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	public boolean isIsarchive() {
		return isarchive;
	}

	public void setIsarchive(boolean isarchive) {
		this.isarchive = isarchive;
	}

	public boolean isIspinned() {
		return ispinned;
	}

	public void setIspinned(boolean ispinned) {
		this.ispinned = ispinned;
	}

	public boolean isIntrash() {
		return intrash;
	}

	public void setIntrash(boolean intrash) {
		this.intrash = intrash;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	@Override
	public String toString() {
		return "User [noteid=" + noteId + ", title=" + title + ", discription=" + discription + ", createdtime="
				+ creadtedtime + ", updatetime=" + updatetime + ", isarchive=" + isarchive + ", ispinned=" + ispinned
				+ ",intrash=" + intrash + "]";
	}

}