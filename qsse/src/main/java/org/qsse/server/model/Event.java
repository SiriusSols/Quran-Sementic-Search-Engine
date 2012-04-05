package org.qsse.server.model;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.simpleds.annotations.Entity;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Entity
public class Event {

	public static final String KIND = "Event";
	
	@Id
    @GeneratedValue
    private Key key;
	private String title;
	private String detail;
	private String location;
	private Date startDate;
	private Date endDate;
	private String timing;
	private boolean publish; // 1 = publish : 0 = unpublish
	
	
	public static Key key(long id) {
		return KeyFactory.createKey(KIND, id);
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
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
	
}
