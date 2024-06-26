/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package udpm.fpt.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import udpm.fpt.model.BILL;
import udpm.fpt.model.Customer;
import udpm.fpt.model.KhachHang;

/**
 *
 * @author PHONG PC
 */
public interface ICustomer_Respository extends JpaRepository<KhachHang, Integer>{
    @Query("From KhachHang Where TrangThai = true")
    List<KhachHang> findKhachHangTheoTrangThai();
    
    @Transactional
    @Modifying
    @Query("UPDATE KhachHang SET TrangThai = false where id = :idSanPham")
    int ChinhSuaTrangThai(@Param("idSanPham") Integer id);
}
