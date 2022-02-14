package tw.org.iii.cma.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.org.iii.cma.domain.AttractionsBean;

public interface AttractionsRepository 
				extends JpaRepository<AttractionsBean,Integer>{


	AttractionsBean findByATRid(Integer atRid);

	List<AttractionsBean> findByATRnameContaining(String atRname);

	Boolean existsByATRname(String atRname);

	List<AttractionsBean> findByATRaddressContaining(String atRaddress);
	
	

}
