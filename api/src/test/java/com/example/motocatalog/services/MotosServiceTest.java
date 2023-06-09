package com.example.motocatalog.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.example.motocatalog.beans.Brand;
import com.example.motocatalog.beans.Motorcycle;
import com.example.motocatalog.beans.SearchCondition;
import com.example.motocatalog.services.exceptions.MotorcycleDeleteFailedException;

@SpringBootTest
public class MotosServiceTest {

    @Autowired
    MotosService service;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ParameterizedTest
    @DisplayName("バイク一覧取得 条件: ブランドID")
    @CsvSource({ "01, Honda", "02, Kawasaki", "03, Yamaha", "04, Suzuki", "05, moto guzzi" })
    void testSearchCondition001(String brandId, String brandName) {
        SearchCondition condition = new SearchCondition();
        condition.setBrandId(brandId);
        List<Motorcycle> motos = service.getMotos(condition);
        assertTrue(motos.size() >= 1);
        for (Motorcycle moto : motos) {
            assertEquals(moto.getBrand().getBrandName(), brandName);
        }
    }

    @Test
    @DisplayName("バイク一覧取得 条件: 存在しないブランドID")
    void testSearchCondition002() {
        SearchCondition condition = new SearchCondition();
        condition.setBrandId("99");
        List<Motorcycle> motos = service.getMotos(condition);
        assertTrue(motos.size() >= 0);
    }

    @ParameterizedTest
    @DisplayName("バイク一覧取得 条件: バイク名-完全一致")
    @CsvSource({ "GB350", "W800 CAFE", "V7 III Racer 10th ANNIVERSARY" })
    void testSearchCondition003(String keyword) {
        SearchCondition condition = new SearchCondition();
        condition.setKeyword(keyword);
        List<Motorcycle> motos = service.getMotos(condition);
        assertTrue(motos.size() >= 1);
        for (Motorcycle moto : motos) {
            assertEquals(moto.getMotoName(), keyword);
        }
    }

    @ParameterizedTest
    @DisplayName("バイク一覧取得 条件: バイク名-部分一致")
    @CsvSource({ "GB35, GB350", "800 CAFE, W800 CAFE", "Racer 10th, V7 III Racer 10th ANNIVERSARY" })
    void testSearchCondition004(String keyword, String motoName) {
        SearchCondition condition = new SearchCondition();
        condition.setKeyword(keyword);
        List<Motorcycle> motos = service.getMotos(condition);
        assertTrue(motos.size() >= 1);
        for (Motorcycle moto : motos) {
            assertEquals(moto.getMotoName(), motoName);
        }
    }

    @Test
    @DisplayName("バイク一覧取得 条件: バイク名-該当なし")
    void testSearchCondition005() {
        SearchCondition condition = new SearchCondition();
        condition.setKeyword("存在しないキーワード");
        List<Motorcycle> motos = service.getMotos(condition);
        assertTrue(motos.size() == 0);
    }

    @ParameterizedTest
    @DisplayName("バイク一覧取得 条件: バイク名/ブランドID")
    @CsvSource({ "01, GB35, GB350", "02, 800 CAFE, W800 CAFE", "05, Racer 10th, V7 III Racer 10th ANNIVERSARY" })
    void testSearchCondition006(String brandId, String keyword, String motoName) {
        SearchCondition condition = new SearchCondition();
        condition.setKeyword(keyword);
        condition.setBrandId(brandId);
        List<Motorcycle> motos = service.getMotos(condition);
        assertTrue(motos.size() >= 1);
        for (Motorcycle moto : motos) {
            assertEquals(moto.getMotoName(), motoName);
        }
    }

    @Test
    @DisplayName("バイク一覧取得 条件: バイク名/ブランドID-該当なし")
    void testSearchCondition007() {
        SearchCondition condition = new SearchCondition();
        condition.setBrandId("存在しないブランドID");
        condition.setKeyword("存在しないキーワード");
        List<Motorcycle> motos = service.getMotos(condition);
        assertTrue(motos.size() == 0);
    }

    @Test
    @DisplayName("バイク一覧取得 条件: なし→全件該当")
    void testSearchCondition008() {
        SearchCondition condition = new SearchCondition();
        List<Motorcycle> motos = service.getMotos(condition);
        assertTrue(motos.size() == 10);
    }

    @ParameterizedTest
    @DisplayName("バイク一覧取得 条件: バイク番号")
    @CsvSource({ "1, GB350", "3, W800 CAFE" })
    void testSearchCondition009(int motoNo, String motoName) {
        Motorcycle moto = service.getMotos(motoNo);
        assertEquals(moto.getMotoName(), motoName);
    }

    @Test
    @DisplayName("バイク一覧取得 条件: バイク番号.全項目チェック")
    void testSearchCondition010() {
        int motoNo = 1;
        Motorcycle moto = service.getMotos(motoNo);
        assertEquals(moto.getMotoNo(), motoNo);
        assertEquals(moto.getMotoName(), "GB350");
        assertEquals(moto.getSeatHeight(), 800);
        assertEquals(moto.getCylinder(), 1);
        assertEquals(moto.getCooling(), "空冷");
        assertEquals(moto.getPrice(), 550000);
        assertEquals(moto.getBrand().getBrandId(), "01");
        assertEquals(moto.getVersion(), 1);
        assertEquals(moto.getInsDt(), LocalDateTime.parse("2023-01-01 12:00:00", formatter)); // ここの値はDBからCopyしてくる
        assertEquals(moto.getUpdDt(), LocalDateTime.parse("2023-01-01 12:00:00", formatter)); // ここの値はDBからCopyしてくる
    }

    @Test
    @DisplayName("バイク情報更新")
    @Transactional
    @Rollback
    void testUpdateByMoto011() {
        // 変更前のバイク情報取得
        Motorcycle before = service.getMotos(1);
        // バイク情報更新処理
        before.setMotoName("ChangeName");
        // バイク情報保存処理
        service.update(before);
        // 変更後のバイク情報取得
        Motorcycle after = service.getMotos(1);
        // テスト実行
        assertEquals(after.getMotoName(), "ChangeName");
        assertEquals(after.getVersion(), (before.getVersion() + 1));
    }

    @Test
    @DisplayName("バイク情報登録")
    @Sql({ "/schema.sql", "/data.sql" })
    @Transactional
    @Rollback
    void testInsertByMoto012() {
        // 変更前のバイク情報取得
        Motorcycle before = new Motorcycle();
        // バイク情報更新処理
        before.setMotoName("登録テスト");
        before.setSeatHeight(10);
        before.setCylinder(1);
        before.setCooling("水冷");
        before.setPrice(1000);
        before.setComment("登録テストコメント");
        before.setBrand(new Brand("01", "Honda"));
        // バイク情報登録処理
        service.register(before);
        // 変更後のバイク情報取得
        Motorcycle after = service.getMotos(11);
        // テスト実行
        assertEquals(after.getMotoNo(), 11);
        assertEquals(after.getMotoName(), "登録テスト");
        assertEquals(after.getSeatHeight(), 10);
        assertEquals(after.getCylinder(), 1);
        assertEquals(after.getCooling(), "水冷");
        assertEquals(after.getPrice(), 1000);
        assertEquals(after.getComment(), "登録テストコメント");
        assertEquals(after.getBrand().getBrandId(), "01");
        assertEquals(after.getVersion(), 1);
    }

    @Test
    @DisplayName("バイク情報削除")
    @Sql({ "/schema.sql", "/data.sql" })
    @Transactional
    @Rollback
    void testDeleteByMoto013() {
        // 変更前のバイク情報取得
        Motorcycle before = service.getMotos(1);
        // 削除処理
        service.delete(before.getMotoNo());
        // 再度同じバイク情報を削除しようとすると例外がスローされることを確認
        assertThrows(MotorcycleDeleteFailedException.class, () -> {
            service.delete(before.getMotoNo());
        });

    }
}
