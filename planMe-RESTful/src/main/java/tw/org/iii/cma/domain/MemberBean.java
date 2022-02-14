package tw.org.iii.cma.domain;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

@Entity
@Table(name = "MEMBER")
@Where(clause = "deleted= false")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="wholeTravelBeans")
public class MemberBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MBID")
	private Integer MBid;
	
//	@JsonManagedReference(value="member-message")
	@OneToMany(
			mappedBy = "memberBean",	
			cascade = {
					CascadeType.REMOVE
			})
	private List<MessageBean> messageBeans;
	
	public List<MessageBean> getMessageBeans() {
		return messageBeans;
	}

	public void setMessageBeans(List<MessageBean> messageBeans) {
		this.messageBeans = messageBeans;
	}

	@JsonManagedReference
	@OneToMany(mappedBy = "memberBean",
			cascade = {
					CascadeType.REMOVE
			})
	private List<ArticleBean> articlesBeans;
	
	public List<ArticleBean> getArticlesBeans() {
		return articlesBeans;
	}

	public void setArticlesBeans(List<ArticleBean> articlesBeans) {
		this.articlesBeans = articlesBeans;
	}

//	@JsonManagedReference
	@OneToMany(mappedBy = "memberBean",
			cascade = {
					CascadeType.REMOVE
			})
	List<WholeTravelBean> wholeTravelBeans;
	
	
	public List<WholeTravelBean> getWholeTravelBeans() {
		return wholeTravelBeans;
	}

	public void setWholeTravelBeans(List<WholeTravelBean> wholeTravelBeans) {
		this.wholeTravelBeans = wholeTravelBeans;
	}
	
//	@JsonManagedReference
	@OneToMany(//mappedBy = "memberBean",
			cascade = {
					CascadeType.REMOVE
			})
	@JoinColumn(name="MBid")
	private List<AtrphotoBean> atrphotoBeans;
	
	public List<AtrphotoBean> getAtrphotoBeans() {
		return atrphotoBeans;
	}

	public void setAtrphotoBeans(List<AtrphotoBean> atrphotoBeans) {
		this.atrphotoBeans = atrphotoBeans;
	}
	
	
	@OneToMany(
			mappedBy = "memberBean",
			cascade = {
					CascadeType.REMOVE
			},
			 fetch=FetchType.EAGER)
	List<PlaceStorage> placeStorages;
	
	public List<PlaceStorage> getPlaceStorages() {
		return placeStorages;
	}

	public void setPlaceStorages(List<PlaceStorage> placeStorages) {
		this.placeStorages = placeStorages;
	}
	
	@OneToMany(
			mappedBy = "memberBean",
			cascade = {
					CascadeType.REMOVE
			})
	List<ArticleStorage> articleStorages;
	
	public List<ArticleStorage> getArticleStorages() {
		return articleStorages;
	}

	public void setArticleStorages(List<ArticleStorage> articleStorages) {
		this.articleStorages = articleStorages;
	}
	
	@OneToMany(
			mappedBy = "memberBean",
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

	//	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name="member_role", 
		joinColumns = {@JoinColumn(name="MBid", referencedColumnName = "MBid")}, 
		inverseJoinColumns = {@JoinColumn(name="roleid",referencedColumnName = "roleid")})
    private Set<RoleBean> roleBeans;
	
	public Set<RoleBean> getRoleBeans() {
		return roleBeans;
	}

	public void setRoleBeans(Set<RoleBean> roleBeans) {
		this.roleBeans = roleBeans;
	}

	@Column(name = "MBNAME")
	private String MBname;
	
	@Column(name = "MBPASSWORD")
	private String MBpassword;
	
	@Column(name = "MBEMAIL")
	private String MBemail;
	
	@Column(name = "MBGOOGLE")
	private String MBGoogle;
	
	@Column(name = "MBFB")
	private String MBFB;
	
	@Lob
	@Column(name = "MBPHOTO", columnDefinition="longblob")
	private byte[] MBPhoto;
	
	@Column(name = "DELETED",columnDefinition="bit default 0")
	private Boolean Deleted = false;

	public Integer getMBid() {
		return MBid;
	}

	public void setMBid(Integer mBid) {
		MBid = mBid;
	}

	public String getMBname() {
		return MBname;
	}

	public void setMBname(String mBname) {
		MBname = mBname;
	}

	public String getMBpassword() {
		return MBpassword;
	}

	public void setMBpassword(String mBpassword) {
		MBpassword = mBpassword;
	}

	public String getMBemail() {
		return MBemail;
	}

	public void setMBemail(String mBemail) {
		MBemail = mBemail;
	}

	public String getMBGoogle() {
		return MBGoogle;
	}

	public void setMBGoogle(String mBGoogle) {
		MBGoogle = mBGoogle;
	}

	public String getMBFB() {
		return MBFB;
	}

	public void setMBFB(String mBFB) {
		MBFB = mBFB;
	}
	
	public byte[] getMBPhoto() {
		return MBPhoto;
	}

	public void setMBPhoto(byte[] mBPhoto) {
		MBPhoto = mBPhoto;
	}

	public Boolean getDeleted() {
		return Deleted;
	}

	public void setDeleted(Boolean deleted) {
		Deleted = deleted;
	}
	
		
}
