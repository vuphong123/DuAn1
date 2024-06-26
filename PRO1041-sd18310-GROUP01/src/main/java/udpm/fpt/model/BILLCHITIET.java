/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udpm.fpt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author PHONG PC
 */
@Entity
@Table
@Getter
@Setter
public class BILLCHITIET {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @OneToOne
    @JoinColumn(name = "id_Bill")
    private BILL idBill;
    
    @OneToOne
    @JoinColumn(name = "id_SanPhamChiTIet")
    private SANPHAMCHITIET idSanPhamChiTiet;

    @Column(name = "SoLuongDaMua")
    private Integer soLuongDaMua;

    @Column(name = "ThanhTien")
    private Double ThanhTien;

    @Column(name = "GiaTienHienTai")
    private Double giaTienHienTai;

    @Column(name = "NgayTao")
    private Date ngayTao;

    @Column(name = "NgaySua")
    private Date ngaySua;
}
