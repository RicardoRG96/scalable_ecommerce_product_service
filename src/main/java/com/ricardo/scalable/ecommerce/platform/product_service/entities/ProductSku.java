package com.ricardo.scalable.ecommerce.platform.product_service.entities;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_skus")
public class ProductSku {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "product_id", unique = false)
    @ManyToOne
    private Product product;

    @JoinColumn(name = "size_attribute_id", unique = false)
    @ManyToOne
    private ProductAttribute sizeAttribute;

    @JoinColumn(name = "color_attribute_id", unique = false)
    @ManyToOne
    private ProductAttribute colorAttribute;

    private String sku;

    private Double price;

    private Integer stock;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_featured")
    private Boolean isFeatured;

    @Column(name = "is_on_sale")
    private Boolean isOnSale;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "updated_at")
    @CreationTimestamp
    private Timestamp updatedAt;
    
    public ProductSku() {
    }

    public ProductSku(Long id, Product product, ProductAttribute sizeAttribute, ProductAttribute colorAttribute,
        String sku, Double price, Integer stock, Boolean isActive, Boolean isFeatured, Boolean isOnSale,
        Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.product = product;
        this.sizeAttribute = sizeAttribute;
        this.colorAttribute = colorAttribute;
        this.sku = sku;
        this.price = price;
        this.stock = stock;
        this.isActive = isActive;
        this.isFeatured = isFeatured;
        this.isOnSale = isOnSale;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductAttribute getSizeAttribute() {
        return sizeAttribute;
    }

    public void setSizeAttribute(ProductAttribute sizeAttribute) {
        this.sizeAttribute = sizeAttribute;
    }

    public ProductAttribute getColorAttribute() {
        return colorAttribute;
    }

    public void setColorAttribute(ProductAttribute colorAttribute) {
        this.colorAttribute = colorAttribute;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(Boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    public Boolean getIsOnSale() {
        return isOnSale;
    }

    public void setIsOnSale(Boolean isOnSale) {
        this.isOnSale = isOnSale;
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
