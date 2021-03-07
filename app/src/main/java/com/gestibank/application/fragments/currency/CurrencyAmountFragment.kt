package com.gestibank.application.fragments.currency

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.gestibank.application.R
import com.gestibank.application.api.APIService
import com.gestibank.application.models.CurrencyModel
import kotlinx.android.synthetic.main.fragment_currency__conversion.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrencyAmountFragment : Fragment() {
    var cours = 0.0
    var result = 0.0
    var defaultVal = 0.0
    val values: Array<String> = arrayOf("USD")
    val currencies: Array<String> = arrayOf("EUR", "GBP", "CAD", "PLN")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_currency__conversion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        spinner_currency.adapter =
            ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, values)
        spinner_amount.adapter =
            ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, currencies)

        btn_convert.setOnClickListener {
            if (edit_amount.text != null && edit_amount.text.isNotEmpty()) {
                getCurrency(
                    edit_amount.text.toString(),
                    spinner_amount.selectedItem.toString()
                )
            }
        }

        edit_amount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                txt_result_conversion.visibility = View.GONE

            }
        })


    }


    private fun getCurrency(valAmount: String, spinnerAmount: String) {
        val currencyService = APIService.getCurrencyService()

        val URl =
            "live?access_key=be01455ba7b0f7fb18a34924ae4e13f0&currencies=$spinnerAmount&source=USD&amount=${valAmount.toDouble()}&format=1/"
        val call: Call<CurrencyModel> = currencyService.getData(URl)

        call.enqueue(object : Callback<CurrencyModel?> {
            override fun onResponse(
                call: Call<CurrencyModel?>?,
                response: Response<CurrencyModel?>
            ) {
                if (response.isSuccessful) {
                    val quotes: String = response.body()!!.quotes!!.toString()
                    val rate = quotes!!.substring(quotes!!.indexOf("=") + 1, quotes!!.indexOf("}"))
                    defaultVal = valAmount!!.toDouble()
                    cours = rate!!.toDouble()
                    val res1 = cours * defaultVal
                    val res2 = ((res1 * 100).toInt() / 100)
                    txt_result_conversion.text = res2.toString()
                    txt_result_conversion.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<CurrencyModel?>, t: Throwable) {
                Log.d("onFailure", "" + t.message)
            }
        })

    }
}

