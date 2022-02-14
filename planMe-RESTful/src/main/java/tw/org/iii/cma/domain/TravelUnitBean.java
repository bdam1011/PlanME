package tw.org.iii.cma.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

@Entity
@Table(name = "travelunit")
public class TravelUnitBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TUid")
	private Integer TUid;
	
//	@JsonBackReference
	@JsonIgnore
	@ManyToOne
	@JoinColumn(
		name= "DTid",
		referencedColumnName = "DTid"		
	)
	private DayTravelBean dayTravelBean;
	
	public DayTravelBean getDayTravelBean() {
		return dayTravelBean;
	}

	public void setDayTravelBean(DayTravelBean dayTravelBean) {
		this.dayTravelBean = dayTravelBean;
	}

	
//	@JsonManagedReference
//	@JsonIgnore
//	@OneToOne(
//			mappedBy = "travelUnitBean",
//			cascade = CascadeType.ALL
//	)
//	@JsonIgnore
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

	@Column(name = "TUtitle")
	private String TUtitle;
	
	@Column(name = "TUlike")
	private Integer TUlike;
	
	@Column(name = "TUsequence")
	private Integer TUsequence;
	
	@Column(name = "Transportation")
	private String Transportation;
	
	@Column(name = "movingtime")
	private Integer movingtime;
	
	@Column(name = "staytime")
	private Integer staytime;

	public Integer getTUid() {
		return TUid;
	}

	public void setTUid(Integer tUid) {
		TUid = tUid;
	}


	public String getTUtitle() {
		return TUtitle;
	}

	public void setTUtitle(String tUtitle) {
		TUtitle = tUtitle;
	}

	public Integer getTUlike() {
		return TUlike;
	}

	public void setTUlike(Integer tUlike) {
		TUlike = tUlike;
	}

	public Integer getTUsequence() {
		return TUsequence;
	}

	public void setTUsequence(Integer tUsequence) {
		TUsequence = tUsequence;
	}

	public String getTransportation() {
		return Transportation;
	}

	public void setTransportation(String transportation) {
		Transportation = transportation;
	}

	public Integer getMovingtime() {
		return movingtime;
	}

	public void setMovingtime(Integer movingtime) {
		this.movingtime = movingtime;
	}

	public Integer getStaytime() {
		return staytime;
	}

	public void setStaytime(Integer staytime) {
		this.staytime = staytime;
	}
	
	

	@Override
	public String toString() {
		return "TravelUnitBean [TUid=" + TUid + ", TUtitle=" + TUtitle + ", TUlike=" + TUlike
				+ ", TUsequence=" + TUsequence + ", Transportation=" + Transportation + ", movingtime=" + movingtime
				+ ", staytime=" + staytime + "]";
	}
	
	
	
	
}
