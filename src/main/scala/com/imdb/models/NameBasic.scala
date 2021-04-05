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

object NameBasic extends Parsable[NameBasic] {
  val nameIdFieldName = "nconst"

  def apply(a: List[String]): NameBasic = NameBasic(
    nconst = a.head,
    primaryName = DataService.convert[String](a.lift(1)),
    birthYear = DataService.convert[Int](a.lift(2)),
    deathYear = DataService.convert[Int](a.lift(3)),
    primaryProfession = a.lift(4).map(_.split(",").toList).getOrElse(Nil),
    knownForTitles = a.lift(5).map(_.split(",").toList).getOrElse(Nil)
  )

  def fromMap(m: Map[String, String]): Try[NameBasic] =
    Try(
      NameBasic(
        nconst = m(nameIdFieldName),
        primaryName = DataService.convert[String](m.get("primaryName")),
        birthYear = DataService.convert[Int](m.get("birthYear")),
        deathYear = DataService.convert[Int](m.get("deathYear")),
        primaryProfession = m.get("primaryProfession").toList,
        knownForTitles = m.get("primaryProfession").toList
      )
    )
}
