package tw.org.iii.cma.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.org.iii.cma.domain.AtrphotoBean;


public interface AtrphotoRepository 
				extends JpaRepository<AtrphotoBean,Integer>{

	AtrphotoBean findByPhotoID(Integer photoID);

//	List<AtrphotoBean> findByATRid(Integer atRid);

}