package app.komatatsu.autocheckin

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Roster(val version: String, val data: ArrayList<Ticket>) : Parcelable
