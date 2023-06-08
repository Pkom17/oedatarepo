package ci.itech.oedatarepo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@ToString
@Entity
@Table(name = "site")
public class Site implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name", nullable = false, length = 255)
	private String name;

	@Column(name = "diis_code", length = 255,nullable = true)
	private String diisCode;

	@Column(name = "datim_name", length = 255,nullable = true)
	private String datimName;

	@Column(name = "datim_code", length = 255,nullable = true)
	private String datimCode;

	@Column(name = "longitude", nullable = true)
	private Float longitude;

	@Column(name = "latitude", nullable = true)
	private Float latitude;

	@Column(name = "district_id", nullable = true)
	private Integer districtId;

	@ManyToOne
	@JoinColumn(name = "district_id", referencedColumnName = "id", insertable = false, updatable = false)
	private District district;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_updated_at",nullable = true)
	private Date lastUpdatedAt;

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(id);
		sb.append("|");
		sb.append(name);
		sb.append("|");
		sb.append(diisCode);
		sb.append("|");
		sb.append(datimName);
		sb.append("|");
		sb.append(datimCode);
		sb.append("|");
		sb.append(longitude);
		sb.append("|");
		sb.append(latitude);
		sb.append("|");
		sb.append(districtId);
		return sb.toString();
	}

}
