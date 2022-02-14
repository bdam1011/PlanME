package tw.org.iii.cma.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.org.iii.cma.dao.MemberRepository;
import tw.org.iii.cma.dao.TripStorageRepository;
import tw.org.iii.cma.dao.WholeTravelRepository;
import tw.org.iii.cma.domain.MemberBean;
import tw.org.iii.cma.domain.TripStorage;
import tw.org.iii.cma.domain.WholeTravelBean;

@Service
@Transactional
public class TripStorageRepositoryService {
	@Autowired
	private TripStorageRepository tripStorageRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private WholeTravelRepository wholeTravelRepository;
	
	public TripStorage insert(TripStorage bean) {
		TripStorage result = null;
		if(bean!=null&& bean.getTripStorageId()!=null) {
			Optional<TripStorage> optional = tripStorageRepository.findById(bean.getTripStorageId());
			if(!optional.isPresent()) {
				MemberBean mb = memberRepository.findByMBid(bean.getTripStorageId().getMBid());
				WholeTravelBean wtb = wholeTravelRepository.findByWTid(bean.getTripStorageId().getWTid());
				if(mb!=null && wtb!=null) {
					wtb.setWTlike(wtb.getWTlike()+1);
					bean.setMemberBean(mb);
					bean.setWholeTravelBean(wtb);
					return tripStorageRepository.save(bean);
				}
				
			}
		}
		return result;			
	}
	
	public boolean delete(TripStorage bean) {
		boolean result = false;
		if(bean!=null && bean.getTripStorageId()!=null) {
			boolean exist = tripStorageRepository.existsById(bean.getTripStorageId());
			if(exist) {
				WholeTravelBean wtb = wholeTravelRepository.findByWTid(bean.getTripStorageId().getWTid());
				wtb.setWTlike(wtb.getWTlike()-1);
				tripStorageRepository.deleteById(bean.getTripStorageId());
				result = true;
			}
		}
		return result;
	}
}
