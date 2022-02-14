package tw.org.iii.cma.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import tw.org.iii.cma.domain.AtrphotoBean;
import tw.org.iii.cma.domain.AttractionsBean;
import tw.org.iii.cma.domain.TagStorage;
import tw.org.iii.cma.domain.TagStorageId;
import tw.org.iii.cma.service.AttractionsRepositoryService;
import tw.org.iii.cma.service.TagStorageRepositoryService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/foodmap")
public class AttractionsApiController {
	@Autowired
	private AttractionsRepositoryService attractionsRepositoryService;

	@Autowired
	private TagStorageRepositoryService tagStorageRepositoryService;
	
	
	// 查詢多筆資料
	@GetMapping("/atrs")
	public ResponseEntity<List<AttractionsBean>> select() {
		List<AttractionsBean> selects = attractionsRepositoryService.select(null);
		return ResponseEntity.ok(selects);
	}
	
	// 由頁數查詢多筆資料
	@GetMapping("/atrs/page")
	public ResponseEntity<List<AttractionsBean>> selectPage(
			@RequestParam(defaultValue = "0")Integer page) {
		List<AttractionsBean> selects = attractionsRepositoryService.selectPage(page);
		return ResponseEntity.ok(selects);
	}

	// 查1筆資料
	@GetMapping("/atrs/id/{id}")
	public ResponseEntity<?> select(@PathVariable(name = "id") Integer id) {
		AttractionsBean bean = new AttractionsBean();
		bean.setATRid(id);
		List<AttractionsBean> selects = attractionsRepositoryService.select(bean);
		if (selects != null && !selects.isEmpty()) {
			System.out.println(selects.get(0).getAtrphotoBeans());
			return ResponseEntity.ok(selects.get(0));
		}
		return ResponseEntity.notFound().build();
	}

	// 模糊搜尋景點Name
	@GetMapping(path = "/atrs/name/{name}", produces = "application/json")
	public ResponseEntity<?> selectAttractionsName(@PathVariable(name = "name") String name) {
		AttractionsBean bean = new AttractionsBean();
		bean.setATRname(name);
		List<AttractionsBean> selects = attractionsRepositoryService.selectAttractionsName(bean);
		if (selects != null && !selects.isEmpty()) {
			return ResponseEntity.ok(selects);
		}
		return ResponseEntity.notFound().build();
	}
	
	//搜尋縣市景點
	@GetMapping(path = "/atrs/city/{city}", produces = "application/json")
	public ResponseEntity<?> selectTags(@PathVariable(name = "city") String city) {
		AttractionsBean bean = new AttractionsBean();
		bean.setATRaddress(city);
		List<AttractionsBean> selects = attractionsRepositoryService.selectCityAttractions(bean);
		if (selects != null && !selects.isEmpty()) {
			return ResponseEntity.ok(selects);
		}
		return ResponseEntity.notFound().build();
	}
	

	// 搜尋photo from AttractionsBean
	@GetMapping(path = "/atrs/id/{id}/photo", produces = "application/json")
	public ResponseEntity<?> selectPhoto(@PathVariable(name = "id") Integer id) {
		AttractionsBean bean = new AttractionsBean();
		bean.setATRid(id);
		List<AtrphotoBean> selects = attractionsRepositoryService.selectPhoto(bean);
		if (selects != null && !selects.isEmpty()) {
			return ResponseEntity.ok(selects);
		}
		return ResponseEntity.notFound().build();
	}

	// 插入一筆資料
	@PostMapping(path = "/atrs", consumes = { "application/json" }, produces = { "application/json" })
	public ResponseEntity<?> insert(@RequestBody AttractionsBean bean) {
		if (bean != null) {
			AttractionsBean result = attractionsRepositoryService.insert(bean);
			if (result != null) {
				URI uri = URI.create("/atrs/" + result.getATRid());
				return ResponseEntity.created(uri).body(result);
			}
		}
		return ResponseEntity.noContent().build();
	}
	
	// 載入景點 - 觀光資訊資料庫
	@PostMapping(path = "/atrs/loadopen")
	public ResponseEntity<?> insertOpenData() throws IOException {
		AttractionsBean bean = new AttractionsBean();
		AttractionsBean newbean =attractionsRepositoryService.insertOpeanData(bean);
		if(newbean!=null) {
			return ResponseEntity.ok(newbean);
		}
		return ResponseEntity.notFound().build();
	}

	// 刪除一筆資料
	@DeleteMapping("/atrs/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		AttractionsBean bean = new AttractionsBean();
		bean.setATRid(id);
		// 回傳的是boolean
		boolean deleted = attractionsRepositoryService.delete(bean);
		if (deleted) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// 修改(其實是覆蓋)
	@PutMapping("/atrs/{atrid}")
	public ResponseEntity<?> update(@PathVariable("atrid") Integer atrid,
									@RequestBody AttractionsBean bean) {
		bean.setATRid(atrid);
		AttractionsBean update = attractionsRepositoryService.update(bean);
		if (update != null) {
			return ResponseEntity.ok(update);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// 新增tag連結
	@PostMapping(path = "/atrs/tagstorage", produces = { "application/json" })
	public ResponseEntity<?> insertTagStorage(@RequestParam Integer atrid, 
											  @RequestParam Integer tagid) {
		TagStorageId tempId = new TagStorageId(atrid, tagid);
		TagStorage bean = new TagStorage();
		bean.setTagStorageId(tempId);
		TagStorage result = tagStorageRepositoryService.insert(bean);
		if (result != null) {
			URI uri = URI.create("/atrs/tagstorage/" + result.getTagStorageId());
			return ResponseEntity.created(uri).body(result);
		}
		return ResponseEntity.noContent().build();
	}

	// tag連結刪除
	@DeleteMapping("/atrs/tagstorage")
	public ResponseEntity<?> deleteTagStorage(@RequestParam Integer atrid,
											  @RequestParam Integer tagid) {
		TagStorageId tempId = new TagStorageId(atrid, tagid);
		TagStorage bean = new TagStorage();
		bean.setTagStorageId(tempId);
		boolean deleted = tagStorageRepositoryService.delete(bean);
		if (deleted) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
