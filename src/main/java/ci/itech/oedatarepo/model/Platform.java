package ci.itech.oedatarepo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "platform")
public class Platform implements Serializable {
 
private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id ;

    @Column(name="name", nullable=true, length=255)
    private String name ;

    @Column(name="longitude",nullable = true)
    private Float longitude ;

    @Column(name="latitude",nullable = true)
    private Float latitude ;

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_pushed_at",nullable = true)
	private Date lastPushedAt;

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_updated_at",nullable = true)
	private Date lastUpdatedAt;

    public String toString() {
        StringBuilder sb = new StringBuilder(); 
        sb.append(id);
        sb.append("|");
        sb.append(longitude);
        sb.append("|");
        sb.append(latitude);
        sb.append("|");
        sb.append(lastPushedAt);
        sb.append("|");
        sb.append(lastUpdatedAt);
        return sb.toString(); 
    } 

}



