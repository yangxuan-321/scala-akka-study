package org.moda.stream.demo1

import akka.{Done, NotUsed}
import akka.actor.ActorSystem
import akka.stream.scaladsl.Source

import scala.concurrent.Future

object Main extends App {
    implicit val system = ActorSystem("QuickStart")
    // 创建了这个源之后，意味着我们有了如何发出前100个自然数的描述，但这个源还没有被激活。
    // 也就是说，在这一步，我们只是创建了 一个流的 元素描述。真正的数据还没到位
    val source :Source[Int, NotUsed] = Source(1 to 100)

    // runForeach才开始运行
    // source.runForeach(i => println(i))

    // runForeach  流完成 之后，会 返回一个Future[Done]
    val done: Future[Done] = source.runForeach(i => println(i))

    implicit val ec = system.dispatcher
    done.onComplete(_ => system.terminate())

}
