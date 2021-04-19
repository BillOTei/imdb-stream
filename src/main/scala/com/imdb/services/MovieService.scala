package com.imdb.services

import com.imdb.models.{NameBasic, TitleBasic, TitlePrincipal}

object MovieService {
  val cpuThreads: Int = Runtime.getRuntime.availableProcessors() // Let's stick to logical threads for now

  def principalsForMovieName(name: String) = {
    val groupingStage = GroupConsecutive(
      (a: Map[String, String], b: Map[String, String]) => a(TitlePrincipal.titleIdFieldName) == b(TitlePrincipal.titleIdFieldName)
    )
    DataService
      .streamFile("title.principals.tsv.gz")
//      .via(groupingStage)
//      .zipWith(DataService.streamFile("title.basics.tsv.gz")) { (principals, title) =>
//        (title, principals)
//      }
//      .filter(m => {
//        m._1.get(TitleBasic.originalTitleFieldName).exists(_.toLowerCase contains name.toLowerCase) ||
//          m._1.get(TitleBasic.primaryTitleFieldName).exists(_.toLowerCase contains name.toLowerCase)
//      })
  }

  /*
    (HashMap(runtimeMinutes -> \N, tconst -> tt0009667, startYear -> 1917, endYear -> \N, primaryTitle -> The Sudden Gentleman, isAdult -> 0, genres -> Comedy,Drama, titleType -> movie, originalTitle -> The Sudden Gentleman),
    List(HashMap(tconst -> tt0009803, job -> \N, nconst -> nm0491048, characters -> ["Stanley"], ordering -> 1, category -> actor),
     HashMap(tconst -> tt0009803, job -> \N, nconst -> nm0640405, characters -> ["Katherine"], ordering -> 2, category -> actress),
      HashMap(tconst -> tt0009803, job -> \N, nconst -> nm0587829, characters -> ["Rube"], ordering -> 3, category -> actor),
       HashMap(tconst -> tt0009803, job -> \N, nconst -> nm5030055, characters -> ["Charlie the Elephant"], ordering -> 4, category -> actor),
        HashMap(tconst -> tt0009803, job -> \N, nconst -> nm0404327, characters -> \N, ordering -> 5, category -> director),
         HashMap(tconst -> tt0009803, job -> producer, nconst -> nm0827753, characters -> \N, ordering -> 6, category -> producer)))

   */

  def tvSeriesWithGreatestNumberOfEpisodes() = ???

  private def principals(titleId: String) =
    DataService
      .streamFile("title.principals.tsv")
      .filter(m => {
        m.get(TitlePrincipal.titleIdFieldName).contains(titleId)
      })

  private def names(nameId: String) =
    DataService
      .streamFile("name.basics.tsv")
      .filter(m => {
        m.get(NameBasic.nameIdFieldName).contains(nameId)
      })

  final case class Principal(name: String, birthYear: Int, deathYear: Option[Int], profession: List[String])

  final case class TvSeries(original: String, startYear: Int, endYear: Option[Int], genres: List[String])

}
