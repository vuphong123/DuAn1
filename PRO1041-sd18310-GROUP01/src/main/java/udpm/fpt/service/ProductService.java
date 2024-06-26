package udpm.fpt.service;

import udpm.fpt.model.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;

import static udpm.fpt.Application.getBean;

import udpm.fpt.repository.*;

/**
 * @author NONG HOANG VU
 */
public class ProductService {
//
//    private final IProductInfo r = getBean(IProductInfo.class);
//    private final IMilk iMilk = getBean(IMilk.class);
//    private final IFlavor iFlavor = getBean(IFlavor.class);
//    private final IUnit iUnit = getBean(IUnit.class);
//    private final IPackagingSpecification iPackagingSpecification = getBean(IPackagingSpecification.class);
//    private final IHistoryProduct iHistoryProduct = getBean(IHistoryProduct.class);
//    private final ISaleMilk iSaleMilk = getBean(ISaleMilk.class);
//    private final HistoryProductService historyProduct = new HistoryProductService();
//
//    @Autowired
//    public ProductService() {
//    }
//
//    public CompletableFuture<List<ProductInfo>> loadAsync() {
//        return CompletableFuture.supplyAsync(() -> {
//            return Collections.unmodifiableList(r.findAll());
//        }, Executors.newCachedThreadPool());
//    }
//
//    public CompletableFuture<List<ProductInfo>> loadASearch(ProductInfoByCriteria data) {
//        return CompletableFuture.supplyAsync(() -> {
//            return Collections.unmodifiableList(r.findProductInfoByCriteria(data.getProductName(), data.getFlavor(), data.getPackagingType(), data.getMeasurementUnit(), data.getVolume(), data.getEntryDate(), data.getMinQuantity(), data.getMaxQuantity(), data.getMinPrice(), data.getMaxPrice(), data.getExpiryStatus()));
//        }, Executors.newCachedThreadPool());
//    }
//
//    public CompletableFuture<List<ProductInfo>> loadASearchByBarcode(ProductInfoByCriteria data) {
//        return CompletableFuture.supplyAsync(() -> {
//            return Collections.unmodifiableList(r.findProductInfoByCriteriaBarcode(data.getProductName(), data.getFlavor(), data.getPackagingType(), data.getMeasurementUnit(), data.getVolume(), data.getEntryDate(), data.getMinQuantity(), data.getMaxQuantity(), data.getMinPrice(), data.getMaxPrice(), data.getExpiryStatus()));
//        }, Executors.newCachedThreadPool());
//    }
//
//    public Milk getMilkByBarcode(Long barcode) {
//        return iMilk.findAllByBarcode(barcode);
//    }
//
//    public CompletableFuture<List<Flavor>> loadFlavor() {
//        return CompletableFuture.supplyAsync(() -> {
//            List<Flavor> all = this.iFlavor.findAll();
//            return all;
//        }, Executors.newCachedThreadPool());
//    }
//
//    public Boolean insertFlavor(Flavor flavor, User user) {
//        if (this.iFlavor.save(flavor) != null) {
//            return this.historyProduct.trackHistory("A new flavor " + flavor.getTaste().trim() + " has been added", user.getUsername(), HistoryProductService.ChangeType.NEW);
//        }
//        return false;
//    }
//
//    public Boolean removeByTaste(String flavor, User user) {
//        if (this.iFlavor.deleteFlavor(flavor)) {
//            return this.historyProduct.trackHistory("The " + flavor.trim() + " flavor has been removed", user.getUsername(), HistoryProductService.ChangeType.REMOVE);
//        }
//        return false;
//    }
//
//    public CompletableFuture<List<Unit>> loadUnit() {
//        return CompletableFuture.supplyAsync(() -> {
//            return this.iUnit.findAll();
//        }, Executors.newCachedThreadPool());
//    }
//
//    public Boolean removeByMeasurement_unit(Unit unit, User user) {
//        this.iUnit.deleteById(unit.getId());
//        return this.historyProduct.trackHistory("The " + unit.getMeasurement_unit().trim() + " measurement uni has been removed", user.getUsername(), HistoryProductService.ChangeType.REMOVE);
//    }
//
//    public Boolean insertUnit(Unit unit, User user) {
//        if (this.iUnit.save(unit) != null) {
//            return this.historyProduct.trackHistory("A new unit " + unit.getMeasurement_unit().trim() + " has been added", user.getUsername(), HistoryProductService.ChangeType.NEW);
//        }
//        return false;
//    }
//
//    public CompletableFuture<List<PackagingSpecification>> loadPackagingSpecification() {
//        return CompletableFuture.supplyAsync(() -> {
//            return this.iPackagingSpecification.findAll();
//        }, Executors.newCachedThreadPool());
//    }
//
//    public Boolean insertPackagingSpecification(PackagingSpecification packagingSpecification, User user) {
//        if (this.iPackagingSpecification.save(packagingSpecification) != null) {
//            return this.historyProduct.trackHistory("A packaging type " + packagingSpecification.getPackaging_type().trim() + " has been added", user.getUsername(), HistoryProductService.ChangeType.NEW);
//        }
//        return false;
//    }
//
//    public Boolean removePackagingSpecification(PackagingSpecification packagingSpecification, User user) {
//        this.iPackagingSpecification.deleteById(packagingSpecification.getId());
//        return this.historyProduct.trackHistory("The " + packagingSpecification.getPackaging_type().trim() + " packaging type has been removed", user.getUsername(), HistoryProductService.ChangeType.REMOVE);
//    }
//
//    public Boolean insertProduct(Milk m, ProductInfo pi) {
//        if (iMilk.findAllByBarcode(m.getBarcode()) == null) {
//            if (r.save(pi) != null) {
//                return this.historyProduct.trackHistory("New product has been added with barcode " + m.getBarcode(), pi.getUser().getUsername(), HistoryProductService.ChangeType.NEW);
//            }
//        }
//        return false;
//    }
//
//    public Boolean insertReplenishment(Milk m, ProductInfo pi) {
//        if (r.save(pi) != null) {
//            return this.historyProduct.trackHistory("New product has been replenishment with barcode " + m.getBarcode(), pi.getUser().getUsername(), HistoryProductService.ChangeType.NEW);
//        }
//        return false;
//    }
//
//    public Boolean updateProduct(Milk m, ProductInfo pi, User user) {
//        if (this.iMilk.save(m) != null && r.save(pi) != null) {
//            return this.historyProduct.trackHistory("ID " + m.getId() + " has been updated", user.getUsername(), HistoryProductService.ChangeType.UPDATE);
//        }
//        return false;
//    }
//
//    public Boolean hideRestoreProduct(Milk m, User user) {
//        if (this.iMilk.save(m) != null) {
//            HistoryProduct historyProduct = new HistoryProduct();
//            String type = m.getIsDelete() ? "Added to repository" : "Restored";
//            historyProduct.setDescription("ID " + m.getId() + " has been " + type);
//            historyProduct.setDatetime(new Date());
//            historyProduct.setUsername(user.getUsername());
//            historyProduct.setChangeType(type);
//            return this.iHistoryProduct.save(historyProduct) != null;
//        }
//        return false;
//    }
//
//    public Boolean deleteProduct(Long idMilk, Integer idProductInfo, Milk milk, User user) {
//        Boolean milkDeleted;
//        Boolean productInfoDeleted;
//        String message = "ID " + milk.getId() + " has been deleted";
//        HistoryProduct historyProduct = new HistoryProduct();
//        historyProduct.setDescription(message);
//        historyProduct.setDatetime(new Date());
//        historyProduct.setUsername(user.getUsername());
//        historyProduct.setChangeType("Delete");
//        this.iHistoryProduct.save(historyProduct);
//        try {
//            iMilk.deleteById(idMilk);
//            milkDeleted = true;
//        } catch (Exception e) {
//            milkDeleted = false;
//        }
//        try {
//            r.deleteById(idProductInfo);
//            productInfoDeleted = true;
//        } catch (Exception e) {
//            productInfoDeleted = false;
//        }
//        return milkDeleted && productInfoDeleted;
//    }
//
//    public List<SaleMilk> getPercentSale() {
//        return this.iSaleMilk.findSalesInRange();
//    }

}
