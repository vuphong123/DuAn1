package udpm.fpt.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author NONG HOANG VU
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DashboardProduct {
    private Integer totalProduct;
    private Integer InventoryQuantity;
    private Integer ExpiredProducts;
}
