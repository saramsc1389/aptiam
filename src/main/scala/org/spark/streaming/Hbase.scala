package org.spark.streaming

import org.apache.hadoop.hbase.client.{HBaseAdmin, HTable, Put}
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.{HBaseConfiguration, HColumnDescriptor, HTableDescriptor}

object Hbase {

  def main(args: Array[String]) {

    //  val sparkConf = new SparkConf().setAppName("HBaseBulkPutExample ")
    //    val sc = new SparkContext(sparkConf)

    //    val conf1 = new HBaseConfiguration()
    //    val admin = new HBaseAdmin(conf1)
    //
    //    val listtables=admin.listTables()
    //    listtables.foreach(println)

    //    val conf = HBaseConfiguration.create();
    //
    //    println ("hbase.zookeeper.quorum--" + (Properties envOrNone "hbase_zookeeper_quorum"))
    //
    //    conf.set(
    //      "hbase.zookeeper.quorum",
    //      Properties.envOrElse(
    //        "hbase_zookeeper_quorum",
    //        conf.get("hbase.zookeeper.quorum")))
    //
    //    val admin = new HBaseAdmin(conf)
    //
    //    // list the tables
    //    val listtables=admin.listTables()
    //    listtables.foreach(println)


    val conf = HBaseConfiguration.create()

    val tableName = "spark_table"
    conf.set(TableInputFormat.INPUT_TABLE, tableName)
    val admin = new HBaseAdmin(conf)

    println(admin.isTableAvailable(tableName))

    if (!admin.isTableAvailable(tableName)) {
      val tableDesc = new HTableDescriptor(tableName)
      tableDesc.addFamily(new HColumnDescriptor("spark_table".getBytes()))
      admin.createTable(tableDesc)
    }

    val table = new HTable(conf, tableName)
    var row = new Put("dummy-1".getBytes())
    row.add("cf".getBytes(), "record - 11".getBytes(), (""""[ {"key" : "CMS Perm Gen","value" : {"committed" : 30605312,"init" : 21757952,"max" : 85983232,"used" : 30390776}}]""").getBytes())
    row.add("cf".getBytes(), "record - 12".getBytes(), (""""[ {"key123" : "CMS Perm Gen","value" : {"committed" : 30605312,"init" : 21757952,"max" : 85983232,"used" : 30390776}}]""").getBytes())

    table.put(row)

    table.close()

  }

}
