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

  def foldRight2[B](z: B)(f: (A, B) => B): B = {
    def inner(acc: B => B, target: A, left: MyList[A]): B => B = {
      val g = (b: B) => acc(f(target, b))
      left match {
        case MyNil => g
        case MyCons(h, t) => inner(g, h, t)
      }
    }
    inner((b: B) => b, head, tail)(z)
  }

  def map[B](f: A => B): MyList[B] = {
    val nil: MyList[B] = MyNil
    this.foldRight(nil) {
      case (a, z) => MyCons(f(a), z)
    }
  }

  def map2[B](f: A => B): MyList[B] = ???
  def flatMap[B](f: A => MyList[B]): MyList[B] = ???
  def filter(f: A => Boolean): MyList[A] = ???
  def withFilter(f: A => Boolean): MyList[A] = ???
  def :::[A](tail: MyList[A]): MyList[A] = ???
}

case object MyNil extends MyList[Nothing] {

  override def head: Nothing = throw new Exception("head not found")

  override def tail: MyList[Nothing] = throw new Exception("tail not found")
}

case class MyCons[+A](h: A, t: MyList[A]) extends MyList[A] {

  override def head: A = h

  override def tail: MyList[A] = t
}
