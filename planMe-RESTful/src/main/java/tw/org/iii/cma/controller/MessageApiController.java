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
import tw.org.iii.cma.dao.ArticleRepository;
import tw.org.iii.cma.dao.MemberRepository;
import tw.org.iii.cma.domain.ArticleBean;
import tw.org.iii.cma.domain.MemberBean;
import tw.org.iii.cma.domain.MessageBean;
import tw.org.iii.cma.service.MemberRepositoryService;
import tw.org.iii.cma.service.MessageRepositoryService;




//Spring Boot自動將資料轉化成JSON格式
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/foodmap")
public class MessageApiController {
	@Autowired
	private MessageRepositoryService messageRepositoryService;
	
	@Autowired
	private MemberRepositoryService memberRepositoryService;
	
	@Autowired
	private ArticleRepository articleRepository;
	
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
	@GetMapping("/messages")
	public ResponseEntity<List<MessageBean>> select() {
		List<MessageBean> selects = messageRepositoryService.select(null);
		return ResponseEntity.ok(selects);
	}

	// 查1筆資料
	@GetMapping("/messages/{id}")
	public ResponseEntity<?> select(@PathVariable(name = "id") Integer id) {
		MessageBean bean = new MessageBean();
		bean.setMSGid(id);
		List<MessageBean> selects = messageRepositoryService.select(bean);
		if (selects != null && !selects.isEmpty()) {
			return ResponseEntity.ok(selects.get(0));
		}
		return ResponseEntity.notFound().build();

	}

	// 查檢舉的文章
	@GetMapping("/messages/reported")
	public ResponseEntity<?> selectRepoerted(@RequestParam(defaultValue = "0") Integer page) {

		List<MessageBean> selects = messageRepositoryService.selectReported(page);
		if (selects != null && !selects.isEmpty()) {
			return ResponseEntity.ok(selects);
		}
		return ResponseEntity.notFound().build();

	}

	// 插入一筆資料
	@PostMapping("/messages")
	public ResponseEntity<?> insert(@RequestBody MessageBean bean, @RequestParam Integer actid,
									@RequestHeader(name = "Authorization") String authorization) {
		if (bean != null && bean.getMSGid() == null) {
			String name = JWTparser(authorization);
			List<MemberBean> mbs = memberRepositoryService.selectSelf(name);
			List<ArticleBean> acts = null;
			Optional<ArticleBean> optional = articleRepository.findById(actid);
			if (!mbs.isEmpty() && optional.isPresent()) {
				bean.setMemberBean(mbs.get(0));
				acts = new ArrayList<ArticleBean>();
				acts.add(optional.get());
				bean.setArticleBean(acts.get(0));
			}
			MessageBean result = messageRepositoryService.insert(bean);
			if (result != null) {
				URI uri = URI.create("/messages/" + result.getMSGid());
				return ResponseEntity.created(uri).body(result);
			}

		}
		return ResponseEntity.noContent().build();

	}

	// 軟刪除一筆資料
	@DeleteMapping("/messages/{id}")
	public ResponseEntity<?> softDelete(@PathVariable Integer id) {
		MessageBean bean = new MessageBean();
		bean.setMSGid(id);
		// 回傳的是boolean
		boolean deleted = messageRepositoryService.softDelete(bean);
		if (deleted) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
		
		
	// 檢舉修改
	@PutMapping("/messages/reported")
	public ResponseEntity<?> update(@RequestParam(name = "msgid") Integer msgid,
									@RequestParam(name = "reported") Boolean reported) {
		MessageBean update = messageRepositoryService.update(msgid,reported);
		if (update != null) {
			return ResponseEntity.ok(update);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// 按讚修改
	@PutMapping("/messages/like")
	public ResponseEntity<?> updateLike(@RequestParam("MSGid") Integer MSGid,
										@RequestParam("like") Boolean like) {
		MessageBean update = messageRepositoryService.updateLike(MSGid,like);
		if (update != null) {
			return ResponseEntity.ok(update);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
