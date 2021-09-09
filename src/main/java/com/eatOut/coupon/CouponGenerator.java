package com.eatOut.coupon;

import com.eatOut.calendar.CalendarDateCalculator;
import com.eatOut.calendar.IDateCalculator;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CouponGenerator implements ICouponGenerator {
    private int couponId;
    private String couponName;
    private String couponCode;
    private int quantity;
    private int amount;
    private String description;
    private LocalDateTime expiryDate;
    private IDateCalculator dateCalculator;
    List<CouponGenerator> couponGeneratorList;

    public CouponGenerator() {
        dateCalculator = new CalendarDateCalculator();
        couponGeneratorList = new ArrayList<>();
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public int addCoupons(ICouponGeneratorDAO couponGeneratorDAO, String name, String quantity, String amount, String description, String expiryDate) {
        int row = 0;
        try {
            String couponCode = this.generateCouponCode(name);
            Timestamp expiryDateTime = Timestamp.valueOf(dateCalculator.parseToLocalDateTime(expiryDate));
            row = couponGeneratorDAO.insertCoupons(name, couponCode, quantity, amount, description, expiryDateTime);
        } catch (Exception ex) {
            ex.getMessage();
        }
        return row;
    }

    @Override
    public List<CouponGenerator> loadCoupons(ICouponGeneratorDAO couponGeneratorDAO) {
        try {
            couponGeneratorList = couponGeneratorDAO.viewCoupons();
        } catch (Exception ex) {
            ex.getMessage();
        }
        return couponGeneratorList;
    }

    @Override
    public String generateCouponCode(String couponName) {
        int[] key = new int[]{4, 3, 2, 1, 5};
        couponName = fillSpacesWithinString(couponName);
        int colSize = key.length;
        int couponLen = couponName.length();
        double rowLen = Math.ceil((double) couponLen / (double) colSize);
        int rowSize = (int) rowLen;
        String[][] codeArray = new String[rowSize][colSize];
        return generateTranspositionMatrixCode(codeArray, rowSize, colSize, couponLen, key, couponName);
    }

    private String fillSpacesWithinString(String couponName) {
        StringBuilder codeBuilder = new StringBuilder();
        String[] pArr = couponName.split(" ");
        for (int i = 0; i < pArr.length - 1; i++) {
            pArr[i] = pArr[i].concat("%");
        }

        for (String str : pArr) {
            codeBuilder.append(str);
        }
        couponName = codeBuilder.toString();
        return couponName;
    }

    private String generateTranspositionMatrixCode(String[][] codeArray, int rowSize, int colSize, int couponLen, int[] key, String couponName) {
        int i = 0;
        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                codeArray[row][col] = i < couponLen ? Character.toString(couponName.charAt(i)) : "%";
                i += 1;
            }
        }
        return generateSecurityCode(codeArray, key);
    }

    private String generateSecurityCode(String[][] codeArray, int[] key) {

        StringBuilder codeBuilder = new StringBuilder();
        for (int colKey : key) {
            for (int row = 0; row < codeArray.length; row++) {
                codeBuilder.append(codeArray[row][colKey - 1]);
            }
        }
        return codeBuilder.toString();
    }
}
