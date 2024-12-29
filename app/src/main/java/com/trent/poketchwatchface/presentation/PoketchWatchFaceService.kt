package com.trent.poketchwatchface.presentation

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.SurfaceHolder
import androidx.wear.watchface.CanvasType
import androidx.wear.watchface.ComplicationSlotsManager
import androidx.wear.watchface.Renderer
import androidx.wear.watchface.WatchFace
import androidx.wear.watchface.WatchFaceService
import androidx.wear.watchface.WatchFaceType
import androidx.wear.watchface.style.CurrentUserStyleRepository
import androidx.wear.watchface.WatchState
import java.time.ZonedDateTime
import java.util.*

class PoketchWatchFaceService : WatchFaceService() {

    override suspend fun createWatchFace(
        surfaceHolder: SurfaceHolder,
        watchState: WatchState,
        complicationSlotsManager: ComplicationSlotsManager,
        currentUserStyleRepository: CurrentUserStyleRepository
    ): WatchFace {
        val renderer = PoketchRenderer(
            surfaceHolder,
            currentUserStyleRepository,
            watchState,
            CanvasType.HARDWARE,
            16L
        )
        return WatchFace(
            WatchFaceType.DIGITAL,
            renderer
        )
    }
}

class PoketchRenderer(
    surfaceHolder: SurfaceHolder,
    currentUserStyleRepository: CurrentUserStyleRepository,
    watchState: WatchState,
    canvasType: Int,
    frameTimeMillis: Long
) : Renderer.CanvasRenderer2<Renderer.SharedAssets>(
    surfaceHolder,
    currentUserStyleRepository,
    watchState,
    canvasType,
    frameTimeMillis,
    true
) {

    private val paint = Paint().apply {
        color = Color.WHITE
        textSize = 50f
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }

    private val calendar = Calendar.getInstance()

    override suspend fun createSharedAssets(): SharedAssets {
        // Implement this method to create and return any shared assets.
        return object : SharedAssets {
            override fun onDestroy() {
            }
        }
    }

    override fun render(
        canvas: Canvas,
        bounds: Rect,
        zonedDateTime: ZonedDateTime,
        sharedAssets: SharedAssets
    ) {
        // Set the background color
        canvas.drawColor(Color.parseColor("#70b070"))

        // Get the current time
        calendar.timeInMillis = System.currentTimeMillis()
        val hour = String.format(Locale.getDefault(), "%02d", calendar.get(Calendar.HOUR_OF_DAY))
        val minute = String.format(Locale.getDefault(), "%02d", calendar.get(Calendar.MINUTE))
        val timeText = "$hour:$minute"

        // Draw the time in the center of the screen
        canvas.drawText(timeText, bounds.centerX().toFloat(), bounds.centerY().toFloat(), paint)
    }

    override fun renderHighlightLayer(
        canvas: Canvas,
        bounds: Rect,
        zonedDateTime: ZonedDateTime,
        sharedAssets: SharedAssets
    ) {
    }
}