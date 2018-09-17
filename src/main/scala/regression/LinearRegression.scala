package regression

import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.ermain.scala.spark.ciombra.data_processing.{PreliminaryAnalysis, Preprocessing, SparkSessionCreate}
import org.apache.spark.ml.tuning.{CrossValidator, ParamGridBuilder}
import org.apache.spark.mllib.evaluation.RegressionMetrics
import org.apache.spark.sql.{Row, SparkSession}
import org.ermain.scala.spark.ciombra.data_processing.DataPipeline._
import org.apache.log4j.Logger
import org.apache.log4j.Level

object LinearRegression{

  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSessionCreate.createSession
    import spark.implicits._

    Logger.getLogger("org")
      .setLevel(Level.OFF)
    Logger.getLogger("akka")
      .setLevel(Level.OFF)

    // Hyper-parameters
    val numFolds: Int = 10
    val MaxIter: Seq[Int] = Seq(100)
    val Tol: Seq[Double] = Seq(1e-6)
    val RegParam: Seq[Double] = Seq(0.001)
    val ElasticParam: Seq[Double] = Seq(0.001)


    // Prepare the processing data
    val inputDataset = Preprocessing.data

//    // This is where we create the 'features' column for the linear regression analysis
//    val assembler = new VectorAssembler()
//      .setInputCols(Array("Age", "BMI", "Glucose", "Insulin", "HOMA", "Leptin", "Adiponectin", "Resistin", "MCP_1"))
//      .setOutputCol("features")

    // Create the Logistic Regression object
    val logRegress = new LogisticRegression()
      .setFeaturesCol("features")     // This sets the feature column for the data-set
      .setLabelCol("label")  // This sets the Label column of the data-set
//      .setMaxIter(5)
//      .setRegParam(0.3)
//      .setElasticNetParam(0.8)


    // WE now create a pipeline
    println("Building ML Pipeline.....")
    val linRegressPipeline = new Pipeline()
      .setStages(Array(assembler, logRegress))

    // ***********************************************************
    println("Preparing K-fold Cross Validation and Grid Search: Model tuning")
    // ***********************************************************
    // Create the Parameter-Grid-Builder to search for the best parameters
    // for the most accurate model
    val paramGridBuilder = new ParamGridBuilder()
      .addGrid(logRegress.maxIter, MaxIter)
      .addGrid(logRegress.regParam, RegParam)
      .addGrid(logRegress.tol, Tol)
      .addGrid(logRegress.elasticNetParam, ElasticParam)
      .build()

    val crossEval = new CrossValidator()
      .setNumFolds(numFolds)
      .setEstimatorParamMaps(paramGridBuilder)
      .setEvaluator(new RegressionEvaluator)
      .setEstimator(linRegressPipeline)
    // Input the training data into the Regression model
    val trainInputData = PreliminaryAnalysis.training
    // Create the logistic regression model
//    val logRegressModel = linRegressPipeline
//        .fit(trainInputData)
    // ************************************************************
    println("Training model with Linear Regression algorithm")
    // ************************************************************
    val crossValidatorModel = crossEval.fit(trainInputData)


    val trainPredictionsAndLabels = crossValidatorModel
      .transform(trainInputData)
      .select("label", "prediction")
      .map{
        case Row(label: Double, prediction: Double) =>
          (label, prediction)
      }
      .rdd

    // Show the predictions and labels for the testing set
    val testInputData = PreliminaryAnalysis.testing
    val testPredictionsAndLabels = crossValidatorModel
      .transform(testInputData)
      .select("label", "prediction")
      .map{
        case Row(label: Double, prediction: Double) =>
          (label, prediction)
      }.rdd

    // Print the schema for the data-set of the selected testing set

    /*
            +--------------------+--------------------+----------+--------------+
            |       rawPrediction|         probability|prediction|Classification|
            +--------------------+--------------------+----------+--------------+
            |[-3.6532604438237...|[0.00208045692691...|       2.0|             1|
            |[-3.6532604438237...|[0.00208045692691...|       2.0|             1|
            |[-3.6532604438237...|[0.00208045692691...|       2.0|             2|
            |[-3.6532604438237...|[0.00208045692691...|       2.0|             2|
            |[-3.6532604438237...|[0.00208045692691...|       2.0|             2|
            |[-3.6532604438237...|[0.00208045692691...|       2.0|             2|
            |[-3.6532604438237...|[0.00208045692691...|       2.0|             1|
            |[-3.6532604438237...|[0.00208045692691...|       2.0|             2|
            |[-3.6532604438237...|[0.00208045692691...|       2.0|             2|
            |[-3.6532604438237...|[0.00208045692691...|       2.0|             2|
            |[-3.6532604438237...|[0.00208045692691...|       2.0|             2|
            |[-3.6532604438237...|[0.00208045692691...|       2.0|             2|
            |[-3.6532604438237...|[0.00208045692691...|       2.0|             2|
            |[-3.6532604438237...|[0.00208045692691...|       2.0|             2|
            |[-3.6532604438237...|[0.00208045692691...|       2.0|             2|
            |[-3.6532604438237...|[0.00208045692691...|       2.0|             1|
            |[-3.6532604438237...|[0.00208045692691...|       2.0|             2|
            |[-3.6532604438237...|[0.00208045692691...|       2.0|             1|
            +--------------------+--------------------+----------+--------------+

     */
//    predictions.printSchema()
//    val selected = predictions.select("rawPrediction", "probability", "prediction", "Classification")
//    selected.show()

    // Now, begin the testing the accuracy of this model
    val trainRegressionMetrics =  new RegressionMetrics(trainPredictionsAndLabels)
    val testRegressionMetrics =   new RegressionMetrics(testPredictionsAndLabels)


    println(s"Training Mean Squared Error: ${trainRegressionMetrics.meanSquaredError}")
    println(s"Testing Mean Squared Error: ${testRegressionMetrics.meanSquaredError}")
  }
}
