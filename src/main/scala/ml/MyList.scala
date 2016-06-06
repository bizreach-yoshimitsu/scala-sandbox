package ml

import scala.{:: => _}

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
    foldLeft(nil) {
      case (z, a) => MyCons(a, z)
    }
  }

  def foldRight[B](z: B)(f: (A, B) => B): B = {
    def inner(acc: B => B, target: A, left: MyList[A]): B => B = {
      val g = (b: B) => acc(f(target, b))
      left match {
        case MyNil => g
        case MyCons(h, t) => inner(g, h, t)
      }
    }
    inner((b: B) => b, head, tail)(z)
  }

  def foldRight2[B](z: B)(f: (A, B) => B): B = {
    reverse.foldLeft(z) {
      case (z, c) => f(c, z)
    }
  }

  def map[B](f: A => B): MyList[B] = {
    val nil: MyList[B] = MyNil
    foldRight(nil) {
      case (a, z) => MyCons(f(a), z)
    }
  }

  def map2[B](f: A => B): MyList[B] = ???

  def flatMap[B](f: A => MyList[B]): MyList[B] = {
    def inner(acc: MyList[B], target: A, left: MyList[A]): MyList[B] = {
      val mapped = acc ::: f(target)
      left match {
        case MyNil => mapped
        case MyCons(h, t) => inner(mapped, h, t)
      }
    }
    inner(MyNil, head, tail)
  }

  def filter(f: A => Boolean): MyList[A] = ???
  def withFilter(f: A => Boolean): MyList[A] = ???

  def :::[B >: A](appends: MyList[B]): MyList[B] = {
    appends.foldRight(this) {
      case (a, z) => {
        MyCons(a.asInstanceOf[A], z)
      }
    }
  }

  def add[B >: A](append: B): MyList[B] = ???
}

case object MyNil extends MyList[Nothing] {

  override def head: Nothing = throw new Exception("head not found")

  override def tail: MyList[Nothing] = throw new Exception("tail not found")
}

case class MyCons[+A](h: A, t: MyList[A]) extends MyList[A] {

  override def head: A = h

  override def tail: MyList[A] = t
}
