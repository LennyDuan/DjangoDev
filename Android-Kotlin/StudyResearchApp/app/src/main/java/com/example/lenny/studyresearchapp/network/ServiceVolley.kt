package com.example.lenny.studyresearchapp.network

import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by Lenny on 13/08/2017.
 */
class ServiceVolley : ServiceInterface {
    val TAG = ServiceVolley::class.java.simpleName
    //val basePath = "https://your/backend/api/"

    override fun post(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit) {
        Log.d("Volley POST: ", path)
        Log.d("Volley POST: ", "Body - $params")

        val jsonObjReq = object : JsonObjectRequest(Method.POST, path, params,
                Response.Listener<JSONObject> { response ->
                    Log.d(TAG, "/post request OK! Response: $response")
                    completionHandler(response)
                },
                Response.ErrorListener { error ->
                    VolleyLog.e(TAG, "/post request fail! Error: ${error.message}")
                    completionHandler(null)
                }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Content-Type", "application/json")
                return headers
            }
        }

        BackendVolley.instance?.addToRequestQueue(jsonObjReq, TAG)
    }

    override fun get(path: String, completionHandler: (response: String?) -> Unit) {
        Log.d("Volley GET: ", path)

//        val jsonObjReq = object : JsonObjectRequest(Method.GET, path, null,
//                Response.Listener<JSONObject> { response ->
//                    Log.d(TAG, "/get request OK! Response: $response")
//                    completionHandler(response)
//                },
//                Response.ErrorListener { error ->
//                    VolleyLog.e(TAG, "/get request fail! Error: ${error.message}")
//                    completionHandler(null)
//                }) {
//        }

        val stringRequest = StringRequest(Request.Method.GET, path,
                Response.Listener<String> { response ->
                    try {
                        Log.d(TAG, "/get request OK! Response: $response")
                        completionHandler(response)
                    } catch (e: JSONException) {
                        VolleyLog.e(TAG, "/get request fail! Error: ${e.message}")
                        e.printStackTrace()
                    }
                }, Response.ErrorListener { })

        BackendVolley.instance?.addToRequestQueue(stringRequest, TAG)
    }
}