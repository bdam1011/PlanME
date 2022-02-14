package tw.org.iii.cma.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.org.iii.cma.domain.MemberBean;

public interface MemberRepository extends JpaRepository<MemberBean, Integer> {
	boolean existsByMBidAndMBname(Integer mBid, String mBname);

	Optional<MemberBean> findByMBemail(String MBemail);

	Optional<MemberBean> findByMBnameOrMBemail(String MBname, String MBemail);

	Optional<MemberBean> findByMBname(String MBname);

	Boolean existsByMBname(String MBname);

	Boolean existsByMBemail(String MBemail);

	MemberBean findByMBid(Integer mbid);

	List<MemberBean> findByMBnameContaining(String name);

}
