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
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author dangc
 */
@Entity
@Table(name = "KHACHHANG")
@Getter
@Setter
public class KhachHang { 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "TenKhachHang")
    private String tenKhachHang;
    
    @Column(name = "sdt")
    private String sdt;
    
    @Column(name = "gioiTinh")
    private int gioiTinh;
    
    @Column(name = "TrangThai")
    private Boolean TrangThai;
    
    @Column(name = "NgayTao")
    private Date ngayTao;
    
    @Column(name = "NgaySua")
    private Date ngaySua;
    
    @Column(name = "Gmail")
    private String Gmail;
}
