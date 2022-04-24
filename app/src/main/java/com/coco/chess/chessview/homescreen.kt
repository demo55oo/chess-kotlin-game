package com.coco.chess.chessview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.coco.chess.MainActivity
import com.coco.chess.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_homescreen.*

class homescreen : AppCompatActivity() {
    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homescreen)
        MobileAds.initialize(this)
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-2341497470012319/7464992328\n"
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
        val intent2 = Intent(this , MainActivity::class.java)
        button.setOnClickListener{
          if (mInterstitialAd.isLoaded) {
                mInterstitialAd.show()
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.")
            }
            startActivity(intent2)
        }

    }
}