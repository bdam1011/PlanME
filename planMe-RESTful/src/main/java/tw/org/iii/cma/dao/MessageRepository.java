package tw.org.iii.cma.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tw.org.iii.cma.domain.MessageBean;

public interface MessageRepository 
			extends JpaRepository<MessageBean, Integer> {

	Page<MessageBean> findByisReported(boolean b, Pageable pageable);

	MessageBean findByMSGid(Integer msGid);

//	boolean existsByMSGidAndMBid(Integer msGid, Integer mBid);

}
