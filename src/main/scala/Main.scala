import akka.Done
import akka.actor.ActorSystem
import com.imdb.models.TitlePrincipal
import com.imdb.services.{DataService, MovieService}

import scala.concurrent._

object Main extends App {
  implicit val system: ActorSystem = ActorSystem("Imdb")

  val source = MovieService.principalsForMovieName("Autour d'une cabine")

  val done: Future[Done] = source.runForeach(i => {
    println(i)
  })
  implicit val ec: ExecutionContextExecutor = system.dispatcher
  done.onComplete(_ => system.terminate())

}
