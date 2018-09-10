package regression

import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.LogisticRegression
import org.ermain.scala.spark.ciombra.data_processing.Preprocessing
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.linalg.Vectors

object LinearRegression {

  def main(args: Array[String]): Unit = {

    // Prepare the processing data
    val inputDataset = Preprocessing.data

    // This is where we create the 'features' column for the linear regression analysis
    val assembler = new VectorAssembler()
      .setInputCols(Array("Age", "BMI", "Glucose", "Insulin", "HOMA", "Leptin", "Adiponectin", "Resistin", "MCP_1"))
      .setOutputCol("features")

    // Create the Logistic Regression object
    val logRegress = new LogisticRegression()
      .setMaxIter(5)
      .setRegParam(0.3)
      .setElasticNetParam(0.8)
      .setFeaturesCol("features")     // This sets the feature column for the data-set
      .setLabelCol("Classification")  // This sets the Label column of the data-set

    // WE now create a pipeline
    val linRegressPipeline = new Pipeline()
      .setStages(Array(assembler, logRegress))

    // Create the logistic regression model
    val logRegressModel = linRegressPipeline
        .fit(inputDataset)


    // Display the coefficients and intercept of the entire dataset
    println(s"Dataset Coefficient: ${logRegressModel.uid}")
    // println(s"Dataset Intercept: ${logRegressModel.intercept}")
  }
}
