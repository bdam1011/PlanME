package tw.org.iii.cma.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.org.iii.cma.dao.AttractionsRepository;
import tw.org.iii.cma.dao.DayTravelRepository;
import tw.org.iii.cma.dao.TravelUnitRepository;
import tw.org.iii.cma.domain.AttractionsBean;
import tw.org.iii.cma.domain.DayTravelBean;
import tw.org.iii.cma.domain.TravelUnitBean;




@Service
@Transactional
public class TravelUnitRepositoryService {
	@Autowired
	private TravelUnitRepository travelUnitRepository;
	@Autowired
	private AttractionsRepository attractionsRepository;
	
	@Autowired
	private DayTravelRepository dayTravelRepository;
	
	public List<TravelUnitBean> select(TravelUnitBean bean) {
		List<TravelUnitBean> result = null;
		if(bean!=null && bean.getTUid()!=null && !bean.getTUid().equals(0)) {
			Optional<TravelUnitBean> optional = travelUnitRepository.findById(bean.getTUid());
			if(optional.isPresent()) {
				result = new ArrayList<TravelUnitBean>();
				result.add(optional.get());
			}
		} else {
			result = travelUnitRepository.findAll();
		}
		return result;
	}
	
	public TravelUnitBean insert(TravelUnitBean bean) {
		TravelUnitBean result = null;
		if(bean!=null && bean.getTUid()==null) {
			return travelUnitRepository.save(bean);
		}
		return result;	
	}
	
	
	public TravelUnitBean update(TravelUnitBean bean,Integer dtid, Integer atrid) {
		TravelUnitBean result = null;
		if(bean!=null && bean.getTUid()!=null) {
			TravelUnitBean select = travelUnitRepository.findByTUid(bean.getTUid());
			if(select!=null) {
				DayTravelBean dayTravel =dayTravelRepository.findByDTid(dtid);
				if(dayTravel!=null) {
					bean.setDayTravelBean(dayTravel);
				}else {
					bean.setDayTravelBean(select.getDayTravelBean());
				}
				AttractionsBean atttraction = attractionsRepository.findByATRid(atrid);
				if(atttraction!=null) {
					bean.setAttractionsBean(atttraction);
				}else {
					bean.setAttractionsBean(select.getAttractionsBean());
				}
				bean.setTUlike(select.getTUlike());
				return travelUnitRepository.save(bean);
			}
		}
		return result;
	}
	
	//hard delete
	public boolean delete(TravelUnitBean bean) {
		boolean result = false;
		if(bean!=null && bean.getTUid()!=null) {
			boolean exist = travelUnitRepository.existsById(bean.getTUid());
			if(exist) {
				travelUnitRepository.deleteById(bean.getTUid());
				result = true;
			}
		}
		return result;
	}
}
