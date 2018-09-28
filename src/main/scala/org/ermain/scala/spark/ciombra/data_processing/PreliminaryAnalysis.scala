package org.ermain.scala.spark.ciombra.data_processing

import org.apache.spark.sql.{DataFrame, SparkSession}

object PreliminaryAnalysis {

    val session: SparkSession = SparkSessionCreate.createSession
    val dataInput: DataFrame = Preprocessing.data

    println(dataInput.printSchema())

    // Split the data into a training and testing set
    val seed: Double = 98765L
    println("Splitting data-set....")
    val Array(trainingSet, testingSet) = dataInput.randomSplit(Array(0.75, 0.25))

    println("Displaying the first ten rows of the data set.....")
    dataInput.show(10)

    dataInput.createOrReplaceTempView("coimbra")
    val sqlDF = session.sql("SELECT Age, BMI FROM coimbra WHERE Age BETWEEN 20 AND 40")
    sqlDF.show(10)


}
