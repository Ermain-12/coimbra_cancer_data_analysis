package org.ermain.scala.spark.ciombra.data_processing

import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.RandomForestClassifier
import org.apache.spark.ml.feature.{StringIndexer, VectorAssembler}
import org.ermain.scala.spark.ciombra.data_processing.PreliminaryAnalysis.dataInput

object DataPipeline {

  def main(args: Array[String]): Unit = {

    val featuresColumn: Array[String] = Array("BMI", "Glucose", "Insulin", "HOMA",
      "Leptin", "Adiponectin", "Resistin", "MCP_1")

    // This is where we create the 'features' column for the linear regression analysis
    val assembler = new VectorAssembler()
      .setInputCols(featuresColumn)
      .setOutputCol("features")

    val dataInputDF = assembler.transform(Preprocessing.data)

    dataInputDF.show()

    val labelIndexer = new StringIndexer()
      .setInputCol("classification")
      .setOutputCol("label")


    val dataInputDF2 = labelIndexer.fit(dataInputDF)
        .transform(dataInputDF)

    dataInputDF2.show(10)

    val Array(trainingSetDF, testingSetDF) = dataInputDF2.randomSplit(Array(0.7, 0.3))

    val randomForestClassifier = new RandomForestClassifier()
      .setImpurity("gini")
      .setMaxDepth(3)
      .setNumTrees(20)
      .setFeatureSubsetStrategy("auto")
      .setSeed(5043)

    val randomForestModel = randomForestClassifier.fit(trainingSetDF)

    val randomForestPredictions = randomForestModel.transform(testingSetDF)
    randomForestPredictions.show(10)
  }

}
