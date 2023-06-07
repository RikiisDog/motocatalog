-- m_brandへの挿入
INSERT IGNORE INTO m_brand (brand_id, brand_name)
VALUES 
('01', 'Honda'),
('02', 'Kawasaki'),
('03', 'Yamaha'),
('04', 'moto guzzi');

-- m_motorcycleへの挿入
INSERT IGNORE INTO m_motorcycle (
    moto_name,
    seat_height,
    cylinder,
    cooling,
    price,
    comment,
    brand_id,
    version
)
VALUES 
('GB350', 800, 1, '空冷', 550000, 'エンジン音が素晴らしい', '01', 1),
('Z900RS', 800, 4, '水冷', 1260000, 'エンジン音が低く渋い', '02', 1);