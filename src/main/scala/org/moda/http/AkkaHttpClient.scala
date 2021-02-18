package org.moda.http

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.adapter._
import akka.http.scaladsl.Http

trait AkkaHttpClient[Req, Res] {
  implicit def system: ActorSystem[_]

//  private[this] def defaultClient() = {
//    for {
//      a <- Http()(system.toClassic)
//    } yield a
//  }

}
