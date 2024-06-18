package com.example.fitlog.ui.add.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties

@Composable()
fun SelectableList(
    modifier: Modifier = Modifier,
    list: List<String>,
    selectedIndex: Int,
    showSelectableList: Boolean,
    changeShowSelectableList: (Boolean) -> Unit,
    onItemClick: (Int) -> Unit
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .clickable { changeShowSelectableList(true) }
            .height(60.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = list[selectedIndex],
            fontSize = 18.sp,
            modifier = Modifier.padding(3.dp)
        )
    }
    Box {
        if (showSelectableList) {
            Popup(
                alignment = Alignment.TopCenter,
                properties = PopupProperties(
                    excludeFromSystemGesture = true,
                ),
                onDismissRequest = { changeShowSelectableList(false) }
            ) {
                Row {
                    Column(
                        modifier = modifier
                            .background(Color.White)
                            .heightIn(max = 180.dp)
                            .padding(horizontal = 15.dp)
                            .weight(3f)
                            .verticalScroll(state = scrollState)
                            .border(width = 1.dp, color = Color.Gray),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        list.onEachIndexed { index, item ->
                            if (index != 0) {
                                HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
                            }
                            Box(
                                modifier = Modifier
                                    .height(40.dp)
                                    .fillMaxWidth()
                                    .clickable {
                                        onItemClick(index)
                                        changeShowSelectableList(false)
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = item,
                                    fontSize = 18.sp,
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}