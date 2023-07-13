package id.app.register.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import  id.app.register.model.User;

public interface UsersRepository extends JpaRepository<User, Long>{
	public List<User> findByUsername(String username);
	public User findByUsername_(String username);
}
