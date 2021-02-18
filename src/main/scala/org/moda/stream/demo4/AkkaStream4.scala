package org.moda.stream.demo4

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.scaladsl.Source

import scala.concurrent.duration._

object Main extends App {

  implicit val system = ActorSystem("reactive-tweets")

  val source: Source[Int, NotUsed] = Source(1 to 3)

  val factorials = source.scan(BigInt(1))((acc, next) => acc * next)

  factorials
    .zipWith(Source(0 to 100))((num, idx) => s"$idx! = $num")
    .throttle(1, 1.second)
    .runForeach(println)
}
