package tw.org.iii.cma.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
import tw.org.iii.cma.domain.ArticleStorage;
import tw.org.iii.cma.domain.ArticleStorageId;
import tw.org.iii.cma.domain.MemberBean;
import tw.org.iii.cma.domain.PlaceStorage;
import tw.org.iii.cma.domain.PlaceStorageId;
import tw.org.iii.cma.domain.TripStorage;
import tw.org.iii.cma.domain.TripStorageId;
import tw.org.iii.cma.service.ArticleStorageRepositoryService;
import tw.org.iii.cma.service.MemberRepositoryService;
import tw.org.iii.cma.service.PlaceStorageRepositoryService;
import tw.org.iii.cma.service.TripStorageRepositoryService;

//Spring Boot自動將資料轉化成JSON格式
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/foodmap")
public class MemberApiController {
	@Autowired
	private MemberRepositoryService memberRepositoryService;
	
	@Autowired
	private PlaceStorageRepositoryService placeStorageRepositoryService;
	
	@Autowired
	private ArticleStorageRepositoryService articleStorageRepositoryService;
	
	@Autowired
	private TripStorageRepositoryService tripStorageRepositoryService;
	
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
	@GetMapping("/members")
	public ResponseEntity<List<MemberBean>> select() {
		List<MemberBean> selects = memberRepositoryService.select(null);
		return ResponseEntity.ok(selects);
	}
		
	// 由頁數查詢多筆資料
	@GetMapping("/members/page")
	public ResponseEntity<List<MemberBean>> selectPage(@RequestParam(defaultValue = "0") Integer page) {
		List<MemberBean> selects = memberRepositoryService.selectPage(page);
		
		return ResponseEntity.ok(selects);
	}

	// 由名字模糊搜尋
	@GetMapping("/members/name")
	public ResponseEntity<?> select(@RequestParam String name) {
		List<MemberBean> selects = memberRepositoryService.selectName(name);
		if (selects != null && !selects.isEmpty()) {
			return ResponseEntity.ok(selects);
		}
		return ResponseEntity.notFound().build();

	}

	// 查自己資料
	@GetMapping("/myself")
//		@PreAuthorize("MBname.equals(#MBname)")
	public ResponseEntity<?> selectSelf(@RequestHeader(name = "Authorization") String authorization) {
		
		String name = JWTparser(authorization);
		List<MemberBean> selects = memberRepositoryService.selectSelf(name);
		if (selects != null && !selects.isEmpty()) {
			return ResponseEntity.ok(selects.get(0));
		}
		return ResponseEntity.notFound().build();

	}
	
	// USER <-> ADMIN
	@PutMapping("/members/newadmin")
	public ResponseEntity<?> newAdmin(@RequestParam("MBid") Integer MBid,
			                          @RequestParam("auth") String auth){//輸入USER or ADMIN (大小寫不限)
		MemberBean newAdmin = memberRepositoryService.newAdmin(MBid,auth);
		if (newAdmin != null) {
			return new ResponseEntity<>("The Authorization of MBid: "+newAdmin.getMBid()+" MBname: "+newAdmin.getMBname()+" has been changed to "+auth, HttpStatus.OK);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// 修改Password
	@PutMapping("/members/{MBid}")
	public ResponseEntity<?> update(@PathVariable("MBid") Integer MBid, @RequestBody MemberBean bean) {
		bean.setMBid(MBid);
		MemberBean update = memberRepositoryService.update(bean);
		if (update != null) {
			return ResponseEntity.ok(update);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// 軟刪除一筆資料
	@DeleteMapping("/members/{id}")
	public ResponseEntity<?> softDelete(@PathVariable Integer id) {
		MemberBean bean = new MemberBean();
		bean.setMBid(id);
		// 回傳的是boolean
		boolean deleted = memberRepositoryService.softDelete(bean);
		if (deleted) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// 物理刪除
	@DeleteMapping("/members/{id}/{name}")
	public ResponseEntity<?> delete(@PathVariable Integer id, @PathVariable String name) {
		MemberBean bean = new MemberBean();
		bean.setMBid(id);
		bean.setMBname(name);
		// 回傳的是boolean
		boolean deleted = memberRepositoryService.delete(bean);
		if (deleted) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// 新增景點收藏
	@PostMapping(path = "/placestorage", produces = { "application/json" })
	public ResponseEntity<?> insertPStorage( @RequestParam Integer atrid,
											 @RequestHeader(name = "Authorization") String authorization) {
		
		String name = JWTparser(authorization);
		List<MemberBean> selects = memberRepositoryService.selectSelf(name);
		if(selects!=null) {
			PlaceStorageId tempId = new PlaceStorageId(selects.get(0).getMBid(), atrid);
			PlaceStorage bean = new PlaceStorage();
			bean.setPlaceStorageId(tempId);
			PlaceStorage result = placeStorageRepositoryService.insert(bean);
			if (result != null) {
				URI uri = URI.create("/placestorage/" + result.getPlaceStorageId());
				return ResponseEntity.created(uri).body(result);
			}
		}
		return ResponseEntity.noContent().build();
	}

	// 景點收藏刪除
	@DeleteMapping("/placestorage")
	public ResponseEntity<?> deletePStorage(@RequestParam Integer atrid,
											@RequestHeader(name = "Authorization") String authorization) {
		boolean deleted =false;
		String name = JWTparser(authorization);
		List<MemberBean> selects = memberRepositoryService.selectSelf(name);
		if(selects!=null) {
			PlaceStorageId tempId = new PlaceStorageId(selects.get(0).getMBid(), atrid);
			PlaceStorage bean = new PlaceStorage();
			bean.setPlaceStorageId(tempId);
			deleted = placeStorageRepositoryService.delete(bean);
		}
		if (deleted) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// 新增文章收藏
	@PostMapping(path = "/articlestorage", produces = { "application/json" })
	public ResponseEntity<?> insertAStorage(@RequestParam Integer atcid,
											@RequestHeader(name = "Authorization") String authorization) {
		String name = JWTparser(authorization);
		List<MemberBean> selects = memberRepositoryService.selectSelf(name);
		if(selects!=null) {
			ArticleStorageId tempId = new ArticleStorageId(selects.get(0).getMBid(), atcid);
			ArticleStorage bean = new ArticleStorage();
			bean.setArticleStorageId(tempId);
			ArticleStorage result = articleStorageRepositoryService.insert(bean);
			if (result != null) {
				URI uri = URI.create("/articlestorage/" + result.getArticleStorageId());
				return ResponseEntity.created(uri).body(result);
			}
		}
		return ResponseEntity.noContent().build();
	}

	// 文章收藏刪除
	@DeleteMapping("/articlestorage")
	public ResponseEntity<?> deleteAStorage(@RequestParam Integer atcid,
											@RequestHeader(name = "Authorization") String authorization) {
		boolean deleted = false;
		String name = JWTparser(authorization);
		List<MemberBean> selects = memberRepositoryService.selectSelf(name);
		if(selects!=null) {
			ArticleStorageId tempId = new ArticleStorageId(selects.get(0).getMBid(), atcid);
			ArticleStorage bean = new ArticleStorage();
			bean.setArticleStorageId(tempId);
			deleted = articleStorageRepositoryService.delete(bean);
		}
		if (deleted) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// 新增行程收藏
	@PostMapping(path = "/tripstorage", produces = { "application/json" })
	public ResponseEntity<?> insertTStorage(@RequestParam Integer wtid,
											@RequestHeader(name = "Authorization") String authorization) {
		String name = JWTparser(authorization);
		List<MemberBean> selects = memberRepositoryService.selectSelf(name);
		if(selects!=null) {
			TripStorageId tempId = new TripStorageId(selects.get(0).getMBid(), wtid);
			TripStorage bean = new TripStorage();
			bean.setTripStorageId(tempId);
			TripStorage result = tripStorageRepositoryService.insert(bean);
			if (result != null) {
				URI uri = URI.create("/tripstorage" + result.getTripStorageId());
				return ResponseEntity.created(uri).body(result);
			}
		}
		return ResponseEntity.noContent().build();
	}

	// 行程收藏刪除
	@DeleteMapping("/tripstorage")
	public ResponseEntity<?> deleteTStorage(@RequestParam Integer wtid,
											@RequestHeader(name = "Authorization") String authorization) {
		boolean deleted = false;
		String name = JWTparser(authorization);
		List<MemberBean> selects = memberRepositoryService.selectSelf(name);
		if(selects!=null) {
			TripStorageId tempId = new TripStorageId(selects.get(0).getMBid(), wtid);
			TripStorage bean = new TripStorage();
			bean.setTripStorageId(tempId);
			deleted = tripStorageRepositoryService.delete(bean);
		}
		if (deleted) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
