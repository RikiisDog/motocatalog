package com.example.motocatalog.beans;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * バイク
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Motorcycle {

    private int motoNo;
    private String motoName;
    private int seatHeight;
    private int cylinder;
    private String cooling;
    private int price;
    private String comment;
    private Brand brand;
    private int version;
    private LocalDateTime insDt;
    private LocalDateTime updDt;

}
