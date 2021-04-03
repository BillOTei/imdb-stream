package com.imdb.services

import akka.stream.IOResult
import akka.stream.alpakka.csv.scaladsl.{CsvParsing, CsvToMap}
import akka.stream.scaladsl.{FileIO, Source}
import shapeless._
import shapeless.syntax.typeable._

import java.nio.file.Paths
import scala.concurrent.Future

object DataService {
  private val folder = getClass.getResource("").getPath + "data"

  def convert[T: Typeable](v: Option[String]): Option[T] = v match {
    case Some("""\N""") => None
    case Some(x)        => x.cast[T]
    case _              => None
  }

  def streamFile(fileName: String): Source[Map[String, String], Future[IOResult]] = {
    val file = Paths.get(folder + "/" + fileName)
    FileIO
      .fromPath(file)
      //.via(Compression.inflate())
      .via(CsvParsing.lineScanner('\t'))
      .via(CsvToMap.toMapAsStrings())
  }
}
