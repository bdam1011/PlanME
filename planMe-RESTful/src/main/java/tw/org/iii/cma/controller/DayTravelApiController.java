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

import tw.org.iii.cma.dao.WholeTravelRepository;
import tw.org.iii.cma.domain.DayTravelBean;
import tw.org.iii.cma.domain.WholeTravelBean;
import tw.org.iii.cma.service.DayTravelRepositoryService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/foodmap")
public class DayTravelApiController {
	@Autowired
	private DayTravelRepositoryService dayTravelRepositoryService;
	
	@Autowired
	private WholeTravelRepository wholeTravelRepository;
	
	// 查詢多筆資料
		@GetMapping("/dts")
		public ResponseEntity<List<DayTravelBean>> select() {
			List<DayTravelBean> selects = dayTravelRepositoryService.select(null);
			return ResponseEntity.ok(selects);
		}

		// 查1筆資料
		@GetMapping("/dts/{id}")
		public ResponseEntity<?> select(@PathVariable(name ="id") Integer id) {
			DayTravelBean bean = new DayTravelBean();
			bean.setDTid(id);
			List<DayTravelBean> selects = dayTravelRepositoryService.select(bean);
			if(selects!=null&& !selects.isEmpty()) {
				return ResponseEntity.ok(selects.get(0));
			}
			return ResponseEntity.notFound().build();

		}

		// 插入一筆資料
		@PostMapping(path="/dts/wtid/{id}",
				     produces = {"application/json"},
				     consumes = {"application/json"})
		public ResponseEntity<?> insert(@RequestBody DayTravelBean bean,//dtbegin格式 ex:2020-01-01
										@PathVariable Integer id) {
			if(bean!=null && bean.getDTid()==null) {
				List<WholeTravelBean> temps = null;
				Optional<WholeTravelBean> optional = wholeTravelRepository.findById(id);
				if(optional.isPresent()) {
					temps = new ArrayList<WholeTravelBean>();
					temps.add(optional.get());
					bean.setWholeTravelBean(temps.get(0));
				}
				DayTravelBean result = dayTravelRepositoryService.insert(bean);
				if(result!=null) {
					URI uri = URI.create("/dts/"+result.getDTid());
					return ResponseEntity.created(uri).body(result);
				}
				
			}
			return ResponseEntity.noContent().build();	

		}

		
		//物理刪除
		@DeleteMapping("/dts/{id}/{title}")
		public ResponseEntity<?> delete(@PathVariable Integer id,@PathVariable String title) {
			DayTravelBean bean = new DayTravelBean();
			bean.setDTid(id);
			bean.setDTtitle(title);
			// 回傳的是boolean
			boolean deleted = dayTravelRepositoryService.delete(bean);
			if (deleted) {
				return ResponseEntity.noContent().build();
			} else {
				return ResponseEntity.notFound().build();
			}
		}
		
		

		// 修改(其實是覆蓋)
		@PutMapping("/dts/{dtid}")
		public ResponseEntity<?> update(@PathVariable("dtid") Integer dtid,
										@RequestParam(required= false) Integer wtid,
										@RequestBody DayTravelBean bean) {
			bean.setDTid(dtid);
			DayTravelBean update = dayTravelRepositoryService.update(bean,wtid);
			if (update != null) {
				return ResponseEntity.ok(update);
			} else {
				return ResponseEntity.notFound().build();
			}
		}
}
