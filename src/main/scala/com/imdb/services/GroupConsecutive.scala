package com.imdb.services

import akka.stream.stage._
import akka.stream.{Attributes, FlowShape, Inlet, Outlet}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
  * Groups consecutive elements from stream based on predicate
  */
case class GroupConsecutive[A](property: (A, A) => Boolean) extends GraphStage[FlowShape[A, List[A]]] {
  val in: Inlet[A]         = Inlet[A]("GroupConsecutive.in")
  val out: Outlet[List[A]] = Outlet[List[A]]("GroupConsecutive.out")
  override val shape: FlowShape[A, List[A]] = FlowShape.of(in, out)

  override def createLogic(attr: Attributes): GraphStageLogic =
    new GraphStageLogic(shape) with StageLogging {
      var buffer: mutable.Builder[A, ListBuffer[A]] = ListBuffer.newBuilder[A]
      var last: Option[A]                           = None

      setHandler(
        in,
        new InHandler {
          override def onPush(): Unit = {
            val newElement = grab(in) // fetch new element
            last match {
              case Some(v) =>
                if (property(newElement, v)) { // same then last
                  buffer += newElement
                  pull(in)
                } else { // different then last
                  emit(out, buffer.result().toList)
                  buffer = ListBuffer.newBuilder
                  buffer += newElement
                  last = Some(newElement)
                }
              case None =>
                // first element newElement
                buffer += newElement
                last = Some(newElement)
                pull(in)
            }
          }

          override def onUpstreamFinish(): Unit = {
            val dataToFlush = buffer.result().toList
            if (dataToFlush.nonEmpty) emit(out, dataToFlush)
            last = None
            complete(out)
          }
        }
      )

      setHandler(out, new OutHandler {
        override def onPull(): Unit =
          pull(in)
      })
    }
}
