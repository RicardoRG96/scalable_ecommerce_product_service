SET NON_KEYWORDS VALUE;

-- CATEGORIES

INSERT INTO categories (name, description, parent)
    VALUES ('Hombre', 'Descripcion hombre', null);

INSERT INTO categories (name, description, parent)
    VALUES ('Tecnologia', 'Descripcion tecnologia', null);
    
INSERT INTO categories (name, description, parent)
    VALUES ('Deportes', 'Equipamiento deportivo, ropa deportiva, accesorios', null);

INSERT INTO categories (name, description, parent)
    VALUES ('Jeans-hombre', 'Descripcion jeans hombre', 1);

INSERT INTO categories (name, description, parent)
    VALUES ('Poleras-hombre', 'Descripcion poleras hombre', 1);

INSERT INTO categories (name, description, parent)
    VALUES ('Smartphone', 'Telefonos celulares', 2);

INSERT INTO categories (name, description, parent)
    VALUES ('Notebooks', 'Computadores portatiles', 2);

INSERT INTO categories (name, description, parent)
    VALUES ('Futbol', 'Descripcion futbol', 3);

INSERT INTO categories (name, description, parent)
    VALUES ('Unknown', 'Unknown', null);


-- BRANDS

INSERT INTO brands (name, description, logo_url)
    VALUES ('Lee', 'Marca líder en moda', 'https://example.com/lee_logo.png');

INSERT INTO brands (name, description, logo_url)
    VALUES ('Apple', 'Marca líder en tecnologia', 'https://example.com/apple_logo.png');

INSERT INTO brands (name, description, logo_url)
    VALUES ('ASUS', 'Empresa multinacional de tecnología', 'https://example.com/asus_logo.png');

INSERT INTO brands (name, description, logo_url)
    VALUES ('Nike', 'Marca deportiva estadounidense', 'https://example.com/nike_logo.png');

INSERT INTO brands (name, description, logo_url)
    VALUES ('Puma', 'Marca deportiva alemana', 'https://example.com/puma_logo.png');

INSERT INTO brands (name, description, logo_url)
    VALUES ('Unknown', 'Unknown', 'https://example.com/unknown.png');


-- PRODUCTS

INSERT INTO products (name, description, category_id, brand_id, cover) 
    VALUES ('iPhone 15', 'Smartphone Apple', 6, 2, 'https://example.com/images/iphone15.jpg');

INSERT INTO products (name, description, category_id, brand_id, cover) 
    VALUES ('Asus Zenbook', 'Notebook de ultima generacion', 7, 3, 'https://example.com/images/asus_zenbook.jpg');

INSERT INTO products (name, description, category_id, brand_id, cover) 
    VALUES ('Balon premier league 2025', 'Balon oficial Premier League', 8, 4, 'https://example.com/images/balon_pl.jpg');

INSERT INTO products (name, description, category_id, brand_id, cover) 
    VALUES ('Jeans Lee', 'Descripcion pantalones Lee', 4, 1, 'https://example.com/images/jeans_lee.jpg');

INSERT INTO products (name, description, category_id, brand_id, cover) 
    VALUES ('Polera Puma', 'Descripcion polera Puma', 5, 5, 'https://example.com/images/polera_puma.jpg');


-- PRODUCTS_ATTRIBUTES

INSERT INTO product_attributes (type, value)
    VALUES ('color', 'red');

INSERT INTO product_attributes (type, value)
    VALUES ('color', 'blue');

INSERT INTO product_attributes (type, value)
    VALUES ('color', 'black');

INSERT INTO product_attributes (type, value)
    VALUES ('size', 'S');

INSERT INTO product_attributes (type, value)
    VALUES ('size', 'M');

INSERT INTO product_attributes (type, value)
    VALUES ('size', 'L');

INSERT INTO product_attributes (type, value)
    VALUES ('color', 'none-color');

INSERT INTO product_attributes (type, value)
    VALUES ('size', 'none-size');


-- PRODUCTS_SKU

INSERT INTO product_skus (product_id, size_attribute_id, color_attribute_id, sku, price, stock, is_active, is_featured, is_on_sale)
    VALUES (1, 8, 3, 'SKU2210', 1500.99, 540, TRUE, TRUE, TRUE);

INSERT INTO product_skus (product_id, size_attribute_id, color_attribute_id, sku, price, stock, is_active, is_featured, is_on_sale)
    VALUES (2, 8, 3, 'SKU2501', 999.99, 200, TRUE, FALSE, TRUE);

INSERT INTO product_skus (product_id, size_attribute_id, color_attribute_id, sku, price, stock, is_active, is_featured, is_on_sale)
    VALUES (3, 8, 2, 'SKU9820', 29.99, 958, TRUE, TRUE, TRUE);

INSERT INTO product_skus (product_id, size_attribute_id, color_attribute_id, sku, price, stock, is_active, is_featured, is_on_sale)
    VALUES (4, 4, 1, 'SKU8562', 29.99, 100, TRUE, FALSE, FALSE);

INSERT INTO product_skus (product_id, size_attribute_id, color_attribute_id, sku, price, stock, is_active, is_featured, is_on_sale)
    VALUES (4, 5, 2, 'SKU8563', 29.99, 100, TRUE, FALSE, FALSE);

INSERT INTO product_skus (product_id, size_attribute_id, color_attribute_id, sku, price, stock, is_active, is_featured, is_on_sale)
    VALUES (4, 6, 3, 'SKU8564', 29.99, 100, TRUE, FALSE, FALSE);

INSERT INTO product_skus (product_id, size_attribute_id, color_attribute_id, sku, price, stock, is_active, is_featured, is_on_sale)
    VALUES (5, 4, 1, 'SKU1254', 19.99, 180, TRUE, FALSE, FALSE);
    
INSERT INTO product_skus (product_id, size_attribute_id, color_attribute_id, sku, price, stock, is_active, is_featured, is_on_sale)
    VALUES (5, 5, 2, 'SKU1255', 19.99, 70, TRUE, FALSE, FALSE);

INSERT INTO product_skus (product_id, size_attribute_id, color_attribute_id, sku, price, stock, is_active, is_featured, is_on_sale)
    VALUES (5, 6, 3, 'SKU1256', 19.99, 70, TRUE, FALSE, FALSE);

INSERT INTO product_skus (product_id, size_attribute_id, color_attribute_id, sku, price, stock, is_active, is_featured, is_on_sale)
    VALUES (5, 6, 1, 'SKU1257', 19.99, 70, TRUE, FALSE, FALSE);

INSERT INTO product_skus (product_id, size_attribute_id, color_attribute_id, sku, price, stock, is_active, is_featured, is_on_sale)
    VALUES (5, 5, 3, 'SKU1258', 19.99, 70, TRUE, FALSE, FALSE);


-- PRODUCT_GALLERY

INSERT INTO product_gallery (product_id, color_attribute_id, image_url) 
    VALUES (1, 3, 'https://example.com/images/iphone15-black.jpg');

INSERT INTO product_gallery (product_id, color_attribute_id, image_url) 
    VALUES (2, 2, 'https://example.com/images/asus-zenbook-blue.jpg');

INSERT INTO product_gallery (product_id, color_attribute_id, image_url) 
    VALUES (3, 1, 'https://example.com/images/balon-premier-league-red.jpg');

INSERT INTO product_gallery (product_id, color_attribute_id, image_url) 
    VALUES (4, 3, 'https://example.com/images/jeans-lee-black.jpg');

INSERT INTO product_gallery (product_id, color_attribute_id, image_url) 
    VALUES (4, 2, 'https://example.com/images/jeans-lee-blue.jpg');

INSERT INTO product_gallery (product_id, color_attribute_id, image_url) 
    VALUES (5, 3, 'https://example.com/images/polera-puma-black.jpg');

INSERT INTO product_gallery (product_id, color_attribute_id, image_url) 
    VALUES (5, 2, 'https://example.com/images/polera-puma-blue.jpg');


-- DISCOUNTS

INSERT INTO discounts (discount_type, discount_value, start_date, end_date, is_active)
    VALUES ('fixed_amount', 10.00, '2025-03-01 00:00:00', '2025-03-31 23:59:59', TRUE);

INSERT INTO discounts (discount_type, discount_value, start_date, end_date, is_active)
    VALUES ('free_shipping', 5.00, '2025-03-10 00:00:00', '2025-04-15 23:59:59', TRUE);

INSERT INTO discounts (discount_type, discount_value, start_date, end_date, is_active)
    VALUES ('percentage', 20.00, '2025-03-03 00:00:00', '2025-03-31 23:59:59', TRUE);

INSERT INTO discounts (discount_type, discount_value, start_date, end_date, is_active)
    VALUES ('percentage', 10.00, '2025-02-01 00:00:00', '2025-02-28 23:59:59', TRUE);


-- DISCOUNTS_PRODUCT_SKU

INSERT INTO discount_product_sku (discount_id, product_sku_id)
    VALUES (1, 1);

INSERT INTO discount_product_sku (discount_id, product_sku_id)
    VALUES (4, 1);

INSERT INTO discount_product_sku (discount_id, product_sku_id)
    VALUES (2, 1);

INSERT INTO discount_product_sku (discount_id, product_sku_id)
    VALUES (3, 2);

INSERT INTO discount_product_sku (discount_id, product_sku_id)
    VALUES (4, 3);

INSERT INTO discount_product_sku (discount_id, product_sku_id)
    VALUES (2, 3);

INSERT INTO discount_product_sku (discount_id, product_sku_id)
    VALUES (1, 4);

INSERT INTO discount_product_sku (discount_id, product_sku_id)
    VALUES (2, 4);


-- DISCOUNT_CODES

INSERT INTO discount_codes (code, discount_id, usage_limit, used_count)
    VALUES ('10OFFMARCH2025', 4, 100, 0);

INSERT INTO discount_codes (code, discount_id, usage_limit, used_count)
    VALUES ('20OFFAPRIL2025', 3, 50, 0);

INSERT INTO discount_codes (code, discount_id, usage_limit, used_count)
    VALUES ('10DOLLARSOFFMARCH', 1, 70, 0);

INSERT INTO discount_codes (code, discount_id, usage_limit, used_count)
    VALUES ('FREESHIPPINGAPRIL2025', 2, 100, 0);