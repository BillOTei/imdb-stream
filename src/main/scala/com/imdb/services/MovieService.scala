package com.imdb.services

import com.imdb.models.TitleBasic

import scala.concurrent.Future

object MovieService {
  val cpuThreads: Int = Runtime.getRuntime.availableProcessors() // Let's stick to logical threads for now

  def principalsForMovieName(name: String) = {
    DataService.streamFile("title.basics.tsv")
      .mapAsync(cpuThreads)(v => Future.fromTry(TitleBasic.fromMap(v)))
  }

  def tvSeriesWithGreatestNumberOfEpisodes() = ???

  final case class Principal(name: String, birthYear: Int, deathYear: Option[Int], profession: List[String])

  final case class TvSeries(original: String, startYear: Int, endYear: Option[Int], genres: List[String])

}
