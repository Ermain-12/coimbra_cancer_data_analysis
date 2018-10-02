package random_forest

import org.apache.spark.ml.{Pipeline, PipelineModel, PipelineStage}
import org.apache.spark.ml.classification.RandomForestClassifier
import org.apache.spark.ml.evaluation.BinaryClassificationEvaluator
import org.apache.spark.ml.tuning.{CrossValidator, ParamGridBuilder}
import org.apache.spark.sql.SparkSession
import org.ermain.scala.spark.ciombra.data_processing.{DataPipeline, PreliminaryAnalysis, SparkSessionCreate}

object RandomForest {

  def main(args: Array[String]): Unit = {
    val seed: Long = 12345L
    /// Create a spark session
    val spark: SparkSession = SparkSessionCreate.createSession


    import spark.implicits._

    val randomForestModel = new RandomForestClassifier()
      .setFeaturesCol("features")
      .setLabelCol("label")
      .setSeed(seed)


    // Set the stages for the pipeline
//    val randomForestPipeline = new Pipeline()
//      .setStages(Array(DataPipeline.labelStringIndexer,
//                       DataPipeline.assembler,
//                       randomForestModel
//      ))
//
    // Search through decision tree's maxDepth parameter for best model
    val paramGrid = new ParamGridBuilder()
      .addGrid(randomForestModel.maxDepth, 5 :: 10 :: 15 :: 20 :: 25 :: 30 :: Nil)
      .addGrid(randomForestModel.featureSubsetStrategy, "auto" :: "all" :: Nil)
      .addGrid(randomForestModel.impurity, "gini" :: "entropy" :: Nil)
      .addGrid(randomForestModel.maxBins, 3 :: 5 :: 10 :: 15 :: 25 :: 35 :: 45 :: Nil)
      .addGrid(randomForestModel.numTrees, 10 :: 50 :: 100 :: Nil)
      .build()

    val evaluator = new BinaryClassificationEvaluator()
      .setLabelCol("label")
      .setRawPredictionCol("prediction")

    // Now set-up a 10-fold Cross-Validator
    val crossValid = new CrossValidator()
      .setEstimatorParamMaps(paramGrid)
      .setEstimator(randomForestModel)
      .setEvaluator(evaluator)
      .setNumFolds(10)

    val trainInputData = PreliminaryAnalysis.trainingSet
     val crossValModel = crossValid.fit(trainInputData)

     val bestModel = crossValModel.bestModel
     println("The Best Model and Parameters:\n--------------------")
     println(bestModel.asInstanceOf[PipelineModel].stages(3))

     //Make predictions
      val predictionData = crossValModel.transform(PreliminaryAnalysis.testingSet)
      predictionData.show(10)
      val predictions = crossValModel.transform(PreliminaryAnalysis.trainingSet)
      predictions.show(10)
  }
}
