package tw.org.iii.cma.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.org.iii.cma.domain.TripStorage;
import tw.org.iii.cma.domain.TripStorageId;

public interface TripStorageRepository 
		extends JpaRepository<TripStorage, TripStorageId> {

}
