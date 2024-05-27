package com.example.fitlog.ui.add.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import com.example.fitlog.R
import com.example.fitlog.common.MyTextField
import com.example.fitlog.common.isDoubleFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetInfoScreen(
    index: Int,
    weight: String,
    reps: Int,
    changeWeight: (Int, String) -> Unit,
    changeReps: (Int, Int) -> Unit,
    removeSetInfo: (Int) -> Unit,
) {
    val isWeightError by derivedStateOf { !isDoubleFormat(weight) }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "${index + 1} 세트")
        MyTextField(
            modifier = Modifier.weight(1f),
            value = weight,
            onValueChange = { changeWeight(index, it) },
            label = { Text(text = "무게") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = isWeightError
        )
        Text(text = "kg")
        MyTextField(
            modifier = Modifier.weight(1f),
            value = "$reps",
            onValueChange = { changeReps(index, if (it == "") 0 else it.toInt()) },
            label = { Text(text = "횟수") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(text = "회")
        IconButton(onClick = { removeSetInfo(index) }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_remove_circle),
                contentDescription = "remove setInfo",
                tint = Color.Red
            )
        }
    }
}