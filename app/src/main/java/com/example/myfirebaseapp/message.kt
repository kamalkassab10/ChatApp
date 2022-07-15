package com.example.myfirebaseapp

class message {
    var messag : String? = null
    var senderid :String ?=null

    constructor(){}

    constructor(message :String? , senderid :String?)
    {
        this.messag = message
        this.senderid= senderid

    }
}