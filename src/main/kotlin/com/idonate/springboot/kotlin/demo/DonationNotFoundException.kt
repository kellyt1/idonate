package com.idonate.springboot.kotlin.demo

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class DonationNotFoundException : RuntimeException()