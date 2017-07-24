package com.mycloud.service;

import java.util.List;

import com.mycloud.pojo.Note;
import com.mycloud.pojo.NoteFolder;

public interface NoteService {

	List<NoteFolder> getNoteFolder(String userid) throws Exception;

	NoteFolder createNoteFolder(String userid, String folder) throws Exception;

	List<Note> getNote(String userid, String folderid) throws Exception;

	Note createNote(String userid, String folderid, String note) throws Exception;

	void updateNote(String userid, String noteid, String content) throws Exception;

	void changeNoteFolderState(String userid, String folderid) throws Exception;

	void changeNoteState(String userid, String noteid) throws Exception;

	List<Note> getShareNote(String userid) throws Exception;

	void shareNote(String userid, String noteid) throws Exception;
}
