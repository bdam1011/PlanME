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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "ARTICLE")
@Where(clause = "deleted= false")
public class ArticleBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ATCid")
	private Integer ATCid;
	
//	@JsonManagedReference(value="article-message")
	@OneToMany(//mappedBy = "articleBean",
			fetch = FetchType.LAZY,
			cascade = {
					
					CascadeType.REMOVE
			})
	@JoinColumn(name="ATCid")
	private Set<MessageBean> messageBeans;
	
	public Set<MessageBean> getMessageBeans() {
		return messageBeans;
	}

	public void setMessageBeans(Set<MessageBean> messageBeans) {
		this.messageBeans = messageBeans;
	}
	

	@JsonBackReference
//	@JsonIgnore
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
	
	@JsonIgnore
	@OneToMany(mappedBy = "articleBean",
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

	@Column(name = "ATCTITLE")
	private String ATCtitle;
	
	@Lob 
	@Column(name = "ATCTEXT", length=2048)
	private String ATCtext;
	
	@Lob
	@Column(name = "ATCPHOTO",columnDefinition="longblob")
	private byte[] ATCPhoto;
	
	@Column(name = "ATCLIKE	",columnDefinition="Integer default 0")
	private Integer ATClike = 0;
	
	@Column(name = "ATCUPLOADT")
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date ATCUploadT;
	
	@Column(name = "ATCREVISET")
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date ATCReviseT;
		
	@Column(name = "ISREPORTED",columnDefinition="bit default 0")
	private Boolean isReported = false;
	
	@Column(name = "DELETED",columnDefinition="bit default 0")
	private Boolean Deleted = false;

	public Integer getATCid() {
		return ATCid;
	}

	public void setATCid(Integer aTCid) {
		ATCid = aTCid;
	}

	public String getATCtitle() {
		return ATCtitle;
	}

	public void setATCtitle(String aTCtitle) {
		ATCtitle = aTCtitle;
	}

	public String getATCtext() {
		return ATCtext;
	}

	public void setATCtext(String aTCtext) {
		ATCtext = aTCtext;
	}

	public Integer getATClike() {
		return ATClike;
	}

	public void setATClike(Integer aTClike) {
		ATClike = aTClike;
	}

	public java.util.Date getATCUploadT() {
		return ATCUploadT;
	}

	public void setATCUploadT(java.util.Date aTCUploadT) {
		ATCUploadT = aTCUploadT;
	}


	public java.util.Date getATCReviseT() {
		return ATCReviseT;
	}

	public void setATCReviseT(java.util.Date aTCReviseT) {
		ATCReviseT = aTCReviseT;
	}

	public byte[] getATCPhoto() {
		return ATCPhoto;
	}

	public void setATCPhoto(byte[] aTCPhoto) {
		ATCPhoto = aTCPhoto;
	}

	public Boolean getIsReported() {
		return isReported;
	}

	public void setIsReported(Boolean isReported) {
		this.isReported = isReported;
	}

	public Boolean getDeleted() {
		return Deleted;
	}

	public void setDeleted(Boolean deleted) {
		Deleted = deleted;
	}

	@Override
	public String toString() {
		return "ArticleBean [ATCid=" + ATCid + ", ATCtitle=" + ATCtitle + ", ATCtext=" + ATCtext
				+ ", ATClike=" + ATClike + ", ATCUploadT=" + ATCUploadT + ", ATCReviseT=" + ATCReviseT + ", isReported="
				+ isReported + ", Deleted=" + Deleted + "]";
	}
	
}
