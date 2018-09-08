package org.ermain.scala.spark.ciombra.data_processing

import java.io.File

import org.apache.spark.sql.SparkSession

object SparkSessionCreate {

  def createSession: SparkSession = {

    val sparkwarehouseLocation = new File("spark-warehouse").getAbsolutePath
    val spark = SparkSession.builder()
      .master("local[*]")
      .config("spark-warehouse", sparkwarehouseLocation)
      .appName("coimbra_data_analysis")
      .getOrCreate()

    spark
  }
}
