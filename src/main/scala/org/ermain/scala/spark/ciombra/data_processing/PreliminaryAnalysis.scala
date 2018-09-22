package org.ermain.scala.spark.ciombra.data_processing

import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

object PreliminaryAnalysis {


    val session: SparkSession = SparkSessionCreate.createSession
    val dataInput: DataFrame = Preprocessing.data

    println(dataInput.printSchema())

    // Split the data into a training and testing set
    val seed: Double = 98765L
    println("Splitting data-set....")
    val dataSplit = dataInput.randomSplit(Array(0.75, 0.25), 1234L)

    println("Displaying the first ten rows of the data set.....")
    dataInput.show(10)

    dataInput.createOrReplaceTempView("coimbra")
    val sqlDF = session.sql("SELECT Age, BMI FROM coimbra WHERE Age BETWEEN 20 AND 40")
    sqlDF.show(10)

    val trainingSet: Dataset[Row] = dataSplit(0)
    val testingSet: Dataset[Row] = dataSplit(1)
//    val training = trainingSet
//        .withColumnRenamed("classification", "label")

//    println(s"Training set size is ${training.show(5)}")
//    println(s"Training set is ${training.describe()}")
//
//    val testing = testingSet
//        .withColumnRenamed("classification", "label")
//    println(s"Testing set size is ${testingSet.show(5)}")
//    println(s"Testing set is ${testing.describe()}")

}
