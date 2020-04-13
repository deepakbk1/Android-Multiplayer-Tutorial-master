package com.deepak.multiplayertutorial

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.deepak.multiplayertutorial.actions.GetScore
import com.deepak.multiplayertutorial.dagger.ContextModule
import com.deepak.multiplayertutorial.dagger.DaggerTutorialApplicationComponent
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var blockingStub: TutorialActionServiceGrpc.TutorialActionServiceBlockingStub

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DaggerTutorialApplicationComponent.builder().contextModule(ContextModule(this)).build().inject(this)

        scoreButton.setOnClickListener { getGreetingAsynchronously(idEditText.text.toString()) }
    }

    private fun getGreetingAsynchronously(name: String) {
        doAsync {

            val getScoreResponse = blockingStub.getScore(GetScore.GetScoreRequest.newBuilder().setName(name).build())

            uiThread {
                if(getScoreResponse.statusCode == GetScore.GetScoreResponse.StatusCode.OK)
                    toast(getScoreResponse.message)
                else
                    toast("Server error")
            }
        }

    }
}
