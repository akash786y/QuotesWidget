package com.example.quoteswidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import kotlin.random.Random

class DailyQuoteWidget : AppWidgetProvider() {

    companion object {
        private val quotes = listOf(
            "Believe in yourself! - Anonymous",
            "The best way to predict the future is to create it. - Peter Drucker",
            "Success is not final, failure is not fatal: It is the courage to continue that counts. - Winston Churchill",
            "Your time is limited, so don’t waste it living someone else’s life. - Steve Jobs",
            "Hardships often prepare ordinary people for an extraordinary destiny. - C.S. Lewis",
            "Do what you can, with what you have, where you are. - Theodore Roosevelt",
            "The only way to do great work is to love what you do. - Steve Jobs",
            "It does not matter how slowly you go as long as you do not stop. - Confucius",
            "Difficulties in life are intended to make us better, not bitter. - Dan Reeves",
            "Opportunities don't happen. You create them. - Chris Grosser",
            "Act as if what you do makes a difference. It does. - William James",
            "Courage is resistance to fear, mastery of fear, not absence of fear. - Mark Twain",
            "Success is getting what you want. Happiness is wanting what you get. - Dale Carnegie",
            "You are never too old to set another goal or to dream a new dream. - C.S. Lewis",
            "Don’t wait for opportunity. Create it. - Anonymous",
            "Dream big and dare to fail. - Norman Vaughan",
            "Doubt kills more dreams than failure ever will. - Suzy Kassem",
            "Happiness depends upon ourselves. - Aristotle",
            "It always seems impossible until it’s done. - Nelson Mandela",
            "Do what you love and love what you do. - Ray Bradbury"
        )

        fun getRandomQuote(): Pair<String, String> {
            val quote = quotes[Random.nextInt(quotes.size)]
            val parts = quote.split(" - ")
            return if (parts.size == 2) Pair(parts[0], parts[1]) else Pair(quote, "Unknown")
        }
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action == "com.example.dailyquotewidget.UPDATE_QUOTE") {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(
                ComponentName(context, DailyQuoteWidget::class.java)
            )
            for (appWidgetId in appWidgetIds) {
                updateWidget(context, appWidgetManager, appWidgetId)
            }
        }
    }

    private fun updateWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val views = RemoteViews(context.packageName, R.layout.widget_layout)

        val (quote, author) = getRandomQuote()
        views.setTextViewText(R.id.quote_text, quote)
        views.setTextViewText(R.id.quote_author, "- $author")

        val intent = Intent(context, DailyQuoteWidget::class.java).apply {
            action = "com.example.dailyquotewidget.UPDATE_QUOTE"
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        views.setOnClickPendingIntent(R.id.widget_container, pendingIntent)

        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}