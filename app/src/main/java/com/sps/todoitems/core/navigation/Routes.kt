package com.sps.todoitems.core.navigation

import kotlinx.serialization.Serializable

interface Routes {

    @kotlinx.serialization.Serializable
    object Main: Routes

    @Serializable
    object Details: Routes

}