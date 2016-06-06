import org.scalatestplus.play.PlaySpec

class MainSpec extends PlaySpec {

  "hello" must {
    "world" in {
      "hello world" mustBe "hello world" 
    }
  }
}
