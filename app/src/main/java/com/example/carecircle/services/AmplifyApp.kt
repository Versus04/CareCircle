package com.example.carecircle.services

import android.app.Application
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.kotlin.core.Amplify

class AmplifyApp : Application(){
    override fun onCreate() {
        super.onCreate()
        Amplify.addPlugin(AWSCognitoAuthPlugin())
        Amplify.configure(applicationContext)
    }
}