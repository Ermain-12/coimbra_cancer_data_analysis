package org.ermain.scala.spark.ciombra.data_processing

import org.apache.spark.sql.{Dataset, Row, SparkSession}

object Preprocessing {

  val session: SparkSession = SparkSessionCreate.createSession

  // Specify the path to the data source
  val dataPath = "data/coimbra.csv"

  val data: Dataset[Row] = session.read
    .option("inferSchema", "true")
    .option("header", "true")
    .format("com.databricks.spark.csv")
    .load(dataPath)
    .cache()
    .na.drop()


  val dataDF = data
  dataDF.groupBy("classification")
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
