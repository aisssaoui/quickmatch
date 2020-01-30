package com.example.quickmatch.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TestObject(val message: String) : Parcelable
