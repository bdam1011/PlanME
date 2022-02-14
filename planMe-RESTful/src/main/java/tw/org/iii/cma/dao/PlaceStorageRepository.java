package tw.org.iii.cma.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.org.iii.cma.domain.PlaceStorage;
import tw.org.iii.cma.domain.PlaceStorageId;

public interface PlaceStorageRepository 
	extends JpaRepository<PlaceStorage, PlaceStorageId> {

}
