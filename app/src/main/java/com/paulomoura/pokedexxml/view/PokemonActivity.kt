package com.paulomoura.pokedexxml.view

import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.paulomoura.pokedexxml.R
import com.paulomoura.pokedexxml.databinding.ActivityPokemonBinding
import com.paulomoura.pokedexxml.extension.bindings
import com.paulomoura.pokedexxml.extension.px
import com.paulomoura.pokedexxml.extension.toPokemonNumber
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

    private fun showDetails(pokemon: Pokemon?) {
        pokemon?.let {
            with(binding) {
                textViewName.text = it.name
                textViewNumber.text = it.number.toPokemonNumber()
                Glide.with(root).load(it.imageUrl).into(imageViewUrl)
                textViewDescription.text = it.description
                it.types.forEach { type ->
                    linearLayoutTypes.addView(buildTextViewType(type))
                }
                it.evolutions?.let { evolutions ->
                    if (evolutions.size == 1) {
                        linearLayoutEvolutions.addView(buildTextViewNoEvolution())
                    }
                    evolutions.forEach { evolution ->
                        linearLayoutEvolutions.addView(buildLinearLayoutEvolution(evolution))
                    }
                    linearLayoutEvolutions.isVisible = true
                }
            }
        }
    }

    private fun buildTextViewType(type: String): TextView {
        return TextView(this@PokemonActivity).apply {
            text = type.uppercase()
            setTypeface(typeface, Typeface.BOLD)
            layoutParams = LinearLayout.LayoutParams(128.px, 40.px).apply { marginEnd = 16.px }
            gravity = Gravity.CENTER
            background = GradientDrawable().apply {
                cornerRadius = 64f
                setColor(ContextCompat.getColor(this@PokemonActivity, getTypeColor(type)))
            }
        }
    }

    private fun buildTextViewNoEvolution(): TextView {
        return TextView(this@PokemonActivity).apply {
            text = getString(R.string.no_evolution)
            textSize = 20f
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = 16.px
                bottomMargin = 16.px
            }
            gravity = Gravity.CENTER_VERTICAL
        }
    }

    private fun buildLinearLayoutEvolution(evolution: Pokemon): LinearLayout {
        val imageViewEvolution = ImageView(this@PokemonActivity).apply {
            layoutParams = LinearLayout.LayoutParams(128.px, 128.px)
        }
        val textViewEvolutionName = TextView(this@PokemonActivity).apply {
            text = evolution.name
            textSize = 20f
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
            )
            gravity = Gravity.CENTER
        }
        val textViewEvolutionNumber = TextView(this@PokemonActivity).apply {
            text = evolution.number.toPokemonNumber()
            textSize = 20f
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
            )
            gravity = Gravity.CENTER
        }
        val linearLayoutEvolution = LinearLayout(this@PokemonActivity).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { bottomMargin = 16.px }
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            addView(imageViewEvolution)
            addView(textViewEvolutionName)
            addView(textViewEvolutionNumber)
        }
        Glide.with(binding.root).load(evolution.imageUrl).into(imageViewEvolution)
        return linearLayoutEvolution
    }

    @ColorRes
    private fun getTypeColor(type: String): Int {
        return when (type.uppercase()) {
            TYPE.GRASS.toString() -> R.color.green
            TYPE.POISON.toString() -> R.color.purple
            TYPE.FIRE.toString() -> R.color.red
            TYPE.FLYING.toString() -> R.color.cornflower_blue
            TYPE.WATER.toString() -> R.color.deep_sky_blue
            TYPE.BUG.toString() -> R.color.yellow_green
            TYPE.NORMAL.toString() -> R.color.light_slate_gray
            TYPE.ELECTRIC.toString() -> R.color.gold
            TYPE.GROUND.toString() -> R.color.burly_wood
            TYPE.FAIRY.toString() -> R.color.hot_pink
            TYPE.FIGHTING.toString() -> R.color.brown
            TYPE.PSYCHIC.toString() -> R.color.deep_pink
            TYPE.ROCK.toString() -> R.color.dark_khaki
            TYPE.STEEL.toString() -> R.color.slate_gray
            TYPE.ICE.toString() -> R.color.turquoise
            TYPE.GHOST.toString() -> R.color.dark_slate_blue
            TYPE.DRAGON.toString() -> R.color.blue_violet
            else -> R.color.secondTextColor
        }
    }

    internal enum class TYPE { GRASS, POISON, FIRE, FLYING, WATER, BUG, NORMAL, ELECTRIC, GROUND, FAIRY, FIGHTING, PSYCHIC, ROCK, STEEL, ICE, GHOST, DRAGON }

    companion object {
        const val POKEMON_EXTRA = "pokemon_extra"
    }
}