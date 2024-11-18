package com.sps.todoitems.core.navigation

import kotlinx.serialization.Serializable

interface Routes {

    @Serializable
    object Main: Routes

    @Serializable
    object Details: Routes
}