package com.example.williamcisang.a100burpeesday.domain

import java.io.Serializable

/**
 * Created by WCisang on 15/09/2018.
 */
class SessionState : Serializable{

    var state: Int = 0

    companion object {
        const val ON_PROGRESS = 0
        const val FINALIZED = 1
    }

}