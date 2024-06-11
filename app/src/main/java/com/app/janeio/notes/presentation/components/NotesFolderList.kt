package com.app.janeio.notes.presentation.components

import Janeio.R
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.janeio.notes.domain.AppViewModel
import com.app.janeio.notes.domain.FileType

import com.app.janeio.notes.domain.NavScreen
import com.app.janeio.notes.domain.Note
import com.app.janeio.notes.domain.NotesViewModel
import com.app.janeio.notes.domain.UIState
import com.app.janeio.ui.theme.Black2
import com.app.janeio.ui.theme.LightGrey
import com.app.janeio.ui.theme.Typography
import com.app.janeio.ui.theme.XWhite


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotesNFoldersList( navController: NavHostController,
                       notesViewModel:NotesViewModel,
                       uiState: UIState){

    notesViewModel.getAllNotesMain()

    //val notes = notesViewModel.notesList.collectAsState()
    val notes = notesViewModel.notesList.collectAsState()
   // Log.d("NOTES COMP *******", notes.toString())

//    val filesNFolders = listOf()
    // show empty compose if items are empty
    if (notes.value.isEmpty()){
     EmptyListView()
    }
    LazyColumn (verticalArrangement = Arrangement.spacedBy(20.dp)) {
        items(notes.value){item->

            NoteItemView(data = item,
                modifier = Modifier.combinedClickable (
                    onClick = {
                        Log.d("FType XXXX", item.Type)
                        // navigate to a single note view
                        if(item.Type == FileType.FILE.name){

                            navController.navigate(NavScreen.SingleNoteScreen.route+"/${item.id}")
                        }
                        // navigate to single folder screen
                        if (item.Type == FileType.FOLDER.name){
                            Log.d("Folder ID XXXXXX", item.id.toString())
                            navController.navigate(NavScreen.SingleFolderScreen.route+"/${item.id}")
                        }
                    },
                    onLongClick = {
                        Log.d("Long Press XXXX", "pressed")
                        notesViewModel.updateShowNoteListCheckBox(true)
                    }
                ),  notesViewModel, uiState
            )
        }
    }
}


data class NoteItem(
    val title:String,
    val summary:String,
    val type: Int
)





@Composable
fun NoteItemView(data: Note, modifier: Modifier,
                 notesViewModel: NotesViewModel,
                 uiState: UIState
){

    Box (modifier = Modifier
        .fillMaxWidth()
        .background(Black2)
        .padding(10.dp)

    ) {
        Column (modifier = modifier ) {


            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                var isChecked by rememberSaveable {
                    mutableStateOf(false)
                }

                Text(text = data.Title, fontSize = 16.sp, color = XWhite)
                val isListCheckBox = uiState.showNotListCheckBox
                if (isListCheckBox){
                    Checkbox(
                        modifier = Modifier,
                        checked = isChecked,
                        onCheckedChange = {
                            if (isChecked) {
                                notesViewModel.removeFromMultiTempDeleteList(data)
                                isChecked = false

                            }else{
                                notesViewModel.updateMultiTempDeleteLIst(data)
                                isChecked = true

                            }

                        },
                        colors = CheckboxDefaults.colors(
                            uncheckedColor = XWhite,

                            )
                    )
                }

            }

            Text(
                text = data.Note,
                fontSize = 10.sp,
                color = LightGrey,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (data.Type == FileType.FOLDER.name){
                Icon(
                    imageVector = Icons.Outlined.Folder,
                    contentDescription = "" ,
                    tint = XWhite
                )
            }


        }
    }

}

@Composable
fun EmptyListView(){
    val image = painterResource(id = R.drawable.empty_notes)
    Column (horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = image, contentDescription ="Empty list", Modifier.size(227.dp,227.dp ))
        Text(text = "What do you want to note today?", style = Typography.titleLarge, color = XWhite)
        Text(text = "Tap + to add a note", style = Typography.titleLarge, color = XWhite)
    }
}


