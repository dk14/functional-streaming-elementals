// type-classes and context bounds in Scala

object TypeClasses extends App {

  sealed trait Pet

  sealed trait SmallPet extends Pet

  case object Puppy extends SmallPet
  case object Pussy extends SmallPet

  sealed trait NinjaTurtle extends Pet
  case object Leonardo extends NinjaTurtle
  case object Donatello extends NinjaTurtle

  trait Cool[T <: Pet] {
    def beCool(a: T): Option[T]
  }

  implicit def coolNinja[T <: NinjaTurtle] = new Cool[T] {
    def beCool(a: T) = Some(a)
  }

  implicit def notCool[T <: SmallPet] = new Cool[T] {
    def beCool(a: T) = None
  }

  def run[T <: Pet: Cool](t: T) = implicitly[Cool[T]].beCool(t)

  run(Donatello)
  run(Puppy)

}