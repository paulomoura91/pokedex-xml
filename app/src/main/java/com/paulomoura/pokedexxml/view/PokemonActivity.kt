package com.paulomoura.pokedexxml.view

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.paulomoura.pokedexxml.R
import com.paulomoura.pokedexxml.databinding.ActivityPokemonBinding
import com.paulomoura.pokedexxml.extension.bindings
import com.paulomoura.pokedexxml.extension.px
import com.paulomoura.pokedexxml.model.entity.Pokemon

class PokemonActivity : AppCompatActivity() {

    private val binding: ActivityPokemonBinding by bindings(ActivityPokemonBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val pokemon = getPokemon()
        showDetails(pokemon)
    }

    private fun getPokemon() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        intent.getParcelableExtra(POKEMON_EXTRA, Pokemon::class.java)
    } else {
        @Suppress("DEPRECATION") intent.getParcelableExtra(POKEMON_EXTRA)
    }

    @SuppressLint("SetTextI18n")
    private fun showDetails(pokemon: Pokemon?) {
        pokemon?.let {
            with(binding) {
                textViewName.text = it.name
                textViewNumber.text = "Nº ${String.format("%03d", it.number)}"
                Glide.with(root).load(it.imageUrl).into(imageViewUrl)
                textViewDescription.text = it.description
                it.types.forEach { type ->
                    val textViewType = TextView(this@PokemonActivity).apply {
                        text = type.uppercase()
                        setTypeface(typeface, Typeface.BOLD)
                        layoutParams = LinearLayout.LayoutParams(128.px, 40.px).apply { marginEnd = 16.px }
                        gravity = Gravity.CENTER
                        setBackgroundColor(ContextCompat.getColor(this@PokemonActivity, getTypeColor(type)))
                    }
                    linearLayoutTypes.addView(textViewType)
                }
            }
        }
    }

    @ColorRes
    private fun getTypeColor(type: String): Int {
        return when(type) {
            TYPE.grass.toString() -> R.color.green
            TYPE.poison.toString() -> R.color.purple
            TYPE.fire.toString() -> R.color.red
            TYPE.flying.toString() -> R.color.cornflower_blue
            TYPE.water.toString() -> R.color.deep_sky_blue
            TYPE.bug.toString() -> R.color.yellow_green
            TYPE.normal.toString() -> R.color.light_slate_gray
            TYPE.electric.toString() -> R.color.yellow
            TYPE.ground.toString() -> R.color.burly_wood
            TYPE.fairy.toString() -> R.color.hot_pink
            TYPE.fighting.toString() -> R.color.brown
            TYPE.psychic.toString() -> R.color.deep_pink
            TYPE.rock.toString() -> R.color.dark_khaki
            TYPE.steel.toString() -> R.color.slate_gray
            TYPE.ice.toString() -> R.color.turquoise
            TYPE.ghost.toString() -> R.color.dark_slate_blue
            TYPE.dragon.toString() -> R.color.blue_violet
            else -> R.color.secondTextColor
        }
    }

    internal enum class TYPE { grass, poison, fire, flying, water, bug, normal, electric, ground, fairy, fighting, psychic, rock, steel, ice, ghost, dragon }

    companion object {
        const val POKEMON_EXTRA = "pokemon_extra"
    }
}