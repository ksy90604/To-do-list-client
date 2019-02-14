package com.example.task;

public class Task {

	private String id;		// id
	private String title; 	// 할일
	private String regDate;	// 등록일
	private String updDate;	// 수정일
	private String finYn; 	// 완료여부
	private String parentId;// 참조 부모 id

    protected Task() {}
    
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getUpdDate() {
		return updDate;
	}
	public void setUpdDate(String updDate) {
		this.updDate = updDate;
	}
	public String getFinYn() {
		return finYn;
	}
	public void setFinYn(String finYn) {
		this.finYn = finYn;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Override
    public String toString() {
        return String.format("Task[id='%d', title='%s']", id, title);
    }
	
}
