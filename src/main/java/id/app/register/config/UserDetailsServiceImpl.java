package id.app.register.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import id.app.register.model.User;
import id.app.register.repository.UserRepository;
@CacheConfig(cacheNames = {"user"})
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
    private UserRepository userRepository;
	
	@Override
	@Cacheable(value="userss")
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.getUserByUsername(username);
        
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
         
        return new MyUserDetails(user);
	}

}
