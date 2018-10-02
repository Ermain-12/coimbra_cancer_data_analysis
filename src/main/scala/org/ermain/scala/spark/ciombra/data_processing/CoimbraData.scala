package org.ermain.scala.spark.ciombra.data_processing

import org.apache.spark.sql.types._

object CoimbraData {

  case class Patient(age: String,
                     BMI: String,
                     Glucose: String,
                     Insulin: String,
                     HOMA: String,
                     Leptin: String,
                     Adiponectin: String,
                     Resistin: String,
                     MCP_1: String,
                     Classification: String)

  val patientSchema = StructType(Array(
    StructField("age", StringType, true),
    StructField("BMI", StringType, true),
    StructField("Glucose", StringType, true),
    StructField("Insulin", StringType, true),
    StructField("HOMA", StringType, true),
    StructField("Leptin", StringType, true),
    StructField("Adiponectin", StringType, true),
    StructField("Resistin", StringType, true),
    StructField("MCP_1", StringType, true),
    StructField("classification", StringType, true)
  ))
}
