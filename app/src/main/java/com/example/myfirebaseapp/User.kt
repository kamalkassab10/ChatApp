package com.example.myfirebaseapp

class User {
    var name:String?=null
    var email :String?=null
    var uid :String?=null
    var pass :String?=null
    var phone :String?=null


    constructor(){}


    constructor(name:String? , email :String? , uid :String? , pass :String? , phone:String?)
    {
        this.name= name
        this.email = email
        this.uid = uid
        this.pass = pass
        this.phone = phone

    }
}