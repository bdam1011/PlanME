package tw.org.iii.cma.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tagstorage")
public class TagStorage implements Serializable{
	
	@EmbeddedId
	private TagStorageId tagStorageId;
	
	@Column(name="storagetime")
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date storagetime = new java.util.Date();
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(
		name= "tagid",
		referencedColumnName = "tagid"		
	)
	@MapsId("tagid")
	private TagBean tagBean;
	

	public TagBean getTagBean() {
		return tagBean;
	}

	public void setTagBean(TagBean tagBean) {
		this.tagBean = tagBean;
	}

	@ManyToOne
	@JoinColumn(
		name= "ATRid",
		referencedColumnName = "ATRid"		
	)
	@MapsId("ATRid")
	private AttractionsBean attractionsBean;
	
	public AttractionsBean getAttractionsBean() {
		return attractionsBean;
	}

	public void setAttractionsBean(AttractionsBean attractionsBean) {
		this.attractionsBean = attractionsBean;
	}


	public TagStorageId getTagStorageId() {
		return tagStorageId;
	}

	public void setTagStorageId(TagStorageId tagStorageId) {
		this.tagStorageId = tagStorageId;
	}

	public java.util.Date getStoragetime() {
		return storagetime;
	}

	public void setStoragetime(java.util.Date storagetime) {
		this.storagetime = storagetime;
	}
	
	
}
