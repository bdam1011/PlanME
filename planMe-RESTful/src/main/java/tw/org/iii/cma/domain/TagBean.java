package tw.org.iii.cma.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tag")
public class TagBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tagid")
	private Integer tagid;
	
	@Column(name = "tagname")
	private String tagname;
	
//	@JsonIgnore
	@OneToMany(mappedBy = "tagBean",
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

	public Integer getTagid() {
		return tagid;
	}

	public void setTagid(Integer tagid) {
		this.tagid = tagid;
	}

	public String getTagname() {
		return tagname;
	}

	public void setTagname(String tagname) {
		this.tagname = tagname;
	}

	@Override
	public String toString() {
		return "TagBean [tagid=" + tagid + ", tagname=" + tagname + "]";
	}
	
	
	
	

}
