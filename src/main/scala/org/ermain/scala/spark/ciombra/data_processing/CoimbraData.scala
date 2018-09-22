package org.ermain.scala.spark.ciombra.data_processing

import org.apache.spark.sql.types.{DoubleType, IntegerType, StructField, StructType}

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

  val patientSchema = StructType(Array(
    StructField("age", IntegerType, true),
    StructField("BMI", DoubleType, true),
    StructField("Glucose", DoubleType, true),
    StructField("Insulin", DoubleType, true),
    StructField("HOMA", DoubleType, true),
    StructField("Leptin", DoubleType, true),
    StructField("Adiponectin", DoubleType, true),
    StructField("Resistin", DoubleType, true),
    StructField("MCP_1", DoubleType, true),
    StructField("classification", IntegerType, true)
  ))
}
