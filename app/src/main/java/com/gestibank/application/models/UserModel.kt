package com.gestibank.application.models

data class UserModel(var prenom: String, var email: String, var tel: String, var role: String, var name: String,var paswwsord: String,var agent:String,var balance:String)

data class UserModelAgent(var prenom: String, var email: String, var tel: String, var matricule: String, var role: String, var name: String,var paswwsord: String)

