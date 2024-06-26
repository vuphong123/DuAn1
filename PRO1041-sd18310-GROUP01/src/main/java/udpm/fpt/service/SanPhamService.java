/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udpm.fpt.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import static udpm.fpt.Application.getBean;
import udpm.fpt.model.ChatLieu;
import udpm.fpt.model.KieuDang;
import udpm.fpt.model.MAUSAC;
import udpm.fpt.model.SANPHAM;
import udpm.fpt.model.SANPHAMCHITIET;
import udpm.fpt.model.SIZE;
import udpm.fpt.repository.IChatLieu;
import udpm.fpt.repository.IChiTietSanPham_Respository;
import udpm.fpt.repository.IKieuDang;
import udpm.fpt.repository.IMauSac;
import udpm.fpt.repository.ISANPHAM_Respository;
import udpm.fpt.repository.ISize;

/**
 *
 * @author PHONG PC
 */
public class SanPhamService {

    ISANPHAM_Respository itSanPham = getBean(ISANPHAM_Respository.class);
    IChiTietSanPham_Respository itSanPhamChiTiet = getBean(IChiTietSanPham_Respository.class);
    ISize itSize = getBean(ISize.class);
    IMauSac itMauSac = getBean(IMauSac.class);
    IChatLieu itChatLieu = getBean(IChatLieu.class);
    IKieuDang itKieuDang = getBean(IKieuDang.class);

    //Sản phẩm
    public CompletableFuture<List<SANPHAM>> findALlSanPham() {
        return CompletableFuture.supplyAsync(() -> {
            return itSanPham.findAll();
        }, Executors.newCachedThreadPool());
    }
    
    public CompletableFuture<List<SANPHAM>> findSanPhamHoatDong() {
        return CompletableFuture.supplyAsync(() -> {
            return itSanPham.findAllSanPham("Hoạt động");
        }, Executors.newCachedThreadPool());
    }

    public SANPHAM saveAndUpdateSanPham(SANPHAM sp) {
        return itSanPham.save(sp);
    }

    public boolean deleteSanPham(SANPHAM sp) {
        try {
            itSanPham.delete(sp);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean updateSoLuongSanPham(int soLuong, Integer idSanPham){
        return itSanPham.updateQuantity(soLuong, idSanPham) !=0 ;
    } 
    
    public boolean updateSoLuongSanPham2(int soLuong1 ,int soLuong2, Integer idSanPham){
        return itSanPham.updateQuantityPart2(soLuong1, soLuong2, idSanPham) !=0 ;
    }
    
    public boolean truSoLuong(int soLuong1 , Integer idSanPham){
        return itSanPham.ChinhSuaSoLuong(soLuong1, idSanPham) !=0 ;
    } 
    // Sản phẩm chi tiết
    public CompletableFuture<List<SANPHAMCHITIET>> findALlSanPhamChiTiet() {
        return CompletableFuture.supplyAsync(() -> {
            return itSanPhamChiTiet.findAll();
        }, Executors.newCachedThreadPool());
    }
    
    public SANPHAMCHITIET saveAndUpdateSanPhamChiTiet(SANPHAMCHITIET sp) {
        return itSanPhamChiTiet.save(sp);
    }
    
    // Size
    public CompletableFuture<List<SIZE>> findALlSize() {
        return CompletableFuture.supplyAsync(() -> {
            return itSize.findAll();
        }, Executors.newCachedThreadPool());
    }
    
    public SIZE insertAndUpdateSize(SIZE s){
        return itSize.save(s);
    }

    //Màu sắc
    public CompletableFuture<List<MAUSAC>> findALlMauSac() {
        return CompletableFuture.supplyAsync(() -> {
            return itMauSac.findAll();
        }, Executors.newCachedThreadPool());
    }
    
    public MAUSAC insertAndUpdateMauSac(MAUSAC s){
        return itMauSac.save(s);
    }
    
    //Chất liệu
    public CompletableFuture<List<ChatLieu>> findALChatLieu() {
        return CompletableFuture.supplyAsync(() -> {
            return itChatLieu.findAll();
        }, Executors.newCachedThreadPool());
    }
    
    public ChatLieu insertAndUpdateChatLieu(ChatLieu s){
        return itChatLieu.save(s);
    }
    
    //Kiểu dáng 
    public CompletableFuture<List<KieuDang>> findAllKieuDang() {
        return CompletableFuture.supplyAsync(() -> {
            return itKieuDang.findAll();
        }, Executors.newCachedThreadPool());
    }
    
    public KieuDang insertAndUpdateKieuDang(KieuDang s){
        return itKieuDang.save(s);
    }
}
