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
import udpm.fpt.model.BILL;

/**
 *
 * @author PHONG PC
 */
public interface IBill_Respository extends JpaRepository<BILL, String>{
    @Query("From BILL WHERE TrangThai like :status ORDER BY NgayTao desc")
    List<BILL> findBillTheoTrangThai(@Param("status") String string);
    
    @Query(value = "SELECT SUM(TongTienSauKhiGiam) FROM BILL WHERE CONVERT(DATE, NgayTao) = CONVERT(DATE, GETDATE())",  nativeQuery = true)
    Double tongTienHomNay();
    
    @Query(value = "SELECT SUM(TongTienSauKhiGiam) FROM BILL WHERE CONVERT(DATE, NgayTao) BETWEEN CONVERT(DATE, :timKiem) AND CONVERT(DATE, :timKiem2) and TrangThai = :trangThai",  nativeQuery = true)
    Double tongTien7Ngay(@Param("timKiem") Date timKiem, @Param("timKiem2") Date timKiem2, @Param("trangThai") String trangThai);
    
    @Query(value = "SELECT SUM(TongTienSauKhiGiam) FROM BILL WHERE YEAR(NgayTao) = :timKiem",  nativeQuery = true)
    Double tongTienTheoNam(@Param("timKiem") int timKiem);
    
    @Query(value = "SELECT SUM(TongTienSauKhiGiam) FROM BILL WHERE MONTH(NgayTao) = MONTH(GETDATE())",  nativeQuery = true)
    Double tongTienTheoThang();
    
    
    
    //Tông hóa đơn
    @Query(value = "SELECT count(id) FROM BILL WHERE  CONVERT(DATE, NgayTao) BETWEEN CONVERT(DATE, :timKiem) AND CONVERT(DATE, :timKiem2)",  nativeQuery = true)
    Double tongHoaDon(@Param("timKiem") Date timKiem, @Param("timKiem2") Date timKiem2);
    
    @Query(value = "SELECT count(id) FROM BILL WHERE  CONVERT(DATE, NgayTao) BETWEEN CONVERT(DATE, :timKiem) AND CONVERT(DATE, :timKiem2) and TrangThai = :trangThai",  nativeQuery = true)
    Double tongHoaDonThanhCong(@Param("timKiem") Date timKiem, @Param("timKiem2") Date timKiem2, @Param("trangThai") String trangThai);
    
    
    //Hóa đơn bị hủy
    @Query(value = "SELECT count(id) FROM BILL WHERE TrangThai = 3 and MONTH(NgayTao) = MONTH(GETDATE())",  nativeQuery = true)
    Double tongHoaDonHuy();
    
    @Query(value = "SELECT count(id) FROM BILL WHERE TrangThai = 3 and NgayTao between :fromDate and :toDate",  nativeQuery = true)
    Double tongHoaDonHuyTheoNgay(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    
    @Transactional
    @Modifying
    @Query("UPDATE BILL SET TongSanPham = TongSanPham + :soLuong1 where id = :idSanPham")
    int ChinhSuaSoLuong(@Param("soLuong1") int soLuong1, @Param("idSanPham") String id);
    
    //Tổng tiền từng tháng
    @Query(value = "SELECT SUM(TongTienSauKhiGiam) FROM BILL WHERE MONTH(NgayTao) = 1 AND YEAR(NgayTao) = :namTimKiem",  nativeQuery = true)
    Double tongTienTheoThang1(@Param("namTimKiem") int namTimKiem);
    @Query(value = "SELECT SUM(TongTienSauKhiGiam) FROM BILL WHERE MONTH(NgayTao) = 2 AND YEAR(NgayTao) = :namTimKiem",  nativeQuery = true)
    Double tongTienTheoThang2(@Param("namTimKiem") int namTimKiem);
    @Query(value = "SELECT SUM(TongTienSauKhiGiam) FROM BILL WHERE MONTH(NgayTao) = 3 AND YEAR(NgayTao) = :namTimKiem",  nativeQuery = true)
    Double tongTienTheoThang3(@Param("namTimKiem") int namTimKiem);
    @Query(value = "SELECT SUM(TongTienSauKhiGiam) FROM BILL WHERE MONTH(NgayTao) = 4 AND YEAR(NgayTao) = :namTimKiem",  nativeQuery = true)
    Double tongTienTheoThang4(@Param("namTimKiem") int namTimKiem);
    @Query(value = "SELECT SUM(TongTienSauKhiGiam) FROM BILL WHERE MONTH(NgayTao) = 5 AND YEAR(NgayTao) = :namTimKiem",  nativeQuery = true)
    Double tongTienTheoThang5(@Param("namTimKiem") int namTimKiem);
    @Query(value = "SELECT SUM(TongTienSauKhiGiam) FROM BILL WHERE MONTH(NgayTao) = 6 AND YEAR(NgayTao) = :namTimKiem",  nativeQuery = true)
    Double tongTienTheoThang6(@Param("namTimKiem") int namTimKiem);
    @Query(value = "SELECT SUM(TongTienSauKhiGiam) FROM BILL WHERE MONTH(NgayTao) = 7 AND YEAR(NgayTao) = :namTimKiem",  nativeQuery = true)
    Double tongTienTheoThang7(@Param("namTimKiem") int namTimKiem);
    @Query(value = "SELECT SUM(TongTienSauKhiGiam) FROM BILL WHERE MONTH(NgayTao) = 8 AND YEAR(NgayTao) = :namTimKiem",  nativeQuery = true)
    Double tongTienTheoThang8(@Param("namTimKiem") int namTimKiem);
    @Query(value = "SELECT SUM(TongTienSauKhiGiam) FROM BILL WHERE MONTH(NgayTao) = 9 AND YEAR(NgayTao) = :namTimKiem",  nativeQuery = true)
    Double tongTienTheoThang9(@Param("namTimKiem") int namTimKiem);
    @Query(value = "SELECT SUM(TongTienSauKhiGiam) FROM BILL WHERE MONTH(NgayTao) = 10 AND YEAR(NgayTao) = :namTimKiem",  nativeQuery = true)
    Double tongTienTheoThang10(@Param("namTimKiem") int namTimKiem);
    @Query(value = "SELECT SUM(TongTienSauKhiGiam) FROM BILL WHERE MONTH(NgayTao) = 11 AND YEAR(NgayTao) = :namTimKiem",  nativeQuery = true)
    Double tongTienTheoThang11(@Param("namTimKiem") int namTimKiem);
    @Query(value = "SELECT SUM(TongTienSauKhiGiam) FROM BILL WHERE MONTH(NgayTao) = 12 AND YEAR(NgayTao) = :namTimKiem",  nativeQuery = true)
    Double tongTienTheoThang12(@Param("namTimKiem") int namTimKiem);
    
    @Query(value = "SELECT * FROM BILL WHERE CONVERT(DATE, NgayTao) BETWEEN CONVERT(DATE, :timKiem) AND CONVERT(DATE, :timKiem2) AND TrangThai like CONCAT('%', :trangThai, '%')",  nativeQuery = true)
    List<BILL> findBillWhereTrangThaiAndNgay(@Param("timKiem") Date timKiem, @Param("timKiem2") Date timKiem2, @Param("trangThai") String trangThai);
    
    @Query(value = "SELECT * FROM BILL WHERE TrangThai like CONCAT('%', :trangThai, '%')",  nativeQuery = true)
    List<BILL> findBillWhereTrangThai(@Param("trangThai") String trangThai);
//    @Query("From Bill WHERE id like CONCAT('%', :idBill, '%')")
//    BILL findBill(@Param("idBill") String idBill);
//    
//    @Transactional
//    @Modifying
//    @Query("UPDATE BILL SET TongTien = :tongTien, id_NhanVien = :idNhanVien, KhachHang = :idKhachHang, TrangThai = false WHERE id like :billId")
//    int updateQuantity(@Param("tongTien") double tongTien, @Param("idNhanVien") Integer idNhanVien,  @Param("idKhachHang") String idKhachHang, @Param("billId") String id);
    
}
