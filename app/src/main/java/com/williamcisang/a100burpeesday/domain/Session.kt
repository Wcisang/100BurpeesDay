package com.example.williamcisang.a100burpeesday.domain

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import java.util.*

/**
 * Created by WCisang on 15/09/2018.
 */

@Entity(tableName = "session")
class Session() : Parcelable {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var beginDate = Date()
    var endDate = Date()
    var totalBurpees = 0
    var completedBurpees = 0

    @Embedded
    var sessionState: SessionState? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        beginDate = Date(parcel.readLong())
        endDate = Date(parcel.readLong())
        totalBurpees = parcel.readInt()
        completedBurpees = parcel.readInt()
        sessionState = parcel.readSerializable() as SessionState
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeLong(beginDate.time)
        parcel.writeLong(endDate.time)
        parcel.writeInt(totalBurpees)
        parcel.writeInt(completedBurpees)
        parcel.writeSerializable(sessionState)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Session> {
        override fun createFromParcel(parcel: Parcel): Session {
            return Session(parcel)
        }

        override fun newArray(size: Int): Array<Session?> {
            return arrayOfNulls(size)
        }
    }


}