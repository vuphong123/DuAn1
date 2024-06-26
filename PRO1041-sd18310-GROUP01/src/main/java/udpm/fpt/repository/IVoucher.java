/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package udpm.fpt.repository;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import udpm.fpt.model.Voucher;
/**
 *
 * @author Thanh Dat
 */
public interface IVoucher extends JpaRepository<Voucher, Integer > {
    @Query("Select vc from Voucher vc where vc.ngayKetThuc <= :ed")
    List<Voucher> findByEndDate(@Param("ed")Date ed);
    
    @Query("From Voucher where TrangThai < 3")
    List<Voucher> Voucher();
    
    //Voucher cho hóa đơn
    @Query("From Voucher where TrangThai = 1 and SoLuong >0")
    List<Voucher> findAllVoucher();
     
    @Transactional
    @Modifying
    @Query("UPDATE Voucher SET SoLuong = SoLuong - 1 WHERE id = :id")
    int updateQuantity(@Param("id") Integer id);
    
    @Transactional
    @Modifying
    @Query("UPDATE Voucher SET TrangThai=4 WHERE id = :id")
    int updateTrangThai(@Param("id") Integer id);
}
