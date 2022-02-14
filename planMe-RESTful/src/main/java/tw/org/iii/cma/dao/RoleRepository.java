package tw.org.iii.cma.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.org.iii.cma.domain.RoleBean;

public interface RoleRepository 
			extends JpaRepository<RoleBean, Integer> {
	Optional<RoleBean> findByrolename(String rolename);
}
