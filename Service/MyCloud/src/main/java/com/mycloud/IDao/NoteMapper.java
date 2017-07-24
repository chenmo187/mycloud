package com.mycloud.IDao;

import java.util.List;
import java.util.Map;

import com.mycloud.pojo.Note;
import com.mycloud.pojo.NoteFolder;

public interface NoteMapper {
	List<NoteFolder> getNoteFolder(String userid);

	NoteFolder checkNoteFolderByName(Map<String, String> map);

	void createNoteFolder(Map<String, String> map);

	List<Note> getNote(Map<String, String> map);

	NoteFolder checkNoteFolderById(Map<String, String> map);

	Note checkNoteByName(Map<String, String> map);

	Integer createNote(Map<String, String> map);

	void updateNote(Map<String, String> map);

	Note getNoteById(Map<String, String> map);

	void createNoteHistory(Map<String, String> map);

	void changeNoteFolderStatus(Map<String, String> map);

	void changeNoteStatus(Map<String, String> map);

	List<Note> getShareNote(String userid);

	void shareNote(Map<String, String> map);

}
