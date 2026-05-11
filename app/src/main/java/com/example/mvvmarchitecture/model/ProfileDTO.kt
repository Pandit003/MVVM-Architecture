package com.example.mvvmarchitecture.model

import com.google.gson.annotations.SerializedName
import java.util.Date

public class ProfileDTO {

    @SerializedName("UserName")
     var userName: String? = null

    @SerializedName("UserID")
     var userID: String? = null

    @SerializedName("UserTypeID")
     var UserTypeID = 0

    @SerializedName("UserType")
     var userType: String? = null

    @SerializedName("SessionIdentifier")
     var sessionIdentifier: String? = null

    @SerializedName("CookieIdentifier")
     var cookieIdentifier: String? = null

    @SerializedName("ClientIP")
     var clientIP: String? = null

    @SerializedName("ClientMAC")
     var clientMAC: String? = null

    @SerializedName("LoginTimeStamp")
     var loginTimeStamp: Date? = null

    @SerializedName("LastRequestTimestamp")
     var lastRequestTimestamp: Date? = null

    @SerializedName("UserRoleID")
     var userRoleID = 0

    @SerializedName("UserRole")
     var userRole: String? = null

    @SerializedName("WarehouseID")
     var warehouseID: Int? = null

    @SerializedName("TenantID")
     var TenantID: Int? = null

    @SerializedName("IsLoggedIn")
     var isLoggedIn: Boolean? = null

    @SerializedName("AccountId")
     var accountId: String? = null

    @SerializedName("FirstName")
     var firstName: String? = null

    @SerializedName("LastName")
     var lastName: String? = null

    @SerializedName("DepartmentIDs")
     var DepartmentIDs: String? = null

    @SerializedName("VStoreType")
     var VStoreType: String? = null

    @SerializedName("VStoreUsername")
     var VStoreUsername: String? = null

    @SerializedName("VStorePassword")
     var VStorePassword: String? = null

    @SerializedName("IsSessionActive")
     var IsSessionActive: String? = null
}