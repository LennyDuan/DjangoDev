package com.example.lenny.studyresearchapp.network

import org.json.JSONObject

/**
 * Created by Lenny on 13/08/2017.
 */
interface ServiceInterface {
    fun post(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit)
    fun get(path: String, completionHandler: (response: String?) -> Unit)
}