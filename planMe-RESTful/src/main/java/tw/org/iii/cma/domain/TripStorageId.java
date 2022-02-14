package tw.org.iii.cma.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TripStorageId implements Serializable{
	
	@Column(name="MBid")
	private Integer MBid;
	
	@Column(name="WTid")
	private Integer WTid;
	
	public TripStorageId(){
		
	}
	
	public TripStorageId(Integer MBid, Integer WTid){
		this.MBid = MBid;
		this.WTid = WTid;
	}

	public Integer getMBid() {
		return MBid;
	}

	public void setMBid(Integer mBid) {
		MBid = mBid;
	}

	public Integer getWTid() {
		return WTid;
	}

	public void setWTid(Integer wTid) {
		WTid = wTid;
	}

	@Override
	public int hashCode() {
		return Objects.hash(MBid, WTid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TripStorageId other = (TripStorageId) obj;
		return Objects.equals(MBid, other.MBid) && Objects.equals(WTid, other.WTid);
	}

	
	

}
