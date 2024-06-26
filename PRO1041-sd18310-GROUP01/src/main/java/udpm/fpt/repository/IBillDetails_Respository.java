/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package udpm.fpt.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import udpm.fpt.model.BILLCHITIET;

/**
 *
 * @author PHONG PC
 */
public interface IBillDetails_Respository extends JpaRepository<BILLCHITIET, Integer>{
//    @Query(value = "SELECT bct.id_SanPhamChiTiet.id_SanPham.id ,bct.id_SanPhamChiTiet.id_SanPham.TenSanPham, bct.id_SanPhamChiTiet.id_SanPham.SoLuong, sum(bct.SoLuongDaMua) as N'SoLuong' , sum(bct.ThanhTien) as N'ThanhTien' FROM BILLCHITIET bct "
//            + "inner join BILL b on b.id = bct.id_Bill.id"
//            + "inner join SANPHAM sp on bct.id_SanPhamChiTiet.id = sp.id "
//            + "where b.TrangThai like :trangThai "
//            + "GROUP BY bct.id_SanPhamChiTiet.id_SanPham.id ,bct.id_SanPhamChiTiet.id_SanPham.TenSanPham, bct.id_SanPhamChiTiet.id_SanPham.SoLuong",  nativeQuery = true)
//    List<BILLCHITIET> tongHoaDon(@Pa);
}
