package udpm.fpt.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import udpm.fpt.Application;
import static udpm.fpt.Application.getBean;
import udpm.fpt.model.BILL;
import udpm.fpt.model.BILLCHITIET;
import udpm.fpt.repository.IBillDetails_Respository;
import udpm.fpt.repository.IBill_Respository;

/**
 * @author NONG HOANG VU
 */
public class HistoryService {
    IBill_Respository itBill = getBean(IBill_Respository.class);
    IBillDetails_Respository itBillDetail = getBean(IBillDetails_Respository.class);
    
    public CompletableFuture<List<BILL>> findAllBill() {
        return CompletableFuture.supplyAsync(() -> {
            return itBill.findAll();
        }, Executors.newCachedThreadPool());
    }
    
    public CompletableFuture<List<BILLCHITIET>> findAllBillChiTiet() {
        return CompletableFuture.supplyAsync(() -> {
            return itBillDetail.findAll();
        }, Executors.newCachedThreadPool());
    }
    
    public CompletableFuture<List<BILL>> findAllBillWhereNgayAndTrangThai(Date timKiem, Date timKiem2, String trangThai) {
        return CompletableFuture.supplyAsync(() -> {
            return itBill.findBillWhereTrangThaiAndNgay(timKiem, timKiem2, trangThai);
        }, Executors.newCachedThreadPool());
    }
    
    public CompletableFuture<List<BILL>> findAllBillWhereTrangThai(String trangThai) {
        return CompletableFuture.supplyAsync(() -> {
            return itBill.findBillWhereTrangThai(trangThai);
        }, Executors.newCachedThreadPool());
    }
    
    
}