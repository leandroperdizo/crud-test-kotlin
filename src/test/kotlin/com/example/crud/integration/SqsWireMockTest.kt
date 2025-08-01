package com.example.crud.integration

import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.net.HttpURLConnection
import java.net.URL

@WireMockTest(httpPort = 8080)
class SimpleWireMockTest {

    @Test
    fun `test GET with WireMock`() {
        stubFor(get(urlEqualTo("/test"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "text/plain")
                .withBody("Teste Wiremock")))

        val url = URL("http://localhost:8080/test")
        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        conn.connect()

        assertEquals(200, conn.responseCode)
        val body = conn.inputStream.bufferedReader().readText()
        assertEquals("Teste Wiremock", body)

        verify(getRequestedFor(urlEqualTo("/test")))
    }
}
