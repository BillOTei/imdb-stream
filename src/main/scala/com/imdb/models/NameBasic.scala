package com.imdb.models

import com.imdb.services.DataService

import scala.util.Try

case class NameBasic(
    nconst: String,
    primaryName: Option[String],
    birthYear: Option[Int],
    deathYear: Option[Int],
    primaryProfession: List[String],
    knownForTitles: List[String]
)

object NameBasic {

  def fromMap(m: Map[String, String]): Try[NameBasic] =
    Try(
      NameBasic(
        nconst = m("nconst"),
        primaryName = DataService.convert[String](m.get("primaryName")),
        birthYear = DataService.convert[Int](m.get("birthYear")),
        deathYear = DataService.convert[Int](m.get("deathYear")),
        primaryProfession = m.get("primaryProfession").toList,
        knownForTitles = m.get("primaryProfession").toList
      )
    )
}
