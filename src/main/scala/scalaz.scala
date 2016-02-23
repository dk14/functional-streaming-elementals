import scalaz._, Scalaz._
import scalaz.concurrent._
import scalaz.stream._
import async._
import io._
import Process._


object Flows {
	
  type P[T] = Process[Task, T]
  val f1,f3,f4,f5 = (_: Int) + 0
  val f2 = (x: Int) => {Thread.sleep(1000); x + 0}
    val send1 = stdOutLines.contramap[Int]("send1: " + _)
  val send2 = stdOutLines.contramap[Int]("send2: " + _)

  val p1: P[Int] => P[Int] = _.filter(_ == 1).map(f2).observe(send1).map(f3).observe(send2)
  val p2: P[Int] => P[Int] = _.filter(_ == 2).map(f4).observe(send1).map(f5).observe(send2)
}

import Flows._

object ScalazStream extends App {

  val in = Process(1,2)


  val out = in.map(f1).map(emit).flatMap(x => p1(x) merge p2(x))

  out.run.runAsync(println)
}

object ScalazStreamAsyncAndBp extends App {

  //async + back-pressure

  val inputq = boundedQueue[Int](2)
  val bufferq = boundedQueue[Int](2)

  val toBuffer = inputq.dequeue.map(f1).to(bufferq.enqueue)
  val out = bufferq.dequeue.map(emit).flatMap(x => p1(x) merge p2(x))

  toBuffer.run.runAsync(println)
  out.run.runAsync(println)

  inputq.enqueueAll(Seq(1,2))

  inputq.enqueueOne(1).run;inputq.enqueueOne(1).run;inputq.enqueueOne(1).run;inputq.enqueueOne(1).run;inputq.enqueueOne(1).run;inputq.enqueueOne(1).run

}