package tw.org.iii.cma.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PlaceStorageId implements Serializable{
	
	@Column(name="MBid")
	private Integer MBid;
	
	@Column(name="ATRid")
	private Integer ATRid;
	
	public PlaceStorageId(){
		
	}
	
	public PlaceStorageId(Integer MBid, Integer ATRid){
		this.MBid = MBid;
		this.ATRid = ATRid;
	}

	public Integer getMBid() {
		return MBid;
	}

	public void setMBid(Integer mBid) {
		MBid = mBid;
	}

	public Integer getATRid() {
		return ATRid;
	}

	public void setATRid(Integer aTRid) {
		ATRid = aTRid;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ATRid, MBid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlaceStorageId other = (PlaceStorageId) obj;
		return Objects.equals(ATRid, other.ATRid) && Objects.equals(MBid, other.MBid);
	}
	
	

}
