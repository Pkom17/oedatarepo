package ci.itech.oedatarepo.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "district")
public class District implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "region_id", nullable = true)
    private Integer regionId;

    @ManyToOne
    @JoinColumn(name = "region_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Region region;

    @OneToMany(mappedBy = "district")
    private List<Site> listOfSite;

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id);
        sb.append("|");
        sb.append(name);
        sb.append("|");
        sb.append(regionId);
        return sb.toString();
    }

}
