INSERT INTO categories (name, description)
VALUES
    ('Smartphone', 'Telefonos celulares'),
    ('Notebooks', 'Computadores portatiles'),
    ('Decohogar', 'Diseño del hogar'),
    ('Deportes', 'Equipamiento deportivo, ropa deportiva, accesorios');

INSERT INTO brands (name, description, logo_url)
VALUES
    ('Apple', 'Marca líder en tecnologia', 'https://example.com/apple_logo.png'),
    ('ASUS', 'Empresa multinacional de tecnología', 'https://example.com/asus_logo.png'),
    ('Nike', 'Marca deportiva estadounidense', 'https://example.com/nike_logo.png'),
    ('Puma', 'Marca deportiva alemana', 'https://example.com/puma_logo.png');

INSERT INTO products (sku, upc, name, description, category_id, brand_id, price, stock, image_url, is_active, is_featured, is_on_sale) 
VALUES
    ('SKU2210', 'UPC6569', 'iPhone 15', 'Smartphone Apple', 1, 1, 1500.99, 540, 'https://example.com/images/iphone15_algodon.jpg', TRUE, TRUE, TRUE),
    ('SKU2501', 'UPC1515', 'Asus Zenbook', 'Notebook de ultima generacion', 2, 2, 999.99, 200, 'https://example.com/images/asus_zenbook.jpg', TRUE, FALSE, TRUE),
    ('SKU9820', 'UPC281852', 'Balon premier league 2025', 'Balon oficial Premier League', 4, 3, 29.99, 958, 'https://example.com/images/balon_pl.jpg', TRUE, TRUE, TRUE);