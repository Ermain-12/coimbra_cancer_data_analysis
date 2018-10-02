package org.ermain.scala.spark.ciombra.data_processing

import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.feature.{StringIndexer, VectorAssembler}

object DataPipeline {

    val bmiIndexer: StringIndexer = new StringIndexer()
      .setInputCol("BMI")
      .setOutputCol("indexedBMI")

    val glucoseIndexer: StringIndexer = new StringIndexer()
      .setInputCol("Glucose")
      .setOutputCol("indexedGlucose")

    val insulinIndexer: StringIndexer = new StringIndexer()
      .setInputCol("Insulin")
      .setOutputCol("indexedInsulin")

    val homaIndexer: StringIndexer = new StringIndexer()
      .setInputCol("HOMA")
      .setOutputCol("indexedHOMA")

    val leptinIndexer: StringIndexer = new StringIndexer()
      .setInputCol("Leptin")
      .setOutputCol("indexedLeptin")

    val adiponectinIndexer: StringIndexer = new StringIndexer()
      .setInputCol("Adiponectin")
      .setOutputCol("indexedAdiponectin")

    val resistinIndexer: StringIndexer = new StringIndexer()
      .setInputCol("Resistin")
      .setOutputCol("indexedResistin")

    val mcpIndexer: StringIndexer = new StringIndexer()
      .setInputCol("MCP_1")
      .setOutputCol("indexedMCP")


    val featuresColumn: Array[String] = Array("indexedBMI",
        "indexedGlucose", "indexedInsulin", "indexedHOMA",
        "indexedLeptin", "indexedAdiponectin", "indexedResistin", "indexedMCP")

    // This is where we create the 'features' column for the linear regression analysis
    val assembler = new VectorAssembler()
      .setInputCols(featuresColumn)
      .setOutputCol("features")


    val labelIndexer: StringIndexer = new StringIndexer()
      .setInputCol("classification")
      .setOutputCol("Label")
      .setHandleInvalid("skip")

    // Create the Logistic Regression object
    val logRegress = new LogisticRegression()
      .setFeaturesCol("features")     // This sets the feature column for the data-set
      .setLabelCol("Label")  // This sets the Label column of the data-set

    val pipeline = new Pipeline()
        .setStages(Array(bmiIndexer,
            glucoseIndexer,
            insulinIndexer,
            homaIndexer,
            leptinIndexer,
            adiponectinIndexer,
            resistinIndexer,
            mcpIndexer,
            labelIndexer,
          assembler,
            logRegress))


}
