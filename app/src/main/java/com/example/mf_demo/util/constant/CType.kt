package com.example.mf_demo.util.constant

class CType {
    enum class ScreenType(val type: String) {
        UserList("UserList"),
        UserDetail("UserDetail"),
        None("None")
    }

    enum class DataType(val type: Int) {
        UserList(1),
        UserDetail(2),
        UserRepo(3)
    }

    enum class MessageType() {
        OpenUrl,
        Retry,
        Setting
    }

    enum class LogType() {
        Screen,
        Action,
        Api,
        Error
    }

}