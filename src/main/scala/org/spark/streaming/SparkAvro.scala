package org.spark.streaming

import java.util.{Date, Properties}

import kafka.producer.{KeyedMessage, Producer, ProducerConfig}
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}
import com.databricks.spark.avro._

import scala.util.Random


/**
  * Created by saravanan on 16/05/16.
  */
object SparkAvro {

  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("RheaSparkConnector").setMaster("local[3]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    println(sc)
    val df = sqlContext.read.avro("/Users/saravanan/Downloads/twitter.avro")
    df.printSchema()
    df.first()
    df.show()

    val events = 10000//args(0).toInt
    val topic = "spark-topic"
    val brokers = "localhost:9092"
    val rnd = new Random()
    val props = new Properties()
    props.put("metadata.broker.list", brokers)
    props.put("serializer.class", "kafka.serializer.StringEncoder")
    props.put("producer.type", "async")

    val config = new ProducerConfig(props)
    val producer = new Producer[String, String](config)
    val t = System.currentTimeMillis()
    for (nEvents <- Range(0, events)) {
      println("1")
      val runtime = new Date().getTime();
      val ip = "192.168.2." + rnd.nextInt(255);
      val msg = runtime + "," + nEvents + ",www.example.com," + ip;
      val data = new KeyedMessage[String, String](topic, ip, """{"action":"create","timestamp":"2016-01-07 00:01:17"}""");
      producer.send(data);
      Thread.sleep(100)
      println
    }

    System.out.println("sent per second: " + events * 1000 / (System.currentTimeMillis() - t));
    producer.close();


  }

}
