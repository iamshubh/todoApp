package com.sps.todoitems.ui

import kotlinx.serialization.Serializable

@Serializable
sealed interface UiAction {
    data object None : UiAction
    data object Loading : UiAction
    data object SuccessAddition : UiAction
    data object FailureAddition : UiAction

    data class ItemAddition(val text: String) : UiAction
    data class ItemDeletion(val id: Long): UiAction
    data class ItemSearch(val text: String) : UiAction
}