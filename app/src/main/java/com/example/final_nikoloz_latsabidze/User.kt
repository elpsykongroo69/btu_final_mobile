package com.example.final_nikoloz_latsabidze

class User {
    var name: String? = null
    var email: String? = null
    var balance: Double? = null
    var id: String? = null

    constructor() {}
    constructor(name: String?, email: String?, balance: Double?, id: String?) {
        this.name = name
        this.email = email
        this.balance = balance
        this.id = id
    }
}