package tw.org.iii.cma.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.org.iii.cma.dao.AttractionsRepository;
import tw.org.iii.cma.dao.MemberRepository;
import tw.org.iii.cma.dao.PlaceStorageRepository;
import tw.org.iii.cma.domain.AttractionsBean;
import tw.org.iii.cma.domain.MemberBean;
import tw.org.iii.cma.domain.PlaceStorage;

@Service
@Transactional
public class PlaceStorageRepositoryService {
	@Autowired
	private PlaceStorageRepository placeStorageRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private AttractionsRepository attractionsRepository;
	
	public PlaceStorage insert(PlaceStorage bean) {
		PlaceStorage result = null;
		if(bean!=null&& bean.getPlaceStorageId()!=null) {
			Optional<PlaceStorage> optional = placeStorageRepository.findById(bean.getPlaceStorageId());
			if(!optional.isPresent()) {
				MemberBean mb = memberRepository.findByMBid(bean.getPlaceStorageId().getMBid());
				AttractionsBean atrb = attractionsRepository.findByATRid(bean.getPlaceStorageId().getATRid());
				if(mb!=null && atrb!=null) {
					atrb.setATRlike(atrb.getATRlike()+1);
					bean.setMemberBean(mb);
					bean.setAttractionsBean(atrb);
					return placeStorageRepository.save(bean);
				}
				
			}
		}
		return result;			
	}
	
	public boolean delete(PlaceStorage bean) {
		boolean result = false;
		if(bean!=null && bean.getPlaceStorageId()!=null) {
			boolean exist = placeStorageRepository.existsById(bean.getPlaceStorageId());
			if(exist) {
				AttractionsBean atrb = attractionsRepository.findByATRid(bean.getPlaceStorageId().getATRid());
				atrb.setATRlike(atrb.getATRlike()-1);
				placeStorageRepository.deleteById(bean.getPlaceStorageId());
				result = true;
			}
		}
		return result;
	}
}
