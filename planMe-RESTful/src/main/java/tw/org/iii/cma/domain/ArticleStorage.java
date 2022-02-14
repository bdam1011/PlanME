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
@Table(name = "articlestorage")
public class ArticleStorage implements Serializable{
	
	@EmbeddedId
	private ArticleStorageId articleStorageId;
	
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
		name= "ATCid",
		referencedColumnName = "ATCid"		
	)
	@MapsId("ATCid")
	private ArticleBean articleBean;
	
	public ArticleStorageId getArticleStorageId() {
		return articleStorageId;
	}

	public void setArticleStorageId(ArticleStorageId articleStorageId) {
		this.articleStorageId = articleStorageId;
	}

	public ArticleBean getArticleBean() {
		return articleBean;
	}

	public void setArticleBean(ArticleBean articleBean) {
		this.articleBean = articleBean;
	}

	public java.util.Date getStoragetime() {
		return storagetime;
	}

	public void setStoragetime(java.util.Date storagetime) {
		this.storagetime = storagetime;
	}
	
	
}
