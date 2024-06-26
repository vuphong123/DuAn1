/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udpm.serviceDelivery;

/**
 *
 * @author PHONG PC
 */
public class ThongKeModel {
    private Integer id;
    private String tenSanPham;
    private Integer soLuong;
    private Integer soLuongDaMua;
    private Double thanhTien;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public Integer getSoLuongDaMua() {
        return soLuongDaMua;
    }

    public void setSoLuongDaMua(Integer soLuongDaMua) {
        this.soLuongDaMua = soLuongDaMua;
    }

    public Double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(Double thanhTien) {
        this.thanhTien = thanhTien;
    }
    
    
    public ThongKeModel() {
    }

    public ThongKeModel(Integer id, String tenSanPham, Integer soLuong, Integer soLuongDaMua, Double thanhTien) {
        this.id = id;
        this.tenSanPham = tenSanPham;
        this.soLuong = soLuong;
        this.soLuongDaMua = soLuongDaMua;
        this.thanhTien = thanhTien;
    }
    
}
