
package ci.itech.oedatarepo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "app_user")
public class AppUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "email", nullable = false, length = 255)
	@NotEmpty
	@Email
	private String email;

	@Column(name = "password", nullable = false, length = 100)
	private String password;

	@Column(name = "password_reset")
	private boolean passwordReset = true;

	@Column(name = "active")
	private boolean active = true;

	@Column(name = "locked")
	private boolean locked = false;

	@Column(name = "first_name", length = 45)
	private String firstName;

	@Column(name = "last_name", length = 45)
	private String lastName;

	@Column(name = "phone_contact", nullable = false, length = 45)
	@Pattern(regexp = "^((00225)?|(\\+225)?)([\\d]{10})$", message = "Numéro de phone invalide !")
	private String phoneContact;

	@Column(name = "role", length = 45)
	private String role;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_login")
	private Date lastLogin;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "password_expire_at")
	private Date passwordExpireAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "created_by")
	private Integer createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_updated_at")
	private Date lastUpdatedAt;

	@Column(name = "last_updated_by")
	private Integer lastUpdatedBy;

	@ManyToMany(targetEntity = Site.class)
	@JoinTable(name = "app_user_has_site", joinColumns = @JoinColumn(name = "app_user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "site_id", referencedColumnName = "id"))
	@JsonManagedReference
	private List<Site> listOfSite = new ArrayList<Site>();

	public String getAuthorities() {
		return this.getRole();
	}

}
