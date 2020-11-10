package com.example.ch8n

class GoogleAnalytics() : Analytics{

    override fun isEnable(): Boolean {

        return true
    }

    override fun Log(event: String, params: MutableMap<String, String>) {
        println("GoogleAnalytics")
    }
}


class FirebaseAnalytics() : Analytics{

    override fun isEnable(): Boolean {

        return true
    }

    override fun Log(event: String, params: MutableMap<String, String>) {
        println("FirebaseAnalytics")
    }
}

interface Analytics{

    fun isEnable(): Boolean

    fun Log(event : String, params : MutableMap<String, String>)
}

class AnalyticsWrapper : Analytics{

    private val analytics = mutableListOf<Analytics>()

    fun addAnalytics(vararg analytic: Analytics){
        analytics.addAll(analytic)
    }

    override fun isEnable(): Boolean {
        return true
    }

    override fun Log(event: String, params: MutableMap<String, String>) {

        analytics.filter {
            it.isEnable()
        }.forEach { it.Log(event, params) }
    }

}

fun main(){
    val analyticsWrapper = AnalyticsWrapper()

    analyticsWrapper.addAnalytics(GoogleAnalytics(), FirebaseAnalytics())
    analyticsWrapper.Log("Pokemon", mutableMapOf("Chetu" to "Coder"))
}