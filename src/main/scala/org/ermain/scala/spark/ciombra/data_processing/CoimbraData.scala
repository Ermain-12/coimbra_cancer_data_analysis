package org.ermain.scala.spark.ciombra.data_processing

object CoimbraData {

  case class Patient(age: Int,
                     BMI: Double,
                     Glucose: Int,
                     Insulin: Double,
                     HOMA: Double,
                     Leptin: Double,
                     Adiponectin: Double,
                     Resistin: Double,
                     MCP_1: Double,
                     Classification: Int)
}
