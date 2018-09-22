package org.ermain.scala.spark.ciombra.data_processing

import org.apache.spark.ml.feature.{StringIndexer, VectorAssembler}

object DataPipeline {

  val labelStringIndexer: StringIndexer = new StringIndexer()
    .setInputCol("classification")
    .setOutputCol("label")

  // This is where we create the 'features' column for the linear regression analysis
  val assembler = new VectorAssembler()
    .setInputCols(Array("Age", "BMI", "Glucose", "Insulin", "HOMA", "Leptin", "Adiponectin", "Resistin", "MCP_1"))
    .setOutputCol("features")
}
