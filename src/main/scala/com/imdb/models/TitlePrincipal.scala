package com.imdb.models

import com.imdb.services.DataService

import scala.util.Try

case class TitlePrincipal(
    tconst: String,
    ordering: Option[Int],
    nconst: String,
    category: Option[String],
    job: Option[String],
    characters: Option[String]
)

object TitlePrincipal extends Parsable[TitlePrincipal] {
  val titleIdFieldName = "tconst"

  def apply(a: List[String]): TitlePrincipal = TitlePrincipal(
    tconst = a.head,
    ordering = DataService.convert[Int](a.lift(1)),
    nconst = a(2),
    category = DataService.convert[String](a.lift(3)),
    job = DataService.convert[String](a.lift(4)),
    characters = DataService.convert[String](a.lift(5))
  )

  def fromMap(m: Map[String, String]): Try[TitlePrincipal] =
    Try(
      TitlePrincipal(
        tconst = m(titleIdFieldName),
        ordering = DataService.convert[Int](m.get("ordering")),
        nconst = m("nconst"),
        category = DataService.convert[String](m.get("category")),
        job = DataService.convert[String](m.get("job")),
        characters = DataService.convert[String](m.get("characters"))
      )
    )
}
