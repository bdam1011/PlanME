package tw.org.iii.cma.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.org.iii.cma.domain.TravelUnitBean;

public interface TravelUnitRepository 
		extends JpaRepository<TravelUnitBean,Integer>{

	TravelUnitBean findByTUid(Integer tUid);

}
