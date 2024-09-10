package com.dqitech.gesturedemoapp

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(navController: NavHostController) {
    var scale by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var color by remember { mutableStateOf(Color.Gray) }
    var rotationAngle by remember { mutableStateOf(0f) }
    var isLongPressed by remember { mutableStateOf(false) }
    var isShaken by remember { mutableStateOf(false) }
    val boxSize = 200.dp
    val flingThreshold = 200f

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTransformGestures(
                    onGesture = { _, pan, zoom, rotation ->
                        scale *= zoom // Pinch to zoom
                        offsetX += pan.x
                        offsetY += pan.y
                        rotationAngle += rotation

                        if (scale > 2.0f) {
                            scale = 1f
                            offsetX = 0f
                            offsetY = 0f
                            rotationAngle = 0f
                            isShaken = !isShaken
                        }
                    }
                )
            }
            .background(color)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        navController.navigate("details")
                    },
                    onDoubleTap = {
                        color = if (color == Color.Gray) Color.Red else Color.Gray
                    },
                    onLongPress = {
                        isLongPressed = !isLongPressed
                    }
                )
            }
    ) {
        Box(
            modifier = Modifier
                .size(boxSize)
                .padding(20.dp)
                .offset { IntOffset(offsetX.toInt(), offsetY.toInt()) }
                .background(if (isLongPressed) Color.Green else Color.Blue)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            if (offsetX > flingThreshold) {
                                navController.navigate("details")
                            }
                        },
                        onHorizontalDrag = { change, dragAmount ->
                            change.consume()
                            offsetX += dragAmount
                        }
                    )
                }
                .rotate(rotationAngle)
                .scale(scale)
        ) {
            Text(
                text = if (isShaken) "Shake detected!" else "Tap this!",
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

