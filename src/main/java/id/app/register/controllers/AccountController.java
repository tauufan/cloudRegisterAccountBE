package id.app.register.controllers;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import id.app.register.model.Account;
import id.app.register.model.User;
import id.app.register.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(description="Account Maintenance", tags = "Account")
@Slf4j
public class AccountController {
	@Autowired
	AccountService accSrv;
	
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping("/account")
    @ApiOperation(value = "List Account")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public ResponseEntity<?> findAll(
			HttpServletRequest request
			, @RequestParam(defaultValue = "0") int page
    		, @RequestParam(defaultValue = "10") int size
    		) throws JsonProcessingException {
		try {
			log.info("Get Account Data All ");
			return accSrv.findAll(page, size);
		} catch (Exception e) {
			log.error(e.toString());
			return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}
	
	@ResponseStatus(code = HttpStatus.OK)
	@PostMapping("/account")
    @ApiOperation(value = "Insert Account")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public ResponseEntity<?> insertAccount(@Valid @RequestBody Account newAccount, HttpServletRequest req){
		
		try {
			log.info("Insert Account Data "+newAccount.toString());
			Account data = new Account();
			data = newAccount;
			return accSrv.insertAccout(data);
		} catch (Exception e) {
			log.error(e.toString());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping("/account/{uuid}")
    @ApiOperation(value = "Account By ID")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public ResponseEntity<?> getAccountByID(
			@PathVariable UUID uuid,
			HttpServletRequest request) throws JsonProcessingException {
		try {
			log.info("Get Account Data "+ uuid);
			return accSrv.dataAccount(uuid);
		} catch (Exception e) {
			log.error(e.toString());
			return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}
	
	@ResponseStatus(code = HttpStatus.OK)
	@PutMapping("/account/{uuid}")
    @ApiOperation(value = "Update Account")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public ResponseEntity<?> UpdateAccount(
			@Valid @RequestBody Account updateData,
			@PathVariable UUID uuid,
			HttpServletRequest request) throws JsonProcessingException {
		try {
			log.info("Account Data Update "+ uuid + updateData.toString());
			return accSrv.updateAccount(updateData, uuid);
		} catch (Exception e) {
			log.error(e.toString());
			return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}
	
	@ResponseStatus(code = HttpStatus.OK)
	@DeleteMapping("/account/{uuid}")
    @ApiOperation(value = "Delete Account")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public ResponseEntity<?> DeleteAccount(
			@PathVariable UUID uuid,
			HttpServletRequest request) throws JsonProcessingException {
		try {
			log.info("Account Data Delete "+ uuid );
			return accSrv.deleteAccount(uuid);
		} catch (Exception e) {
			log.error(e.toString());
			return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}
}
