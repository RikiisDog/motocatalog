-- テーブルの削除
DROP TABLE m_motorcycle;
DROP TABLE m_brand;

-- ブランドマスタの作成
CREATE TABLE IF NOT EXISTS m_brand (
	brand_id VARCHAR(2) NOT NULL PRIMARY KEY COMMENT 'ブランドID',
	brand_name VARCHAR(20) COMMENT 'ブランド名'
) COMMENT 'ブランドマスタ';

-- モーターサイクルマスタの作成
CREATE TABLE IF NOT EXISTS m_motorcycle (
	moto_no INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'バイク番号',
	moto_name VARCHAR(100) COMMENT 'バイク名',
	seat_height INT UNSIGNED COMMENT 'シート高',
	cylinder INT UNSIGNED COMMENT 'シリンダー',
	cooling VARCHAR(20) COMMENT '冷却',
	price INT UNSIGNED COMMENT '価格',
	comment VARCHAR(200) COMMENT 'コメント',
	brand_id VARCHAR(2) NOT NULL COMMENT 'ブランドID',
	version INT UNSIGNED COMMENT 'バージョン',
	ins_dt DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '登録日時',
	upd_dt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時',
	PRIMARY KEY (moto_no),
	FOREIGN KEY (brand_id) REFERENCES m_brand(brand_id)
) COMMENT 'モーターサイクルマスタ';