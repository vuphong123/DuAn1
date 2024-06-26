package udpm.fpt.model;

import jakarta.persistence.*;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "SANPHAM")
@Getter
@Setter
public class SANPHAM {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "TenSanPham")
    private String TenSanPham;

    @Column(name = "MoTa", columnDefinition = "NVARCHAR(MAX)")
    private String moTa;

    @Column(name = "TrangThai")
    private String TrangThai;

    @Column(name = "NgayTao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date NgayTao;

    @Column(name = "NgaySua")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ngaySua;
    
    @Column(name = "SoLuong")
    private int SoLuong;
    
    // Constructors, getters, and setters
}
