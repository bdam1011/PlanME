package tw.org.iii.cma.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import tw.org.iii.cma.dao.MemberRepository;
import tw.org.iii.cma.dao.WholeTravelRepository;
import tw.org.iii.cma.domain.MemberBean;
import tw.org.iii.cma.domain.WholeTravelBean;
import tw.org.iii.cma.service.MemberRepositoryService;
import tw.org.iii.cma.service.WholeTravelRepositoryService;



//Spring Boot自動將資料轉化成JSON格式
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/foodmap")
public class WholeTravelApiController {
	@Autowired
	private WholeTravelRepositoryService wholeTravelRepositoryService;
	
	@Autowired
	private MemberRepositoryService memberRepositoryService;
	
	
	@Value("${app.jwt-secret}")
    private String jwtSecret;
	
	private String JWTparser(String authorization) {
		String token = authorization.substring(7);
		Claims claims = Jwts.parser()
							.setSigningKey(jwtSecret)
							.parseClaimsJws(token)
							.getBody();
		String name = claims.getSubject();
		return name;
	}
	
	// 查詢多筆資料
		@GetMapping("/wts")
		public ResponseEntity<List<WholeTravelBean>> selectByPage(@RequestParam(defaultValue = "0")Integer page) {
			List<WholeTravelBean> selects = wholeTravelRepositoryService.selectByPage(page);
			return ResponseEntity.ok(selects);
		}
		
		//多條件查詢多筆
		@GetMapping("/wts/multi")
		public ResponseEntity<List<WholeTravelBean>> select(
				@RequestParam(required= false) String WTtitle,
				@RequestParam(required= false)Integer WTdays,
				@RequestParam(required= false)Integer WTlike,
				@RequestParam(defaultValue = "0")Integer page,
				@RequestParam(defaultValue = "WTid")String sortingItem) {
			List<WholeTravelBean> selects = wholeTravelRepositoryService
					.multiSelect(WTtitle,WTdays,WTlike,page,sortingItem);
			if(selects!=null&& !selects.isEmpty()) {
				return ResponseEntity.ok(selects);
			}
			return ResponseEntity.notFound().build();
		}
		
		// 查別人所有已發表行程
		@GetMapping("/wts/others")
		public ResponseEntity<List<WholeTravelBean>> selectOthersTrip(
				@RequestParam("MBid")Integer MBid,
				@RequestParam(defaultValue = "0")Integer page,
				@RequestParam(defaultValue = "WTid")String sortingItem){
			List<WholeTravelBean> selects = wholeTravelRepositoryService
					.selectByMBid(MBid, page, sortingItem);
			if(selects!=null&& !selects.isEmpty()) {
				return ResponseEntity.ok(selects);
			}
			return ResponseEntity.notFound().build();
		}
		
		//查自己行程
		@GetMapping("/wts/myself")
		public ResponseEntity<?> selectSelf(@RequestHeader(name = "Authorization") String authorization,
											@RequestParam(required= false)String WTTitle) {

			String name = JWTparser(authorization);
			List<MemberBean> selects = memberRepositoryService.selectSelf(name);
			if (selects != null && !selects.isEmpty()) {
				MemberBean myself = selects.get(0);
				if(WTTitle==null) {
					return ResponseEntity.ok(myself.getWholeTravelBeans());
				}
				List<WholeTravelBean> mytrip = wholeTravelRepositoryService.selectMyTripByWTtitle(myself,WTTitle);
				if(mytrip!=null) {
					return ResponseEntity.ok(mytrip);
				}	
			}
			return ResponseEntity.notFound().build();

		}

		// 查1筆資料
		@GetMapping("/wts/{WTid}")
		public ResponseEntity<?> select(@PathVariable(name ="WTid") Integer WTid) {
			WholeTravelBean bean = new WholeTravelBean();
			bean.setWTid(WTid);
			List<WholeTravelBean> selects = wholeTravelRepositoryService.select(bean);
			if(selects!=null&& !selects.isEmpty()) {
				return ResponseEntity.ok(selects.get(0));
			}
			return ResponseEntity.notFound().build();
		}

		// 插入一筆資料
		@PostMapping(path="/wts",
				     produces = {"application/json"},
				     consumes = {"application/json"})
		public ResponseEntity<?> insert(@RequestBody WholeTravelBean bean,
				  						@RequestHeader(name = "Authorization") String authorization) {
			if(bean!=null && bean.getWTid()==null) {
				String name = JWTparser(authorization);
				List<MemberBean> selects = memberRepositoryService.selectSelf(name);
				if(selects!=null) {
					bean.setMemberBean(selects.get(0));
				}
				WholeTravelBean result = wholeTravelRepositoryService.insert(bean);
				if(result!=null) {
					URI uri = URI.create("/wts/"+result.getWTid());
					return ResponseEntity.created(uri).body(result);
				}
				
			}
			return ResponseEntity.noContent().build();	

		}

		// 複製一筆資料
		@PostMapping(path = "/wts/duplicate", produces = { "application/json" })
		public ResponseEntity<?> duplicate(@RequestParam(name ="wtid") Integer wtid,
										   @RequestHeader(name = "Authorization") String authorization) {
			String name = JWTparser(authorization);
			List<MemberBean> selects = memberRepositoryService.selectSelf(name);
			
			if(selects!=null) {
				MemberBean myself = selects.get(0);
				WholeTravelBean newBean = wholeTravelRepositoryService.duplicate(wtid, myself);
				if(newBean!=null) {
					URI uri = URI.create("/wts/duplicate"+newBean.getWTid());
					return ResponseEntity.created(uri).body(newBean);
				}
			}
			return ResponseEntity.noContent().build();
		}
		

		// 軟刪除一筆資料
		@DeleteMapping("/wts/{id}")
		public ResponseEntity<?> softDelete(@PathVariable Integer id) {
			WholeTravelBean bean = new WholeTravelBean();
			bean.setWTid(id);
			// 回傳的是boolean
			boolean deleted = wholeTravelRepositoryService.softDelete(bean);
			if (deleted) {
				return ResponseEntity.noContent().build();
			} else {
				return ResponseEntity.notFound().build();
			}
		}
		
		//物理刪除
		@DeleteMapping("/wts/{id}/{title}")
		public ResponseEntity<?> delete(@PathVariable Integer id,@PathVariable String title) {
			WholeTravelBean bean = new WholeTravelBean();
			bean.setWTid(id);
			bean.setWTtitle(title);
			// 回傳的是boolean
			boolean deleted = wholeTravelRepositoryService.delete(bean);
			if (deleted) {
				return ResponseEntity.noContent().build();
			} else {
				return ResponseEntity.notFound().build();
			}
		}
		
		

		// 修改全部行程資料
		//MemberBean、DayTravelBeans、WTlike(要從memberApiController修改)、Deleted、IsPublic不用放在body bean裡
		@PutMapping("/wts/{wtid}")
		public ResponseEntity<?> update(@PathVariable("wtid") Integer wtid,
										@RequestBody WholeTravelBean bean,
										@RequestHeader(name = "Authorization") String authorization) {
			String email = JWTparser(authorization);
			bean.setWTid(wtid);
			WholeTravelBean update = wholeTravelRepositoryService.update(bean,email);
			if (update != null) {
				return ResponseEntity.ok(update);
			} else {
				return ResponseEntity.notFound().build();
			}
		}
		// 修改公開與否
		@PutMapping("/wts/publicity")
		public ResponseEntity<?>publicity(@RequestParam(name = "wtid") Integer wtid,
				@RequestParam(name = "ispublic") Boolean ispublic,
				@RequestHeader(name = "Authorization") String authorization){
			String email = JWTparser(authorization);
			WholeTravelBean publicity = wholeTravelRepositoryService.updatePublicity(wtid, ispublic, email);
			if (publicity != null) {
				return ResponseEntity.ok(publicity);
			} else {
				return ResponseEntity.notFound().build();
			}
		}
		

}
