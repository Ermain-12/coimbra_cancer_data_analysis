package org.ermain.scala.spark.ciombra.data_processing

import org.apache.spark
import org.apache.spark._
import org.apache.spark.sql.types._
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

object Preprocessing {

  val session: SparkSession = SparkSessionCreate.createSession

  // Specify the path to the data source
  val dataPath = "data/coimbra.csv"

  val data: DataFrame = session.read
    .option("inferSchema", "true")
    .option("header", "true")
    .format("com.databricks.spark.csv")
    .load(dataPath)
    .cache()

  val dataDF = data
  dataDF.groupBy("Classification")
    .sum("BMI")
    .show()


  dataDF.groupBy("classification")
      .sum("HOMA")
      .show()

  dataDF.groupBy("classification")
      .sum("Resistin")
      .show()

  dataDF.groupBy("classification")
      .sum("Insulin")
      .show()

  dataDF.drop("age")

}
