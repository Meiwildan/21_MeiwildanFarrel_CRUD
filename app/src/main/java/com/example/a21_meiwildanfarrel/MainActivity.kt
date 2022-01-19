
package com.example.a21_meiwildanfarrel

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.withContext
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a21_meiwildanfarrel.room.Constant
import com.example.a21_meiwildanfarrel.room.Movie
import com.example.a21_meiwildanfarrel.room.MovieDb
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    val db by lazy { MovieDb(this) }
    lateinit var movieAdapter: MovieAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupListener()
        setupRecyclerView()

    }
    override fun onStart(){
        super.onStart()
        loadMovie()

    }


    fun loadMovie(){
        CoroutineScope(Dispatchers.IO).launch {
            val movies = db.movieDao().getMovies()
            Log.d("MainActivity", "dbresponse: $movies")
            withContext(Dispatchers.Main){
                movieAdapter.setData(movies)
            }
        }
    }

    fun setupListener(){
        add_movie.setOnClickListener{
            intentEdit(0, Constant.TYPE_CREATE)
        }
    }

    fun intentEdit(movieid: Int, intentType: Int){
        startActivity(
            Intent(applicationContext, AddActivity::class.java)
                .putExtra("intent_id", movieid)
                .putExtra("intent_type", intentType)
        )
    }

    private fun setupRecyclerView() {
        movieAdapter = MovieAdapter(arrayListOf(), object : MovieAdapter.OnAdapterListener {
            override fun onClick(movie: Movie) {

                intentEdit(movie.id, Constant.TYPE_READ)
            }

            override fun onUpdate(movie: Movie) {
                intentEdit(movie.id, Constant.TYPE_UPDATE)
            }

            override fun onDelete(movie: Movie) {
                deleteDialog(movie)
            }

        })
        rv_movie.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = movieAdapter
        }
    }

        private fun deleteDialog(movie: Movie){
            val alerDialog = AlertDialog.Builder(this)
            alerDialog.apply{
                setTitle("Konfirmasi")
                setMessage("Yakin hapus ${movie.title}?")
                setNegativeButton("Batal") { dialogInterface, i ->
                    dialogInterface.dismiss()
                }
                setPositiveButton("Hapus") { dialogInterface, i ->
                    dialogInterface.dismiss()
                    CoroutineScope(Dispatchers.IO).launch {
                        db.movieDao().deleteMovie(movie)
                        loadMovie()
                    }

                }
            }
            alerDialog.show()
        }
    }

