package com.example.recipe

import android.annotation.SuppressLint
import android.content.Context
import android.inputmethodservice.InputMethodService
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.recipe.databinding.ActivityHomeBinding
import com.example.recipe.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySearchBinding
    private lateinit var rvAdapter: SearchAdapter
    private lateinit var dataList:ArrayList<Recipe>
    private lateinit var recipes: List<Recipe?>
    @SuppressLint("ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.Search.requestFocus()
        var db= Room.databaseBuilder(this@SearchActivity,AppDatabase::class.java,"db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()

        var DaoObject=db.getDao()

        recipes = DaoObject.getAll()!!
        setUpRecyclerView()

        binding.goBackHome.setOnClickListener {
            finish()
        }
        binding.Search.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString()!=""){
                    filterData(p0.toString())
                }
                else{
                    setUpRecyclerView()
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        binding.rvSearch.setOnClickListener {
            val view = currentFocus
            if (view != null) {
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }


    }

    private fun filterData(filterText:String){
        var filterData=ArrayList<Recipe>()
        for (i in recipes.indices){
            if(recipes[i]!!.tittle.lowercase().contains(filterText.lowercase())){
                filterData.add(recipes[i]!!)
            }
            rvAdapter.filterList(filterList = filterData)
        }

    }
    private fun setUpRecyclerView() {
        dataList=ArrayList()
        binding.rvSearch.layoutManager=
            LinearLayoutManager(this)

        for(i in recipes!!.indices){
            if(recipes[i]!!.category.contains("Popular")){
                dataList.add(recipes[i]!!)
            }
            rvAdapter=SearchAdapter(dataList,this)
            binding.rvSearch.adapter=rvAdapter
        }

    }
}