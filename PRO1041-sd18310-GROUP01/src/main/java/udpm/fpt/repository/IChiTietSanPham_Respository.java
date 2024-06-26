package udpm.fpt.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import udpm.fpt.model.SANPHAM;
import udpm.fpt.model.SANPHAMCHITIET;

/**
 *
 * @author NONG HOANG VU
 */
public interface IChiTietSanPham_Respository extends JpaRepository<SANPHAMCHITIET, Integer> {

    @Query("From SANPHAMCHITIET spct "
            + "inner join SANPHAM sp on spct.id_SanPham.id = sp.id "
            + "inner join MAUSAC ms on spct.id_MauSac.id = ms.id "
            + "inner join SIZE s on spct.id_Size.id = s.id "
            + "inner join KieuDang kd on spct.id_KieuDang.id = kd.id "
            + "where spct.TrangThai = true and sp.TenSanPham like CONCAT('%', :nameProduct, '%') "
            + "and ms.TenMauSac like CONCAT('%', :nameColor, '%') "
            + "and s.TenSize like CONCAT('%', :nameSize, '%') "
            + "and kd.TenKieuDang like CONCAT('%', :nameKieuDang, '%')")
    List<SANPHAMCHITIET> timKiemTongHop( @Param("nameProduct") String nameProduct, @Param("nameColor") String nameColor, @Param("nameSize") String nameSize, @Param("nameKieuDang") String nameKieuDang);

//    @Query("From SANPHAMCHITIET spct "
//            +"inner join SANPHAM sp on spct.id_SanPham.id = sp.id "
//            +"inner join MAUSAC ms on spct.id_MauSac.id = ms.id "
//            +"inner join SIZE s on spct.id_Size.id = s.id "
//            +"where sp.TenSanPham like CONCAT('%', :nameProduct, '%') "
//            +"and ms.TenMauSac like CONCAT('%', :nameColor, '%') "
//            +"and s.TenSize like CONCAT('%', :nameSize, '%')")
//    List<SANPHAMCHITIET> listSanPham(@Param("nameProduct") String nameProduct, @Param("nameColor") String nameColor, @Param("nameSize") String nameSize);
    @Transactional
    @Modifying
    @Query("UPDATE SANPHAMCHITIET SET SoLuong = SoLuong - :soLuong1 where id = :idSanPham")
    int ChinhSuaSoLuong(@Param("soLuong1") int soLuong1, @Param("idSanPham") Integer id);

    @Transactional
    @Modifying
    @Query("UPDATE SANPHAMCHITIET SET SoLuong = SoLuong + :soLuong where id = :idSanPham")
    int updateQuantity(@Param("soLuong") int soLuong, @Param("idSanPham") Integer id);

    @Query("From SANPHAMCHITIET where TrangThai = true")
    List<SANPHAMCHITIET> findAllSanPham();
}
