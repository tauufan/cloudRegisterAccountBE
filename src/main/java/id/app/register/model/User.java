package id.app.register.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Entity
@Table(name = "user_rest")
@Data
public class User {
	@Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(hidden = true)
	private Long id;
	@NotEmpty(message = "Tidak boleh kosong!")
	private String username;
	private String password;
	@ApiModelProperty(example = "ROLE_ADMIN / ROLE_USER")
	private String role;
    private boolean enabled;

//    public User() {
//    	
//    }
//    
//    public User(String username,String password, String role, boolean enabled) {
//		super();
//		this.username = username;
//		this.password = password;
//		this.role = role;
//		this.enabled = enabled;
//	}
//    
//	public User(String username, String role, boolean enabled) {
//		super();
//		this.username = username;
//		this.role = role;
//		this.enabled = enabled;
//	}
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
//	public String getUsername() {
//		return username;
//	}
//	public void setUsername(String username) {
//		this.username = username;
//	}
//	public String getPassword() {
//		return password;
//	}
//	public void setPassword(String password) {
//		this.password = password;
//	}
//	public String getRole() {
//		return role;
//	}
//	public void setRole(String role) {
//		this.role = role;
//	}
//	public boolean isEnabled() {
//		return enabled;
//	}
//	public void setEnabled(boolean enabled) {
//		this.enabled = enabled;
//	}
}
