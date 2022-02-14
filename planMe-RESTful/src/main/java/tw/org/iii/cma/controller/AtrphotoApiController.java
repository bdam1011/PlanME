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
import tw.org.iii.cma.dao.AttractionsRepository;
import tw.org.iii.cma.dao.MemberRepository;
import tw.org.iii.cma.domain.AtrphotoBean;
import tw.org.iii.cma.domain.AttractionsBean;
import tw.org.iii.cma.domain.MemberBean;
import tw.org.iii.cma.service.AtrphotoRepositoryService;
import tw.org.iii.cma.service.MemberRepositoryService;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/foodmap")
public class AtrphotoApiController {

		@Autowired
		AtrphotoRepositoryService AtrphotoRepositoryService;
		
		@Autowired
		private MemberRepositoryService memberRepositoryService;
		
		@Autowired
		private AttractionsRepository attractionsRepository;
		
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
		@GetMapping("/photos")
		public ResponseEntity<List<AtrphotoBean>> select() {
			List<AtrphotoBean> selects = AtrphotoRepositoryService.select(null);
			return ResponseEntity.ok(selects);
		}

		// 查1筆資料
		@GetMapping("/photos/{id}")
		public ResponseEntity<?> select(@PathVariable(name ="id") Integer id) {
			AtrphotoBean bean = new AtrphotoBean();
			bean.setPhotoID(id);
			List<AtrphotoBean> selects = AtrphotoRepositoryService.select(bean);
			if(selects!=null&& !selects.isEmpty()) {
				return ResponseEntity.ok(selects.get(0));
			}
			return ResponseEntity.notFound().build();

		}

		// 插入一筆資料
		@PostMapping(path="/photos",
				     produces = {"application/json"},
				     consumes = {"application/json"})
		public ResponseEntity<?> insert(@RequestBody AtrphotoBean bean,
										@RequestParam Integer atrid,
										@RequestHeader(name = "Authorization") String authorization) {
			if(bean!=null && bean.getPhotoID()==null) {
				String name = JWTparser(authorization);
				List<MemberBean> mbs = memberRepositoryService.selectSelf(name);
				List<AttractionsBean> ats = null;
				Optional<AttractionsBean> optional = attractionsRepository.findById(atrid);
				if(!mbs.isEmpty()&&optional.isPresent()) {
					bean.setMemberBean(mbs.get(0));
					ats = new ArrayList<AttractionsBean>();
					ats.add(optional.get());
					bean.setAttractionsBean(ats.get(0));
				}
				AtrphotoBean result =AtrphotoRepositoryService.insert(bean);
				if(result!=null) {
					URI uri = URI.create("/photos/"+result.getPhotoID());
					return ResponseEntity.created(uri).body(result);
				}
				
			}
			return ResponseEntity.noContent().build();	

		}

		// 刪除一筆資料
		@DeleteMapping("/photos/{id}")
		public ResponseEntity<?> delete(@PathVariable Integer id) {
			AtrphotoBean bean = new AtrphotoBean();
			bean.setPhotoID(id);
			// 回傳的是boolean
			boolean deleted = AtrphotoRepositoryService.delete(bean);
			if (deleted) {
				return ResponseEntity.noContent().build();
			} else {
				return ResponseEntity.notFound().build();
			}

		}

		// 修改(其實是覆蓋)
		@PutMapping("/photos/{id}")
		public ResponseEntity<?> update(@PathVariable("id") Integer id, 
										@RequestBody AtrphotoBean bean) {
			bean.setPhotoID(id);
			AtrphotoBean update = AtrphotoRepositoryService.update(bean);
			if (update != null) {
				return ResponseEntity.ok(update);
			} else {
				return ResponseEntity.notFound().build();
			}
		}
	}
