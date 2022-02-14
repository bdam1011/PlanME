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
import tw.org.iii.cma.domain.ArticleBean;
import tw.org.iii.cma.domain.MemberBean;
import tw.org.iii.cma.service.ArticleRepositoryService;
import tw.org.iii.cma.service.MemberRepositoryService;


//Spring Boot自動將資料轉化成JSON格式
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/foodmap")
public class ArticleApiController {
	@Autowired
	private ArticleRepositoryService articleRepositoryService;
	
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
	@GetMapping("/articles")
	public ResponseEntity<List<ArticleBean>> select() {
		List<ArticleBean> selects = articleRepositoryService.select(null);
		return ResponseEntity.ok(selects);
	}

	// 查1筆資料
	@GetMapping("/articles/{id}")
	public ResponseEntity<?> select(@PathVariable(name = "id") Integer id) {
		ArticleBean bean = new ArticleBean();
		bean.setATCid(id);
		List<ArticleBean> selects = articleRepositoryService.select(bean);
		if (selects != null && !selects.isEmpty()) {
			return ResponseEntity.ok(selects.get(0));
		}
		return ResponseEntity.notFound().build();

	}

	// 查人發的文章
	@GetMapping("/articles/name")
	public ResponseEntity<?> selectByName(@RequestParam("name") String name) {

		List<ArticleBean> selects = articleRepositoryService.selectByMember(name);
		if (selects != null && !selects.isEmpty()) {
			return ResponseEntity.ok(selects);
		}
		return ResponseEntity.notFound().build();

	}

	// 查檢舉的文章
	@GetMapping("/articles/reported")
	public ResponseEntity<?> selectRepoerted(@RequestParam(defaultValue = "0") Integer page) {

		List<ArticleBean> selects = articleRepositoryService.selectReported(page);
		if (selects != null && !selects.isEmpty()) {
			return ResponseEntity.ok(selects);
		}
		return ResponseEntity.notFound().build();

	}

	// 插入一筆資料
	@PostMapping(path = "/articles", produces = { "application/json" }, consumes = { "application/json" })
	public ResponseEntity<?> insert(@RequestBody ArticleBean bean,
									@RequestHeader(name = "Authorization") String authorization) {
		if (bean != null && bean.getATCid() == null) {
			String name = JWTparser(authorization);
			List<MemberBean> mbs = memberRepositoryService.selectSelf(name);
			
			if(!mbs.isEmpty()) {
				bean.setMemberBean(mbs.get(0));
			}
			ArticleBean result = articleRepositoryService.insert(bean);
			if (result != null) {
				URI uri = URI.create("/articles/" + result.getATCid());
				return ResponseEntity.created(uri).body(result);
			}
		}
		return ResponseEntity.noContent().build();

	}

	// 軟刪除一筆資料
	@DeleteMapping("/articles/{id}")
	public ResponseEntity<?> softDelete(@PathVariable Integer id) {
		ArticleBean bean = new ArticleBean();
		bean.setATCid(id);
		// 回傳的是boolean
		boolean deleted = articleRepositoryService.softDelete(bean);
		if (deleted) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// 物理刪除
	@DeleteMapping("/articles/{id}/{title}")
	public ResponseEntity<?> delete(@PathVariable Integer id, @PathVariable String title) {
		ArticleBean bean = new ArticleBean();
		bean.setATCid(id);
		bean.setATCtitle(title);
		// 回傳的是boolean
		boolean deleted = articleRepositoryService.delete(bean);
		if (deleted) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// 修改(ArticleBean bean需要有atctitle,atctext,IsReported)
	@PutMapping("/articles/{atcid}")
	public ResponseEntity<?> update(@PathVariable("atcid") Integer atcid,
									@RequestHeader(name = "Authorization") String authorization,
									@RequestBody ArticleBean bean) {
		String email = JWTparser(authorization);
		bean.setATCid(atcid);
		ArticleBean update = articleRepositoryService.update(bean,email);
		if (update != null) {
			return ResponseEntity.ok(update);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	//檢舉修改
	@PutMapping("/articles/reported")
	public ResponseEntity<?> updateReported(@RequestParam("atcid") Integer atcid,
											@RequestParam("reported") Boolean reported ) {

		ArticleBean update = articleRepositoryService.updateReported(atcid,reported);
		if (update != null) {
			return ResponseEntity.ok(update);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
