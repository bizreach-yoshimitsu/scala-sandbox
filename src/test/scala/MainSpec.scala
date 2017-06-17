import org.scalatest.{ MustMatchers, WordSpec }

class MainSpec extends WordSpec with MustMatchers {
  "hello" must {
    "sandbox" in {
      1 mustBe 1
    }
  }
}
