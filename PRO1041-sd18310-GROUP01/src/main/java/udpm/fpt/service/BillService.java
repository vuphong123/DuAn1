/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udpm.fpt.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import static udpm.fpt.Application.getBean;
import udpm.fpt.model.BILL;
import udpm.fpt.model.BILLCHITIET;
import udpm.fpt.model.KhachHang;
import udpm.fpt.model.KieuDang;
import udpm.fpt.model.MAUSAC;
import udpm.fpt.model.SANPHAM;
import udpm.fpt.model.SANPHAMCHITIET;
import udpm.fpt.model.SIZE;
import udpm.fpt.model.Voucher;
import udpm.fpt.repository.IChiTietSanPham_Respository;
import udpm.fpt.repository.ICustomer_Respository;
import udpm.fpt.repository.IKieuDang;
import udpm.fpt.repository.IMauSac;
import udpm.fpt.repository.ISANPHAM_Respository;
import udpm.fpt.repository.ISize;
import udpm.fpt.repository.IVoucher;
import udpm.fpt.repository.IBillDetails_Repository;
import udpm.fpt.repository.IBill_Repository;

/**
 *
 * @author PHONG PC
 */
public class BillService {

    private IChiTietSanPham_Respository spct = getBean(IChiTietSanPham_Respository.class);
    private ISANPHAM_Respository sp = getBean(ISANPHAM_Respository.class);
    private ICustomer_Respository kh = getBean(ICustomer_Respository.class);
    private IBill_Repository HD = getBean(IBill_Repository.class);
    private IBillDetails_Repository HDCT = getBean(IBillDetails_Repository.class);
    private IVoucher voucher = getBean(IVoucher.class);
    private IMauSac itMS = getBean(IMauSac.class);
    private IKieuDang itKD = getBean(IKieuDang.class);
    private ISize itS = getBean(ISize.class);
    public String generateRandomNumber() {
        List<Integer> digits = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            digits.add(i);
        }

        Collections.shuffle(digits);

        StringBuilder randomNumber = new StringBuilder();
        Random random = new Random();

        int codeLength = 6;
        for (int i = 0; i < codeLength; i++) {
            int randomDigit = digits.get(random.nextInt(digits.size()));

            // Đảm bảo số không trùng
            while (randomNumber.toString().contains(String.valueOf(randomDigit))) {
                randomDigit = digits.get(random.nextInt(digits.size()));
            }

            randomNumber.append(randomDigit);
        }

        return randomNumber.toString();
    }

    public CompletableFuture<List<BILL>> finAllBill() {
        return CompletableFuture.supplyAsync(() -> {
            return HD.findBillTheoTrangThai("Chưa thanh toán");
        }, Executors.newCachedThreadPool());
    }

    public CompletableFuture<List<SANPHAMCHITIET>> findMilkByName() {
        return CompletableFuture.supplyAsync(() -> {
            return spct.findAllSanPham();
        }, Executors.newCachedThreadPool());
    }
    
    public CompletableFuture<List<BILLCHITIET>> findBillDetail() {
        return CompletableFuture.supplyAsync(() -> {
            return HDCT.findAll();
        }, Executors.newCachedThreadPool());
    }
    public CompletableFuture<List<SANPHAM>> findSanPham() {
        return CompletableFuture.supplyAsync(() -> {
            return sp.findAll();
        }, Executors.newCachedThreadPool());
    }

    public CompletableFuture<List<KhachHang>> findKhachHang() {
        return CompletableFuture.supplyAsync(() -> {
            return kh.findKhachHangTheoTrangThai();
        }, Executors.newCachedThreadPool());
    }

    public boolean themHoaDon(BILL bill) {
        if (bill.toString() == null) {
            return false;
        } else {
            HD.save(bill);
            return true;
        }
    }

    public boolean suaTrangThaiHoaDon(BILL bill) {
            HD.save(bill);
            return true;
    }

    public boolean themHoaDonChiTiet(List<BILLCHITIET> billChiTiet) {
        if (billChiTiet.size() <= 0) {
            return false;
        } else {
            HDCT.saveAll(billChiTiet);
            return true;
        }
    }

    public CompletableFuture<List<Voucher>> finAllVoucher() {
        return CompletableFuture.supplyAsync(() -> {
            return voucher.findAllVoucher();
        }, Executors.newCachedThreadPool());
    }

    public boolean setSLVoucher(Integer id) {
        return voucher.updateQuantity(id) != 0;
    }

    public CompletableFuture<List<SANPHAMCHITIET>> searchSanPham(String nameProduct, String nameColor, String nameSize, String nameKieuDang) {
        return CompletableFuture.supplyAsync(() -> {
            return spct.timKiemTongHop(nameProduct, nameColor, nameSize, nameKieuDang);
        }, Executors.newCachedThreadPool());
    }
    
    public CompletableFuture<List<MAUSAC>> findAllMauSac() {
        return CompletableFuture.supplyAsync(() -> {
            return itMS.findAll();
        }, Executors.newCachedThreadPool());
    }
    public CompletableFuture<List<KieuDang>> findAllKieuDang() {
        return CompletableFuture.supplyAsync(() -> {
            return itKD.findAll();
        }, Executors.newCachedThreadPool());
    }
    public CompletableFuture<List<SIZE>> findAllSize() {
        return CompletableFuture.supplyAsync(() -> {
            return itS.findAll();
        }, Executors.newCachedThreadPool());
    }
}
