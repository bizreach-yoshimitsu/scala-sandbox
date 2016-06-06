package ml

sealed trait MyList[+A] {

  def head: A

  def tail: MyList[A]

  def foldLeft[B](z: B)(f: (B, A) => B): B = {
    def inner(acc: B, target: A, left: MyList[A]): B = {
      val folded = f(acc, target)
      left match {
        case MyNil => folded
        case MyCons(h, t) => inner(folded, h, t)
      }
    }
    inner(z, head, tail)
  }

  def reverse: MyList[A] = {
    val nil: MyList[A] = MyNil
    this.foldLeft(nil) {
      case (z, a) => MyCons(a, z)
    }
  }

  def foldRight[B](z: B)(f: (A, B) => B): B = {
    this.reverse.foldLeft(z) {
      case (z, c) => f(c, z)
    }
  }

  // def foldRight2[B](z: B)(f: (A, B) => B): B = {
  //   def inner(acc: B => B, left: MyList[A]): A => B = {
  //     left match {
  //       case MyNil => (a: A) => {
  //         val result = f(a, acc(z))
  //         println(s"a = $a, z = $z, result = $result")
  //         result
  //       }
  //       case MyCons(h, t) => inner((b: B) => {
  //         val accb = acc(b)
  //         val result = f(h, accb)
  //         println(s"h = $h, acc(b) = $accb, result = $result")
  //         result
  //       }, t)
  //     }
  //   }
  //   inner((b: B) => b, tail)(head)
  //   // a1, a2, a3
  //   // f(a1, f(a2, f(a3, z)))
  // }

  def map[B](f: A => B): MyList[B] = {
    val nil: MyList[B] = MyNil
    this.reverse.foldLeft(nil) {
      case (z, a) => MyCons(f(a), z)
    }
  }

  def :::[A](tail: MyList[A]): MyList[A] = ???
  def flatMap[B](f: A => MyList[B]): MyList[B] = ???
  def filter(f: A => Boolean): MyList[A] = ???
  def withFilter(f: A => Boolean): MyList[A] = ???
}

case object MyNil extends MyList[Nothing] {

  override def head: Nothing = throw new Exception("head not found")

  override def tail: MyList[Nothing] = throw new Exception("tail not found")
}

case class MyCons[+A](h: A, t: MyList[A]) extends MyList[A] {

  override def head: A = h

  override def tail: MyList[A] = t
}
