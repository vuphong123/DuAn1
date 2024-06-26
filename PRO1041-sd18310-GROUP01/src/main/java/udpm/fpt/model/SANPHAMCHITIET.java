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
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author PHONG PC
 */
@Entity
@Table(name = "SANPHAMCHITIET")
@Getter
@Setter
public class SANPHAMCHITIET {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @OneToOne
    @JoinColumn(name = "id_MauSac")
    private MAUSAC id_MauSac;
    
    @OneToOne
    @JoinColumn(name = "id_Size")
    private SIZE id_Size;
    
    @OneToOne
    @JoinColumn(name = "id_ChatLieu")
    private ChatLieu id_ChatLieu;

    @OneToOne
    @JoinColumn(name = "id_SanPham")
    private SANPHAM id_SanPham;

    @Column(name = "SoLuong")
    private Integer SoLuong;

    @Column(name = "TrangThai")
    private Boolean TrangThai;

    @Column(name = "NgayTao")
    private Date ngayTao;

    @Column(name = "NgaySua")
    private Date ngaySua;
    
    @Column(name = "GiaSanPham")
    private Double giaSanPham;
    // Constructors, getters, setters, and other methods

    // ..
    @OneToOne
    @JoinColumn(name = "id_KieuDang")
    private KieuDang id_KieuDang;
}
