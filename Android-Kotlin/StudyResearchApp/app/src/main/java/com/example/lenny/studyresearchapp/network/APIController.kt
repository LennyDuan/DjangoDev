package com.example.lenny.studyresearchapp.network

import org.json.JSONObject

/**
 * Created by Lenny on 13/08/2017.
 */
class APIController constructor(serviceInjection: ServiceInterface): ServiceInterface {
    private val service: ServiceInterface = serviceInjection

    override fun post(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit) {
        service.post(path, params, completionHandler)
    }

    override fun get(path: String, completionHandler: (response: String?) -> Unit) {
        service.get(path, completionHandler)
    }

    override fun delete(path: String, completionHandler: (response: String?) -> Unit) {
        service.delete(path, completionHandler)
    }
}