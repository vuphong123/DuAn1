package udpm.fpt.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author NONG HOANG VU
 */
@Entity
@Table(name = "ProductInfo")
@Getter
@Setter

public class ProductInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "milk_id")
    private SANPHAM milk;

    @OneToOne
    @JoinColumn(name = "flavor_id")
    private Flavor flavor;

    @Column(name = "brand")
    private String brand;

    @Column(name = "volume")
    private Double volume;

    @OneToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @Column(name = "origin")
    private String origin;

    @Column(name = "composition")
    private String composition;

    @OneToOne
    @JoinColumn(name = "packaging_id")
    private PackagingSpecification packagingSpecification;

    @Column(name = "product_description")
    private String product_description;

    @Column(name = "create_at")
    private Date create_at;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
