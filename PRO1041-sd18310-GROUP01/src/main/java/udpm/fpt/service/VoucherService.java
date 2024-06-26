/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udpm.fpt.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import static udpm.fpt.Application.getBean;
import udpm.fpt.model.BILL;
import udpm.fpt.model.KhachHang;
import udpm.fpt.model.Voucher;
import udpm.fpt.repository.ICustomer_Respository;
import udpm.fpt.repository.IVoucher;

/**
 *
 * @author Thanh Dat
 */
public class VoucherService {
    private IVoucher ivc = getBean(IVoucher.class);
    private ICustomer_Respository iKH = getBean(ICustomer_Respository.class);
    public VoucherService(){}
    public List<Voucher> getAll(){
        return this.ivc.Voucher();
    }
    
    public boolean addNew(Voucher vc){
        return this.ivc.save(vc) != null;
    }
    public boolean deletedVoucher(int ID){
        try{
            this.ivc.deleteById(ID);
            return true;
        }catch(Exception ex){
            return false;
        }
    }
    public boolean updateVoucher(Voucher vc){
        return this.ivc.save(vc) != null;
    }
    public List<Voucher> Search(Date ed){
        return this.ivc.findByEndDate(ed);
    }
    public int deleteVoucher(Integer id){
        return this.ivc.updateTrangThai(id);
    }
    
    public CompletableFuture<List<KhachHang>> finAllKhachHang() {
        return CompletableFuture.supplyAsync(() -> {
            return iKH.findKhachHangTheoTrangThai();
        }, Executors.newCachedThreadPool());
    }
    
}
