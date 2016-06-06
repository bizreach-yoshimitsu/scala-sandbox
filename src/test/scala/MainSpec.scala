import org.scalatestplus.play.PlaySpec

import ml._

class MainSpec extends PlaySpec {

  "MyList" must {

    "head" in {
      val list = MyCons(1, MyCons(2, MyCons(3, MyNil)))
      val expect = 1
      val actual = list.head
      actual mustBe expect
    }

    "tail" in {
      val list = MyCons(1, MyCons(2, MyCons(3, MyNil)))
      val expect = MyCons(2, MyCons(3, MyNil))
      val actual = list.tail
      actual mustBe expect
    }

    "foldLeft" in {
      val list = MyCons(1, MyCons(2, MyCons(3, MyNil)))
      val expect = -6
      val actual = list.foldLeft(0) {
        case (z, n) => z - n
      }
      actual mustBe expect
    }

    "reverse" in {
      val list = MyCons(1, MyCons(2, MyCons(3, MyNil)))
      val expect = MyCons(3, MyCons(2, MyCons(1, MyNil)))
      val actual = list.reverse
      actual mustBe expect
    }

    "foldRight" in {
      val list = MyCons(1, MyCons(2, MyCons(3, MyNil)))
      val expect = 2
      val actual = list.foldRight2(0) {
        case (n, z) => n - z
      }
      actual mustBe expect
    }

    // "foldRight2" in {
    //   val list = MyCons(1, MyCons(2, MyCons(3, MyNil)))
    //   val expect = 2
    //   val actual = list.foldRight(0) {
    //     case (n, z) => n - z
    //   }
    //   actual mustBe expect
    // }

    "map" in {
      val list = MyCons(1, MyCons(2, MyCons(3, MyNil)))
      val expect = MyCons("101", MyCons("102", MyCons("103", MyNil)))
      val actual = list.map(n => (n + 100).toString)
      actual mustBe expect
    }
  }
}
