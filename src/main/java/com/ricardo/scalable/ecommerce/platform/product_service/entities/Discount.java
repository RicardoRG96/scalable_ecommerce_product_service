package com.ricardo.scalable.ecommerce.platform.product_service.entities;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ricardo.scalable.ecommerce.platform.libs_common.entities.ProductSku;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "discounts")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "discount_type")
    private String discountType;

    @NotNull
    @Column(name = "discount_value")
    private Double discountValue;

    @NotNull    
    @Column(name = "start_date")
    private Timestamp startDate;

    @NotNull
    @Column(name = "end_date")
    private Timestamp endDate;

    @Column(name = "is_active")
    private Boolean isActive;

    @JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
    @ManyToMany
    @JoinTable(
        name = "discount_product_sku",
        joinColumns = { @JoinColumn(name = "discount_id") },
        inverseJoinColumns = { @JoinColumn(name = "product_sku_id") },
        uniqueConstraints = { @UniqueConstraint(columnNames = { "discount_id", "product_sku_id" }) }
    )
    private List<ProductSku> productSkus;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "updated_at")
    @CreationTimestamp
    private Timestamp updatedAt;

    public Discount() {
    }

    public Discount(Long id, @NotBlank String discountType, @NotBlank Double discountValue,
            @NotNull Timestamp startDate, @NotNull Timestamp endDate, Boolean isActive, List<ProductSku> productSkus,
            Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
        this.productSkus = productSkus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public Double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Double discountValue) {
        this.discountValue = discountValue;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public List<ProductSku> getProductSkus() {
        return productSkus;
    }

    public void setProductSkus(List<ProductSku> productSkus) {
        this.productSkus = productSkus;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    
}
