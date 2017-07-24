package com.mycloud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycloud.pojo.Note;
import com.mycloud.pojo.NoteFolder;
import com.mycloud.result.BaseResult;
import com.mycloud.result.ResultUtils;
import com.mycloud.service.NoteService;
import com.mycloud.utils.ParameterUtils;

@Controller
@RequestMapping("note")
public class NoteController {
	@Autowired
	private NoteService noteService;

	@RequestMapping("getnotefolder")
	@ResponseBody
	public BaseResult getNoteFolderById(String userid) throws Exception {
        
		ParameterUtils.checkParameter(userid);
		
		List<NoteFolder> list = noteService.getNoteFolder(userid);
		return ResultUtils.getResultByData(list);
	}

	@RequestMapping("getnote")
	@ResponseBody
	public BaseResult getNoteById(String userid, String folderid) throws Exception {
		
		ParameterUtils.checkParameter(userid,folderid);
		
		List<Note> list = noteService.getNote(userid, folderid);
		return ResultUtils.getResultByData(list);
	}

	@RequestMapping("createnotefolder")
	@ResponseBody
	public BaseResult createNoteFolderById(String userid, String folder) throws Exception {
		
		ParameterUtils.checkParameter(userid,folder);
		
		return ResultUtils.getResultByData(noteService.createNoteFolder(userid, folder));

	}

	@RequestMapping("createnote")
	@ResponseBody
	public BaseResult createNote(String userid, String folderid, String note) throws Exception {
		
		ParameterUtils.checkParameter(userid,folderid,note);
		
		return ResultUtils.getResultByData(noteService.createNote(userid, folderid, note));

	}

	@RequestMapping("updatenote")
	@ResponseBody
	public BaseResult updateNote(String userid, String noteid, String content) throws Exception {
	
		ParameterUtils.checkParameter(userid,noteid);
		
		noteService.updateNote(userid, noteid, content);
		return ResultUtils.getResultByData(null);

	}

	@RequestMapping("deletenotefolder")
	@ResponseBody
	public BaseResult changeNoteFolderStatus(String userid, String folderid) throws Exception {
		
		ParameterUtils.checkParameter(userid,folderid);
		
		noteService.changeNoteFolderState(userid, folderid);
		return ResultUtils.getResultByData(null);
	}

	@RequestMapping("deletenote")
	@ResponseBody
	public BaseResult changeNoteStatus(String userid, String noteid) throws Exception {
		
		ParameterUtils.checkParameter(userid,noteid);
		
		noteService.changeNoteState(userid, noteid);
		return ResultUtils.getResultByData(null);
	}

	@RequestMapping("getsharenote")
	@ResponseBody
	public BaseResult getShareNote(String userid) throws Exception {

		ParameterUtils.checkParameter(userid);
		
		return ResultUtils.getResultByData(noteService.getShareNote(userid));
	}
	
	@RequestMapping("sharenote")
	@ResponseBody
	public BaseResult shareNote(String userid,String noteid) throws Exception {
		
		ParameterUtils.checkParameter(userid,noteid);
		
		noteService.shareNote(userid, noteid);
		return ResultUtils.getResultByData(null);
	}
	
}
