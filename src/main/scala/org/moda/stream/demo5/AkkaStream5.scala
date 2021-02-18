package org.moda.stream.demo5

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.scaladsl.{Flow, Keep, RunnableGraph, Sink}
import org.moda.stream.demo3.Tweet

import scala.concurrent.Future

object Main extends App {

  import TwitterStream._

  implicit val system = ActorSystem("reactive-tweets")
  implicit val executionContext = system.dispatcher

//  "count elements on finite stream" in {
  //#tweets-fold-count
  val count: Flow[TwitterStream.Tweet, Int, NotUsed] = Flow[TwitterStream.Tweet].map(_ => 1)

  val sumSink: Sink[Int, Future[Int]] = Sink.fold[Int, Int](0)(_ + _)

  val counterGraph: RunnableGraph[Future[Int]] =
    tweets.via(count).toMat(sumSink)(Keep.right)

  val sum: Future[Int] = counterGraph.run()

  sum.foreach(c => println(s"Total tweets processed: $c"))
  //#tweets-fold-count

  new AnyRef {
    //#tweets-fold-count-oneline
    val sum: Future[Int] = tweets.map(t => 1).runWith(sumSink)
    //#tweets-fold-count-oneline
  }
}
