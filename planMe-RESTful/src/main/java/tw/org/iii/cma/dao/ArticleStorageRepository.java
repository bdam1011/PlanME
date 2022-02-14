package tw.org.iii.cma.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.org.iii.cma.domain.ArticleStorage;
import tw.org.iii.cma.domain.ArticleStorageId;

public interface ArticleStorageRepository 
		extends JpaRepository<ArticleStorage,ArticleStorageId> {

}
