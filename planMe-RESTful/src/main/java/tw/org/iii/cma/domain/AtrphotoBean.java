package tw.org.iii.cma.domain;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

@Entity
@Table(name = "Atrphoto")
public class AtrphotoBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "photoID")
	private Integer photoID;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(
		name= "ATRid",
		referencedColumnName = "ATRid"		
	)
	private AttractionsBean attractionsBean;
	
	
	public AttractionsBean getAttractionsBean() {
		return attractionsBean;
	}

	public void setAttractionsBean(AttractionsBean attractionsBean) {
		this.attractionsBean = attractionsBean;
	}

	@Column(name = "title")
	private String title;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(
		name= "MBid",
		referencedColumnName = "MBid"		
	)
	private MemberBean memberBean;
	
	public MemberBean getMemberBean() {
		return memberBean;
	}

	public void setMemberBean(MemberBean memberBean) {
		this.memberBean = memberBean;
	}

	@Column(name = "photo")
	private String photo;
	
	@Column(name = "uploadT")
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date uploadT = new java.util.Date();

	public Integer getPhotoID() {
		return photoID;
	}

	public void setPhotoID(Integer photoID) {
		this.photoID = photoID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public java.util.Date getUploadT() {
		return uploadT;
	}

	public void setUploadT(java.util.Date uploadT) {
		this.uploadT = uploadT;
	}

	@Override
	public String toString() {
		return "AtrphotoBean [photoID=" + photoID + ", title=" + title 
				+ ", photo=" +photo + ", uploadT=" + uploadT +"]";
	}

	

}
