package app.komatatsu.autocheckin

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ticket(val hash: String, val name: String, val number: Long, val kind: String) : Parcelable
