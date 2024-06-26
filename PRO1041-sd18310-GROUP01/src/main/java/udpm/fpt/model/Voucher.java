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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import udpm.fpt.model.Voucher;
/**
 *
 * @author Thanh Dat
 */
@Entity
@Table(name = "VOUCHER", schema = "dbo")
@Getter
@Setter
public class Voucher {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "TenPhieu")
    private String tenPhieu;

    @Column(name = "DieuKienGiam")
    private Double dieuKienGiam;

    @Column(name = "NgayBatDau")
    private Date ngayBatDau;

    @Column(name = "NgayKetThuc")
    private Date ngayKetThuc;

    @Column(name = "NgayTao")
    private Date ngayTao;

    @Column(name = "SoLuong")
    private int SoLuong;

    @Column(name = "TrangThai")
    private int TrangThai;

    @Column(name = "PhamTramGiam")
    private Double phamTramGiam;

}
