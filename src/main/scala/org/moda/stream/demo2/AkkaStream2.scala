package org.moda.stream.demo2

import java.nio.file.Paths

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.IOResult
import akka.stream.scaladsl.{FileIO, Source}
import akka.util.ByteString

import scala.concurrent.Future

object Main extends App {
  implicit val system = ActorSystem("QuickStart")

  val source: Source[Int, NotUsed] = Source(1 to 5)

  val factorials = source.scan(BigInt(1))((acc, next) => acc * next)

  val result: Future[IOResult] =
    factorials.map(num => ByteString(s"$num\n")).runWith(FileIO.toPath(Paths.get("/opt/MODA/MODA-TEST-FILE/factorials.txt")))

  println("success - !")
}