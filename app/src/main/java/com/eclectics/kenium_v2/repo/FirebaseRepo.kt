package com.eclectics.kenium_v2.repo

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class FirebaseRepo @Inject constructor() {
    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
}