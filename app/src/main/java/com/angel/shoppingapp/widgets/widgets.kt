package com.angel.shoppingapp.widgets

import android.inputmethodservice.Keyboard
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.angel.shoppingapp.model.Product
import com.angel.shoppingapp.model.products

@Composable
fun RowCard(item: List<Product>) {
            Row(modifier = Modifier
            .clip(shape = RoundedCornerShape(15))
            .background(color = Color.LightGray)
            .width(370.dp)
            .size(150.dp),
        verticalAlignment = Alignment.CenterVertically) {

            rememberAsyncImagePainter(model = "https://play-lh.googleusercontent.com/yPtnkXQAn6yEahOurxuYZL576FDXWn3CqewVcEWJsXlega_nSiavBvmaXwfTGktGlQ")

//            Image(imageVector = Icons.Default.AccountBox,
//                contentDescription = "Icon Image",
//                modifier = Modifier.size(150.dp))

            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
            Text(text = item.toString(), style = TextStyle(fontSize = 25.sp))
        }
    }

    Log.d("Test", "RowCard: ${item}")
    Log.d("Test", "RowCard: ${item}")
}

//item: Product