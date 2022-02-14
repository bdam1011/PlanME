package tw.org.iii.cma.domain;

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

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.voodoodyne.jackson.jsog.JSOGGenerator;


@Entity
@Table(name = "Message")
@Where(clause = "deleted= false")
public class MessageBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MSGID")
	private Integer MSGid;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(
		name= "ATCid",
		referencedColumnName = "ATCid"		
	)
	private ArticleBean articleBean;
	
	public ArticleBean getArticleBean() {
		return articleBean;
	}

	public void setArticleBean(ArticleBean articleBean) {
		this.articleBean = articleBean;
	}

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

	@Lob 
	@Column(name = "MSTEXT", length=2048)
	private String MStext;
	
	@Column(name = "MSLIKE")
	private Integer MSlike=0;
	
	@Column(name = "MST")
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date MST = new java.util.Date();
	
	@Column(name = "ISREPORTED",columnDefinition="bit default 0")
	private Boolean isReported = false;
	
	@Column(name = "DELETED",columnDefinition="bit default 0")
	private Boolean Deleted = false;

	public Integer getMSGid() {
		return MSGid;
	}

	public void setMSGid(Integer mSGid) {
		MSGid = mSGid;
	}
	

	public String getMStext() {
		return MStext;
	}

	public void setMStext(String mStext) {
		MStext = mStext;
	}

	public Integer getMSlike() {
		return MSlike;
	}

	public void setMSlike(Integer mSlike) {
		MSlike = mSlike;
	}

	public java.util.Date getMST() {
		return MST;
	}

	public void setMST(java.util.Date mST) {
		MST = mST;
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
		return "MessageBean [MSGid=" + MSGid + ", articleBean=" + articleBean + ", memberBean=" + memberBean
				+ ", MStext=" + MStext + ", MSlike=" + MSlike + ", MST=" + MST + ", isReported=" + isReported
				+ ", Deleted=" + Deleted + "]";
	}
	
}
