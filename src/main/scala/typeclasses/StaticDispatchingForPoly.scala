
// non-working static dispatching for polymorphic methods (gives "method defined twice" error)

/**
  sealed trait NinjaTurtle extends Pet
  case object Leonardo extends NinjaTurtle
  case object Donatello extends NinjaTurtle

  object CoolOps {
    def beCool[T <: SmallPet](a: T): Option[T] = None
  	def beCool[T <: NinjaTurtle](a: T): Option[T] = Some(a)
  }
  */

 