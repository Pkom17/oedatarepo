package ci.itech.oedatarepo.dto;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	private Integer id;
	private String lastName;
	private String firstName;
	@Email
	private String email;
	@Pattern(regexp = "^((00225)?|(\\+225)?)([\\d]{10})$", message = "Numéro de phone invalide !")
	private String phoneContact;
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$", message = "Doit contenir au moins 8 caractères, une majuscule, une minuscule, un chiffre et un caractère spécial (! @ # $ % & *)")
	private String password;
	@NotBlank
	private String repassword;
	private boolean active = true;
	private boolean locked = false;
	@DateTimeFormat(pattern="dd/MM/YYYY")
	private Date passwordExpireAt;
	private String role;
	private String sites;
	private String districts;
}
