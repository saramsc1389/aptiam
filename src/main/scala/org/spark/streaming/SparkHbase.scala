package org.spark.streaming

import org.apache.hadoop.hbase.client.{HBaseAdmin, HTable, Put}
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.{HBaseConfiguration, HColumnDescriptor, HTableDescriptor}
import org.apache.spark.{SparkConf, SparkContext}

import scala.util.Random

/**
  * Created by saravanan on 20/05/16.
  */
object SparkHbase {

  val conf = HBaseConfiguration.create()
  val rnd = new Random()

  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("hbaseTest").setMaster("local[3]")
    val sc = new SparkContext(conf)

    //val location = "/Users/saravanan/Desktop/first_5L.tsv"
    val location = args(0)
    println(location)

    val people = sc.textFile(location)
    println(people.count())
    println(people.first())

    people.foreach(r=>saveHbase(r,"dailymail"))

  println("sucess")


  }

  def saveHbase(rddValue: String, rowNameNew: String): Unit = {

    val splitValue = rddValue.split("\t").toList

    var cn = "Type\tCMID\tSlotID\tSiteID\tSiteName\tPriceLevel\tRequestID\tAdUnit\tDealID\tDomain\tCountry\tState\tDMA\tConnectionSpeed\tOS\tBrowser\tDeviceType\tLanguage\tDSP\tBrandID\tBrandName\tCampaignID\tUserType\tTimeStamp\tWinningRate\tBidRate\tClearRate\tRequestUUID\tServerID"
    val columnName = cn.split("\t").toList


    val tableName = "dailymail"
    conf.set(TableInputFormat.INPUT_TABLE, tableName)
    val admin = new HBaseAdmin(conf)

    println(admin.isTableAvailable(tableName))

    if (!admin.isTableAvailable(tableName)) {
      val tableDesc = new HTableDescriptor(tableName)
      tableDesc.addFamily(new HColumnDescriptor("dailymail".getBytes()))
      admin.createTable(tableDesc)
    }

    val table = new HTable(conf, tableName)
    var rowName = rowNameNew + "_" + rnd.nextInt(500000000)
    var row = new Put(rowName.getBytes())

    for (i <- 0 to (splitValue.length - 1)) {
      row.add("cf".getBytes(), columnName(i).getBytes(), (splitValue(i)).getBytes())
    }

    table.put(row)

    table.close()
  }

}
