package ir.radindev.dunifoodsimple

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.radindev.dunifoodsimple.databinding.ActivityMainBinding
import ir.radindev.dunifoodsimple.databinding.DialogAddNewItemBinding
import ir.radindev.dunifoodsimple.databinding.DialogDeleteItemBinding
import ir.radindev.dunifoodsimple.databinding.DialogUpdateItemBinding
import java.text.ParsePosition
const val Tag_MAIN_ACTIVITY="activityMain"
class MainActivity : AppCompatActivity(), FoodAdapter.FoodEvents {
    lateinit var binding: ActivityMainBinding
    lateinit var myAdapter: FoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

            Log.v(Tag_MAIN_ACTIVITY,"صفحه اصلی صدا زده شد")

        // how to use recycler view :
        // 1. create view of recyclerView in activity_main.xml
        // 2. create item for recyclerView
        // 3. create adapter and view holder for recyclerView
        // 4. set adapter to recyclerView and set layout manager

        val foodList = arrayListOf(
            Food(
                "Hamburger",
                "15",
                "3",
                "Isfahan, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food1.jpg",
                20,
                4.5f
            ),
            Food(
                "Grilled fish",
                "20",
                "2.1",
                "Tehran, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food2.jpg",
                10,
                4f
            ),
            Food(
                "Lasania",
                "40",
                "1.4",
                "Isfahan, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food3.jpg",
                30,
                2f
            ),
            Food(
                "pizza",
                "10",
                "2.5",
                "Zahedan, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food4.jpg",
                80,
                1.5f
            ),
            Food(
                "Sushi",
                "20",
                "3.2",
                "Mashhad, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food5.jpg",
                200,
                3f
            ),
            Food(
                "Roasted Fish",
                "40",
                "3.7",
                "Jolfa, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food6.jpg",
                50,
                3.5f
            ),
            Food(
                "Fried chicken",
                "70",
                "3.5",
                "NewYork, USA",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food7.jpg",
                70,
                2.5f
            ),
            Food(
                "Vegetable salad",
                "12",
                "3.6",
                "Berlin, Germany",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food8.jpg",
                40,
                4.5f
            ),
            Food(
                "Grilled chicken",
                "10",
                "3.7",
                "Beijing, China",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food9.jpg",
                15,
                5f
            ),
            Food(
                "Baryooni",
                "16",
                "10",
                "Ilam, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food10.jpg",
                28,
                4.5f
            ),
            Food(
                "Ghorme Sabzi",
                "11.5",
                "7.5",
                "Karaj, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food11.jpg",
                27,
                5f
            ),
            Food(
                "Rice",
                "12.5",
                "2.4",
                "Shiraz, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food12.jpg",
                35,
                2.5f
            ),
        )
        Log.v(Tag_MAIN_ACTIVITY,"radin : ${foodList.size}")

        myAdapter = FoodAdapter(foodList.clone() as ArrayList<Food>, this)
        binding.recyclerMain.adapter = myAdapter
        binding.recyclerMain.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)


        binding.btnNewFood.setOnClickListener {

            val dialog = AlertDialog.Builder(this).create()
            val dialogBinding = DialogAddNewItemBinding.inflate(layoutInflater)
            dialog.setView(dialogBinding.root)

            dialog.setCancelable(true)

            dialog.show()

            dialogBinding.dialogBtnDone.setOnClickListener {

                if (
                    dialogBinding.dialogEdtNameFood.length() > 0 &&
                    dialogBinding.dialogEdtFoodPrice.length() > 0 &&
                    dialogBinding.dialogEdtFoodCity.length() > 0 &&
                    dialogBinding.dialogEdtFoodDistance.length() > 0
                ) {
                    val txtName = dialogBinding.dialogEdtNameFood.text.toString()
                    val txtPrice = dialogBinding.dialogEdtFoodPrice.text.toString()
                    val txtDistance = dialogBinding.dialogEdtFoodDistance.text.toString()
                    val txtCity = dialogBinding.dialogEdtFoodCity.text.toString()
                    val txtRatingNumber: Int = (1..150).random()
                    val ratingBarStar: Float = (1..5).random().toFloat()
                    val randomForUrl = (0 until 12).random()
                    val urlPic = foodList[randomForUrl].urlImage

                    val newFood = Food(
                        txtName,
                        txtPrice,
                        txtDistance,
                        txtCity,
                        urlPic,
                        txtRatingNumber,
                        ratingBarStar
                    )
                    myAdapter.addFood(newFood)
                    dialog.dismiss()

                    binding.recyclerMain.scrollToPosition(0)

                } else {
                    Toast.makeText(this, "لطفا همه مقالدیر را وارد کنید :)", Toast.LENGTH_SHORT)
                        .show()
                }


            }


        }


        binding.edtSearch.addTextChangedListener { editTextInput ->
            if (editTextInput!!.isNotEmpty()) {

                val cloneList = foodList.clone() as ArrayList<Food>
                val filteredList = cloneList.filter { foodItem ->
                    foodItem.txtSubject.contains(editTextInput)
                }
                myAdapter.setdate(ArrayList(filteredList))
            } else {
                myAdapter.setdate(foodList.clone() as ArrayList<Food>)
            }
        }

    }

    override fun onFoodClicked(food: Food, position: Int) {
        val dialog = AlertDialog.Builder(this).create()
        val updateDialogBinding = DialogUpdateItemBinding.inflate(layoutInflater)

        dialog.setView(updateDialogBinding.root)
        dialog.setCancelable(true)
        dialog.show()

        updateDialogBinding.dialogEdtNameFood.setText(food.txtSubject)
        updateDialogBinding.dialogEdtFoodCity.setText(food.txtCity)
        updateDialogBinding.dialogEdtFoodPrice.setText(food.txtPrice)
        updateDialogBinding.dialogEdtFoodDistance.setText(food.txtDistance)


        updateDialogBinding.dialogUpdateBtnCancel.setOnClickListener {
            dialog.dismiss()
        }
        updateDialogBinding.dialogUpdateBtnDone.setOnClickListener {
            if (
                updateDialogBinding.dialogEdtNameFood.length() > 0 &&
                updateDialogBinding.dialogEdtFoodPrice.length() > 0 &&
                updateDialogBinding.dialogEdtFoodCity.length() > 0 &&
                updateDialogBinding.dialogEdtFoodDistance.length() > 0
            ) {
                val txtName = updateDialogBinding.dialogEdtNameFood.text.toString()
                val txtPrice = updateDialogBinding.dialogEdtFoodPrice.text.toString()
                val txtDistance = updateDialogBinding.dialogEdtFoodDistance.text.toString()
                val txtCity = updateDialogBinding.dialogEdtFoodCity.text.toString()

                val newFood = Food(
                    txtName,
                    txtPrice,
                    txtDistance,
                    txtCity,
                    food.urlImage,
                    food.numOfRating,
                    food.rating
                )
                //update item :
                myAdapter.updateFood(newFood, position)
                dialog.dismiss()
            } else {
                Toast.makeText(this, "لطفا همه مقالدیر را وارد کنید :)", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onFoodLongClicked(food: Food, pos: Int) {

        val dialog = AlertDialog.Builder(this).create()
        val dialogDeleteBinding = DialogDeleteItemBinding.inflate(layoutInflater)
        dialog.setView(dialogDeleteBinding.root)
        dialog.setCancelable(true)
        dialog.show()

        dialogDeleteBinding.dialogBtnDeleteCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialogDeleteBinding.dialogBtnDeleteSure.setOnClickListener {
            dialog.dismiss()
            myAdapter.removeFood(food, pos)
        }

    }


}