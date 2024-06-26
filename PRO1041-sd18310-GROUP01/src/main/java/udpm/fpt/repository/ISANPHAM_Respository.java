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
import udpm.fpt.model.SANPHAM;

/**
 *
 * @author PHONG PC
 */
public interface ISANPHAM_Respository extends JpaRepository<SANPHAM, Integer> {
    @Transactional
    @Modifying
    @Query("UPDATE SANPHAM SET SoLuong = SoLuong + :soLuong where id = :idSanPham")
    int updateQuantity(@Param("soLuong") int soLuong, @Param("idSanPham") Integer id);
    
    @Transactional
    @Modifying
    @Query("UPDATE SANPHAM SET SoLuong = SoLuong - :soLuong1 where id = :idSanPham")
    int ChinhSuaSoLuong(@Param("soLuong1") int soLuong1, @Param("idSanPham") Integer id);
    
    @Transactional
    @Modifying
    @Query("UPDATE SANPHAM SET SoLuong = SoLuong - :soLuong1 + :soLuong2 where id = :idSanPham")
    int updateQuantityPart2(@Param("soLuong1") int soLuong1, @Param("soLuong2") int soLuong2, @Param("idSanPham") Integer id);
    
    @Query("From SANPHAM where TrangThai like :hoatDong ORDER BY NgayTao desc")
    List<SANPHAM> findAllSanPham(@Param("hoatDong") String hoatDong);
}
