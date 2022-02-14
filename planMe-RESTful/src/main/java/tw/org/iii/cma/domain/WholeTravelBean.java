package tw.org.iii.cma.domain;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Where;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "WHOLETRAVEL")
@Where(clause = "deleted= false")
public class WholeTravelBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "WTID")
	private Integer WTid;
	

//	@JsonManagedReference
	@OneToMany(mappedBy = "wholeTravelBean",
			cascade = {
					CascadeType.REMOVE
			})
	List<DayTravelBean> dayTravelBeans;
	
	public List<DayTravelBean> getDayTravelBeans() {
		return dayTravelBeans;
	}

	public void setDayTravelBeans(List<DayTravelBean> dayTravelBeans) {
		this.dayTravelBeans = dayTravelBeans;
	}
	
	@JsonIgnore
	@OneToMany(
			mappedBy = "wholeTravelBean",
			cascade = {
					CascadeType.REMOVE
			})
	List<TripStorage> tripStorages;
	
	public List<TripStorage> getTripStorages() {
		return tripStorages;
	}

	public void setTripStorages(List<TripStorage> tripStorages) {
		this.tripStorages = tripStorages;
	}

	//	@JsonBackReference
	@JsonIgnore
	@ManyToOne
	@JoinColumn(
		name= "MBID",
		referencedColumnName = "MBID"		
	)
	private MemberBean memberBean;
	
	public MemberBean getMemberBean() {
		return memberBean;
	}

	public void setMemberBean(MemberBean memberBean) {
		this.memberBean = memberBean;
	}

	@Column(name = "WTTITLE	")
	private String WTtitle;
	
	@Lob 
	@Column(name = "WTINTRODUCE", length=2048)
	private String WTintroduce;
	
	@Column(name = "WTLIKE")
	private Integer WTlike = 0;
	
	@Column(name = "WTSTARTT")
	@Temporal(TemporalType.DATE)
	private java.util.Date WTStartT;
	
	@Column(name = "WTDAYS")
	private Integer WTdays;
	
	@Column(name = "ISPUBLIC",columnDefinition="bit default 1")
	private Boolean isPublic = true;
	
//	@Lob
//	@Column(name = "WTPHOTO",columnDefinition="longblob")
//	private byte[] WTPhoto;
	
	@Column(name = "WTPHOTO")
	private String WTPhoto = "https://p5-tt.byteimg.com/origin/dfic-imagehandler/a5305037-6c5a-4e07-9e70-038e82503dbd.jpg";
	
	@Column(name = "DELETED",columnDefinition="bit default 0")
	private Boolean Deleted = false;

	public Integer getWTid() {
		return WTid;
	}

	public void setWTid(Integer wTid) {
		WTid = wTid;
	}

	public String getWTintroduce() {
		return WTintroduce;
	}

	public void setWTintroduce(String wTintroduce) {
		WTintroduce = wTintroduce;
	}

	public String getWTtitle() {
		return WTtitle;
	}

	public void setWTtitle(String wTtitle) {
		WTtitle = wTtitle;
	}

	public Integer getWTlike() {
		return WTlike;
	}

	public void setWTlike(Integer wTlike) {
		WTlike = wTlike;
	}

	public java.util.Date getWTStartT() {
		return WTStartT;
	}

	public void setWTStartT(java.util.Date wTStartT) {
		WTStartT = wTStartT;
	}

	public Integer getWTdays() {
		return WTdays;
	}

	public void setWTdays(Integer wTdays) {
		WTdays = wTdays;
	}

	public Boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}

	public String getWTPhoto() {
		return WTPhoto;
	}

	public void setWTPhoto(String wTPhoto) {
		WTPhoto = wTPhoto;
	}

	public Boolean getDeleted() {
		return Deleted;
	}

	public void setDeleted(Boolean deleted) {
		Deleted = deleted;
	}

	@Override
	public String toString() {
		return "WholeTravelBean [WTid=" + WTid +", WTintroduce=" + WTintroduce + ", WTtitle="
				+ WTtitle + ", WTlike=" + WTlike + ", WTStartT=" + WTStartT + ", WTdays=" + WTdays + ", isPublic="
				+ isPublic + ", WTPhoto=" + WTPhoto + ", Deleted=" + Deleted + "]";
	}
	
	
}
