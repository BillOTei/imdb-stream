package com.imdb.services

import akka.stream.IOResult
import akka.stream.alpakka.csv.scaladsl.{CsvParsing, CsvToMap}
import akka.stream.scaladsl.{FileIO, Source => AkkaSource}
import shapeless._
import shapeless.syntax.typeable._

import java.nio.file.Paths
import scala.collection.mutable
import scala.concurrent.Future
import scala.io.Source
import scala.util.{Try, Using}

object DataService {
  private val folder = getClass.getResource("").getPath + "data"

  def convert[T: Typeable](v: Option[String]): Option[T] = v match {
    case Some("""\N""") => None
    case Some(x)        => x.cast[T]
    case _              => None
  }

  def streamFile(fileName: String): AkkaSource[Map[String, String], Future[IOResult]] = {
    val file = Paths.get(folder + "/" + fileName)
    FileIO
      .fromPath(file)
      //.via(Compression.inflate())
      .via(CsvParsing.lineScanner('\t'))
      .via(CsvToMap.toMapAsStrings())
  }

  def fileToInMemoryMap[T](fileName: String, apply: List[String] => T): Try[mutable.HashMap[String, T]] = {
    val map = collection.mutable.HashMap[String, T]()
    Using(Source.fromFile(folder + "/" + fileName)) { content =>
      val parsed = content
        .getLines
        .map(_.split('\t'))
      parsed.next
      parsed.foreach { arr =>
        val o = apply(arr.toList)
        map += (arr(0) -> o)
      }

      map
    }
  }

  def fileToInMemoryStringMap[T](fileName: String): Try[mutable.HashMap[String, Array[String]]] = {
    val map = collection.mutable.HashMap[String, Array[String]]()
    Using(Source.fromFile(folder + "/" + fileName)) { content =>
      val parsed = content
        .getLines
        .map(_.split('\t'))
      parsed.next
      parsed.foreach { arr =>
        map += (arr(0) -> arr)
      }

      map
    }
  }
}
