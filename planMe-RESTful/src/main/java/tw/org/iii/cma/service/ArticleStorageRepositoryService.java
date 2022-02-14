package tw.org.iii.cma.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.org.iii.cma.dao.ArticleRepository;
import tw.org.iii.cma.dao.ArticleStorageRepository;
import tw.org.iii.cma.dao.MemberRepository;
import tw.org.iii.cma.domain.ArticleBean;
import tw.org.iii.cma.domain.ArticleStorage;
import tw.org.iii.cma.domain.MemberBean;

@Service
@Transactional
public class ArticleStorageRepositoryService {
	@Autowired
	private ArticleStorageRepository articleStorageRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private ArticleRepository articleRepository;
	
	public ArticleStorage insert(ArticleStorage bean) {
		ArticleStorage result = null;
		if(bean!=null&& bean.getArticleStorageId()!=null) {
			Optional<ArticleStorage> optional = articleStorageRepository.findById(bean.getArticleStorageId());
			if(!optional.isPresent()) {
				MemberBean mb = memberRepository.findByMBid(bean.getArticleStorageId().getMBid());
				ArticleBean atcb = articleRepository.findByATCid(bean.getArticleStorageId().getATCid());
				if(mb!=null && atcb!=null && atcb.getMemberBean().getMBid()!=mb.getMBid()) {
					atcb.setATClike(atcb.getATClike()+1);
					bean.setMemberBean(mb);
					bean.setArticleBean(atcb);
					return articleStorageRepository.save(bean);
				}
				
			}
		}
		return result;			
	}
	
	public boolean delete(ArticleStorage bean) {
		boolean result = false;
		if(bean!=null && bean.getArticleStorageId()!=null) {
			boolean exist = articleStorageRepository.existsById(bean.getArticleStorageId());
			if(exist) {
				ArticleBean atcb = articleRepository.findByATCid(bean.getArticleStorageId().getATCid());
				atcb.setATClike(atcb.getATClike()-1);
				articleStorageRepository.deleteById(bean.getArticleStorageId());
				result = true;
			}
		}
		return result;
	}
}
