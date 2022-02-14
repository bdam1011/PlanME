package tw.org.iii.cma.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.org.iii.cma.dao.AttractionsRepository;
import tw.org.iii.cma.dao.TagRepository;
import tw.org.iii.cma.dao.TagStorageRepository;
import tw.org.iii.cma.domain.AttractionsBean;
import tw.org.iii.cma.domain.TagBean;
import tw.org.iii.cma.domain.TagStorage;


@Service
@Transactional
public class TagStorageRepositoryService {
	@Autowired
	private TagStorageRepository tagStorageRepository;
	
	@Autowired
	private TagRepository tagRepository;
	
	@Autowired
	private AttractionsRepository attractionsRepository;
	
	public TagStorage insert(TagStorage bean) {
		TagStorage result = null;
		if(bean!=null&& bean.getTagStorageId()!=null) {
			Optional<TagStorage> optional = tagStorageRepository.findById(bean.getTagStorageId());
			if(!optional.isPresent()) {
				TagBean tb = tagRepository.findBytagid(bean.getTagStorageId().getTagid());
				AttractionsBean atrb = attractionsRepository.findByATRid(bean.getTagStorageId().getATRid());
				if(tb!=null && atrb!=null) {
					bean.setTagBean(tb);
					bean.setAttractionsBean(atrb);
					return tagStorageRepository.save(bean);
				}
				
			}
		}
		return result;			
	}
	
	public boolean delete(TagStorage bean) {
		boolean result = false;
		if(bean!=null && bean.getTagStorageId()!=null) {
			boolean exist = tagStorageRepository.existsById(bean.getTagStorageId());
			if(exist) {
				tagStorageRepository.deleteById(bean.getTagStorageId());
				result = true;
			}
		}
		return result;
	}
}
