package com.idonate.springboot.kotlin.demo

import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@RestController
class DonationController {

    private val donations = ConcurrentHashMap<UUID, Donation>()


    @GetMapping("/")
    fun allCats(): List<Donation>{
        return donations.values.toList()
    }

    @PostMapping("/")
    fun addDonation(@RequestBody donation : Donation){
        UUID.randomUUID().let {id ->
            donation.id = id
            donations.put(id, donation)

        }
    }

    @GetMapping("/{id}")
    fun getCat(@PathVariable id : String) : Donation{
        return donations[UUID.fromString(id)] ?: throw DonationNotFoundException()
    }
}