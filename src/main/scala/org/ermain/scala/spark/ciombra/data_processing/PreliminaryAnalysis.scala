package org.ermain.scala.spark.ciombra.data_processing

import org.apache.spark.sql.{DataFrame, Dataset, Row}

object PreliminaryAnalysis {

  def main(args: Array[String]): Unit = {
    val session = SparkSessionCreate.createSession

    val dataInput: DataFrame = Preprocessing.data

    println(dataInput.printSchema())

    // Split the data into a training and testing set
    val seed: Double = 98765L
    val dataSplit: Array[Dataset[Row]] = dataInput.randomSplit(Array(0.85, 0.15))
    println(s"dataSplit size: ${dataSplit.length}")

    dataInput.select("Age", "BMI", "Glucose", "Insulin").show(10)

    val trainingSet = dataSplit(0)
    // println(s"Training set size is ${trainingSet.count()}")

    val testingSet = dataSplit(1)
    // println(s"Testing set size is ${testingSet.count()}")
  }
}
