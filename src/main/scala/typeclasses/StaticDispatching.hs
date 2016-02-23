//static dispatching with type-classes

data Puppy = Puppy
data Pussy = Pussy

data NinjaTurtle = Donatello | Leonardo
data SmallPet = PetPuppy Puppy | PetPussy Pussy

data Pet = Turtle NinjaTurtle | CutePet SmallPet

class Cute t where beCute :: (Num a, Ord a) => t -> a -> Bool
instance Cute Puppy where beCute p cuteness = cuteness < 4
instance Cute Pussy where beCute p cuteness = cuteness < 100

class Cool t where beCool :: t -> Maybe t
instance Cool NinjaTurtle where beCool t = Just t
instance Cool SmallPet where beCool t = Nothing

import Data.Maybe

let a = beCool (PetPuppy Puppy)
isJust a

let a = beCool Donatello
isJust a

- using type-classes and polymorphic functions together in haskell

data A
data A = A
data B = B

class AB x
instance AB A
instance AB B


f :: (AB a) => a -> Bool
f a = True

f A
f B

