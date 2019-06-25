package com.sja.demo.bigdata.hbase;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.*;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.TableNotFoundException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.RetriesExhaustedWithDetailsException;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.coprocessor.AggregationClient;
import org.apache.hadoop.hbase.client.coprocessor.LongColumnInterpreter;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

/**
 * HBase查询与插入操作工具类
 *
 * @author author
 *
 */
 //采用注入方式，HBaseService为定义的查询接口，可不需要。
public class HBaseService {

	private static Logger log = Logger.getLogger(HBaseService.class.getName());

	static ConfigUtil util = new ConfigUtil("conf/zookeeper.properties");
    private static final String HBASE_ZOOKEEPER_QUORUM = util
			.getString("hbase_zookeeper_quorum");
	private static final String ZOOKEEPER_ZNODE_PARENT = util
			.getString("zookeeper_znode_parent");
	private static Configuration conf = HBaseConfiguration.create();
	static {
		conf.set("hbase.zookeeper.quorum", HBASE_ZOOKEEPER_QUORUM);
		//conf.set("zookeeper.znode.parent", ZOOKEEPER_ZNODE_PARENT);
	}

	/**
	 * 创建表
	 *
	 * @param tableName
	 *            表名
	 * @param columnFamily
	 *            列簇集合
	 * @return 成功-true 失败-false
	 */
	@SuppressWarnings("resource")
	public boolean createTable(String tableName, List<String> columnFamily) {
		try {
			if (StringUtils.isBlank(tableName) || columnFamily == null
					|| columnFamily.size() < 0) {
				log.error("===Parameters tableName|columnFamily should not be null,Please check!===");
			}
			HBaseAdmin admin = new HBaseAdmin(conf);
			if (admin.tableExists(tableName)) {
				return true;
			} else {
				HTableDescriptor tableDescriptor = new HTableDescriptor(
						TableName.valueOf(tableName));
				for (String cf : columnFamily) {
					tableDescriptor.addFamily(new HColumnDescriptor(cf));
				}
				admin.createTable(tableDescriptor);
				log.info("===Create Table " + tableName
						+ " Success!columnFamily:" + columnFamily.toString()
						+ "===");
			}
		} catch (MasterNotRunningException e) {
			// TODO Auto-generated catch block
			log.error(e);
			return false;
		} catch (ZooKeeperConnectionException e) {
			// TODO Auto-generated catch block
			log.error(e);
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e);
			return false;
		}
		return true;
	}

	/**
	 * 查询单条记录
	 *
	 * @param tableName
	 *            表名
	 * @param rowKey
	 *            rowKey值
	 * @return 返回单条记录
	 */
	public List<Map<String, String>> selectOneByRowKey(String tableName,
			String rowKey) {
		if (StringUtils.isBlank(rowKey) || StringUtils.isBlank(tableName)) {
			log.error("===Parameters tableName|rowKey should not be blank,Please check!===");
			return null;
		}
		List<Map<String, String>> rowList = new ArrayList<Map<String, String>>();
		try {
			Get get = new Get(Bytes.toBytes(rowKey));
			HTableInterface hTable = getHTable(tableName);
			if (hTable != null) {
				Result result = hTable.get(get);
				Map<String, String> cellMap = getRowByResult(result);
				rowList.add(cellMap);
			}
			hTable.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e);
		}
		return rowList;
	}

	/**
	 * 分页查询表数据
	 *
	 * @param tableName
	 *            表名
	 * @param pageSize
	 *            页大小
	 * @param lastrowKey
	 *            起始rowkey值
	 * @return 返回查询数据结果集
	 */
	public List<Map<String, String>> selectAllByPage(String tableName,
			int pageSize,String name,String startDate,String EndDate,String lastrowKey,List<String> nameList) {
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
		if(!StringUtils.isBlank(name)){
			SingleColumnValueFilter cloumnfilter = new SingleColumnValueFilter("cf1".getBytes(), "name".getBytes(),
					CompareOp.EQUAL, name.getBytes());
			filterList.addFilter(cloumnfilter);
		}
		if(!StringUtils.isBlank(startDate)){
			SingleColumnValueFilter cloumnfilter = new SingleColumnValueFilter("cf1".getBytes(), "date".getBytes(),
					CompareOp.GREATER_OR_EQUAL, startDate.getBytes());
			filterList.addFilter(cloumnfilter);
		}
		if(!StringUtils.isBlank(EndDate)){
			SingleColumnValueFilter cloumnfilter = new SingleColumnValueFilter("cf1".getBytes(), "date".getBytes(),
					CompareOp.LESS_OR_EQUAL, EndDate.getBytes());
			filterList.addFilter(cloumnfilter);
		}
		if(nameList!=null && nameList.size()>0){
			Collection<Filter> filters = new ArrayList<>();
			for(String n : nameList){
				Filter filter = HBaseFilterUtil.eqFilter("cf1","name",n.getBytes());
				filters.add(filter);
			}
			Filter filter = HBaseFilterUtil.orFilter(filters);
			filterList.addFilter(filter);
		}

		Filter pageFilter = new PageFilter(pageSize);
		filterList.addFilter(pageFilter);
		if (!CpConstants.ROWKEY_FIRST.equals(lastrowKey)) {
			Filter rowFilter2 = new RowFilter(CompareOp.GREATER,
					new BinaryComparator(Bytes.toBytes(lastrowKey)));
			filterList.addFilter(rowFilter2);
		}
		scan.setFilter(filterList);
		List<Map<String, String>> lists = new ArrayList<Map<String, String>>();
		try {
			ResultScanner rs = hTable.getScanner(scan);
			for (Result result : rs) {
				lists.add(getRowByResult(result));
			}
			hTable.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e);
		}
		return lists;
	}

	/**
	 * 根据状态分页查询表数据
	 *
	 * @param tableName
	 *            表名
	 * @param ddate
	 *            数据日期
	 * @param pageSize
	 *            页大小
	 * @param lastrowKey
	 *            起始rowkey值
	 * @param status
	 *            发送状态
	 * @return 返回查询数据结果集
	 */
	public List<Map<String, String>> selectAllByPageStatus(String tableName,
			String ddate, int pageSize, String lastrowKey, String status) {
		if (StringUtils.isBlank(tableName) || StringUtils.isBlank(ddate)
				|| StringUtils.isBlank(pageSize + "")
				|| StringUtils.isBlank(lastrowKey)) {
			log.error("===Parameters tableName|ddate|pageSize|rowKey should not be blank,Please check!===");
			return null;
		}
		HTable hTable = (HTable) getHTable(tableName);
		Scan scan = new Scan();
		FilterList filterList = new FilterList(
				FilterList.Operator.MUST_PASS_ALL);
		filterList
				.addFilter(new SingleColumnValueFilter(Bytes.toBytes("info"),
						Bytes.toBytes("status"), CompareOp.EQUAL, Bytes
								.toBytes(status)));
		Filter rowFilter1 = new RowFilter(CompareOp.EQUAL,
				new SubstringComparator(ddate));
		Filter pageFilter = new PageFilter(pageSize);
		filterList.addFilter(rowFilter1);
		filterList.addFilter(pageFilter);
//		if (!CpConstants.ROWKEY_FIRST.equals(lastrowKey)) {
//			Filter rowFilter2 = new RowFilter(CompareFilter.CompareOp.GREATER,
//					new BinaryComparator(Bytes.toBytes(lastrowKey)));
//			filterList.addFilter(rowFilter2);
//		}
		scan.setFilter(filterList);
		List<Map<String, String>> lists = new ArrayList<Map<String, String>>();
		try {
			ResultScanner rs = hTable.getScanner(scan);
			for (Result result : rs) {
				lists.add(getRowByResult(result));
			}
			hTable.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e);
		}
		return lists;
	}

	/**
	 * 获取页数
	 *
	 * @param tableName
	 *            表名
	 * @param name
	 *            数据日期
	 * @param pageSize
	 *            分页大小
	 * @return 返回页数
	 */
	public int getPages(String tableName, String name, int pageSize) {
		if (StringUtils.isBlank(tableName)
				|| StringUtils.isBlank(pageSize + "")) {
			log.error("===Parameters tableName|ddate|pageSize should not be blank,Please check!===");
			return 0;
		}
		enableAggregation(tableName);
		int total = 0;
		try {
			HTable hTable = (HTable) getHTable(tableName);
			Scan scan = new Scan();
			if (!StringUtils.isBlank(name)){
				SingleColumnValueFilter cloumnfilter = new SingleColumnValueFilter("cf1".getBytes(), "name".getBytes(),
						CompareOp.EQUAL, name.getBytes());
				scan.setFilter(cloumnfilter);
			}
			AggregationClient aggregation = new AggregationClient(conf);
			Long count = aggregation.rowCount(hTable,
					new LongColumnInterpreter(), scan);
			total = count.intValue();
			hTable.close();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			log.error(e);
		}
		return (total % pageSize == 0) ? total / pageSize
				: (total / pageSize) + 1;
	}

	/**
	 * 根据发送状态获取页数
	 *
	 * @param tableName
	 *            表名
	 * @param ddate
	 *            数据日期
	 * @param pageSize
	 *            分页大小
	 * @param status
	 *            发送状态
	 * @return 返回页数
	 */
	public int getPagesByStatus(String tableName, String ddate, int pageSize,
			String status) {
		if (StringUtils.isBlank(tableName) || StringUtils.isBlank(ddate)
				|| StringUtils.isBlank(pageSize + "")
				|| StringUtils.isBlank(status)) {
			log.error("===Parameters tableName|ddate|pageSize|status should not be blank,Please check!===");
			return 0;
		}
		enableAggregation(tableName);
		int total = 0;
		try {
			HTable hTable = (HTable) getHTable(tableName);
			Scan scan = new Scan();
			FilterList filterList = new FilterList(
					FilterList.Operator.MUST_PASS_ALL);
			Filter rowFilter = new RowFilter(CompareOp.EQUAL,
					new SubstringComparator(ddate));
			filterList.addFilter(rowFilter);
			filterList.addFilter(new SingleColumnValueFilter(Bytes
					.toBytes("info"), Bytes.toBytes("status"), CompareOp.EQUAL,
					Bytes.toBytes(status)));
			scan.setFilter(filterList);
			AggregationClient aggregation = new AggregationClient(conf);
			Long count = aggregation.rowCount(hTable,
					new LongColumnInterpreter(), scan);
			total = count.intValue();
			hTable.close();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			log.error(e);
		}
		return (total % pageSize == 0) ? total / pageSize
				: (total / pageSize) + 1;
	}

	/**
	 * 获取同一个rowkey下的记录集合
	 *
	 * @param result
	 *            结果集
	 * @return
	 */
	private Map<String, String> getRowByResult(Result result) {
		if (result == null) {
			log.error("===Parameter |result| should not be null,Please check!===");
			return null;
		}
		Map<String, String> cellMap = new HashMap<String, String>();
		for (Cell cell : result.listCells()) {
			String rowkey = Bytes.toString(cell.getRowArray(),
					cell.getRowOffset(), cell.getRowLength());
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
	 * @param tableName
	 *            表名
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
			log.error(e);
			return null;
		}
		return hTable;
	}

	/**
	 * 批量插入或更新
	 *
	 * @param tableName
	 *            表名
	 * @param paraList
	 *            组装成json或xml后的参数
	 * @return 成功-true 失败-false
	 */
	public boolean batchPut(String tableName, List<Map<String, String>> paraList) {
		try {
			List<Put> puts = new ArrayList<Put>();
			for (Map<String, String> map : paraList) {
				Put put = getPutByMap(map);
				puts.add(put);
			}
			HTable hTable = (HTable) getHTable(tableName);
			hTable.put(puts);
			hTable.close();
		} catch (RetriesExhaustedWithDetailsException e) {
			// TODO Auto-generated catch block
			log.error(e);
			return false;
		} catch (InterruptedIOException e) {
			// TODO Auto-generated catch block
			log.error(e);
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e);
			return false;
		}
		return true;
	}

	/**
	 * 根据map返回put
	 *
	 * @param paraMap
	 *            参数map
	 * @return 返回put
	 */
	private Put getPutByMap(Map<String, String> paraMap) {
		if (paraMap == null) {
			log.error("===Parameter |paraMap| should not be null,Please check!===");
			return null;
		}
		Set<Entry<String, String>> set = paraMap.entrySet();
		Iterator<Entry<String, String>> it = set.iterator();
		byte[] rowkey = Bytes.toBytes(paraMap
				.get(CpConstants.HBASE_TABLE_PROP_ROWKEY));
		byte[] columnfamily = Bytes.toBytes(paraMap
				.get(CpConstants.HBASE_TABLE_PROP_COLUMNFAMILY));
		Put put = new Put(rowkey);
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			String key = entry.getKey();
			if (!CpConstants.HBASE_TABLE_PROP_ROWKEY.equals(key)
					&& !CpConstants.HBASE_TABLE_PROP_COLUMNFAMILY.equals(key)) {
				String value = entry.getValue();
				put.add(columnfamily, Bytes.toBytes(key), Bytes.toBytes(value));
			}
		}
		return put;
	}

	/**
	 * 使表具有聚合功能
	 *
	 * @param tableName
	 *            表名
	 */
	@SuppressWarnings("resource")
	private void enableAggregation(String tableName) {
		String coprocessorName = "org.apache.hadoop.hbase.coprocessor.AggregateImplementation";
		try {
			HBaseAdmin admin = new HBaseAdmin(conf);
			HTableDescriptor htd = admin.getTableDescriptor(Bytes
					.toBytes(tableName));
			List<String> coprocessors = htd.getCoprocessors();
			if (coprocessors != null && coprocessors.size() > 0) {
				return;
			} else {
				admin.disableTable(tableName);
				htd.addCoprocessor(coprocessorName);
				admin.modifyTable(tableName, htd);
				admin.enableTable(tableName);
			}
		} catch (TableNotFoundException e) {
			// TODO Auto-generated catch block
			log.error(e);
		} catch (MasterNotRunningException e) {
			// TODO Auto-generated catch block
			log.error(e);
		} catch (ZooKeeperConnectionException e) {
			// TODO Auto-generated catch block
			log.error(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e);
		}
	}
}