package com.imdb.models

import com.imdb.services.DataService

import scala.util.Try

case class TitleBasic(
    tconst: String,
    titleType: Option[String],
    primaryTitle: Option[String],
    originalTitle: Option[String],
    isAdult: Option[Boolean],
    startYear: Option[Int],
    endYear: Option[Int],
    runtimeMinutes: Option[Int],
    genres: List[String]
)

object TitleBasic {

  val primaryTitleFieldName = "primaryTitle"
  val originalTitleFieldName = "originalTitle"
  val titleIdFieldName = "tconst"

  def fromMap(m: Map[String, String]): Try[TitleBasic] =
    Try(
      TitleBasic(
        tconst = m(titleIdFieldName),
        titleType = DataService.convert[String](m.get("titleType")),
        primaryTitle = DataService.convert[String](m.get("primaryTitle")),
        originalTitle = DataService.convert[String](m.get("originalTitle")),
        isAdult = m.get("isAdult").map {
          case "1" => true
          case _   => false
        },
        startYear = DataService.convert[Int](m.get("startYear")),
        endYear = DataService.convert[Int](m.get("endYear")),
        runtimeMinutes = DataService.convert[Int](m.get("runtimeMinutes")),
        genres = m.get("genres").toList
      )
    )
}
