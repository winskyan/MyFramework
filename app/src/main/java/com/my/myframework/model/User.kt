package com.my.myframework.model

import com.my.library_base.base.BaseModel

class User : BaseModel() {
    var firstName: String? = null
    var lastName: String? = null
    override fun toString(): String {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}'
    }
}