name := "scala_spark_coimbra_data_analysis"

version := "0.1"

scalaVersion := "2.11.12"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.3.1",
  "org.apache.spark" %% "spark-sql" % "2.3.1",
  "org.apache.spark" %% "spark-mllib" % "2.3.1",
  "com.databricks" % "spark-csv_2.11" % "1.2.0"
)