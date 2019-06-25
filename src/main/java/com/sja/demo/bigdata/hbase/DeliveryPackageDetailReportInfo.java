package com.sja.demo.bigdata.hbase;


import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * User: whan
 * Date: 2015-11-15
 */
public class DeliveryPackageDetailReportInfo implements Serializable {
    private Long id;
    private Long deliveryDetailId;
    private Date createDate;
    private Date modifyDate;
    private int version;
    private Long tenantId;
    private String code;
    private String mailNo;
    private String platformCode;
    private Long expressId;
    private String expressName;
    private Long shopId;
    private String shopName;
    private Long warehouseId;
    private String warehouseName;
    private Double payment;
    private Double amount;
    private Double postFee;
    private Double postCost;
    private Long provinceId;
    private String privinceName;
    private Long municipalId;
    private String municipalName;
    private Double weightOrigin;
    private Double weightQty;
    private Date deliveryDate;
    private String receiverName;
    private Double qty;
    private String receiverAddress;
    private String scanName;//扫描人
    private String deliveryName;//发货人
    private String pickingUser;//拣货人
    private String packagedUser;//打包人
    private String tableSuffix;
    private Double weight;
    //20170116 yanjing_wang
    private Integer logisticsStatus; //物流状态
    private Date logisticsTime;      //物流更新时间
    private Boolean isUnusual;       //是否是异常包裹

    private String deliveryDateStr;
    private String logisticsStatusStr;
    private String logisticsTimeStr;
    private String isUnusualStr;

    //yanjing_wang 2017/5/8
    private Long countyId;        //县区id
    private String countyName;    //县区名称
    private Double packagePoint;  //打包积分
    private String assignName;    //配货人
    private Date scanDate;        //扫描时间
    private Date weightDate;      //称重时间
    private String weightName;      //称重人

    private Integer cod;          //货到付款
    private String packagePointStr;
    private String codStr;      //货到付款导出字段
    private String scanDateStr;
    private String weightDateStr;
    private String orderCode;   //订单编号
    private Integer orderNum;   //订单数


    private static final DecimalFormat doubleFormat = new DecimalFormat("#,##0.0000");
    private String qtyStr;      //商品数量
    private String amountStr;       //订单金额
    private String postFeeStr;          //物流费用
    private String postCostStr;             //物流成本
    private String weightQtyStr;                //称重重量
    private String weightOriginStr;                 //标准重量
    private String buyerMemo;       //买家留言
    private String sellerMemo;      //卖家备注
    private Double packageNum;      //合计包裹数量
    /**
     * 分销商物流费用
     */
    private Double distributionPostFee;
    private String distributionPostFeeStr;
    private String suffix;//生成表后缀
    /**
     * 商品明细合并信息
     */
    private String itemDetail;

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getWeightOriginStr() {
        return doubleFormat.format(getWeightOrigin() == null ? 0.0D : getWeightOrigin());
    }

    public String getWeightQtyStr() {
        return doubleFormat.format(getWeightQty() == null ? 0.0D : getWeightQty());
    }

    public String getPostCostStr() {
        return doubleFormat.format(getPostCost() == null ? 0.0D : getPostCost());
    }

    public String getPostFeeStr() {
        return doubleFormat.format(getPostFee() == null ? 0.0D : getPostFee());
    }

    public String getAmountStr() {
        return doubleFormat.format(getAmount() == null ? 0.0D : getAmount());
    }

    public String getQtyStr() {
        return doubleFormat.format(getQty() == null ? 0.0D : getQty());
    }

    public String getPackagePointStr() {
        return doubleFormat.format(getPackagePoint() == null ? 0.0D : getPackagePoint());
    }

    public String getDeliveryDateStr() {
        if (null != this.deliveryDate) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            return sdf.format(deliveryDate);
        } else
            return "";
    }

    public String getLogisticsStatusStr() {
        if (logisticsStatus != null) {
            switch (logisticsStatus) {
                case 0:
                    logisticsStatusStr = "无状态";
                    break;
                case 1:
                    logisticsStatusStr = "已取件";
                    break;
                case 2:
                    logisticsStatusStr = "在途中";
                    break;
                case 3:
                    logisticsStatusStr = "签收";
                    break;
                case 4:
                    logisticsStatusStr = "退件问题件";
                    break;
                case 5:
                    logisticsStatusStr = "待取件";
                    break;
                case 6:
                    logisticsStatusStr = "待派件";
                    break;
                default:
                    break;
            }
        }
        return logisticsStatusStr;
    }

    public void setLogisticsStatusStr(String logisticsStatusStr) {
        this.logisticsStatusStr = logisticsStatusStr;
    }

    public String getLogisticsTimeStr() {
        if (null != this.logisticsTime) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            logisticsTimeStr = sdf.format(logisticsTime);
        }
        return logisticsTimeStr;
    }

    public void setLogisticsTimeStr(String logisticsTimeStr) {
        this.logisticsTimeStr = logisticsTimeStr;
    }

    public String getIsUnusualStr() {
        if (this.isUnusual != null && this.isUnusual) {
            isUnusualStr = "是";
        } else {
            isUnusualStr = "否";
        }
        return isUnusualStr;
    }

    public void setIsUnusualStr(String isUnusualStr) {
        this.isUnusualStr = isUnusualStr;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }


    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public String getMailNo() {
        Pattern pattern = Pattern.compile("[0-9]*");
        if (null != mailNo && pattern.matcher(mailNo).matches()) {
            if (mailNo.length() > 15) {
                return mailNo;
            }
        }
        return mailNo;
    }

    public void setMailNo(String mailNo) {
        this.mailNo = mailNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeliveryDetailId() {
        return deliveryDetailId;
    }

    public void setDeliveryDetailId(Long deliveryDetailId) {
        this.deliveryDetailId = deliveryDetailId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getExpressId() {
        return expressId;
    }

    public void setExpressId(Long expressId) {
        this.expressId = expressId;
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
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

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public String getPrivinceName() {
        return privinceName;
    }

    public void setPrivinceName(String privinceName) {
        this.privinceName = privinceName;
    }

    public Long getMunicipalId() {
        return municipalId;
    }

    public void setMunicipalId(Long municipalId) {
        this.municipalId = municipalId;
    }

    public String getMunicipalName() {
        return municipalName;
    }

    public void setMunicipalName(String municipalName) {
        this.municipalName = municipalName;
    }

    public Double getWeightOrigin() {
        return weightOrigin;
    }

    public void setWeightOrigin(Double weightOrigin) {
        this.weightOrigin = weightOrigin;
    }

    public Double getWeightQty() {
        return weightQty;
    }

    public void setWeightQty(Double weightQty) {
        this.weightQty = weightQty;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getScanName() {
        return scanName;
    }

    public void setScanName(String scanName) {
        this.scanName = scanName;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public String getPickingUser() {
        return pickingUser;
    }

    public void setPickingUser(String pickingUser) {
        this.pickingUser = pickingUser;
    }

    public String getPackagedUser() {
        return packagedUser;
    }

    public void setPackagedUser(String packagedUser) {
        this.packagedUser = packagedUser;
    }

    public String getTableSuffix() {
        return tableSuffix;
    }

    public void setTableSuffix(String tableSuffix) {
        this.tableSuffix = tableSuffix;
    }

    public Integer getLogisticsStatus() {
        return logisticsStatus;
    }

    public void setLogisticsStatus(Integer logisticsStatus) {
        this.logisticsStatus = logisticsStatus;
    }

    public Date getLogisticsTime() {
        return logisticsTime;
    }

    public void setLogisticsTime(Date logisticsTime) {
        this.logisticsTime = logisticsTime;
    }

    public Boolean getUnusual() {
        return isUnusual;
    }

    public void setUnusual(Boolean unusual) {
        isUnusual = unusual;
    }

    public Long getCountyId() {
        return countyId;
    }

    public void setCountyId(Long countyId) {
        this.countyId = countyId;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public Double getPackagePoint() {
        return packagePoint;
    }

    public void setPackagePoint(Double packagePoint) {
        this.packagePoint = packagePoint;
    }

    public String getAssignName() {
        return assignName;
    }

    public void setAssignName(String assignName) {
        this.assignName = assignName;
    }

    public Date getScanDate() {
        return scanDate;
    }

    public void setScanDate(Date scanDate) {
        this.scanDate = scanDate;
    }

    public Date getWeightDate() {
        return weightDate;
    }

    public void setWeightDate(Date weightDate) {
        this.weightDate = weightDate;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    public String getCodStr() {
        if(cod != null && cod == 1){
            return "√";
        }else {
            return "";
        }
    }

    public String getScanDateStr() {
        if(scanDate != null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            return sdf.format(scanDate);
        }
        return scanDateStr;
    }

    public String getWeightDateStr() {
        if(weightDate != null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            return sdf.format(weightDate);
        }
        return weightDateStr;
    }

    public String getSellerMemo() {
        return sellerMemo;
    }

    public void setSellerMemo(String sellerMemo) {
        this.sellerMemo = sellerMemo;
    }

    public String getBuyerMemo() {
        return buyerMemo;
    }

    public void setBuyerMemo(String buyerMemo) {
        this.buyerMemo = buyerMemo;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getWeightName() {
        return weightName;
    }

    public void setWeightName(String weightName) {
        this.weightName = weightName;
    }

    public Double getPackageNum() {

        return packageNum;
    }

    public void setPackageNum(Double packageNum) {
        this.packageNum = packageNum;
    }

    public Double getDistributionPostFee() {
        return distributionPostFee;
    }

    public void setDistributionPostFee(Double distributionPostFee) {
        this.distributionPostFee = distributionPostFee;
    }

    public String getDistributionPostFeeStr() {
        return doubleFormat.format(null == distributionPostFee ? 0.0D : distributionPostFee);
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getItemDetail() {
        return itemDetail;
    }

    public void setItemDetail(String itemDetail) {
        this.itemDetail = itemDetail;
    }
}