package ci.itech.oedatarepo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// @ToString
@Entity
@Table(name = "platform")
public class PlatformPrefix implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "platform_id", nullable = false)
    private Integer platformId;

    @ManyToOne
    @JoinColumn(name = "platform_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Platform platform;

    @Column(name = "prefix", nullable = false)
    private String prefix;

    @Column(name = "study", nullable = true)
    private String study;

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id);
        sb.append("|");
        sb.append(platformId);
        sb.append("|");
        sb.append(prefix);
        sb.append("|");
        sb.append(study);
        return sb.toString();
    }
}