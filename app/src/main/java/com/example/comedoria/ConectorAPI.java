package com.example.comedoria;

import static com.example.comedoria.BuildConfig.API_KEY;
import static com.example.comedoria.BuildConfig.API_URL;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ConectorAPI {
    public RequestQueue requestQueue;
    //Interface para respostas JsonObject
    public interface VolleySingleCallback {
        void onSuccess(JSONObject response) throws JSONException;
        void onError(VolleyError error) throws JSONException;
    }
    //Interface para respostas JsonArray
    public interface VolleyArrayCallback {
        void onSuccess(JSONArray response) throws JSONException;
        void onError(VolleyError error) throws JSONException;
    }

    //POST
    //Json Object
    //Com corpo de solicitação
    public static void conexaoSinglePOST(String endpoint, JSONObject dadosDeSolicitacao,
                                   Map<String, String> headers,
                                         Context context,VolleySingleCallback callback){
        String url = API_URL + endpoint;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                dadosDeSolicitacao,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            callback.onSuccess(response);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            callback.onError(error);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }
        };
        RequestQueue filaRequest = Volley.newRequestQueue(context);
        filaRequest.add(request);
    }

    //GET
    //Json array
    //Sem corpo de solicitação
    public static void conexaoArrayGET(String endpoint, Map<String, String> headers,
                                  Context context, VolleyArrayCallback callback)
    {
        String url = API_URL + endpoint;
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            callback.onSuccess(response);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            callback.onError(error);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }
        };
        RequestQueue filaRequest = Volley.newRequestQueue(context);
        filaRequest.add(request);
    }

    public static void conexaoArrayPOST(String endpoint, Map<String, String> headers, JSONArray corpoSolicitacao,
                                        Context context, VolleyArrayCallback callback){
        String url = API_URL + endpoint;
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.POST,
                url,
                corpoSolicitacao,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            callback.onSuccess(response);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            callback.onError(error);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }
        };
        RequestQueue filaRequest = Volley.newRequestQueue(context);
        filaRequest.add(request);
    }

    public static void conexaoArrayPATCH(String endpoint, Map<String, String> headers, JSONArray corpoSolicitacao,
                                        Context context, VolleyArrayCallback callback){
        String url = API_URL + endpoint;
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.PATCH,
                url,
                corpoSolicitacao,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            callback.onSuccess(response);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            callback.onError(error);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }
        };
        RequestQueue filaRequest = Volley.newRequestQueue(context);
        filaRequest.add(request);
    }
    public static void conexaoSinglePATCH(String endpoint, JSONObject dadosDeSolicitacao,
                                         Map<String, String> headers,Context context, VolleySingleCallback callback){
        String url = API_URL + endpoint;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PATCH,
                url,
                dadosDeSolicitacao,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            callback.onSuccess(response);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            callback.onError(error);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }
        };
        RequestQueue filaRequest = Volley.newRequestQueue(context);
        filaRequest.add(request);
    }

}

