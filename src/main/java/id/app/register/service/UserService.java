package id.app.register.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import id.app.register.exception.RecordNotFoundException;
import id.app.register.model.User;
import id.app.register.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;

@Service("UserService")
@Slf4j
public class UserService {
	
	@Autowired
	private UsersRepository repository;
	
	@Autowired PasswordEncoder encypt;
	public List<User> findbyUsernameService(String username) throws JsonProcessingException{
		List<User> user = repository.findByUsername(username);
	    ObjectWriter ow = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .writer();
	    if (user.isEmpty()) {
	    	log.error("Api User Response : NULL User");
        	throw new RecordNotFoundException(username);
    	}else {
    		user.get(0).setPassword("");
    	    log.info("Api User Response 200 : "+ow.writeValueAsString(user));
        	return user;
    	}
	}
	
	public User saveOrUpdateUser(String nUsername
			, String nRole
			, String nPassword
			, Boolean nEnabled
			, String username) throws JsonProcessingException {
		User getUser = repository.findByUsername_(username);
		String pswd = nPassword;
		String pswdEncrypt = encypt.encode(nPassword);
		ObjectWriter ow = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .writer();
		if (getUser == null) {
			User newUserSave = new User();
			newUserSave.setUsername(username);
			newUserSave.setPassword(pswdEncrypt);
			newUserSave.setRole(nRole);
			newUserSave.setEnabled(true);;
			repository.save(newUserSave);
			newUserSave.setPassword(pswd);
			log.info("Api User Response :"+ow.writeValueAsString(newUserSave));
			return newUserSave;
		}else {
			User newUserUpdate = new User();
			Long id = getUser.getId();
			return repository.findById(id)
					.map(x -> {
						x.setUsername(nUsername);
						x.setRole(nRole);
						x.setPassword(pswdEncrypt);
						x.setEnabled(nEnabled);
						x.setId(id);
						repository.save(x);
						newUserUpdate.setId(id);
						newUserUpdate.setUsername(username);
						newUserUpdate.setPassword(pswd);
						newUserUpdate.setRole(nRole);
						newUserUpdate.setEnabled(nEnabled);
						try {
							log.info("Api User Response :"+ow.writeValueAsString(newUserUpdate));
						} catch (JsonProcessingException e) {
							e.printStackTrace();
						}
						return newUserUpdate;
					})
					.orElseGet(() -> {
						try {
							log.error("Api User Response :"+ow.writeValueAsString(newUserUpdate));
						} catch (JsonProcessingException e) {
							e.printStackTrace();
						}
	                    return repository.save(newUserUpdate);
	                });
		}
	}
}
