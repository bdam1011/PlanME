package tw.org.iii.cma.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.org.iii.cma.dao.TagRepository;
import tw.org.iii.cma.domain.AttractionsBean;
import tw.org.iii.cma.domain.TagBean;


@Service
@Transactional
public class TagRepositoryService {
	@Autowired
	private TagRepository tagRepository;
	
	public List<TagBean> select(TagBean bean) {
		List<TagBean> result = null;
		if(bean!=null && bean.getTagid()!=null && !bean.getTagid().equals(0)) {
			Optional<TagBean> optional = tagRepository.findById(bean.getTagid());
			if(optional.isPresent()) {
				result = new ArrayList<TagBean>();
				result.add(optional.get());
			}
		} else {
			result = tagRepository.findAll();
		}
		return result;
	}
	
	// select tags
	public List<TagBean> selectTagName(TagBean bean) {
		List<TagBean> result = null;
		if (bean != null && bean.getTagname() != null) {
			// result = attractionsRepository.findByTagContaining(bean.getTag());
			result = tagRepository.findByTagnameContaining(bean.getTagname());
		}
		return result;
	}
	
	
	public TagBean insert(TagBean bean) {
		TagBean result = null;	
		if(bean!=null && bean.getTagid()==null) {
			return tagRepository.save(bean);
		}
		return result;	
	}
	
	
	public TagBean update(TagBean bean) {
		TagBean result = null;
		if(bean!=null && bean.getTagid()!=null) {
			boolean exist = tagRepository.existsById(bean.getTagid());
			if(exist) {
				return tagRepository.save(bean);
			}
		}
		return result;
	}
	
	
	//hard delete
	public boolean delete(TagBean bean) {
		boolean result = false;
		if(bean!=null && bean.getTagid()!=null) {
			boolean exist = tagRepository.existsById(bean.getTagid());
			if(exist) {
				tagRepository.deleteById(bean.getTagid());
				result = true;
			}
		}
		return result;
	}
}
