name := "SparkStreaming"

version := "1.0"

scalaVersion := "2.10.6"


libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-core_2.10" % "1.5.2",
  "org.apache.spark" % "spark-streaming-kafka_2.10" % "1.5.2",
  "org.apache.spark" % "spark-streaming_2.10" % "1.5.2",
  "com.databricks" % "spark-avro_2.10" % "2.0.1",
  "org.apache.spark" % "spark-sql_2.10" % "1.5.2",
  "org.apache.hadoop" % "hadoop-core" % "0.20.2",
  "org.apache.hbase" % "hbase" % "0.98.8-hadoop2",
  "org.apache.hbase" % "hbase-client" % "0.98.8-hadoop2",
  "org.apache.hbase" % "hbase-common" % "0.98.8-hadoop2",
  "org.apache.hbase" % "hbase-server" % "0.98.8-hadoop2"
)

//mergeStrategy in assembly := {
//  case m if m.toLowerCase.endsWith("manifest.mf") => MergeStrategy.discard
//  case m if m.toLowerCase.matches("meta-inf.*\\.sf$") => MergeStrategy.discard
//  case "META-INF/jersey-module-version" => MergeStrategy.first
//  case _ => MergeStrategy.first
//}