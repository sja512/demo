package com.sja.demo.bigdata.hbase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName :Main
 * @Description:TODO
 * @Author:WangHan
 * @Date:2019/6/13 1:58
 * @Version:V1.0
 */
public class Main {

    public static void main(String[] args){
        HbaseSearch service = new HbaseSearch();
        service.init();
        String tableName = "t_hbase_delivery_package_detail";
//        List<String> columnFamily = new ArrayList<>();
//        columnFamily.add("cf1");
//
//        service.createTable(tableName,columnFamily);
//
//        List<Map<String, String>> paraList = new ArrayList<>();
//        Map<String,String> map1 = new HashMap<>();
//        map1.put(CpConstants.HBASE_TABLE_PROP_ROWKEY,"11111111");
//        map1.put(CpConstants.HBASE_TABLE_PROP_COLUMNFAMILY,"cf1");
//        map1.put("userId","333");
//        map1.put("name","小军11");
//        map1.put("age","22");
//        map1.put("date","201906120303");
//        paraList.add(map1);
//        Map<String,String> map2 = new HashMap<>();
//        map2.put(CpConstants.HBASE_TABLE_PROP_ROWKEY,"22222222");
//        map2.put(CpConstants.HBASE_TABLE_PROP_COLUMNFAMILY,"cf1");
//        map2.put("userId","222");
//        map2.put("name","小红11");
//        map2.put("age","28");
//        map2.put("date","201906130606");
//        paraList.add(map2);
//        service.batchPut(tableName,paraList);
//
//        List<String> nameList = new ArrayList<>();
//        nameList.add("小红11");
//        nameList.add("小军11");
//        List<Map<String, String>> list = service.selectAllByPage(tableName,1,null,null,null,"11111111",null);
//        for(Map<String,String> map : list){
//            System.out.println(map.get("userId")+"-"+map.get("name")+"-"+map.get("age"));
//        }

        List<DeliveryPackageDetailReportInfo> list = service.selectAllByPage(DeliveryPackageDetailReportInfo.class, tableName, 10, "0", null,null,null,null,null);
        list.size();
    }
}
