package com.example.task;

public interface ToDoService {
	public String doGET(String reqUrl) throws Exception;

	public String doPOST(String reqUrl, String title, String parentId) throws Exception;

//	public String doPUT(String reqUrl, String id, String title, String parentId, String finYn) throws Exception;
	public String doPUT(String reqUrl, Task task) throws Exception;
}
