package com.example.loginapidemo

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.loginapidemo.Api.LoginApi
import com.example.loginapidemo.Api.Utility
import com.example.loginapidemo.ModelClass.ErrorModel
import com.example.loginapidemo.ModelClass.UserRequest
import com.example.loginapidemo.ModelClass.UserResponse
import com.google.gson.Gson
import okhttp3.internal.cache.DiskLruCache
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var edNumber: EditText
    lateinit var edPassword: EditText
    lateinit var buttonLogin: Button
    lateinit var userResponse: UserResponse
    lateinit var sharedPreferences: SharedPreferences

    var PREFS_KEY = "prefs"

    var user: UserResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edNumber = findViewById<EditText>(R.id.edNumber)
        val edPassword = findViewById<EditText>(R.id.edPassword)
        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        sharedPreferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)



        fun valid(): Boolean {
            if (edNumber.text.toString().isEmpty()) {
                edNumber.requestFocus()
                edNumber.error = "please enter Number"
                return false
            } else if (edPassword.text.toString().isEmpty()) {
                edPassword.requestFocus()
                edPassword.error = "Please enter Password"
                return false
            }
            return true
        }

        var connected = false
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        connected = networkInfo != null && networkInfo.isConnected

        if (sharedPreferences.getBoolean("isLogged", false)) {
//            val intent = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        } else {


            buttonLogin.setOnClickListener {
                if (valid()) {
                    val mobile: String = edNumber.text.toString().trim { it <= ' ' }
                    val pwd: String = edPassword.text.toString().trim { it <= ' ' }

                    val userRequest = UserRequest(
                        mobile_no = mobile,
                        from_ip = "192.168.32.135",
                        password = pwd,
                        device_uuid = "dwsP7DolRiKUwTVTqV3wcl:APA91bHlsz2zSSRnE587biO7y03eVBMeANvToRUm71nIa_lasnO1iMipCZMb7JbommrlGu3weDv8KQPoK-vhCpQYQ-yDHdZKCeIiTDcSnpAsvyegFcgCLES60TOFmL76p_x8WbRui6pN",
                        device_no = "d1dc911839bf3bef",
                        device_name = "a10s",
                        device_platform = "Android",
                        device_model = "SM-A107F",
                        device_version = "29",
                        device_type = 1,
                        city_id = 1,
                        app_version = "2.9.6"
                    )



                    if (connected) {


                        Log.e(TAG, "onCreate: " + userRequest)


                        val userApi: LoginApi = Utility.getUser().create(LoginApi::class.java)

                        val call: Call<UserResponse> = userApi.sendData(userRequest)

                        call.enqueue(object : Callback<UserResponse> {

                            override fun onResponse(
                                call: Call<UserResponse>,
                                response: Response<UserResponse>,
                            ) {
                                if (response.isSuccessful) {
                                    savePrefsData()
                                    saveUser()
                                    user = response.body()!!
                                    saveUser()
//                                    val intent = Intent(this@MainActivity, HomeActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    val hashMap: HashMap<Any?, Any?> = HashMap<Any?, Any?>()
                                    val gson = Gson()
                                    val errorData: ErrorModel = gson.fromJson(
                                        response.errorBody()!!.string(),
                                        ErrorModel::class.java)
                                }
                            }

                            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                                Toast.makeText(this@MainActivity,
                                    "error: " + t.message,
                                    Toast.LENGTH_SHORT)
                                    .show()
                            }

                        })

                    } else {
                        Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    private fun savePrefsData() {
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLogged", true)
        editor.apply()
    }

    fun saveUser() {
        val prefsEditor: SharedPreferences.Editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(user) // myObject - instance of MyObject

        prefsEditor.putString("MyObject", json)
        prefsEditor.commit()
    }
}





