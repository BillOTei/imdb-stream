package com.imdb.models

import com.imdb.services.DataService

import scala.util.Try

case class Title(
    tconst: Option[String],
    titleType: Option[String],
    primaryTitle: Option[String],
    originalTitle: Option[String],
    isAdult: Option[Boolean],
    startYear: Option[Int],
    endYear: Option[Int],
    runtimeMinutes: Option[Int],
    genres: List[String]
)

object Title {

  val primaryTitleName = "primaryTitle"

  def fromMap(m: Map[String, String]): Try[Title] = Try(Title(
    tconst = m.get("tconst"),
    titleType = m.get("titleType"),
    primaryTitle = m.get("primaryTitle"),
    originalTitle = m.get("originalTitle"),
    isAdult = m.get("isAdult").map {
      case "1" => true
      case _   => false
    },
    startYear = DataService.convert[Int](m.get("startYear")),
    endYear = DataService.convert[Int](m.get("endYear")),
    runtimeMinutes = DataService.convert[Int](m.get("runtimeMinutes")),
    genres = m.get("genres").toList
  ))
}
