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

object TitlePrincipal {

  def fromMap(m: Map[String, String]): Try[TitlePrincipal] =
    Try(
      TitlePrincipal(
        tconst = m("tconst"),
        ordering = DataService.convert[Int](m.get("ordering")),
        nconst = m("nconst"),
        category = DataService.convert[String](m.get("category")),
        job = DataService.convert[String](m.get("job")),
        characters = DataService.convert[String](m.get("characters"))
      )
    )
}
