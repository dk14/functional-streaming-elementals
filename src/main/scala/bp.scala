import scalaz._, Scalaz._
import scalaz.concurrent._
import scalaz.stream._
import async._
import io._
import Process._
import mutable._


object BackPressureExample extends App {

  def q = unboundedQueue[Int]

  val m = Map(1 -> q, 2 -> q, 3 -> q)

  def delay(x: Int) = {Thread.sleep(1000); "processed" + x}

  val getSignal = (k: Int, v: Queue[Int]) => v.size.discrete.map(k -> _).map(_.point[List])

  def message(i: Int) = if(i > 5) "hold your horses!" + i else "gimme more!" + i

  val size = m
    .map (getSignal.tupled)
    .reduce(_ merge _) //merge them
    .scan1Monoid
    .map(_.toMap.values.sum)

  size.observe(stdOutLines.contramap[Int](message)).filter(_ < 5).run.runAsync(println)

  m.mapValues(_.dequeue.map(delay).to(stdOutLines).run).foreach(_._2.runAsync(println))

  m(1).enqueueOne(1).run
  println("sent1")
  
  m(1).enqueueOne(1).run
  println("sent1")
  
  m(2).enqueueOne(2).run
  println("sent2")
  
  m(2).enqueueOne(2).run
  println("sent2") 
  
  m(3).enqueueOne(3).run
  println("sent3")
  
  m(3).enqueueOne(3).run
  println("sent3")
  
  m(1).enqueueOne(4).run
  println("sent4")
  
  m(1).enqueueOne(4).run
  println("sent4")
  
  m(1).enqueueOne(5).run
  println("sent5")
  
  m(1).enqueueOne(5).run
  println("sent5")
  
  m(1).enqueueOne(6).run
  println("sent6")
  
  m(1).enqueueOne(6).run
  println("sent6")

}
