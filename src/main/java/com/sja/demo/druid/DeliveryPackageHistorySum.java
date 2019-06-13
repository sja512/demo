package com.sja.demo.druid;

import java.io.Serializable;
import java.util.Date;

/**
 * @author sja  created on 2019/5/30.
 */
public class DeliveryPackageHistorySum implements Serializable {
    private String time;
    private Long tenantId;
    private String deliveryDates;
    private Date deliveryDate;
    private Long expressId;
    private String expressName;
    private Long warehoseId;
    private String warehouseName;
    private Long shopId;
    private String shopName;
    private Integer itemNum;
    private Integer packageNum;
    private Double postFee;
    private Double postCost;
    private String filed1;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getDeliveryDates() {
        return deliveryDates;
    }

    public void setDeliveryDates(String deliveryDates) {
        this.deliveryDates = deliveryDates;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Integer getItemNum() {
        return itemNum;
    }

    public void setItemNum(Integer itemNum) {
        this.itemNum = itemNum;
    }

    public Integer getPackageNum() {
        return packageNum;
    }

    public void setPackageNum(Integer packageNum) {
        this.packageNum = packageNum;
    }

    public Double getPostFee() {
        return postFee;
    }

    public void setPostFee(Double postFee) {
        this.postFee = postFee;
    }

    public Double getPostCost() {
        return postCost;
    }

    public void setPostCost(Double postCost) {
        this.postCost = postCost;
    }

    public String getFiled1() {
        return filed1;
    }

    public void setFiled1(String filed1) {
        this.filed1 = filed1;
    }

    public Long getExpressId() {
        return expressId;
    }

    public void setExpressId(Long expressId) {
        this.expressId = expressId;
    }

    public Long getWarehoseId() {
        return warehoseId;
    }

    public void setWarehoseId(Long warehoseId) {
        this.warehoseId = warehoseId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
}
