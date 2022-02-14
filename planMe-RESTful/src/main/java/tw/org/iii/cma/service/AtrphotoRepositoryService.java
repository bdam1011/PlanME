package tw.org.iii.cma.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.org.iii.cma.dao.AtrphotoRepository;
import tw.org.iii.cma.domain.AtrphotoBean;


@Service
@Transactional
public class AtrphotoRepositoryService {
	@Autowired
	private AtrphotoRepository atrphotoRepository;
	
	public List<AtrphotoBean> select(AtrphotoBean bean) {
		List<AtrphotoBean> result = null;
		if(bean!=null && bean.getPhotoID()!=null && !bean.getPhotoID().equals(0)) {
			Optional<AtrphotoBean> optional = atrphotoRepository.findById(bean.getPhotoID());
			if(optional.isPresent()) {
				result = new ArrayList<AtrphotoBean>();
				result.add(optional.get());
			}
		} else {
			result = atrphotoRepository.findAll();
		}
		return result;
	}
	
	public AtrphotoBean insert(AtrphotoBean bean) {
		AtrphotoBean result=null;
		if(bean!=null && bean.getPhotoID()==null){
			return atrphotoRepository.save(bean);
		}	
			return result;	
	}
	
	
	public AtrphotoBean update(AtrphotoBean bean) {
		AtrphotoBean result = null;
		if(bean!=null && bean.getPhotoID()!=null) {
			AtrphotoBean select = atrphotoRepository.findByPhotoID(bean.getPhotoID());
			if(select!=null) {
				bean.setAttractionsBean(select.getAttractionsBean());
				bean.setMemberBean(select.getMemberBean());
				bean.setUploadT(new java.util.Date());
				return atrphotoRepository.save(bean);
			}
		}
		return result;
	}
	public boolean delete(AtrphotoBean bean) {
		boolean result = false;
		if(bean!=null && bean.getPhotoID()!=null) {
			boolean exist = atrphotoRepository.existsById(bean.getPhotoID());
			if(exist) {
				atrphotoRepository.deleteById(bean.getPhotoID());
				result = true;
			}
		}
		return result;
	}
	
}


