package tw.org.iii.cma.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.org.iii.cma.domain.TagStorage;
import tw.org.iii.cma.domain.TagStorageId;

public interface TagStorageRepository 
		extends JpaRepository<TagStorage, TagStorageId> {

}
