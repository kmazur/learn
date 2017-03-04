package com.scala.scalafortheimpatient

import java.util.concurrent.Executors

import scala.concurrent.{Await, ExecutionContext, Promise}
import scala.util.{Failure, Success, Try}

object Futures {

  def main(args: Array[String]): Unit = {

    import scala.concurrent.Future
    import scala.concurrent.ExecutionContext.Implicits.global

    val f: Future[Unit] = Future {
      println("like Thread(() -> ...).start()")
    }

    val intFuture: Future[Try[Int]] = Future(Success(5))
    val map: Future[Int] = intFuture.flatMap(i => Future(i.get))

    val crash: Future[Int] = Future {
      if(1 > 0.5) {
        throw new RuntimeException("Crash")
      }
      42
    }

    import scala.concurrent.duration._
    try {
      val result1: Int = Await.result(crash, 10.seconds) // throws exception
      println(result1)
    } catch {
      case _: RuntimeException => println("Caught RuntimeException") // prints this
    }

    // ok - won't throw. It's completed now.
    val resolved = Await.ready(crash, 1.seconds)
    val Some(t) = resolved.value // .value returns Option[Try[Int]] which we know is not None (when !isCompleted) -> Some(Failure)
    println(t) // Failure(java.lang.RuntimeException: Crash)

    t match {
      case Success(intElem) => println(s"$intElem matched")
      case Failure(throwable: Throwable) => println("Got throwable") // prints this
    }

    // t -> Try[Int] -> Failure[RuntimeException]
    t.isFailure // true
    t.isSuccess // false
    val failed: Try[Throwable] = t.failed // turns Try[Int] -> Failure(Throwable)
    val option: Option[Int] = t.toOption
    // val value: Int = t.get // throws RuntimeException: Crash from inner Future {}


    Future {
      5
    }.onComplete { // t: Try[Int] =>
      case Success(i) => println(i) // prints 5
      case Failure(ex) => println(ex.getMessage)
    }


    def getData1(): Int = { Thread.sleep(100); 1 }
    def getData2(): Int = { Thread.sleep(50); 2 }

    val f1 = Future { getData1() }
    val f2 = Future { getData2() }
    val r3: Future[Int] = f1.flatMap(r1 => f2.map(r2 => r1 + r2))
    // this is equivalent
    val r4 = for { r1 <- f1; r2 <- f2 } yield r1 + r2

    def f3 = Future { getData1() } // def -> deferred execution. Works with for

    val success1 = Future { throw new RuntimeException("Ups") }.recover({ case _:Exception => 0})

    val f5 = Future { if(Math.random() > 0.5) throw new RuntimeException() else 1 }
    for (ex <- f5.failed) println(ex)

    f1.zip(f2).onComplete({
      case Success((a, b)) => println(s"Got tuple ($a, $b)") // Got tuple (1, 2)
      case _ => println("Error")
    })

    val futures = List(Future(1), Future(2), Future(3))
    val sequence: Future[List[Int]] = Future.sequence(futures)
    val ints: List[Int] = List(1, 2, 3)
    val traverse: Future[List[Int]] = Future.traverse(ints)(p => Future(p))
    val foldedLeft: Future[Int] = Future.foldLeft(futures)(0)(_ + _)

    val first: Future[Int] = Future.firstCompletedOf(futures)
    val find: Future[Option[Int]] = Future.find(futures)(p => p % 2 == 0)

    Future.successful(1)
    Future.failed(new RuntimeException())
    Future.fromTry(Try("a".toInt))
    val unit: Future[Unit] = Future.unit
    Future.never


    val p = Promise[Int]()
    Future {
      val n = getData1()
      p.trySuccess(n)
    }
    Future {
      val n = getData2()
      p.trySuccess(n) // sets value to n if it's not yet set. Else ignores
    }
    val eventualInt: Future[Int] = p.future



    import scala.concurrent.blocking
    Future {
      val n = 5
      // long running IO -> let Fork-join pool increase thread count
      val result: Int = blocking {
        getData1()
      }
      n + result
    }


    val pool = Executors.newCachedThreadPool()
    implicit val ec = ExecutionContext.fromExecutor(pool)
    // now all futures use this implicit execution context - not global one

  }

}
