package com.example.loginapidemo.ModelClass

data class UserRequest(
    val app_version: String,
    val city_id: Int,
    val device_model: String,
    val device_name: String,
    val device_no: String,
    val device_platform: String,
    val device_type: Int,
    val device_uuid: String,
    val device_version: String,
    val from_ip: String,
    val mobile_no: String,
    val password: String
)