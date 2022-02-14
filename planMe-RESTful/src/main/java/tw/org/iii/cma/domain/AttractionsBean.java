package tw.org.iii.cma.domain;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.voodoodyne.jackson.jsog.JSOGGenerator;


@Entity
@Table(name = "ATTRACTIONS")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="atrphotoBeans",scope=AttractionsBean.class)
public class AttractionsBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ATRid")
	private Integer ATRid;
	
//	@JsonManagedReference
//	@JsonIgnore
	@OneToMany(
			//mappedBy="attractionsBean",
			cascade= {CascadeType.REMOVE}
	)
	@JoinColumn(name="ATRid")
	private List<AtrphotoBean> atrphotoBeans;	
	
	public List<AtrphotoBean> getAtrphotoBeans() {
		return atrphotoBeans;
	}
	
	public void setAtrphotoBeans(List<AtrphotoBean> atrphotoBeans) {
		this.atrphotoBeans = atrphotoBeans;
	}
	
	@JsonIgnore
	@OneToMany(mappedBy = "attractionsBean",
			cascade = {
					CascadeType.REMOVE
			})
	List<PlaceStorage> placeStorages;
	
	public List<PlaceStorage> getPlaceStorages() {
		return placeStorages;
	}

	public void setPlaceStorages(List<PlaceStorage> placeStorages) {
		this.placeStorages = placeStorages;
	}
	
	@JsonIgnore
	@OneToMany(mappedBy = "attractionsBean",
			cascade = {
					CascadeType.REMOVE
			})
	List<TagStorage> tagStorages;
	
	public List<TagStorage> getTagStorages() {
		return tagStorages;
	}

	public void setTagStorages(List<TagStorage> tagStorages) {
		this.tagStorages = tagStorages;
	}

	//	@JsonBackReference
//	@JsonIgnore
//	@OneToOne
//	@JoinColumn(
//			name="ATRid",
//			referencedColumnName = "TUid"
//	)
//	private TravelUnitBean travelUnitBean;
//	
//	public TravelUnitBean getTravelUnitBean() {
//		return travelUnitBean;
//	}
//	public void setTravelUnitBean(TravelUnitBean travelUnitBean) {
//		this.travelUnitBean = travelUnitBean;
//	}
	@JsonIgnore
	@OneToMany(mappedBy = "attractionsBean",
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

	@Column(name = "ATRNAME")
	private String ATRname;
	
	@Lob 
	@Column(name = "ATRintroduce", length=2048)
	private String ATRintroduce;

	@Column(name = "ATRaddress")
	private String ATRaddress;

	@Column(name = "ATRlongitude")
	private double ATRlongitude;

	@Column(name = "ATRlantitude")
	private double ATRlantitude;

	@Column(name = "ATRGM")
	private String ATRGM;

	@Column(name = "ATRtel")
	private String ATRtel;
	
	@Column(name = "ATRopentime")
	private String ATRopentime;

	@Column(name = "score")
	private String score;
	
	@Column(name = "ATRLIKE	")
	private Integer ATRlike = 0;
	
	
	public Integer getATRid() {
		return ATRid;
	}
	public void setATRid(Integer aTRid) {
		ATRid = aTRid;
	}
	public String getATRname() {
		return ATRname;
	}
	public void setATRname(String aTRname) {
		ATRname = aTRname;
	}
	public String getATRintroduce() {
		return ATRintroduce;
	}
	public void setATRintroduce(String aTRintroduce) {
		ATRintroduce = aTRintroduce;
	}
	public String getATRaddress() {
		return ATRaddress;
	}
	public void setATRaddress(String aTRaddress) {
		ATRaddress = aTRaddress;
	}
	
	public double getATRlongitude() {
		return ATRlongitude;
	}

	public void setATRlongitude(double aTRlongitude) {
		ATRlongitude = aTRlongitude;
	}

	public double getATRlantitude() {
		return ATRlantitude;
	}

	public void setATRlantitude(double aTRlantitude) {
		ATRlantitude = aTRlantitude;
	}

	public String getATRGM() {
		return ATRGM;
	}
	public void setATRGM(String aTRGM) {
		ATRGM = aTRGM;
	}
	public String getATRtel() {
		return ATRtel;
	}
	public void setATRtel(String aTRtel) {
		ATRtel = aTRtel;
	}
	public String getATRopentime() {
		return ATRopentime;
	}
	public void setATRopentime(String aTRopentime) {
		ATRopentime = aTRopentime;
	}

	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}

	public Integer getATRlike() {
		return ATRlike;
	}

	public void setATRlike(Integer aTRlike) {
		ATRlike = aTRlike;
	}
	
	
	
}	
	
	
	

