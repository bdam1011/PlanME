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
@Table(name = "tripstorage")
public class TripStorage implements Serializable{
	
	@EmbeddedId
	private TripStorageId TripStorageId;
	
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
		name= "WTid",
		referencedColumnName = "WTid"		
	)
	@MapsId("WTid")
	private WholeTravelBean wholeTravelBean;
	
	public WholeTravelBean getWholeTravelBean() {
		return wholeTravelBean;
	}

	public void setWholeTravelBean(WholeTravelBean wholeTravelBean) {
		this.wholeTravelBean = wholeTravelBean;
	}

	public TripStorageId getTripStorageId() {
		return TripStorageId;
	}

	public void setTripStorageId(TripStorageId TripStorageId) {
		this.TripStorageId = TripStorageId;
	}

	public java.util.Date getStoragetime() {
		return storagetime;
	}

	public void setStoragetime(java.util.Date storagetime) {
		this.storagetime = storagetime;
	}
	
	
}
