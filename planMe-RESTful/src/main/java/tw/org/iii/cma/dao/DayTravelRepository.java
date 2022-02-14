package tw.org.iii.cma.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.org.iii.cma.domain.DayTravelBean;

public interface DayTravelRepository 
			extends JpaRepository<DayTravelBean, Integer> {

	boolean existsByDTidAndDTtitle(Integer dTid, String dTtitle);

	DayTravelBean findByDTid(Integer dTid);

}
