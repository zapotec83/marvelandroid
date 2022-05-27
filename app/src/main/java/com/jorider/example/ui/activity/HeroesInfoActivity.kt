package com.jorider.example.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.jorider.example.databinding.ActivityHeroesDetailInfoBinding
import com.jorider.example.extensions.load
import com.jorider.example.ui.viewmodel.HeroesInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeroesInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHeroesDetailInfoBinding
    private val heroesViewModel: HeroesInfoViewModel by viewModels()

    companion object {

        const val CHARACTER_ID_EXTRA = "com.jorider.example.ui.activity.character_id_extra"

        fun startActivity(context: Context, characterId: String) {
            val intent = Intent(context, HeroesInfoActivity::class.java)
            intent.putExtra(CHARACTER_ID_EXTRA, characterId)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHeroesDetailInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.activityHeroesInfoToolbar.toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true);
        }

        val characterId = intent.getStringExtra(CHARACTER_ID_EXTRA)
        characterId?.let {
            heroesViewModel.getHeroesDetailInformation(it).observe(this) { resultWrapper ->
                resultWrapper.doSuccess { heroes ->
                    binding.activityHeroesInfoToolbar.toolbar.title = heroes.name
                    binding.heroesInfoImg.load(heroes.image)
                    binding.heroesDetailName.text = heroes.description
                }

                resultWrapper.doFail { error ->
                    Snackbar.make(this, binding.root, error.toString(), Snackbar.LENGTH_SHORT).show()
                }
            }
        } ?: kotlin.run {
            Snackbar.make(this, binding.root, "No id found!", Snackbar.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onOptionsItemSelected(@NonNull item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}