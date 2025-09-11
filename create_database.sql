-- =============================================
-- Database: order_management
-- Descrizione: Schema per gestione ordini prodotti
-- Autore: [Il tuo nome]
-- Data: [Data corrente]
-- =============================================

-- Creazione database (eseguire manualmente in pgAdmin se necessario)
-- CREATE DATABASE order_management;
-- \c order_management;

-- Estensione per UUID
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- =============================================
-- TABELLE
-- =============================================

-- Tabella Categorie
CREATE TABLE IF NOT EXISTS categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabella Prodotti
CREATE TABLE IF NOT EXISTS products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL CHECK (price > 0),
    stock_quantity INT NOT NULL DEFAULT 0,
    category_id INT REFERENCES categories(id) ON DELETE SET NULL,
    sku VARCHAR(50) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabella Clienti
CREATE TABLE IF NOT EXISTS customers (
    id SERIAL PRIMARY KEY,
    uuid UUID UNIQUE DEFAULT uuid_generate_v4(),
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabella Indirizzi
CREATE TABLE IF NOT EXISTS addresses (
    id SERIAL PRIMARY KEY,
    customer_id INT NOT NULL REFERENCES customers(id) ON DELETE CASCADE,
    street VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    country VARCHAR(100) NOT NULL,
    is_default BOOLEAN DEFAULT FALSE
);

-- Tabella Ordini
CREATE TABLE IF NOT EXISTS orders (
    id SERIAL PRIMARY KEY,
    order_number VARCHAR(50) UNIQUE NOT NULL,
    customer_id INT NOT NULL REFERENCES customers(id) ON DELETE RESTRICT,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(12,2) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING' 
        CHECK (status IN ('PENDING', 'PROCESSING', 'SHIPPED', 'DELIVERED', 'CANCELLED')),
    shipping_address_id INT NOT NULL REFERENCES addresses(id),
    tracking_number VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabella Dettagli Ordine
CREATE TABLE IF NOT EXISTS order_items (
    id SERIAL PRIMARY KEY,
    order_id INT NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
    product_id INT NOT NULL REFERENCES products(id),
    quantity INT NOT NULL CHECK (quantity > 0),
    unit_price DECIMAL(10,2) NOT NULL,
    discount DECIMAL(5,2) DEFAULT 0.00,
    subtotal DECIMAL(10,2) GENERATED ALWAYS AS (quantity * unit_price * (1 - discount/100)) STORED
);

-- Tabella Stato Spedizioni
CREATE TABLE IF NOT EXISTS shipments (
    id SERIAL PRIMARY KEY,
    order_id INT UNIQUE NOT NULL REFERENCES orders(id),
    shipped_at TIMESTAMP,
    estimated_delivery TIMESTAMP,
    actual_delivery TIMESTAMP,
    carrier VARCHAR(50),
    status VARCHAR(20) CHECK (status IN ('PREPARING', 'IN_TRANSIT', 'DELIVERED', 'RETURNED'))
);

-- =============================================
-- INDICI
-- =============================================

CREATE INDEX IF NOT EXISTS idx_products_category ON products(category_id);
CREATE INDEX IF NOT EXISTS idx_orders_customer ON orders(customer_id);
CREATE INDEX IF NOT EXISTS idx_order_items_order ON order_items(order_id);
CREATE INDEX IF NOT EXISTS idx_order_items_product ON order_items(product_id);
CREATE INDEX IF NOT EXISTS idx_addresses_customer ON addresses(customer_id);
CREATE INDEX IF NOT EXISTS idx_products_sku ON products(sku);
CREATE INDEX IF NOT EXISTS idx_orders_status ON orders(status);
CREATE INDEX IF NOT EXISTS idx_orders_date ON orders(order_date);
CREATE INDEX IF NOT EXISTS idx_customers_email ON customers(email);

-- =============================================
-- VISTE
-- =============================================

CREATE OR REPLACE VIEW order_summary AS
SELECT 
    o.id,
    o.order_number,
    CONCAT(c.first_name, ' ', c.last_name) AS customer_name,
    o.order_date,
    o.total_amount,
    o.status,
    COUNT(oi.id) AS item_count
FROM orders o
JOIN customers c ON o.customer_id = c.id
LEFT JOIN order_items oi ON o.id = oi.order_id
GROUP BY o.id, c.first_name, c.last_name;

CREATE OR REPLACE VIEW low_stock_products AS
SELECT 
    name,
    sku,
    stock_quantity,
    price
FROM products
WHERE stock_quantity < 10;

-- =============================================
-- FUNZIONI E TRIGGER
-- =============================================

-- Funzione per aggiornare timestamp
CREATE OR REPLACE FUNCTION update_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Funzione per generare numero ordine univoco
CREATE OR REPLACE FUNCTION generate_order_number()
RETURNS VARCHAR AS $$
DECLARE
    order_num VARCHAR;
BEGIN
    SELECT 'ORD-' || TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDD') || '-' || 
           LPAD(CAST(COALESCE(MAX(CAST(SPLIT_PART(order_number, '-', 3) AS INT)), 0) + 1 AS VARCHAR), 4, '0')
    INTO order_num
    FROM orders
    WHERE order_number LIKE 'ORD-' || TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMMDD') || '%';
    
    RETURN order_num;
END;
$$ LANGUAGE plpgsql;

-- Funzione per aggiornare totale ordine
CREATE OR REPLACE FUNCTION update_order_total()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE orders
    SET total_amount = (
        SELECT COALESCE(SUM(subtotal), 0)
        FROM order_items
        WHERE order_id = NEW.order_id
    )
    WHERE id = NEW.order_id;
    
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Funzione per impostare numero ordine
CREATE OR REPLACE FUNCTION set_order_number()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.order_number IS NULL THEN
        NEW.order_number := generate_order_number();
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger per aggiornare timestamp prodotti
CREATE TRIGGER update_products_updated_at
BEFORE UPDATE ON products
FOR EACH ROW EXECUTE FUNCTION update_updated_at();

-- Trigger per aggiornare totale ordine
CREATE TRIGGER update_order_total_trigger
AFTER INSERT OR UPDATE OR DELETE ON order_items
FOR EACH ROW EXECUTE FUNCTION update_order_total();

-- Trigger per generare numero ordine
CREATE TRIGGER set_order_number_trigger
BEFORE INSERT ON orders
FOR EACH ROW EXECUTE FUNCTION set_order_number();

-- =============================================
-- DATI DI ESEMPIO
-- =============================================

-- Inserimento categorie
INSERT INTO categories (name, description) VALUES
('Elettronica', 'Dispositivi elettronici'),
('Abbigliamento', 'Vestiti e accessori'),
('Casa', 'Articoli per la casa');

-- Inserimento prodotti
INSERT INTO products (name, description, price, stock_quantity, category_id, sku) VALUES
('Laptop Pro', 'Laptop 15" 16GB RAM', 1299.99, 25, 1, 'LP001'),
('Smartphone X', '5G, 128GB', 799.99, 50, 1, 'SP001'),
('T-Shirt', 'Cotone 100%', 19.99, 200, 2, 'TS001'),
('Caffettiera', 'Automatica', 49.99, 30, 3, 'CF001');

-- Inserimento clienti
INSERT INTO customers (first_name, last_name, email, phone) VALUES
('Mario', 'Rossi', 'mario.rossi@email.com', '3331234567'),
('Laura', 'Bianchi', 'laura.bianchi@email.com', '3449876543');

-- Inserimento indirizzi
INSERT INTO addresses (customer_id, street, city, postal_code, country, is_default) VALUES
(1, 'Via Roma 10', 'Milano', '20121', 'Italia', TRUE),
(2, 'Corso Italia 5', 'Roma', '00100', 'Italia', TRUE);

-- Inserimento ordini (con trigger per numero ordine)
INSERT INTO orders (customer_id, total_amount, status, shipping_address_id) VALUES
(1, 0, 'PROCESSING', 1),
(2, 0, 'PENDING', 2);

-- Inserimento dettagli ordine
INSERT INTO order_items (order_id, product_id, quantity, unit_price, discount) VALUES
(1, 1, 1, 1299.99, 5.00),  -- Laptop con 5% sconto
(1, 3, 2, 19.99, 0),       -- 2 T-Shirt
(2, 2, 1, 799.99, 10.00);  -- Smartphone con 10% sconto

-- =============================================
-- QUERY DI ESEMPIO
-- =============================================

-- Riepilogo ordini con dettagli cliente
-- SELECT * FROM order_summary;

-- Prodotti più venduti
-- SELECT 
--     p.name,
--     SUM(oi.quantity) AS total_sold,
--     SUM(oi.subtotal) AS revenue
-- FROM order_items oi
-- JOIN products p ON oi.product_id = p.id
-- GROUP BY p.id
-- ORDER BY total_sold DESC
-- LIMIT 5;

-- Stato spedizioni
-- SELECT 
--     o.order_number,
--     s.status,
--     s.shipped_at,
--     s.estimated_delivery
-- FROM orders o
-- LEFT JOIN shipments s ON o.id = s.order_id
-- WHERE o.status = 'SHIPPED';

-- Clienti con più ordini
-- SELECT 
--     CONCAT(c.first_name, ' ', c.last_name) AS customer,
--     COUNT(o.id) AS order_count,
--     SUM(o.total_amount) AS total_spent
-- FROM customers c
-- JOIN orders o ON c.id = o.customer_id
-- GROUP BY c.id
-- ORDER BY order_count DESC;

-- Fine script