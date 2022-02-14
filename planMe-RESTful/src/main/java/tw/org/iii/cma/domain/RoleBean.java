package tw.org.iii.cma.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="role")
public class RoleBean {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name="ROLEID")
	 private Integer roleid;

	 @Column(name="ROLENAME" ,length = 60)
	 private String rolename;
	 
//	@JsonIgnore
//	@ManyToMany(cascade=CascadeType.ALL, mappedBy="roleBeans")
//	Set<MemberBean> memberBeans;
//	
//	public Set<MemberBean> getMemberBeans() {
//		return memberBeans;
//	}
//
//	public void setMemberBeans(Set<MemberBean> memberBeans) {
//		this.memberBeans = memberBeans;
//	}

	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	@Override
	public String toString() {
		return "RoleBean [roleid=" + roleid + ", rolename=" + rolename + "]";
	}
	 
	

}
