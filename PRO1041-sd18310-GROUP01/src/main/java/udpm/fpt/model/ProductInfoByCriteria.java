package udpm.fpt.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfoByCriteria {
    private String productName;
    private String flavor;
    private String packagingType;
    private String measurementUnit;
    private Float volume;
    private Date entryDate;
    private Integer minQuantity;
    private Integer maxQuantity;
    private Integer minPrice;
    private Integer maxPrice;
    private String expiryStatus;
}
