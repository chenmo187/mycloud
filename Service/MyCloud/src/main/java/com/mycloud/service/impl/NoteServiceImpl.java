package com.mycloud.service.impl;

import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mycloud.IDao.NoteMapper;
import com.mycloud.pojo.Note;
import com.mycloud.pojo.NoteFolder;
import com.mycloud.result.ResultUtils;
import com.mycloud.service.NoteService;

@Service
public class NoteServiceImpl implements NoteService {
	@Autowired
	private NoteMapper noteMapper;

	@Override
	@Transactional(readOnly = true)
	public List<NoteFolder> getNoteFolder(String userid) {

		return noteMapper.getNoteFolder(userid);

	}

	@Override
	@Transactional
	public NoteFolder createNoteFolder(String userid, String folder) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("folder", folder);
		if (noteMapper.checkNoteFolderByName(map) == null) {
			noteMapper.createNoteFolder(map);
			return noteMapper.checkNoteFolderByName(map);
		} else {
			throw ResultUtils.getExceptionByCode(ResultUtils.NOTEFOLDER_EXIST_CODE);
		}

	}

	@Override
	@Transactional(readOnly = true)
	public List<Note> getNote(String userid, String folderid) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("folderid", folderid);
		// if (noteMapper.checkNoteFolderById(map) != null) {
		return noteMapper.getNote(map);
		// } else {
		// throw ResultUtils.getExceptionByCode(ResultUtils.FOLDER_NOFIND_CODE);
		// }
	}

	@Override
	@Transactional
	public Note createNote(String userid, String folderid, String note) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("folderid", folderid);
		map.put("note", note);

		if (noteMapper.checkNoteFolderById(map) != null) {

			if (noteMapper.checkNoteByName(map) == null) {
				noteMapper.createNote(map);

				return noteMapper.checkNoteByName(map);

			} else {
				throw ResultUtils.getExceptionByCode(ResultUtils.NOTE_EXIST_CODE);
			}

		} else {
			throw ResultUtils.getExceptionByCode(ResultUtils.FOLDER_NOFIND_CODE);
		}

	}

	@Override
	@Transactional
	public void updateNote(String userid, String noteid, String content) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("noteid", noteid);
		Note note = noteMapper.getNoteById(map);
		map.put("content", note.getContent());
		if(!StringUtils.isEmpty(note.getContent())){
			noteMapper.createNoteHistory(map);
		}
		map.put("content", content);
		noteMapper.updateNote(map);

	}

	@Override
	@Transactional
	public void changeNoteState(String userid, String noteid) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("noteid", noteid);
		map.put("status", "2");
	    noteMapper.changeNoteStatus(map);
	}

	@Override
	@Transactional
	public void changeNoteFolderState(String userid, String folderid) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("folderid", folderid);
		map.put("status", "2");
		noteMapper.changeNoteFolderStatus(map);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Note> getShareNote(String userid) throws Exception {
		return noteMapper.getShareNote(userid);
	}

	@Override
	@Transactional
	public void shareNote(String userid, String noteid) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("noteid", noteid);
		noteMapper.shareNote(map);
	}
}
