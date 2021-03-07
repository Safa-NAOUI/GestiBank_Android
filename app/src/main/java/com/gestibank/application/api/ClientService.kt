package com.gestibank.application.api

import com.gestibank.application.models.CurrencyModel
import com.gestibank.application.models.UserModel
import com.gestibank.application.models.UserModelAgent
import retrofit2.Call
import retrofit2.http.*


interface ClientService {

    @GET("users/{email}")
    fun getUserByEmail(@Path("email") email: String): Call<UserModel>

    @GET("list/")
    fun getClients(): Call<ArrayList<UserModel>>

    @GET("clients/list/attente")
    fun getClientsEnAttentes(): Call<ArrayList<UserModel>>

    @GET("clients/list/valide")
    fun getClientsValides(): Call<ArrayList<UserModel>>

    @GET("agent/list")
    fun getListAgents(): Call<ArrayList<UserModelAgent>>

    @POST("add/")
    fun signUpClient(@Body client: UserModel): Call<UserModel>

    @POST("add/agent")
    fun addAgent(@Body client: UserModelAgent): Call<UserModelAgent>

    @GET
    fun getData(@Url url: String): Call<CurrencyModel>

    @PUT("update/{email}")
    fun updateUser(@Path("email") email: String, @Body user: UserModel): Call<UserModel>


















//    /*** get agent with status: Ok **/
////    @GET("users/list/agent")
////    fun getAgentOk(): Call<List<User>>
////
//    @GET("users/list/agent")
//    fun getAgentOk(): Call<ArrayList<User>>
//
//    @POST("clients/add/agent")
//    fun addClient(@Body user: User): Call<User>
//
//
//
////    @get:GET("list/attente")
////    val clientsEnAttentes: Call<List<Any>>
////
////    @get:GET("list/valide")
////    val clientsValides: Call<List<Any>>
////
////    @POST("add/")
////    fun addClient(@Body user: User): Call<User> /*
////    @PUT("update/{id}")
////    Call<User> updateUser(@Path("id") int id, @Body User user);
////
////    @DELETE("delete/{id}")
////    Call<User> deleteUser(@Path("id") int id);*/
}
