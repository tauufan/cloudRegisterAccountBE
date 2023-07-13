package id.app.register.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import id.app.register.model.AuditModel;

@Entity
@Table(name = "account")
@Data
public class Account extends AuditModel{
	
	@Id
	@Type(type = "uuid-char")
	@Column(name = "account_id")
	@ApiModelProperty(value = "uuid", hidden = true)
	private UUID uuid = UUID.randomUUID();
	
	@NotEmpty(message = "Tidak boleh kosong!")
	@Column(name = "nama_lengkap")
	private String namaLengkap;
	
	@NotEmpty(message = "Tidak boleh kosong!")
//	@Column(name = "nik")
	@ApiModelProperty(example = "1571XXXXXXXXXXXX")
	private String nik;
	
	@NotEmpty(message = "Tidak boleh kosong!")
	@Column(name = "jenis_kelamin")
	private String jenisKelamin;
	
	@NotEmpty(message = "Tidak boleh kosong!")
	@Column(name = "tempat_lahir")
	private String tempatLahir;
	
//	@NotEmpty(message = "Tidak boleh kosong!")
	@Column(name = "tanggal_lahir")
	@JsonFormat(pattern = "dd-MM-yyyy", timezone = "Asia/Jakarta")
	@ApiModelProperty(example = "dd-mm-yyyy")
	private Date tanggalLahir;
	
//	@NotEmpty(message = "Tidak boleh kosong!")
	@Column(name = "status_kawin")
	private String statusKawin;
	
//	@NotEmpty(message = "Tidak boleh kosong!")
	@Column(name = "jenis_pekerjaan")
	private String jenisPekerjaan;
	
	@NotEmpty(message = "Tidak boleh kosong!")
	@Column(name = "nama_ibu")
	private String namaIbu;
	
	@NotEmpty(message = "Tidak boleh kosong!")
	private String alamat;
	
	@NotEmpty(message = "Tidak boleh kosong!")
	@Column(name = "no_hp")
	private String noHp;
	
//	@NotEmpty(message = "Tidak boleh kosong!")
	private String email;
}
