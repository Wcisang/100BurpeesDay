package com.example.williamcisang.a100burpeesday.domain

import java.io.Serializable

/**
 * Created by WCisang on 15/09/2018.
 */
class SessionState(state: Int) : Serializable{

    companion object {
        const val ON_PROGRESS = 0
        const val FINALIZED = 1
    }

}