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

import tw.org.iii.cma.dao.ArticleRepository;
import tw.org.iii.cma.dao.MemberRepository;
import tw.org.iii.cma.domain.ArticleBean;
import tw.org.iii.cma.domain.AttractionsBean;
import tw.org.iii.cma.domain.MemberBean;


@Service
@Transactional
public class ArticleRepositoryService {
	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	public List<ArticleBean> select(ArticleBean bean) {
		List<ArticleBean> result = null;
		if(bean!=null && bean.getATCid()!=null && !bean.getATCid().equals(0)) {
			Optional<ArticleBean> optional = articleRepository.findById(bean.getATCid());
			if(optional.isPresent()) {
				result = new ArrayList<ArticleBean>();
				result.add(optional.get());
			}
		} else {
			result = articleRepository.findAll();
		}
		return result;
	}
	
	public List<ArticleBean> selectByMember(String name) {
		List<ArticleBean> result = null;
		if( !name.isEmpty()) {
			Optional<MemberBean> authour = memberRepository.findByMBname(name);
			if(authour.isPresent()) {
				result = articleRepository.findBymemberBean(authour.get());
			}
		} 
		return result;
	}
	
	public List<ArticleBean> selectReported(Integer page) {
		List<ArticleBean> result = null;
		Page<ArticleBean> pageResult = null;
		Pageable pageable = PageRequest.of(page, 20, Sort.by("ATCid").ascending());
		pageResult = articleRepository.findByisReported(true,pageable);
		
		if(pageResult!=null) {
			result = pageResult.getContent();
		}
		return result;
	}
	
	
	
	
	public ArticleBean insert(ArticleBean bean) {
		ArticleBean result = null;	
		if(bean!=null && bean.getATCid()==null) {
			return articleRepository.save(bean);
		}
		return result;	
	}
	
	
	public ArticleBean update(ArticleBean bean,String email) {
		ArticleBean result = null;
		if(bean!=null && bean.getATCid()!=null) {
			ArticleBean select = articleRepository.findByATCid(bean.getATCid());
			if(select!=null && select.getMemberBean().getMBemail().equals(email)) {
				bean.setMemberBean(select.getMemberBean());
				bean.setMessageBeans(select.getMessageBeans());
				bean.setATCUploadT(select.getATCUploadT());
				bean.setATCReviseT(new java.util.Date());
				bean.setATClike(select.getATClike());
				bean.setDeleted(select.getDeleted());
				return articleRepository.save(bean);
			}
		}
		return result;
	}
	
	public ArticleBean updateReported(Integer atcid, Boolean reported) {
		ArticleBean result = null;
		if(atcid!=null ) {
			ArticleBean select = articleRepository.findByATCid(atcid);
			if(select!=null) {
				select.setIsReported(reported);
				return articleRepository.save(select);
			}
		}
		return result;
	}
	
	//soft deleted
	public boolean softDelete(ArticleBean bean) {
		boolean  result = false;
		if(bean!=null && bean.getATCid()!=null) {
			boolean exist = articleRepository.existsById(bean.getATCid());
			if(exist) {
				Optional<ArticleBean> optional = articleRepository.findById(bean.getATCid());
				optional.get().setDeleted(true);
				articleRepository.save(optional.get());
				result = true;
			}
		}
		return result;
	}
	
	//hard delete
	public boolean delete(ArticleBean bean) {
		boolean result = false;
		if(bean!=null && bean.getATCid()!=null && bean.getATCtitle()!=null) {
			boolean exist = articleRepository.existsByATCidAndATCtitle(bean.getATCid(),bean.getATCtitle());
			if(exist) {
				articleRepository.deleteById(bean.getATCid());
				result = true;
			}
		}
		return result;
	}
}
