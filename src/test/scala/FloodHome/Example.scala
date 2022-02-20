package FloodHome


//import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
//import io.gatling.jdbc.Predef._

class Example extends Simulation {

	val environment = System.getProperty("url", "https://challenge.flood.io")
	val users = Integer.getInteger("users", 5)
	val ramp_up = Integer.getInteger("ramp_up", 10)
	val duration = Integer.getInteger("duration", 60)


	val httpProtocol = http
		.baseUrl(environment)
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*css.*""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.png""", """.*png.*"""))
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("uk-UA,uk;q=0.8,en-US;q=0.5,en;q=0.3")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:97.0) Gecko/20100101 Firefox/97.0")
		.disableFollowRedirect

	val headers_0 = Map(
		"Cache-Control" -> "max-age=0",
		"Sec-Fetch-Dest" -> "document",
		"Sec-Fetch-Mode" -> "navigate",
		"Sec-Fetch-Site" -> "none",
		"Sec-Fetch-User" -> "?1",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_1 = Map(
		"Accept" -> "image/avif,image/webp,*/*",
		"Cache-Control" -> "max-age=0",
		"If-Modified-Since" -> "Mon, 08 Jun 2020 14:49:09 GMT",
		"If-None-Match" -> """"5ede4fe5-baab"""",
		"Sec-Fetch-Dest" -> "image",
		"Sec-Fetch-Mode" -> "no-cors",
		"Sec-Fetch-Site" -> "same-origin")

	val headers_2 = Map(
		"Accept" -> "text/html, application/xhtml+xml",
		"Sec-Fetch-Dest" -> "empty",
		"Sec-Fetch-Mode" -> "cors",
		"Sec-Fetch-Site" -> "same-origin",
		"Turbolinks-Referrer" -> "https://challenge.flood.io/")

	val headers_3 = Map(
		"Origin" -> "https://challenge.flood.io",
		"Sec-Fetch-Dest" -> "document",
		"Sec-Fetch-Mode" -> "navigate",
		"Sec-Fetch-Site" -> "same-origin",
		"Sec-Fetch-User" -> "?1",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_6 = Map(
		"Cache-Control" -> "no-cache",
		"DNT" -> "1",
		"Pragma" -> "no-cache",
		"Sec-Fetch-Dest" -> "document",
		"Sec-Fetch-Mode" -> "navigate",
		"Sec-Fetch-Site" -> "cross-site",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_8 = Map(
		"Accept" -> "*/*",
		"If-None-Match" -> """"a9064f02293136f2f37065d0489b7377"""",
		"Sec-Fetch-Dest" -> "empty",
		"Sec-Fetch-Mode" -> "cors",
		"Sec-Fetch-Site" -> "same-origin",
		"X-Requested-With" -> "XMLHttpRequest")

	val headers_10 = Map(
		"Accept" -> "text/html, application/xhtml+xml",
		"Sec-Fetch-Dest" -> "empty",
		"Sec-Fetch-Mode" -> "cors",
		"Sec-Fetch-Site" -> "same-origin",
		"Turbolinks-Referrer" -> "https://challenge.flood.io/done")

	val th_min = 1
	val th_max = 2

	val scn = scenario("Example")
				.exec(http("Homepage")
				.get("/")
				.headers(headers_0)
					.check(regex("name=\"authenticity_token\" type=\"hidden\" value=\"(.+?)\"").find.saveAs("authenticity_token"))
					.check(regex(".*step_id.*value=\"(.+?)\"").find.saveAs("step_id"))
				.resources(http("Get_Image")
					.get("/img/header.png")
					.headers(headers_1)))
				.pause(th_min, th_max)
				.exec(http("Click Start Button")
					.post("/start")
				  .headers(headers_3)
				  .formParam("utf8", "✓")
				  .formParam("authenticity_token", "${authenticity_token}")
				  .formParam("challenger[step_id]", "${step_id}")
				  .formParam("challenger[step_number]", "1")
				  .formParam("commit", "Start")
				.check(status.is(302)))
				.pause(th_min, th_max)
		 		.exec(http("Select Age from dropdown")
					.get("/step/2")
					.headers(headers_0)
					.check(regex(".*step_id.*value=\"(.+?)\"").find.saveAs("step_id")))
				.exec(http("Step_2_Age")
					.post("/start")
					.headers(headers_3)
					.formParam("utf8", "✓")
					.formParam("authenticity_token", "${authenticity_token}")
					.formParam("challenger[step_id]", "${step_id}")
					.formParam("challenger[step_number]", "2")
					.formParam("challenger[age]", "27")
					.formParam("commit", "Next")
					.check(status.is(302)))
				.pause(th_min, th_max)
				.exec(http("Select and enter the largest order value")
					.get("/step/3")
					.headers(headers_0)
					.check(regex(".*step_id.*value=\"(.+?)\"").find.saveAs("step_id"))
					.check(regex(">([0-9]*?)<\\/label>").findAll.transform(list=>list.map(_.toInt).max).saveAs("largest_order")))
				.exec(http("Step_3_Order")
					.post("/start")
					.headers(headers_3)
					.formParam("utf8", "✓")
					.formParam("authenticity_token", "${authenticity_token}")
					.formParam("challenger[step_id]", "${step_id}")
					.formParam("challenger[step_number]", "3")
					.formParam("challenger[largest_order]", "${largest_order}")
					.formParam("challenger[order_selected]", "VCtTOHJJekxHZWNyVUZTUVNQVTFuZz09LS1DRHhxRkdCYXk0cVI3RXluMTJ3NGtnPT0=--7f0740556fcf8149383e6f30c8f192f25b31b290")
					.formParam("commit", "Next")
					.check(status.is(302)))
				.pause(th_min, th_max)
				.exec(http("Go_to_Step_4")
					.get("/step/4")
					.headers(headers_6)
					.check(status.is(200)))
				.pause(th_min, th_max)
					.exec(http("Enter One Time Token")
					.get("/step/5")
					.headers(headers_6)
						.check(regex("token\">([0-9]*?)<\\/").find.saveAs("one_time_token")))
				.exec(http("One Time Token")
					.post("/start")
					.headers(headers_3)
					.formParam("utf8", "✓")
					.formParam("authenticity_token", "${authenticity_token}")
					.formParam("challenger[step_id]", "${step_id}")
					.formParam("challenger[step_number]", "4")
					.formParam("challenger[order_3]", "1645195936")
					.formParam("challenger[order_7]", "1645195936")
					.formParam("challenger[order_9]", "1645195936")
					.formParam("challenger[order_7]", "1645195936")
					.formParam("challenger[order_4]", "1645195936")
					.formParam("challenger[order_5]", "1645195936")
					.formParam("challenger[order_7]", "1645195936")
					.formParam("challenger[order_13]", "1645195936")
					.formParam("challenger[order_10]", "1645195936")
					.formParam("challenger[order_18]", "1645195936")
					.formParam("commit", "Next")
					.check(status.is(302))
					.resources(http("One_Time_Token")
					.get("/code")
					.headers(headers_8)))
				.pause(th_min, th_max)
				.exec(http("You just walked through the test manually")
					.get("/done")
					.headers(headers_0))
				.exec(http("NextStep")
					.post("/start")
					.headers(headers_3)
					.formParam("utf8", "✓")
					.formParam("authenticity_token", "${authenticity_token}")
					.formParam("challenger[step_id]", "${step_id}")
					.formParam("challenger[step_number]", "5")
					.formParam("challenger[one_time_token]", "${one_time_token}")
					.formParam("commit", "Next")
					.check(status.is(302)))
				.pause(th_min, th_max)

				.exec(http("StartAgain")
					.get("/")
					.headers(headers_10))

	setUp(
		scn.inject(rampUsers(users).during(duration))
	).protocols(httpProtocol)
}