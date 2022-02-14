package tw.org.iii.cma.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ArticleStorageId implements Serializable{
	
	@Column(name="MBid")
	private Integer MBid;
	
	@Column(name="ATCid")
	private Integer ATCid;
	
	public ArticleStorageId(){
		
	}
	
	public ArticleStorageId(Integer MBid, Integer ATCid){
		this.MBid = MBid;
		this.ATCid = ATCid;
	}

	public Integer getMBid() {
		return MBid;
	}

	public void setMBid(Integer mBid) {
		MBid = mBid;
	}

	public Integer getATCid() {
		return ATCid;
	}

	public void setATCid(Integer aTCid) {
		ATCid = aTCid;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ATCid, MBid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArticleStorageId other = (ArticleStorageId) obj;
		return Objects.equals(ATCid, other.ATCid) && Objects.equals(MBid, other.MBid);
	}
	
	

}
