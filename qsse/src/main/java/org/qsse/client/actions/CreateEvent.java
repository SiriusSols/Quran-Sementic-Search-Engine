package org.qsse.client.actions;

import java.util.Date;

import org.qsse.shared.dto.VoidResult;

import net.customware.gwt.dispatch.shared.Action;

public class CreateEvent implements Action<VoidResult> {
	
	private long evendId;
	private String title;
	private String detail;
	private String location;
	private Date startDate;
	private Date endDate;
	private String timing;
	private boolean publish;
	private long task;
	
	public CreateEvent(){
		
	}
	
	public long getEvendId() {
		return evendId;
	}

	public void setEvendId(long evendId) {
		this.evendId = evendId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getTiming() {
		return timing;
	}

	public void setTiming(String timing) {
		this.timing = timing;
	}

	public boolean isPublish() {
		return publish;
	}

	public void setPublish(boolean publish) {
		this.publish = publish;
	}

	public long getTask() {
		return task;
	}

	public void setTask(long task) {
		this.task = task;
	}
	
}
