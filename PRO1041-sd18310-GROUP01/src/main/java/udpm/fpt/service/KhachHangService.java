/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udpm.fpt.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import static udpm.fpt.Application.getBean;
import udpm.fpt.model.KhachHang;
import udpm.fpt.model.Voucher;
import udpm.fpt.repository.ICustomer_Respository;

/**
 *
 * @author PHONG PC
 */
public class KhachHangService {
    ICustomer_Respository ITKhachHang = getBean(ICustomer_Respository.class);
    
    public CompletableFuture<List<KhachHang>> finAllKhachHang() {
        return CompletableFuture.supplyAsync(() -> {
            return ITKhachHang.findKhachHangTheoTrangThai();
        }, Executors.newCachedThreadPool());
    }
    
    public boolean deleteKhachHang(Integer id){
        try{
            ITKhachHang.ChinhSuaTrangThai(id);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    public KhachHang themSuaKhachHang(KhachHang kh){
        return ITKhachHang.save(kh);
    }
//    public List<KhachHang> search(){
//        
//    }
}
