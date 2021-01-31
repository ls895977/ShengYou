package com.xiaoshanghai.nancang.bean

  data class GetAppMinetBaseDataBean(
    val `data`: Data,
    val msg: String,
    val status: Int,
    val tiemstamp: Int,
    val url: String
)

data class Data(
    val accountType: Any,
    val active: Int,
    val alipayAccount: Any,
    val bankCardNo: Any,
    val car: Any,
    val charmLevel: Int,
    val city: Any,
    val createTime: String,
    val fanTotal: Int,
    val followTotal: Int,
    val headwear: Any,
    val id: String,
    val isAuthentication: Int,
    val isBeautiful: Int,
    val isClanElder: Int,
    val isInvisible: Int,
    val isPayTicket: Int,
    val latitude: Double,
    val longitude: Double,
    val ownerRoomId: String,
    val roomId: String,
    val userBirthday: String,
    val userIntroduce: Any,
    val userLevel: Int,
    val userName: String,
    val userNoble: String,
    val userNumber: Int,
    val userPassword: String,
    val userPhone: String,
    val userPicture: String,
    val userPictures: List<Any>,
    val userSalt: String,
    val userSex: Int,
    val userStatus: Int,
    val userType: Int,
    val userVoice: Any,
    val wechatOpenid: String,
    val wxpayAccount: Any
)