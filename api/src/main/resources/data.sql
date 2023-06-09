-- ブランドマスタへの挿入
INSERT INTO m_brand (
    brand_id,
    brand_name
)
VALUES 
('01', 'Honda'),
('02', 'Kawasaki'),
('03', 'Yamaha'),
('04', 'Suzuki'),
('05', 'moto guzzi');

-- モーターサイクルマスタへの挿入
INSERT INTO m_motorcycle (
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
('Z900RS', 800, 4, '水冷', 1260000, 'エンジン音が低く渋い', '02', 1),
('W800 CAFE', 790, 2, '水冷', 1100000, 'エンジン音が良いスポーツタイプ', '02', 1),
('YZF-R1', 100, 4, '水冷', 1500000, '見た目が渋いスポーツタイプ', '03', 1),
('Rebel250', 690, 1, '水冷', 599500, '見た目はクルーザーの音は軽め', '01', 1),
('Rebel500', 690, 2, '水冷', 799700, 'よくわからない', '01', 1),
('SR400 Final Edition', 790, 1, '水冷', 605000, 'エンジン音の低音が良い', '03', 1),
('Z900RS CAFE', 820, 4, '水冷', 1290000, '見た目は渋いが後部シートは小さい', '02', 1),
('V7 III Racer 10th ANNIVERSARY', 770, 2, '水冷', 1375000, '珍しい見た目', '05', 1),
('Hayabusa', 800, 4, '水冷', 2156000, '猛スピード', '04', 1);

-- ユーザーマスタへの挿入
INSERT INTO m_user (
    username,
    password
)
VALUES
('test', '$2a$10$b6UKMeAVCceIMPozO8Nrm.mkLGNxzy1Xf6OnhpJXm9kVwRGtCL2mS'),
('admin', '$2a$10$jZF/bl2bCoY7JZYB.vWXTeIOyleJL76d7ZbuK1USJiVR8ieJq.OBO');