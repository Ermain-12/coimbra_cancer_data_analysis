package org.ermain.scala.spark.ciombra.data_processing

import org.apache.spark.ml.feature.{StringIndexer, VectorAssembler}

object DataPipeline {

    val featuresColumn: Array[String] = Array("BMI", "Glucose", "Insulin", "HOMA",
      "Leptin", "Adiponectin", "Resistin", "MCP_1")

    // This is where we create the 'features' column for the linear regression analysis
    val assembler = new VectorAssembler()
      .setInputCols(featuresColumn)
      .setOutputCol("features")

    val dataInputDF = assembler.transform(PreliminaryAnalysis.trainingSet.toDF())

    dataInputDF.show()

    val labelIndexer = new StringIndexer()
      .setInputCol("classification")
      .setOutputCol("label")

}
