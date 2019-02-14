package com.example.task;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/client")
public class ToDoListController {

	final static String restTaskURL = "http://localhost:8080/task";

	@Resource ToDoService toDoService;

	/**
	 * 할 일 목록
	 */
	@RequestMapping(value = "/toDoList", method = RequestMethod.GET)
	public String getToDoList(HttpServletRequest request, Model model) throws Exception {
		String st = toDoService.doGET(restTaskURL + "/list");
		JSONParser parser = new JSONParser();
		JSONArray jsonArray = (JSONArray) parser.parse(st.toString());
		JSONObject jsonObject = null;
		
		List<Task> taskList = new ArrayList<Task>();
		for(int i = 0; i < jsonArray.size(); i++) {
			Task task = new Task();
			jsonObject = (JSONObject) jsonArray.get(i);
			task.setId((String) jsonObject.get("id"));
			task.setTitle((String) jsonObject.get("title"));
			task.setRegDate((String) jsonObject.get("regDate"));
			task.setUpdDate((String) jsonObject.get("updDate"));
			task.setFinYn((String) jsonObject.get("finYn"));
			task.setParentId((String) jsonObject.get("parentId"));
			taskList.add(task);
		}
		
		model.addAttribute("toDoList", taskList);
		return "toDoList";
	}
	
	/**
	 * 할 일 추가
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(HttpServletRequest request, @RequestParam(value="title", required=true)String title,  
			@RequestParam(value="parentId", required=false)String parentId, Model model) throws Exception {
		
		String st = toDoService.doPOST(restTaskURL, title, parentId);

		return st;
	}
	
	/**
	 * 할 일 수정 page
	 */
	@RequestMapping(value = "/toDoView/{id}")
	public String toDoView(HttpServletRequest request, @PathVariable("id")String id, Model model) throws Exception {
		String st = toDoService.doGET(restTaskURL + "/" + id);			// 할일 상세보기
		String st2 = toDoService.doGET(restTaskURL + "/child/" + id);	// 참조하고 있는 하위 일감 수 확인
		Task task = new Task();

		try {
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(st.toString());

			task.setId((String) jsonObject.get("id"));
			task.setTitle((String) jsonObject.get("title"));
			task.setRegDate((String) jsonObject.get("regDate"));
			task.setUpdDate((String) jsonObject.get("updDate"));
			task.setFinYn((String) jsonObject.get("finYn"));
			task.setParentId((String) jsonObject.get("parentId"));
		} catch (Exception e) {
			// TODO: handle exception
		}

		model.addAttribute("toDo", task);
		model.addAttribute("ChildCnt", st2);
		return "toDoView";
	}

	/**
	 * 할 일 수정
	 */
	@RequestMapping(value = "/update")
	public String update(@RequestParam(value="id", required=true)String id, @RequestParam(value="title", required=false)String title
			,  @RequestParam(value="parentId", required=false)String parentId,  @RequestParam(value="finYn", required=false)String finYn
			, Model model) throws Exception {
		
		Task task = new Task();
		
		task.setId(id);
		task.setTitle(title);
		task.setFinYn(finYn);
		task.setParentId(parentId);
		
		String st = toDoService.doPUT(restTaskURL + "/" + id, task);

		return st;
	}
	

}
