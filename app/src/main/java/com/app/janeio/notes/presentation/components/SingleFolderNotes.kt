package com.app.janeio.notes.presentation.components

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.app.janeio.notes.domain.FileType
import com.app.janeio.notes.domain.NavScreen
import com.app.janeio.notes.domain.NotesViewModel
import com.app.janeio.notes.domain.UIState
import com.app.janeio.ui.theme.LightGrey
import com.app.janeio.ui.theme.Typography
import com.app.janeio.ui.theme.XWhite

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SingleFolderNotes(notesViewModel:NotesViewModel,
                      uiState:UIState,
                      navController:NavHostController,
                      folderId:Int
                      ){
    notesViewModel.getFolderNotes(folderId)
    //notesViewModel.getSingle(folderId)
    val notes = notesViewModel.singleFolderNotesList.collectAsState()



//    val filesNFolders = listOf()
    
    Column (modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBar()
        Text(text = notesViewModel.singleNote.collectAsState().value.Title,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = Typography.titleLarge,
            color = XWhite,

        )
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
                                Log.d("Single Note from Folder", NavScreen.SingleNoteScreen.route+"/${item.id}")
                                navController.navigate(NavScreen.SingleNoteScreen.route+"/${item.id}")
                            }
                            // navigate to single folder screen
                            if (item.Type == FileType.FILE.name){

                            }
                        },
                        onLongClick = {
                            Log.d("Long Press XXXX", "pressed")

                        }
                    ),  notesViewModel, uiState
                )
            }
        } 
    }
    
}


