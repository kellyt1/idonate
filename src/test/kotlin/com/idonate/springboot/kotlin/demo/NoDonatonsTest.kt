package com.idonate.springboot.kotlin.demo


import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NoDonatonsTest {

    @Autowired lateinit var restTemplate: TestRestTemplate

    @Test
    fun indexShouldReturnEmptyListWhenNoDonationsExist(){
        val response = restTemplate.getForEntity("/", DonationsList::class.java)

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isNotNull.isEmpty()
    }

    val bbcDonation = Donation(name = "Red cross donation", donor = "Shelita", donee = "Red Cross")

    @Before
    fun createDonation(){
        restTemplate.postForLocation("/", HttpEntity(bbcDonation))
    }

    @Test
    fun indexShouldReturnSingleDonation(){
        val response = restTemplate.getForEntity("/", DonationsList::class.java)

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isNotEmpty
        assertThat(response.body.size).isEqualTo(1)

        assertThat(response.body[0]).isEqualToComparingOnlyGivenFields(bbcDonation, "name", "donor", "donee")
    }

    @Test
    fun getDonationShouldReturnDonationById(){
        val allDonationsResponse = restTemplate.getForEntity("/", DonationsList::class.java)

        val id = allDonationsResponse.body[0].id

        assertThat(id).isNotNull()
        val getDonationResponse = restTemplate.getForEntity("/$id", Donation::class.java)

        assertThat(getDonationResponse.body).isEqualToComparingOnlyGivenFields(bbcDonation, "name", "donor", "donee")
    }

    fun shouldReturnNotFoundIfReceivedWrongId(){
        val response = restTemplate.getForEntity("/${UUID.randomUUID()}", Any::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }
}