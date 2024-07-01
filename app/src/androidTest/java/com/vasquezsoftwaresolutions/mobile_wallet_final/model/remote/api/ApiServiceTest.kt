package com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.api

import com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.request.LoginRequest
import com.vasquezsoftwaresolutions.mobile_wallet_final.model.remote.request.RegisterRequest
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
/**
 *@autor Pablo
 *@create 30-06-2024 19:53
 *@project mobile_wallet_final
 *@Version 1.0
 */
@RunWith(JUnit4::class)
class ApiServiceTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: ApiService

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun loginDeberiaRetornarLoginResponse() {
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"accessToken\":\"fake_token\"}")

        mockWebServer.enqueue(mockResponse)

        val response = apiService.login(LoginRequest("user", "password")).execute()

        val request = mockWebServer.takeRequest()
        assertEquals("/auth/login", request.path)
        assertEquals("POST", request.method)

        val responseBody = response.body()
        assertEquals("fake_token", responseBody?.accessToken)
    }


    @Test
    fun getUserDetailsdeberiaretornaruser() {
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(
                """
                {
                    "first_name": "John",
                    "last_name": "Doe",
                    "email": "john.doe@example.com",
                    "password": "password123",
                    "roleId": 1,
                    "points": 1000
                }
            """
            )

        mockWebServer.enqueue(mockResponse)

        val response = apiService.getUserDetails("Bearer fake_token").execute()

        val request = mockWebServer.takeRequest()
        assertEquals("/auth/me", request.path)
        assertEquals("GET", request.method)
        assertEquals("Bearer fake_token", request.getHeader("Authorization"))

        val responseBody = response.body()
        assertEquals("John", responseBody?.firstName)
        assertEquals("Doe", responseBody?.lastName)
        assertEquals("john.doe@example.com", responseBody?.email)
        assertEquals("password123", responseBody?.password)
        assertEquals(1, responseBody?.roleId)
        assertEquals(1000, responseBody?.points)
    }

    @Test
    fun registerUserBeberiaRetornarRegisterResponse() {
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(
                """
                {
                    "first_name": "John",
                    "last_name": "Doe",
                    "email": "john.doe@example.com",
                    "password": "password123",
                    "roleId": 1,
                    "points": 1000
                }
            """
            )

        mockWebServer.enqueue(mockResponse)

        val response = apiService.registerUser(
            RegisterRequest(
                "John",
                "Doe",
                "john.doe@example.com",
                "password123",
                1,
                50
            )
        ).execute()


        val request = mockWebServer.takeRequest()
        assertEquals("/users", request.path)
        assertEquals("POST", request.method)

        val responseBody = response.body()
        assertEquals("John", responseBody?.firstName)
        assertEquals("Doe", responseBody?.lastName)
        assertEquals("john.doe@example.com", responseBody?.email)
    }
}