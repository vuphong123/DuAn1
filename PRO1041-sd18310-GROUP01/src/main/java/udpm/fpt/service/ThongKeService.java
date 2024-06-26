/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udpm.fpt.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import static udpm.fpt.Application.getBean;
import udpm.fpt.model.BILLCHITIET;
import udpm.fpt.repository.IBillDetails_Respository;
import udpm.fpt.repository.IBill_Respository;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import udpm.fpt.config.DBConnect1;
import udpm.serviceDelivery.ThongKeModel;

/**
 *
 * @author PHONG PC
 */
public class ThongKeService {

    IBill_Respository itBill = getBean(IBill_Respository.class);
    IBillDetails_Respository itBillChiTiet = getBean(IBillDetails_Respository.class);

    public List<ThongKeModel> getALL() {
        String sql = "SELECT sp.id, sp.TenSanPham, sp.SoLuong, sum(bct.SoLuongDaMua) , sum(bct.ThanhTien)  from BILL b "
                + "inner join BILLCHITIET bct on b.id = bct.id_Bill "
                + "inner join SANPHAM sp on bct.id_SanPhamChiTIet = sp.id "
                + "where b.TrangThai like N'Đã thanh toán' "
                + "group by sp.id, sp.TenSanPham , sp.SoLuong order by  sum(bct.SoLuongDaMua) desc";
        try {
            Connection con = DBConnect1.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.execute();
            ResultSet rs = ps.executeQuery();
            List<ThongKeModel> listThongKe = new ArrayList<>();
            while (rs.next()) {
                ThongKeModel tk = new ThongKeModel(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getDouble(5));
                listThongKe.add(tk);
            }
            return listThongKe;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
