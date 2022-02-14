package tw.org.iii.cma.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

@Entity
@Table(name = "DAYTRAVEL")
public class DayTravelBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DTid")
	private Integer DTid;
	
//	@JsonManagedReference
	@OneToMany(mappedBy = "dayTravelBean",
			cascade = {
					CascadeType.REMOVE
			})
	private List<TravelUnitBean> travelUnitBeans;
	
	

	public List<TravelUnitBean> getTravelUnitBeans() {
		return travelUnitBeans;
	}

	public void setTravelUnitBeans(List<TravelUnitBean> travelUnitBeans) {
		this.travelUnitBeans = travelUnitBeans;
	}

	
//	@JsonBackReference
	@JsonIgnore
	@ManyToOne
	@JoinColumn(
		name= "WTid",
		referencedColumnName = "WTid"		
	)
	private WholeTravelBean wholeTravelBean;

	public WholeTravelBean getWholeTravelBean() {
		return wholeTravelBean;
	}

	public void setWholeTravelBean(WholeTravelBean wholeTravelBean) {
		this.wholeTravelBean = wholeTravelBean;
	}

	@Column(name = "DTtitle")
	private String DTtitle;
	
	@Column(name = "DTintroduce", length = 2048, columnDefinition = "text")
	private String DTintroduce;
	
	@Column(name = "DTsequence")
	private Integer DTsequence;
	
	@Column(name = "DTBegin")
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date DTBegin;

	public Integer getDTid() {
		return DTid;
	}

	public void setDTid(Integer dTid) {
		DTid = dTid;
	}


	public String getDTtitle() {
		return DTtitle;
	}

	public void setDTtitle(String dTtitle) {
		DTtitle = dTtitle;
	}

	public String getDTintroduce() {
		return DTintroduce;
	}

	public void setDTintroduce(String dTintroduce) {
		DTintroduce = dTintroduce;
	}

	public Integer getDTsequence() {
		return DTsequence;
	}

	public void setDTsequence(Integer dTsequence) {
		DTsequence = dTsequence;
	}

	public java.util.Date getDTBegin() {
		return DTBegin;
	}

	public void setDTBegin(java.util.Date dTBegin) {
		DTBegin = dTBegin;
	}

	@Override
	public String toString() {
		return "DayTravelBean [DTid=" + DTid + ", DTtitle=" + DTtitle + ", DTintroduce="
				+ DTintroduce + ", DTsequence=" + DTsequence + ", DTBegin=" + DTBegin + "]";
	}
	
	
	
}
