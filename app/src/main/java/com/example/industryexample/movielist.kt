package com.example.industryexample

import android.app.DownloadManager.Request
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class movielist : AppCompatActivity() {

    lateinit var recycleview:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movielist)
        LoadMovies()

        recycleview=findViewById(R.id.recycleview)
        recycleview.adapter=MovieAdaptor()
        recycleview.layoutManager=LinearLayoutManager(
        applicationContext,
        LinearLayoutManager.VERTICAL,
        false
        )
    }
    fun LoadMovies(){
        val request=JsonObjectRequest(
            com.android.volley.Request.Method.GET,
            "https://api.themoviedb.org/3/movie/550?api_key=ec739578ad0377797dd7c7b3b919573c",
           JSONObject(),{ response ->

            Log.e("response",response.toString())
            },{ error->
                Log.e("error",error.toString())

            }


        )
       Volley.newRequestQueue(applicationContext).add(request)


    }
      private inner class MovieAdaptor :
          RecyclerView.Adapter<MoviesViewHolder>(){
          override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
              val view=LayoutInflater.from(parent.context).inflate(R.layout.movie_item,parent,false)

              return MoviesViewHolder(view)
          }

          override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
               // holder.movietitle.text=movieData
               // .getJSONObject(position).getString("title")
              // holder.releaseDate.text=
              //   movieData
          }

          override fun getItemCount(): Int {
              return 10
          }


      }
      private inner class MoviesViewHolder(itemView: View):
              RecyclerView.ViewHolder(itemView)
       {
                  var movietitle:TextView=itemView.findViewById(R.id.movie_name)
                  var releaseDate:TextView=itemView.findViewById(R.id.release_date)
                  var moviethumb:ImageView=itemView.findViewById(R.id.image)

              }
}