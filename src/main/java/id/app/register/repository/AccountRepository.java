package id.app.register.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import id.app.register.model.Account;

public interface AccountRepository extends CrudRepository<Account, UUID>{
	
	Account findByUuid(UUID uuid);
	
	List<Account> findByNamaLengkap(String nama_lengkap);
	
	@Query(value = "SELECT * FROM account", nativeQuery = true)
	Page<Account> findAll(Pageable pageable);
}
