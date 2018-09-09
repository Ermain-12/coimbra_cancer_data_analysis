package regression

import org.apache.spark.ml.classification.LogisticRegression
import org.ermain.scala.spark.ciombra.data_processing.Preprocessing

object LinearRegression {

  def main(args: Array[String]): Unit = {

    // Prepare the processing data
    val inputDataset = Preprocessing.data

    // Create the Logistic Regression object
    val logRegress = new LogisticRegression()
      .setMaxIter(5)
      .setRegParam(0.3)
      .setElasticNetParam(0.8)

    // Create the logistic regression model
    val logRegressModel = logRegress.fit(inputDataset)

    // Display the coefficients and intercept of the entire dataset
    println(s"Dataset Coefficient: ${logRegressModel.coefficients}")
    println(s"Dataset Intercept: ${logRegressModel.intercept}")
  }
}
