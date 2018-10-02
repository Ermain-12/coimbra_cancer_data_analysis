package org.ermain.scala.spark.ciombra.data_processing

import org.apache.spark.sql.{Dataset, SparkSession}
import org.ermain.scala.spark.ciombra.data_processing.CoimbraData.Patient

object Preprocessing {

  val spark: SparkSession = SparkSessionCreate.createSession

  // Specify the path to the data source
  val dataPath = "data/coimbra.csv"

  import spark.implicits._
  val data: Dataset[Patient] = spark.read
    .option("inferSchema", "false")
    .option("header", "true")
    .format("com.databricks.spark.csv")
    .load(dataPath)
    .cache()
    .as[Patient]

  data.drop("age")

}
