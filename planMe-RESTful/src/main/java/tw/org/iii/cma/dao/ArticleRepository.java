package tw.org.iii.cma.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tw.org.iii.cma.domain.ArticleBean;
import tw.org.iii.cma.domain.MemberBean;

public interface ArticleRepository 
			extends JpaRepository<ArticleBean, Integer> {

	boolean existsByATCidAndATCtitle(Integer atCid, String atCtitle);

	ArticleBean findByATCid(Integer atcid);

	List<ArticleBean> findBymemberBean(MemberBean memberBean);

	Page<ArticleBean> findByisReported(boolean b, Pageable pageable);



	

}
