package tw.org.iii.cma.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.org.iii.cma.domain.TagBean;

public interface TagRepository 
		extends JpaRepository<TagBean, Integer> {

	TagBean findBytagid(Integer tagid);

	List<TagBean> findByTagnameContaining(String tagname);

}
