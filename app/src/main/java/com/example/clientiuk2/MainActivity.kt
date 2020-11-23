package com.example.clientiuk2

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        notification("auf","jija")
        postButton.setOnClickListener { getMessage() }
    }

    fun notification(titleContent: String, mainContent: String) {
        val CHANNEL_ID = "IUK2"
        val CHANNEL_NAME = "IUK2-CHANNELL"
        val NOTIFY_ID: Int = 100
        val builderNotification = NotificationCompat.Builder(this, CHANNEL_ID)
        val notificationChannel =
            NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
        notificationChannel.description = "Test"
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
        builderNotification.setContentText(mainContent)
            .setContentTitle(titleContent)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setAutoCancel(true)
        notificationManager.notify(NOTIFY_ID, builderNotification.build())

    }



    private fun getMessage(){
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        notification("2","2")
        val jsonPlaceHolderApi: JsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi::class.java)
        notification("3","3")
        val call: Call<List<Post>> = jsonPlaceHolderApi.getPosts()
        notification("5","5")
        call.enqueue(object : Callback<List<Post>> {

            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    val textView = findViewById<TextView>(R.id.text_view_result)
                    textView.text = "Ura: " + response.code()
                }
                val posts: List<Post>? = response.body()

                notification("6","6")
                if (posts != null) {
                    val textView:TextView = findViewById(R.id.text_view_result)
                    for (post: Post in posts) {
                        var content = ""
                        content+="ID: " + post.id+"\n"
                        content+="UserID: " + post.userId+"\n"
                        content+="Title: " + post.title+"\n"
                        content+="Text: " + post.message+"\n\n"
                        textView.append(content)

                    }
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                notification("Pizda vsemu", "Ne Robit")
            }


        })
    }
}
