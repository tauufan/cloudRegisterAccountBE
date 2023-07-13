package id.app.register;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import id.app.register.model.Account;
import id.app.register.model.User;
import id.app.register.repository.AccountRepository;
import id.app.register.repository.UsersRepository;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class RegisterAccountApplication {

	@Autowired
	UsersRepository repo;
	
	@Autowired
	AccountRepository accountRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(RegisterAccountApplication.class, args);
	}
	
	@Bean
	CommandLineRunner initDatabase(UsersRepository repository) {
		return args -> {
			try {
				User cekUser = repo.findByUsername_("admin");
				if (cekUser == null) {
					User newUser = new User();
					newUser.setUsername("admin");
					newUser.setEnabled(true);
					newUser.setRole("ROLE_ADMIN");
					newUser.setPassword("$2a$10$drSjtr4.KeXAcy8LqgMtdeYqfLjiNeKyWJo6OLyLN1g9jpaZnx1Ge");
					repository.save(newUser);
					System.out.println("+++++++++ User akses Tercreate ++++++++++");
				} else {
					System.out.println("+++++++++ User akses telah tersedia ++++++++++");
				}
				
				List<Account> cekAcc = accountRepo.findByNamaLengkap("TAMI");
				
				if (cekAcc.size() < 1) {
					Account newAcc = new Account();
					newAcc.setNamaLengkap("TAMI");
					newAcc.setNik("320XXXXXXXXXXXXX");
					newAcc.setJenisKelamin("PEREMPUAN");
					newAcc.setAlamat("JL NARIPAN No.12");
					newAcc.setTempatLahir("BANDUNG");
					SimpleDateFormat formatter1=new SimpleDateFormat("dd-MM-yyyy"); 
					Date date1=formatter1.parse("01-01-2000");  
					newAcc.setTanggalLahir(date1);
					newAcc.setStatusKawin("BELUM MENIKAH");
					newAcc.setNoHp("08XXXXXXXXXX");
					newAcc.setEmail("tami@bankbjb.com");
					newAcc.setNamaIbu("EUIS");
					newAcc.setJenisPekerjaan("PEGAWAI BANK BJB");
					accountRepo.save(newAcc);
					System.out.println("+++++++++ Account telah Tercreate ++++++++++");
				} else {
					System.out.println("+++++++++ Account telah tersedia ++++++++++");
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		};

	}
}
