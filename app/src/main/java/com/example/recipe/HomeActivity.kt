package com.example.recipe

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.recipe.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var rvAdapter: PopularAdapter
    private lateinit var dataList:ArrayList<Recipe>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()

        binding.more.setOnClickListener {
            val dialogue = Dialog(this)
            dialogue.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogue.setContentView(R.layout.bottom_sheet)
            dialogue.show()
            dialogue.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
            )
            dialogue.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogue.window!!.setGravity(Gravity.BOTTOM)

        }


        binding.editTextText.setOnClickListener{
            startActivity(Intent(this,SearchActivity::class.java))
        }

        binding.salad.setOnClickListener {
            var MyInetent=Intent(this@HomeActivity,CategoryActivity::class.java)
            MyInetent.putExtra("TITTLE","Salad")
            MyInetent.putExtra("CATEGORY","Salad")
            startActivity(MyInetent)
        }
        binding.maindish.setOnClickListener {  var MyInetent=Intent(this@HomeActivity,CategoryActivity::class.java)
            MyInetent.putExtra("TITTLE","Main Dish")
            MyInetent.putExtra("CATEGORY","Dish")
            startActivity(MyInetent)}
        binding.drinks.setOnClickListener { var MyInetent=Intent(this@HomeActivity,CategoryActivity::class.java)
            MyInetent.putExtra("TITTLE","Drinks")
            MyInetent.putExtra("CATEGORY","Drinks")
            startActivity(MyInetent) }
        binding.dessert.setOnClickListener { var MyInetent=Intent(this@HomeActivity,CategoryActivity::class.java)
            MyInetent.putExtra("TITTLE","Desserts")
            MyInetent.putExtra("CATEGORY","Desserts")
            startActivity(MyInetent) }
    }



    private fun setUpRecyclerView() {
        dataList=ArrayList()
        binding.rvPopular.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        var db= Room.databaseBuilder(this@HomeActivity,AppDatabase::class.java,"db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()

        var DaoObject=db.getDao()
        var recipes=DaoObject.getAll()
         for(i in recipes!!.indices){
            if(recipes[i]!!.category.contains("Popular")){
                dataList.add(recipes[i]!!)
            }
             rvAdapter=PopularAdapter(dataList,this)
             binding.rvPopular.adapter=rvAdapter

         }

    }
}