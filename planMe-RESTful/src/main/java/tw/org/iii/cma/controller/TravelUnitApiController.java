package tw.org.iii.cma.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tw.org.iii.cma.dao.AttractionsRepository;
import tw.org.iii.cma.dao.DayTravelRepository;
import tw.org.iii.cma.domain.AttractionsBean;
import tw.org.iii.cma.domain.DayTravelBean;
import tw.org.iii.cma.domain.TravelUnitBean;
import tw.org.iii.cma.service.TravelUnitRepositoryService;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/foodmap")
public class TravelUnitApiController {
	@Autowired
	private TravelUnitRepositoryService travelUnitRepositoryService;
	
	@Autowired
	private DayTravelRepository dayTravelRepository;
	
	@Autowired
	private AttractionsRepository attractionsRepository;
	
	// 查詢多筆資料
		@GetMapping("/tus")
		public ResponseEntity<List<TravelUnitBean>> select() {
			List<TravelUnitBean> selects = travelUnitRepositoryService.select(null);
			return ResponseEntity.ok(selects);
		}

		// 查1筆資料
		@GetMapping("/tus/{id}")
		public ResponseEntity<?> select(@PathVariable(name ="id") Integer id) {
			TravelUnitBean bean = new TravelUnitBean();
			bean.setTUid(id);
			List<TravelUnitBean> selects = travelUnitRepositoryService.select(bean);
			if(selects!=null&& !selects.isEmpty()) {
				return ResponseEntity.ok(selects.get(0));
			}
			return ResponseEntity.notFound().build();

		}

		// 插入一筆資料
		@PostMapping(path="/tus",
				     produces = {"application/json"},
				     consumes = {"application/json"})
		public ResponseEntity<?> insert(@RequestBody TravelUnitBean bean,
										@RequestParam Integer dtid,
										@RequestParam Integer atrid) {
			if(bean!=null && bean.getTUid()==null) {
				List<DayTravelBean> temps = null;
				Optional<DayTravelBean> optional = dayTravelRepository.findById(dtid);
				AttractionsBean atttraction= attractionsRepository.findByATRid(atrid);
				
				if(optional.isPresent() && atttraction != null) {
					temps = new ArrayList<DayTravelBean>();
					temps.add(optional.get());
					bean.setDayTravelBean(temps.get(0));
					bean.setAttractionsBean(atttraction);
					bean.setTUlike(0);
				}
				TravelUnitBean result = travelUnitRepositoryService.insert(bean);
				if(result!=null) {
					URI uri = URI.create("//"+result.getTUid());
					return ResponseEntity.created(uri).body(result);
				}
				
			}
			return ResponseEntity.noContent().build();	

		}

		
		//物理刪除
		@DeleteMapping("/tus/{id}")
		public ResponseEntity<?> delete(@PathVariable Integer id) {
			TravelUnitBean bean = new TravelUnitBean();
			bean.setTUid(id);
			// 回傳的是boolean
			boolean deleted = travelUnitRepositoryService.delete(bean);
			if (deleted) {
				return ResponseEntity.noContent().build();
			} else {
				return ResponseEntity.notFound().build();
			}
		}
		
		

		// 修改單元行程(atrid可有可無，TUlike的修改在MemberApiController)
		@PutMapping("/tus")
		public ResponseEntity<?> update(@RequestParam(name ="tuid") Integer tuid,
										@RequestParam(required= false) Integer dtid,
										@RequestParam(required= false) Integer atrid,
										@RequestBody TravelUnitBean bean) {
			bean.setTUid(tuid);
			TravelUnitBean update = travelUnitRepositoryService.update(bean,dtid,atrid);
			if (update != null) {
				return ResponseEntity.ok(update);
			} else {
				return ResponseEntity.notFound().build();
			}
		}
}
