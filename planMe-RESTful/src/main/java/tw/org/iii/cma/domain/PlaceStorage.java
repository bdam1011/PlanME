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
@Table(name = "placestorage")
public class PlaceStorage implements Serializable{
	
	@EmbeddedId
	private PlaceStorageId placeStorageId;
	
	@Column(name="storagetime")
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date storagetime = new java.util.Date();
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(
		name= "MBid",
		referencedColumnName = "MBid"		
	)
	@MapsId("MBid")
	private MemberBean memberBean;
	
	public MemberBean getMemberBean() {
		return memberBean;
	}

	public void setMemberBean(MemberBean memberBean) {
		this.memberBean = memberBean;
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

	public PlaceStorageId getPlaceStorageId() {
		return placeStorageId;
	}

	public void setPlaceStorageId(PlaceStorageId placeStorageId) {
		this.placeStorageId = placeStorageId;
	}

	public java.util.Date getStoragetime() {
		return storagetime;
	}

	public void setStoragetime(java.util.Date storagetime) {
		this.storagetime = storagetime;
	}
	
	
}
