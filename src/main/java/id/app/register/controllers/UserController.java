package id.app.register.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import id.app.register.model.User;
import id.app.register.service.UserService;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(description="Client User Information & Maintenance", tags = "User")
@Slf4j
public class UserController {
	
	@Autowired
	private UserService Service;
	
	@Autowired PasswordEncoder encypt;
	
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping("/account/client/{username}")
    @ApiOperation(value = "List Client User")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<User> findbyUsername(HttpServletRequest request, @PathVariable String username) throws JsonProcessingException {
		log.info("Api "+request.getMethod()+" User "+request.getRemoteAddr());
		List<User> result = Service.findbyUsernameService(username);
		log.info("Api "+request.getMethod()+" User Response End "+request.getRemoteAddr());
		return result;
	}
	
	@PutMapping("/account/client/{username}")
	@ApiOperation(value = "Save Or Update Client User By Username")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public User saveOrUpdateUser(HttpServletRequest request, @RequestBody User newUser, @PathVariable String username) throws JsonProcessingException {
		log.info("Api "+request.getMethod()+" User "+request.getRemoteAddr());
	    User result = Service.saveOrUpdateUser(newUser.getUsername()
	    		, newUser.getRole()
	    		, newUser.getPassword()
	    		, newUser.isEnabled()
	    		, username);
	    log.info("Api "+request.getMethod()+" User Response End "+request.getRemoteAddr());
	    return result;
		
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
	
	
}
