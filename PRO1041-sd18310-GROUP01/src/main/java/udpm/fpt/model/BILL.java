 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udpm.fpt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author PHONG PC
 */
@Table
@Entity
@Getter
@Setter
public class BILL {
    
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "TongTien")
    private Double TongTien;

    @OneToOne
    @JoinColumn(name = "id_NhanVien")
    private User id_NhanVien;
    
    @OneToOne
    @JoinColumn(name = "id_KhachHang")
    private KhachHang id_KhachHang;

    @Column(name = "TrangThai")
    private String TrangThai;

    @Column(name = "NgayTao")
    private Date NgayTao;

    @Column(name = "NgaySua")
    private Date NgaySua;

    @Column(name = "id_Voucher")
    private Integer id_Voucher;

    @Column(name = "TongSanPham")
    private int TongSanPham;
    
    @Column(name = "TienGiam")
    private Double TienGiam;
    
    @Column(name = "tienKhachTra")
    private Double tienKhachTra;
    
    @Column(name = "TongTienSauKhiGiam")
    private Double TongTienSauKhiGiam;
    
    @Column(name = "TienTraLai")
    private Double TienTraLai;
}
