package com.imdb.services

object MovieService {

  def principalsForMovieName(name: String) = ???

  def tvSeriesWithGreatestNumberOfEpisodes() = ???

  final case class Principal(name: String, birthYear: Int, deathYear: Option[Int], profession: List[String])

  final case class TvSeries(original: String, startYear: Int, endYear: Option[Int], genres: List[String])

}
