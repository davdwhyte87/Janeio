package com.app.janeio.notes.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.janeio.ui.theme.LightPurple
import com.app.janeio.ui.theme.XWhite
import com.app.janeio.view_models.AppViewModel

@Composable
fun AddButton(onClick: () -> Unit) {
    val appViewModel = hiltViewModel<AppViewModel>()

    FloatingActionButton(
        onClick = { appViewModel.openNewNotesDialog() },
        shape = CircleShape,
        contentColor = XWhite,
        containerColor = LightPurple,
        modifier = Modifier.padding(all=0.dp)
    ) {
        Icon(Icons.Filled.Add, "Floating action button.", modifier = Modifier.padding(all = 0.dp))
    }
}