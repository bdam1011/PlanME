package tw.org.iii.cma.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.org.iii.cma.dao.MemberRepository;
import tw.org.iii.cma.dao.WholeTravelRepository;
import tw.org.iii.cma.domain.DayTravelBean;
import tw.org.iii.cma.domain.MemberBean;
import tw.org.iii.cma.domain.WholeTravelBean;


@Service
@Transactional
public class WholeTravelRepositoryService {
	@Autowired
	private WholeTravelRepository wholeTravelRepository;
	@Autowired
	private MemberRepository memberRepository;
	
	
	//分頁
//		public List<WholeTravelBean> getPagedAttractions(Integer page, Integer size) {
//
//			Page<WholeTravelBean> pageResult = wholeTravelRepository.findAll(PageRequest.of(page, // 查詢的頁數，從0起算
//					size, // 查詢的每頁筆數
//					Sort.by("ATRid").ascending())); // 依ATRid欄位升冪排序
//
//			pageResult.getNumberOfElements(); // 本頁筆數
//			pageResult.getSize(); // 每頁筆數
//			pageResult.getTotalElements(); // 全部筆數
//			pageResult.getTotalPages(); // 全部頁數
//
//			List<WholeTravelBean> wholeTravelList = pageResult.getContent();
//
//			return wholeTravelList;
//
//		}
	
	// select all or by WTid
	public List<WholeTravelBean> select(WholeTravelBean bean) {
		List<WholeTravelBean> result = null;
		if(bean!=null && bean.getWTid()!=null && !bean.getWTid().equals(0)) {
			Optional<WholeTravelBean> optional = wholeTravelRepository.findById(bean.getWTid());
			if(optional.isPresent()) {
				result = new ArrayList<WholeTravelBean>();
				result.add(optional.get());
			}
		} else {
			
			result = wholeTravelRepository.findAll();
		}
		return result;
	}
	
	public List<WholeTravelBean> selectByPage(Integer page){
		List<WholeTravelBean> result = null;
		Pageable pageable = PageRequest.of(page, 10, Sort.by("WTid").ascending());
		Page<WholeTravelBean> pageResult = null;
		pageResult = wholeTravelRepository.findByisPublic(true,pageable);
		if(pageResult!=null && !pageResult.isEmpty()) {
			result = pageResult.getContent();
		}
		return result;
	
	}
	
	//select by WTtitle, WTlike, WTdays at page Sort by sortingItem
	public List<WholeTravelBean>multiSelect(String WTtitle,Integer WTdays,Integer WTlike,Integer page,String sortingItem){
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sortingItem).ascending());
		Page<WholeTravelBean> pageResult = null;
		if(WTtitle!=null) {
			if(WTdays!=null && WTlike!=null) {
				pageResult = wholeTravelRepository
						.findByisPublicAndWTtitleContainingAndWTdaysAndWTlikeGreaterThan(true,WTtitle,WTdays,WTlike,pageable);
				return pageResult.getContent();
			}else if(WTdays!=null) {
				pageResult = wholeTravelRepository
						.findByisPublicAndWTtitleContainingAndWTdays(true,WTtitle,WTdays,pageable);
				return pageResult.getContent();
			}else if(WTlike!=null) {
				pageResult = wholeTravelRepository
						.findByisPublicAndWTtitleContainingAndWTlikeGreaterThan(true,WTtitle,WTlike,pageable);
				return pageResult.getContent();
			}else {
				pageResult = wholeTravelRepository
						.findByisPublicAndWTtitleContaining(true,WTtitle,pageable);
				return pageResult.getContent();
			}
		}else if(WTlike!=null) {
			pageResult = wholeTravelRepository
					.findByisPublicAndWTlikeGreaterThan(true,WTlike,pageable);
			return pageResult.getContent();
			
		}else if(WTdays!=null) {
			pageResult = wholeTravelRepository
					.findByisPublicAndWTdays(true,WTdays,pageable);
			return pageResult.getContent();
		}
		return null;
		
	}
	
	//select by MBid at page Sort by sortingItem
	public List<WholeTravelBean> selectByMBid(Integer MBid,Integer page,String sortingItem){
		List<WholeTravelBean> result = null;
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sortingItem).ascending());
		Page<WholeTravelBean> pageResult = null;
		MemberBean member = memberRepository.findByMBid(MBid);
		pageResult = wholeTravelRepository.findBymemberBeanAndIsPublic(member,true,pageable);
		if(pageResult!=null && !pageResult.isEmpty()) {
			result = pageResult.getContent();
		}
		return result;
	
	}
	
	public List<WholeTravelBean> selectMyTripByWTtitle(MemberBean member,String WTTitle){
		List<WholeTravelBean> result = null;
		
		List<WholeTravelBean> WTmyself = wholeTravelRepository.findBymemberBeanAndWTtitleContaining(member,WTTitle);
		if(WTmyself!=null && !WTmyself.isEmpty()) {
			result = WTmyself;
		}
		return result;
	
	}
	
	
	
	public WholeTravelBean insert(WholeTravelBean bean) {
		WholeTravelBean result = null;
		if(bean!=null && bean.getWTid()==null) {
			return wholeTravelRepository.save(bean);
		}
		return result;	
	}
	
	public WholeTravelBean duplicate(Integer wtid,MemberBean myself) {
		WholeTravelBean oldBean = wholeTravelRepository.findByWTid(wtid);
		if(oldBean!=null&&myself!=null) {
			WholeTravelBean newBean = new WholeTravelBean();
			List<DayTravelBean> dayTravelBeans = oldBean.getDayTravelBeans();
			List<DayTravelBean> clone= new ArrayList<DayTravelBean>(dayTravelBeans.size());
			dayTravelBeans.forEach(dayTravelBean -> clone.add(dayTravelBean));
			newBean.setDayTravelBeans(clone);
			newBean.setIsPublic(false);
			newBean.setMemberBean(myself);
			newBean.setWTdays(oldBean.getWTdays());
			newBean.setWTintroduce(oldBean.getWTintroduce());
			newBean.setWTPhoto(oldBean.getWTPhoto());
			newBean.setWTStartT(oldBean.getWTStartT());
			newBean.setWTtitle("[Duplicate] "+oldBean.getWTtitle());
			return wholeTravelRepository.save(newBean);
		}
		return null;
		
	}
	
	
	public WholeTravelBean update(WholeTravelBean bean, String email) {
		WholeTravelBean result = null;	
		if(bean!=null && bean.getWTid()!=null) {
			WholeTravelBean select =  wholeTravelRepository.findByWTid(bean.getWTid());	
			if(select!=null && select.getMemberBean().getMBemail().equals(email)) {
				bean.setMemberBean(select.getMemberBean());
				bean.setDayTravelBeans(select.getDayTravelBeans());
				bean.setWTlike(select.getWTlike());
				bean.setDeleted(select.getDeleted());
				bean.setIsPublic(select.getIsPublic());
				return wholeTravelRepository.save(bean);
			}
		}
		return result;
	}
	
	public WholeTravelBean updatePublicity(Integer wtid ,Boolean ispublic,String email) {
		WholeTravelBean result = null;	
		if(wtid!=null) {
			WholeTravelBean select =  wholeTravelRepository.findByWTid(wtid);	
			if(select!=null && select.getMemberBean().getMBemail().equals(email)) {
				select.setIsPublic(ispublic);
				return wholeTravelRepository.save(select);
			}
		}
		return result;
	}
	
	//soft deleted
	public boolean softDelete(WholeTravelBean bean) {
		boolean  result = false;
		if(bean!=null && bean.getWTid()!=null) {
			boolean exist = wholeTravelRepository.existsById(bean.getWTid());
			if(exist) {
				Optional<WholeTravelBean> optional = wholeTravelRepository.findById(bean.getWTid());
				optional.get().setDeleted(true);
				wholeTravelRepository.save(optional.get());
				result = true;
			}
		}
		return result;
	}
	
	//hard delete
	public boolean delete(WholeTravelBean bean) {
		boolean result = false;
		if(bean!=null && bean.getWTid()!=null && bean.getWTtitle()!=null) {
			boolean exist = wholeTravelRepository.existsByWTidAndWTtitle(bean.getWTid(),bean.getWTtitle());
			if(exist) {
				wholeTravelRepository.deleteById(bean.getWTid());
				result = true;
			}
		}
		return result;
	}
}
