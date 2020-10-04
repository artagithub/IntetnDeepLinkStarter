package com.example.deeplinkstarter.ui.location

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.deeplinkstarter.R
import com.example.deeplinkstarter.databinding.ActivityLocationBinding
import com.example.deeplinkstarter.enum.AppParametersEnum

class LocationActivity : AppCompatActivity() {

    private lateinit var countryName:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val locationViewBinding = ActivityLocationBinding.inflate(layoutInflater)
        val view = locationViewBinding.root
        val countryList = ArrayList<String>()
        countryList.add("IRAN")
        countryList.add("TURKEY")
        countryList.add("GERMANY")
        countryList.add("CANADA")
        countryList.add("JAPAN")
        countryList.add("UNITED-STATES")

        val countrySpinner = locationViewBinding.countrySpinner

        val arrayAdapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,countryList)
        countrySpinner.adapter =  arrayAdapter
        countrySpinner.onItemSelectedListener = object : OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(applicationContext, R.string.country_must_have_value,Toast.LENGTH_SHORT).show()
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p0 != null) {
                    countryName = p0.selectedItem.toString()
                }else{
                    Toast.makeText(applicationContext, R.string.country_must_have_value,Toast.LENGTH_SHORT).show()
                }
            }

        }

        locationViewBinding.chooseCountry.setOnClickListener(View.OnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra(AppParametersEnum.COUNTRY.value,countryName)
            setResult(Activity.RESULT_OK,resultIntent)
            finish()
        })



        setContentView(view)
    }
}