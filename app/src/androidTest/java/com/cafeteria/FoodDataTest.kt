package com.cafeteria

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.content.ContextCompat
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cafeteria.config.CafeteriaDatabase
import com.cafeteria.dto.Menu
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import java.io.ByteArrayOutputStream
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class FoodDataTest {

    val context: Context = ApplicationProvider.getApplicationContext()

    val imgId = R.drawable.mix
    val resources = context.resources
    val imgDrawable = ContextCompat.getDrawable(context, imgId)
    val bitmap: Bitmap = imgDrawable.let {
        BitmapFactory.decodeResource(resources, imgId)
    }
    val steam = ByteArrayOutputStream()
    val a = bitmap.compress(Bitmap.CompressFormat.JPEG,100, steam)


    private lateinit var foodDao: Menu
    private lateinit var cafeteriaDatabase: CafeteriaDatabase
//    private val food1 = Menu(1, "한식", "김치찌개")

    @Before
    fun createDb() {
        cafeteriaDatabase = Room.inMemoryDatabaseBuilder(context, CafeteriaDatabase::class.java)
            .allowMainThreadQueries()
            .build()
//        foodDao = cafeteriaDatabase.FoodDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        cafeteriaDatabase.close()
    }

//    @Test
//    @Throws(Exception::class)
//    fun foodInsert() = runBlocking {
//        addFoodInsert()
//        val foodData = foodDao.foodAllList().first()
//        assertEquals(foodData, food1)
//    }
//
//    private suspend fun addFoodInsert() {
//        foodDao.foodInsert(food1)
//    }


}