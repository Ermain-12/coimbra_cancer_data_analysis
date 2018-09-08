package org.ermain.scala.spark.ciombra.data_processing

import org.apache.spark
import org.apache.spark._
import org.apache.spark.sql.types._
import org.apache.spark.sql.Row

object Preprocessing {

  def main(args: Array[String]): Unit = {

    val session = SparkSessionCreate.createSession

    // Specify the path to the data source
    val dataPath = "data/coimbra.csv"

    val data = session.read
      .option("inferSchema", "true")
      .option("header", "true")
      .format("com.databricks.spark.csv")
      .load(dataPath)
      .cache()

    println(data.printSchema())

  }


}
