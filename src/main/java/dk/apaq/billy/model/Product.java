package dk.apaq.billy.model;

import java.util.ArrayList;
import java.util.List;

public class Product extends ArchivableEntity {
    private String name;
    private String description;
    private String accountId;
    private String productNo;
    private String suppliersProductNo;
    private String salesTaxRulesetId;
    private List<ProductPrice> prices = new ArrayList<>();
    
    public String getName() {
        return name;
    }

    /**
     * The name of the product.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Optional description that will be used as default on invoice lines with this product.
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public String getAccountId() {
        return accountId;
    }

    /**
     * The account that sales of the product should be coded to.
     * @param accountId
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getSalesTaxRulesetId() {
        return salesTaxRulesetId;
    }

    public void setSalesTaxRulesetId(String salesTaxRulesetId) {
        this.salesTaxRulesetId = salesTaxRulesetId;
    }

    public String getProductNo() {
        return productNo;
    }

    /**
     * A number (or string) that the organization identifies the product by.
     * @param productNo
     */
    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getSuppliersProductNo() {
        return suppliersProductNo;
    }

    /**
     * A number (or string) that the organization's supplier identifies the product by.
     * @param suppliersProductNo
     */
    public void setSuppliersProductNo(String suppliersProductNo) {
        this.suppliersProductNo = suppliersProductNo;
    }

    public List<ProductPrice> getPrices() {
        return prices;
    }

    /**
     * The product can have a unit price for each of the organization's currencies.
     * @param prices
     */
    public void setPrices(List<ProductPrice> prices) {
        this.prices = prices;
    }
    
}
