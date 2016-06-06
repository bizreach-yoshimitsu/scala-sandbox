object Main {

  sealed trait MyList[+A] {

    def :::[A](tail: MyList[A]): MyList[A] = ???
    def map[B](f: A => B): MyList[B] = ???
    def flatMap[B](f: A => MyList[B]): MyList[B] = ???
    def filter(f: A => Boolean): MyList[A] = ???
    def withFilter(f: A => Boolean): MyList[A] = ???
    def foldLeft[B](z: B)(f: (B, A) => B): B = ???
    def foldRight[B](z: B)(f: (A, B) => B): B = ???
    def reverse: MyList[A] = ???

  }

  case object MyNil extends MyList[Nothing]

  case class MyCons[+A](head: A, tail: MyList[A]) extends MyList[A] {

    override def map[B](f: A => B): MyList[B] = {
      val nil: MyList[B] = MyNil
      this.reverse.foldLeft(nil) {
        case (z, a) => MyCons(f(a), z)
      }
    }

    override def flatMap[B](f: A => MyList[B]): MyList[B] = {
      ???
    }

    override def foldLeft[B](z: B)(f: (B, A) => B): B = {
      def inner(acc: B, target: A, left: MyList[A]): B = {
        val folded = f(acc, target)
        left match {
          case MyNil => folded
          case MyCons(h, t) => inner(folded, h, t)
        }
      }
      inner(z, head, tail)
    }

    override def foldRight[B](z: B)(f: (A, B) => B): B = {
      this.reverse.foldLeft(z) {
        case (z, c) => f(c, z)
      }
    }

    override def reverse: MyList[A] = {
      val nil: MyList[A] = MyNil
      this.foldLeft(nil) {
        case (z, a) => MyCons(a, z)
      }
    }
  }

  def main(args: Array[String]): Unit = {
    val list = MyCons(1, MyCons(2, MyCons(3, MyNil)))
    println(s"list\t\t>>> $list")

    val foldLefted = list.foldLeft(0) {
      case (z, n) => z - n
    }
    println(s"foldLeft\t>>> $foldLefted")

    val reversed = list.reverse
    println(s"reverse\t\t>>> $reversed")

    val foldRighted = list.foldRight(0) {
      case (n, z) => n - z
    }
    println(s"foldRight\t>>> $foldRighted")

    val mapped = list.map(_ + 100)
    println(s"map\t\t>>> $mapped")
  }
}
