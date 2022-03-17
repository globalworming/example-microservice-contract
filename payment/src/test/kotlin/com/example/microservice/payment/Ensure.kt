package com.example.microservice.payment

import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.hamcrest.CoreMatchers
import org.hamcrest.Description
import org.hamcrest.MatcherAssert
import org.hamcrest.TypeSafeMatcher

class Ensure {
  companion object {

    private val isLowRiskRequest = object : TypeSafeMatcher<RecordedRequest>() {
      override fun matchesSafely(request: RecordedRequest): Boolean {
        MatcherAssert.assertThat(request.requestUrl.encodedPath(), CoreMatchers.`is`("/api/security"))
        MatcherAssert.assertThat(request.body.readUtf8(), CoreMatchers.`is`("""athena:99"""))
        return true
      }

      override fun describeTo(description: Description) {
        description.appendText("request matches low risk request")
      }
    }

    fun requestMatchesLowRiskRequest(webserver: MockWebServer) {
      MatcherAssert.assertThat(webserver.requestCount, CoreMatchers.`is`(1))
      val request = webserver.takeRequest()
      MatcherAssert.assertThat(request, isLowRiskRequest)
    }
  }

}