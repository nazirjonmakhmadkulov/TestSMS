package com.nazirjon.testsms.model

data class InfoResponse(var status: String,
                        val id: Int,
                        val phone_number: String,
                        val name: String,
                        val email: String,
                        val sex: String?,
                        val birth_day: String?,
                        val city: String,
                        val  rating: Double,
                        val active_order: String?,
                        val organization_id: String?,
                        val need_registration: Boolean)