package tw.org.iii.cma.controller;

import java.net.URI;
import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import tw.org.iii.cma.domain.TagBean;
import tw.org.iii.cma.service.TagRepositoryService;

//Spring Boot自動將資料轉化成JSON格式
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/foodmap")
public class TagApiController {
	@Autowired
	private TagRepositoryService tagRepositoryService;

	// 查詢多筆資料
	@GetMapping("/tags")
	public ResponseEntity<List<TagBean>> select() {
		List<TagBean> selects = tagRepositoryService.select(null);
		return ResponseEntity.ok(selects);
	}

//	// 查1筆資料
//	@GetMapping("/tags/{id}")
//	public ResponseEntity<?> select(@PathVariable(name = "id") Integer id) {
//		TagBean bean = new TagBean();
//		bean.setTagid(id);
//		List<TagBean> selects = tagRepositoryService.select(bean);
//		if (selects != null && !selects.isEmpty()) {
//			return ResponseEntity.ok(selects.get(0));
//		}
//		return ResponseEntity.notFound().build();
//	}

	// 模糊搜尋tagName
	@GetMapping(path = "/tags/{name}", produces = "application/json")
	public ResponseEntity<?> selectTags(@PathVariable(name = "name") String name) {
		TagBean bean = new TagBean();
		bean.setTagname(name);
		List<TagBean> selects = tagRepositoryService.selectTagName(bean);
		if (selects != null && !selects.isEmpty()) {
			return ResponseEntity.ok(selects);
		}
		return ResponseEntity.notFound().build();
	}

	// 插入一筆資料
	@PostMapping(path = "/tags", produces = { "application/json" }, consumes = { "application/json" })
	public ResponseEntity<?> insert(@RequestBody TagBean bean) {
		if (bean != null && bean.getTagid() == null) {
			TagBean result = null;
			result = tagRepositoryService.insert(bean);
			if (result != null) {
				URI uri = URI.create("/tags/" + result.getTagid());
				return ResponseEntity.created(uri).body(result);
			}

		}
		return ResponseEntity.noContent().build();
	}

	// 物理刪除
	@DeleteMapping("/tags/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id, @PathVariable String title) {
		TagBean bean = new TagBean();
		bean.setTagid(id);
		// 回傳的是boolean
		boolean deleted = tagRepositoryService.delete(bean);
		if (deleted) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// 修改(其實是覆蓋)
	@PutMapping("/tags/{id}")
	public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody TagBean bean) {
		bean.setTagid(id);
		TagBean update = tagRepositoryService.update(bean);
		if (update != null) {
			return ResponseEntity.ok(update);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
