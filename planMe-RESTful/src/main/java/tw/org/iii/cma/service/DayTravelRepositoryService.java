package tw.org.iii.cma.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.org.iii.cma.dao.DayTravelRepository;
import tw.org.iii.cma.dao.WholeTravelRepository;
import tw.org.iii.cma.domain.DayTravelBean;
import tw.org.iii.cma.domain.WholeTravelBean;


@Service
@Transactional
public class DayTravelRepositoryService {
	@Autowired
	private DayTravelRepository dayTravelRepository;
	
	@Autowired
	private WholeTravelRepository wholeTravelRepository;
	
	public List<DayTravelBean> select(DayTravelBean bean) {
		List<DayTravelBean> result = null;
		if(bean!=null && bean.getDTid()!=null && !bean.getDTid().equals(0)) {
			Optional<DayTravelBean> optional = dayTravelRepository.findById(bean.getDTid());
			if(optional.isPresent()) {
				result = new ArrayList<DayTravelBean>();
				result.add(optional.get());
			}
		} else {
			result = dayTravelRepository.findAll();
		}
		return result;
	}
	
	public DayTravelBean insert(DayTravelBean bean) {
		DayTravelBean result = null;
		if(bean!=null && bean.getDTid()==null) {
			return dayTravelRepository.save(bean);
		}
		return result;	
	}
	
	
	public DayTravelBean update(DayTravelBean bean ,Integer wtid) {
		DayTravelBean result = null;
		if(bean!=null && bean.getDTid()!=null) {
			DayTravelBean select = dayTravelRepository.findByDTid(bean.getDTid());
			if(select!=null) {
				bean.setTravelUnitBeans(select.getTravelUnitBeans());
				WholeTravelBean wholeTravel= wholeTravelRepository.findByWTid(wtid);
				if(wholeTravel!=null) {
					bean.setWholeTravelBean(wholeTravel);
				}else {
					bean.setWholeTravelBean(select.getWholeTravelBean());
				}
				return dayTravelRepository.save(bean);
			}
		}
		return result;
	}
	
	//hard delete
	public boolean delete(DayTravelBean bean) {
		boolean result = false;
		if(bean!=null && bean.getDTid()!=null && bean.getDTtitle()!=null) {
			boolean exist = dayTravelRepository.existsByDTidAndDTtitle(bean.getDTid(),bean.getDTtitle());
			if(exist) {
				dayTravelRepository.deleteById(bean.getDTid());
				result = true;
			}
		}
		return result;
	}
}
