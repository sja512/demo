package com.sja.demo.bigdata.hbase;


import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.*;

/**
 * @author rain created on 2019/06/19.
 */
public class HbaseSearch {

    private static Logger log = LoggerFactory.getLogger(HbaseSearch.class);

    private static String cluster = "cf1";

    private HConnection conn;
    private HBaseAdmin admin;

    @Value("${hbase.zookeeper.quorum:10.27.110.232,10.27.110.241,10.27.111.233}")
    private String hbaseZookeeperQuorum;
    private Configuration conf;


    protected void init() {
        try {
            conf = HBaseConfiguration.create();
            conf.set("hbase.zookeeper.quorum", "10.27.110.232,10.27.110.241,10.27.111.233");
            admin = new HBaseAdmin(conf);
            conn = HConnectionManager.createConnection(conf);
        } catch (Exception e) {
            log.error("连接hbase异常: ", e);
        }
    }

    /**
     * 分页查询表数据
     *
     * @param tableName  表名
     * @param pageSize   页大小
     * @param lastrowKey 起始rowkey值
     * @return 返回查询数据结果集
     */
    public List selectAllByPage(Class cla, String tableName,
                                                     int pageSize, String lastrowKey, String startDate, String EndDate, String tenantId, String expressId, List<String> shopId) {
        if (StringUtils.isBlank(tableName)
                || StringUtils.isBlank(pageSize + "")
                || StringUtils.isBlank(lastrowKey)) {
            log.error("===Parameters tableName|ddate|pageSize|rowKey should not be blank,Please check!===");
            return null;
        }
        HTable hTable = (HTable) getHTable(tableName);
        Scan scan = new Scan();
        FilterList filterList = new FilterList(
                FilterList.Operator.MUST_PASS_ALL);
        // time filter
        if (!StringUtils.isBlank(startDate)) {
            SingleColumnValueFilter cloumnfilter = new SingleColumnValueFilter(cluster.getBytes(), "date".getBytes(),
                    CompareFilter.CompareOp.GREATER_OR_EQUAL, startDate.getBytes());
            filterList.addFilter(cloumnfilter);
        }
        if (!StringUtils.isBlank(EndDate)) {
            SingleColumnValueFilter cloumnfilter = new SingleColumnValueFilter(cluster.getBytes(), "date".getBytes(),
                    CompareFilter.CompareOp.LESS_OR_EQUAL, EndDate.getBytes());
            filterList.addFilter(cloumnfilter);
        }
        // tenantId
        if (!StringUtils.isBlank(tenantId)) {
            SingleColumnValueFilter cloumnfilter = new SingleColumnValueFilter(cluster.getBytes(), "tenantId".getBytes(),
                    CompareFilter.CompareOp.EQUAL, tenantId.getBytes());
            filterList.addFilter(cloumnfilter);
        }
        // expressId
        if (!StringUtils.isBlank(expressId)) {
            SingleColumnValueFilter cloumnfilter = new SingleColumnValueFilter(cluster.getBytes(), "expressId".getBytes(),
                    CompareFilter.CompareOp.EQUAL, expressId.getBytes());
            filterList.addFilter(cloumnfilter);
        }
        // shopId in filter
        if (shopId != null && shopId.size() > 0) {
            Collection<Filter> filters = new ArrayList<>();
            for (String n : shopId) {
                Filter filter = HBaseFilterUtil.eqFilter(cluster, "shopId", n.getBytes());
                filters.add(filter);
            }
            Filter filter = HBaseFilterUtil.orFilter(filters);
            filterList.addFilter(filter);
        }

        Filter pageFilter = new PageFilter(pageSize);
        filterList.addFilter(pageFilter);
        if (!CpConstants.ROWKEY_FIRST.equals(lastrowKey)) {
            Filter rowFilter2 = new RowFilter(CompareFilter.CompareOp.GREATER,
                    new BinaryComparator(Bytes.toBytes(lastrowKey)));
            filterList.addFilter(rowFilter2);
        }
        Filter filter3 = new RowFilter(CompareFilter.CompareOp.EQUAL,
                new SubstringComparator(tenantId));
        filterList.addFilter(filter3);
        scan.setFilter(filterList);
        List<Map<String, String>> lists = new ArrayList<Map<String, String>>();
        ResultScanner rs = null;
        try {
            rs = hTable.getScanner(scan);
            for (Result result : rs) {
                lists.add(getRowByResult(result));
            }
            hTable.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error("",e);
        }

        List<Object> reslist = new ArrayList<>();
        for (int i =0; i < lists.size(); i++) {
            try {
                reslist.add(Maptobean.stringmap2JavaBean(cla, lists.get(i)));
            }catch (Exception e) {
                System.out.println(e);
            }
        }
        return reslist;
    }

    /**
     * 获取同一个rowkey下的记录集合
     *
     * @param result 结果集
     * @return
     */
    private Map<String, String> getRowByResult(Result result) {
        if (result == null) {
            log.error("===Parameter |result| should not be null,Please check!===");
            return null;
        }
        Map<String, String> cellMap = new HashMap<String, String>();
        for (Cell cell : result.listCells()) {
            if (cellMap.get("rowkey") == null) {
                String rowkey = Bytes.toString(cell.getRowArray(),
                        cell.getRowOffset(), cell.getRowLength());
                cellMap.put("rowkey", rowkey);
            }
            // 列簇
            String cf = Bytes.toString(cell.getFamilyArray(),
                    cell.getFamilyOffset(), cell.getFamilyLength());
            String qf = Bytes.toString(cell.getQualifierArray(),
                    cell.getQualifierOffset(), cell.getQualifierLength());
            String value = Bytes.toString(cell.getValueArray(),
                    cell.getValueOffset(), cell.getValueLength());
//			cellMap.put(CpConstants.HBASE_TABLE_PROP_ROWKEY, rowkey);
//			cellMap.put(CpConstants.HBASE_TABLE_PROP_COLUMNFAMILY, cf);

            cellMap.put(qf, value);
        }
        return cellMap;
    }

    /**
     * 获取HTableInterface
     *
     * @param tableName 表名
     * @return 返回HTableInterface实例
     */
    private HTableInterface getHTable(String tableName) {
        if (StringUtils.isBlank(tableName)) {
            log.error("===Parameter |tableName| should not be blank,Please check!===");
            return null;
        }
        HTableInterface hTable = null;
        try {
            HConnection conn = HConnectionManager.createConnection(conf);
            hTable = conn.getTable(Bytes.toBytes(tableName));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error("",e);
            return null;
        }
        return hTable;
    }
}
