import akka.Done
import akka.actor.ActorSystem
import com.imdb.models.{NameBasic, TitleBasic, TitlePrincipal}
import com.imdb.services.DataService

import scala.concurrent._
import scala.util.{Failure, Success}

object Main extends App {
  implicit val system: ActorSystem = ActorSystem("Imdb")

  val source                                = DataService.streamFile("name.basics.tsv")
  val done: Future[Done]                    = source.runForeach(i => {
    println(NameBasic.fromMap(i) match {
      case Success(value) => println(value)
      case Failure(exception) => {
        println(exception.getMessage)
      }
    })
  })
  implicit val ec: ExecutionContextExecutor = system.dispatcher
  done.onComplete(_ => system.terminate())

}
