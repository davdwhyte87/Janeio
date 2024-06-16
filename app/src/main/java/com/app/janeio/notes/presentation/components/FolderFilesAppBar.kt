package com.app.janeio.notes.presentation.components

import Janeio.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.app.janeio.ui.theme.XWhite


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FolderFilesAppBar(navHostController: NavHostController){
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.secondary,
            titleContentColor = MaterialTheme.colorScheme.secondary

        ),
        navigationIcon = {
            IconButton(onClick = {
                navHostController.popBackStack()
            }) {
                Icon(imageVector =Icons.AutoMirrored.Sharp.ArrowBack,
                    contentDescription = null,
                    tint = XWhite
                )
            }
        },
        title = {
                Text(text = "Notes", fontSize = 20.sp, color = XWhite)
        },
        actions = {
            Row (modifier = Modifier.padding(end = 16.dp)){
                Image(painter = painterResource(id = R.drawable.propic2), contentDescription =null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .padding(1.dp, 1.dp)
                        .size(50.dp, 50.dp),
                    contentScale = ContentScale.FillWidth)
            }

        }
    )
}