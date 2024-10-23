package com.jerson.soundeyes.feature_app.presentation.utils

import android.annotation.SuppressLint
import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale


@SuppressLint("StaticFieldLeak")
object TextToSpeechManager : TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech
    private lateinit var context: Context
    private var isInitialized: Boolean = false

    fun initialize(context: Context) {
        this.context = context
        tts = TextToSpeech(context, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.getDefault())
            isInitialized = result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED
        }
    }

    fun convert(text: String) {
        if (isInitialized) {
            if (!tts.isSpeaking) {
                tts.speak(text, TextToSpeech.QUEUE_ADD, null, null)
            }
        } else {
            println("TextToSpeech não está pronto.")
        }
    }

    fun shutdown() {
        tts.stop()
        tts.shutdown()
    }
}