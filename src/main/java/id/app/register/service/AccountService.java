package id.app.register.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import id.app.register.exception.RecordNotFoundException;
import id.app.register.model.Account;
import id.app.register.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;

@Service("AccountService")
@Slf4j
public class AccountService {
	@Autowired
	AccountRepository repo;
	
	@Autowired PasswordEncoder encypt;
	
	public ResponseEntity<?> findAll(int page, int size) throws JsonProcessingException{
		try {
			Pageable paging = PageRequest.of(page, size);
			Page<Account> getData = null;
			getData = repo.findAll(paging);
			if (getData.isEmpty()) {
	        	log.info("Account Data tidak ditemukan");
		        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}else {
				Map<String, Object> data = new HashMap<>();
				data.put("data", getData.getContent());
		        data.put("currentPage", getData.getNumber());
		        data.put("totalItems", getData.getTotalElements());
		        data.put("totalPages", getData.getTotalPages());
		        ObjectWriter ow = new ObjectMapper()
	                    .setSerializationInclusion(JsonInclude.Include.NON_NULL)
	                    .writer();
		        log.info("Data Account "+ow.writeValueAsString(data));
		        return new ResponseEntity<>(data, HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error(e.toString());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<?> insertAccout(Account newAccount){
		try {
			
			Account insertData = repo.save(newAccount);
			log.info("Account Data berhasil disimpan");
			return new ResponseEntity<>(insertData, HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.toString());
			return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	public ResponseEntity<?> dataAccount(UUID id){
		
		try {
			Account getData = repo.findByUuid(id);
	        if (getData==null) {
	        	log.info("Account Data Tidak Ditemukan");
	        	return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}else {
				ObjectWriter ow = new ObjectMapper()
	                    .setSerializationInclusion(JsonInclude.Include.NON_NULL)
	                    .writer();
		        log.info("Account Data Data "+ow.writeValueAsString(getData));
		        return new ResponseEntity<>(getData, HttpStatus.OK);
			}
	        
		} catch (Exception e) {
			log.info(e.toString());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	public ResponseEntity<?> updateAccount (Account data, UUID id){
		
		try {
			Account cekData = repo.findByUuid(id);
			
			if (cekData == null) {
				log.info("Account data tidak ditemukan");
	        	return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}else {
				Account updatedData = cekData;
				
				updatedData.setNamaLengkap(data.getNamaLengkap());
				updatedData.setNik(data.getNik());
				updatedData.setJenisKelamin(data.getJenisKelamin());
				updatedData.setTempatLahir(data.getTempatLahir());
				updatedData.setTanggalLahir(data.getTanggalLahir());
				updatedData.setStatusKawin(data.getStatusKawin());
				updatedData.setJenisPekerjaan(data.getJenisPekerjaan());
				updatedData.setNamaIbu(data.getNamaIbu());
				updatedData.setAlamat(data.getAlamat());
				updatedData.setNoHp(data.getNoHp());
				updatedData.setEmail(data.getEmail());
				updatedData.setUpdatedBy(data.getUpdatedBy());
				
				Account getData = repo.save(updatedData);
				Map<String, Object> dataResult = new HashMap<>();
		        
				if (getData == null) {
		        	log.info("Account data gagal disimpan");
		        	return new ResponseEntity<>(null, HttpStatus.NOT_MODIFIED);
				}else {
					dataResult.put("data", updatedData);
			        ObjectWriter ow = new ObjectMapper()
		                    .setSerializationInclusion(JsonInclude.Include.NON_NULL)
		                    .writer();
			        log.info("Account data "+ow.writeValueAsString(dataResult));
			        return new ResponseEntity<>(dataResult, HttpStatus.OK);
				}
			}
		} catch (Exception e) {
			log.info(e.toString());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	public ResponseEntity<?> deleteAccount(UUID id){
		
		try {
				Account cekData = repo.findByUuid(id);
				
				if (cekData == null) {
					log.info("Account data tidak ditemukan");
		        	return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
				}else {
					repo.delete(cekData);
					log.info("Account data Berhasil dihapus");
					return new ResponseEntity<>(null, HttpStatus.OK);
				}
				
		} catch (Exception e) {
			log.info(e.toString());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}
