/*
 * ** SQLの順次実行 **
 * ・DBeaverの場合
 * ALT + X キーを押下する
 * 
 * ・A5 SQL MK-2の場合
 * 実行位置を「先頭からすべて」に変更してから実行
 */

-- m_brandへの挿入
INSERT
	INTO
	m_brand (brand_id,
	brand_name)
VALUES ('01',
'Honda');

INSERT
	INTO
	m_brand (brand_id,
	brand_name)
VALUES ('02',
'Kawasaki');

INSERT
	INTO
	m_brand (brand_id,
	brand_name)
VALUES ('03',
'Yamaha');

INSERT
	INTO
	m_brand (brand_id,
	brand_name)
VALUES ('04',
'moto guzzi');

-- m_motorcycleへの挿入
INSERT
	INTO 
	m_motorcycle (
		moto_name,
		seat_height,
		cylinder,
		cooling,
		price,
		comment,
		brand_id,
		version
	)
VALUES (
	'GB350',
	800,
	1,
	'空冷',
	550000,
	'エンジン音が素晴らしい',
	'01',
	1
);
INSERT
	INTO 
	m_motorcycle (
		moto_name,
		seat_height,
		cylinder,
		cooling,
		price,
		comment,
		brand_id,
		version
	)
VALUES (
	'Z900RS',
	800,
	4,
	'水冷',
	1260000,
	'エンジン音が低く渋い',
	'02',
	1
);