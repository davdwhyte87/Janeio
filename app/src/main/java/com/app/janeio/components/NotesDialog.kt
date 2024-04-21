package com.app.janeio.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FileCopy
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.app.janeio.screens.NavScreen
import com.app.janeio.ui.theme.Black1
import com.app.janeio.ui.theme.LightGreen
import com.app.janeio.ui.theme.LightOrange
import com.app.janeio.view_models.AppViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach



@Composable
fun NotesDialog(appViewModel:AppViewModel, navController: NavHostController){
   // val openAlertDialog = remember { mutableStateOf(false)
//    val appViewModel = hiltViewModel<AppViewModel>()
//    val navController = rememberNavController()
    Log.d("APPVIEW M XXXXXXXX", appViewModel.isNewNotesDialogOpen.collectAsState().value.toString())
    //appViewModel.openNewNotesDialog()
    val isOpen  by appViewModel.isNewNotesDialogOpen.collectAsStateWithLifecycle()
    LaunchedEffect(isOpen) {
        Log.d("TRIGGered XXXXXXXX", "yes")
    }

    when {
        isOpen->{
            Dialog(
                onDismissRequest = {appViewModel.closeNewNotesDialog()},
                properties = DialogProperties(
                    usePlatformDefaultWidth = false,
                    dismissOnClickOutside = true
                )
            ) {

                Card (
                    elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                    shape = RectangleShape,
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                        .fillMaxHeight(0.3f),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiary)

                ) {
                    Row (modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically

                    )
                    {

                        Column (
                            modifier = Modifier
                                .padding(all = 15.dp)
                                .width(70.dp),
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Box(modifier = Modifier
                                .background(color = LightGreen, shape = RoundedCornerShape(10.dp))
                                .size(70.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple(color = Black1),
                                    onClick = {
                                        navController.navigate(NavScreen.NewNoteScreen.route)
                                    }
                                )
                            ){
                                Icon(imageVector = Icons.Outlined.FileCopy,
                                    contentDescription = "",
                                    tint = Black1,
                                    modifier = Modifier
                                        .size(40.dp)
                                        .align(Alignment.Center)

                                )
                            }
                            Text(text = "File",
                                color = Color.White, fontSize = 14.sp,
                                textAlign = TextAlign.End,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }


                        Spacer(modifier = Modifier.width(50.dp))

                        Column (
                            modifier = Modifier
                                .padding(all = 15.dp)
                                .width(70.dp),
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Box(modifier = Modifier
                                .background(color = LightOrange, shape = RoundedCornerShape(10.dp))
                                .size(70.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple(color = Black1),
                                    onClick = {}
                                )
                            ){
                                Icon(imageVector = Icons.Outlined.Folder,
                                    contentDescription = "",
                                    tint = Black1,
                                    modifier = Modifier
                                        .size(40.dp)
                                        .align(Alignment.Center)

                                )
                            }
                            Text(text = "Folder",
                                color = Color.White, fontSize = 14.sp,
                                textAlign = TextAlign.End,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }

                    }
                }
            }
        }
    }

}

@Composable
fun DButton(icon:ImageVector, name:String){
    Box(modifier = Modifier
        .background(color = Color.Black)
        .padding(start = 20.dp)){
        Column (
            modifier = Modifier
                .padding(all = 15.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ){

            Icon(imageVector = icon, contentDescription = "")
            Text(text = name)
        }
    }

}