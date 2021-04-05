package com.imdb.services

import com.imdb.models.{NameBasic, TitleBasic, TitlePrincipal}

import scala.collection.mutable

object MovieService {
  val cpuThreads: Int = Runtime.getRuntime.availableProcessors() // Let's stick to logical threads for now

  def principalsForMovieName(name: String) = {
    val titleBasicsSource = DataService
      .streamFile("title.basics.tsv")
      .filter(m => {
        m.get(TitleBasic.originalTitleFieldName).exists(_.toLowerCase contains name.toLowerCase) ||
          m.get(TitleBasic.primaryTitleFieldName).exists(_.toLowerCase contains name.toLowerCase)
      })

    val titlePrincipalsSource = titleBasicsSource
      .flatMapConcat(m => principals(m(TitleBasic.titleIdFieldName)))
    val nameBasics = DataService.fileToInMemoryStringMap("name.basics.tsv").getOrElse(mutable.HashMap[String, String]())

    titlePrincipalsSource
      .map(m => nameBasics.get(m(NameBasic.nameIdFieldName)))
  }

  private def principals(titleId: String) =
    DataService
      .streamFile("title.principals.tsv")
      .filter(m => {
        m.get(TitlePrincipal.titleIdFieldName).contains(titleId)
      })

  def tvSeriesWithGreatestNumberOfEpisodes() = ???

  private def names(nameId: String) =
    DataService
      .streamFile("name.basics.tsv")
      .filter(m => {
        m.get(NameBasic.nameIdFieldName).contains(nameId)
      })

  final case class Principal(name: String, birthYear: Int, deathYear: Option[Int], profession: List[String])

  final case class TvSeries(original: String, startYear: Int, endYear: Option[Int], genres: List[String])

}
