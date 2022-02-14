package tw.org.iii.cma.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TagStorageId implements Serializable{
	
	@Column(name="tagid")
	private Integer tagid;
	
	@Column(name="ATRid")
	private Integer ATRid;
	
	public TagStorageId(){
		
	}
	
	public TagStorageId(Integer tagid, Integer ATRid){
		this.tagid = tagid;
		this.ATRid = ATRid;
	}

	public Integer getTagid() {
		return tagid;
	}

	public void setTagid(Integer tagid) {
		this.tagid = tagid;
	}

	public Integer getATRid() {
		return ATRid;
	}

	public void setATRid(Integer aTRid) {
		ATRid = aTRid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ATRid == null) ? 0 : ATRid.hashCode());
		result = prime * result + ((tagid == null) ? 0 : tagid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TagStorageId other = (TagStorageId) obj;
		if (ATRid == null) {
			if (other.ATRid != null)
				return false;
		} else if (!ATRid.equals(other.ATRid))
			return false;
		if (tagid == null) {
			if (other.tagid != null)
				return false;
		} else if (!tagid.equals(other.tagid))
			return false;
		return true;
	}

	
	
	

}
