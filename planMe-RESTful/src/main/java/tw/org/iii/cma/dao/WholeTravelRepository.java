package tw.org.iii.cma.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tw.org.iii.cma.domain.MemberBean;
import tw.org.iii.cma.domain.WholeTravelBean;

public interface WholeTravelRepository 
			extends JpaRepository<WholeTravelBean, Integer> {

	boolean existsByWTidAndWTtitle(Integer wTid, String wTtitle);

	WholeTravelBean findByWTid(Integer wtid);

	Page<WholeTravelBean> findByisPublicAndWTtitleContainingAndWTdaysAndWTlikeGreaterThan(boolean b, String wTtitle,
			Integer wTdays, Integer wTlike, Pageable pageable);

	Page<WholeTravelBean> findByisPublicAndWTtitleContainingAndWTdays(boolean b, String wTtitle, Integer wTdays,
			Pageable pageable);

	Page<WholeTravelBean> findByisPublicAndWTtitleContainingAndWTlikeGreaterThan(boolean b, String wTtitle,
			Integer wTlike, Pageable pageable);

	Page<WholeTravelBean> findByisPublicAndWTtitleContaining(boolean b, String wTtitle, Pageable pageable);

	Page<WholeTravelBean> findByisPublicAndWTlikeGreaterThan(boolean b, Integer wTlike, Pageable pageable);

	Page<WholeTravelBean> findByisPublic(boolean b, Pageable pageable);

	Page<WholeTravelBean> findBymemberBeanAndIsPublic(MemberBean member, boolean b, Pageable pageable);

	List<WholeTravelBean> findBymemberBeanAndWTtitleContaining(MemberBean member, String wTTitle);

	Page<WholeTravelBean> findByisPublicAndWTdays(boolean b, Integer wTdays, Pageable pageable);

	



	



	

	


}
