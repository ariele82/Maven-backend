-- =============================================
-- Database: order_management
-- Description: Schema for managing orders and products
-- Author: oldm4n
-- Date: future
-- =============================================

-- Create database (execute manually in pgAdmin if necessary)
-- CREATE DATABASE order_management;
-- \c order_management;

-- Extension for UUID
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";



-- =============================================
-- TABLES
-- =============================================
-- Users table
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username TEXT NOT NULL UNIQUE,
    password TEXT,
    email TEXT NOT NULL UNIQUE,
    phone TEXT,
    role TEXT
);
-- Categories table
CREATE TABLE IF NOT EXISTS categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Products table
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

-- Customers table
CREATE TABLE IF NOT EXISTS customers (
    id SERIAL PRIMARY KEY,
    uuid UUID UNIQUE DEFAULT uuid_generate_v4(),
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Addresses table
CREATE TABLE IF NOT EXISTS addresses (
    id SERIAL PRIMARY KEY,
    customer_id INT NOT NULL REFERENCES customers(id) ON DELETE CASCADE,
    street VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    country VARCHAR(100) NOT NULL,
    is_default BOOLEAN DEFAULT FALSE
);

-- Orders table
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

-- Order Items table
CREATE TABLE IF NOT EXISTS order_items (
    id SERIAL PRIMARY KEY,
    order_id INT NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
    product_id INT NOT NULL REFERENCES products(id),
    quantity INT NOT NULL CHECK (quantity > 0),
    unit_price DECIMAL(10,2) NOT NULL,
    discount DECIMAL(5,2) DEFAULT 0.00,
    subtotal DECIMAL(10,2) GENERATED ALWAYS AS (quantity * unit_price * (1 - discount/100)) STORED
);

-- Shipments table
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
-- INDEXES
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
-- VIEWS
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
-- FUNCTIONS AND TRIGGERS
-- =============================================

-- Function to update timestamp
CREATE OR REPLACE FUNCTION update_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Function to generate unique order number
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

-- Function to update order total
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

-- Function to set order number
CREATE OR REPLACE FUNCTION set_order_number()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.order_number IS NULL THEN
        NEW.order_number := generate_order_number();
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger to update product timestamps
CREATE TRIGGER update_products_updated_at
BEFORE UPDATE ON products
FOR EACH ROW EXECUTE FUNCTION update_updated_at();

-- Trigger to update order total
CREATE TRIGGER update_order_total_trigger
AFTER INSERT OR UPDATE OR DELETE ON order_items
FOR EACH ROW EXECUTE FUNCTION update_order_total();

-- Trigger to generate order number
CREATE TRIGGER set_order_number_trigger
BEFORE INSERT ON orders
FOR EACH ROW EXECUTE FUNCTION set_order_number();

-- =============================================
-- SAMPLE DATA
-- =============================================

-- Insert categories
INSERT INTO categories (name, description) VALUES
('Electronics', 'Electronic devices'),
('Clothing', 'Clothes and accessories'),
('Home', 'Household items');

-- Insert products
INSERT INTO products (name, description, price, stock_quantity, category_id, sku) VALUES
('Laptop Pro', '15" Laptop 16GB RAM', 1299.99, 25, 1, 'LP001'),
('Smartphone X', '5G, 128GB', 799.99, 50, 1, 'SP001'),
('T-Shirt', '100% Cotton', 19.99, 200, 2, 'TS001'),
('Coffee Maker', 'Automatic', 49.99, 30, 3, 'CF001');

-- Insert customers
INSERT INTO customers (first_name, last_name, email, phone) VALUES
('Mario', 'Rossi', 'mario.rossi@email.com', '3331234567'),
('Laura', 'Bianchi', 'laura.bianchi@email.com', '3449876543');

-- Insert addresses
INSERT INTO addresses (customer_id, street, city, postal_code, country, is_default) VALUES
(1, 'Via Roma 10', 'Milan', '20121', 'Italy', TRUE),
(2, 'Corso Italia 5', 'Rome', '00100', 'Italy', TRUE);

-- Insert orders (with trigger for order number)
INSERT INTO orders (customer_id, total_amount, status, shipping_address_id) VALUES
(1, 0, 'PROCESSING
