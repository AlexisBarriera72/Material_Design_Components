package com.alexisbarrierapacheco.materialdesigncomponents

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.URLUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.snackbar.Snackbar
import com.alexisbarrierapacheco.materialdesigncomponents.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //animation Fab
        binding.fab.setOnClickListener {
            if (binding.bottomAppBar.fabAlignmentMode == BottomAppBar.FAB_ALIGNMENT_MODE_CENTER) {
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END

            } else {
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            }
        }
        //ocultar cardView
        binding.contentMain.btnSkip.setOnClickListener { binding.contentMain.cvFirst.visibility = View.GONE }

        binding.bottomAppBar.setNavigationOnClickListener {
            Snackbar.make(binding.root, R.string.message_action_success, Snackbar.LENGTH_LONG)
                .setAnchorView(binding.fab)
                .show()
        }
        //Mostrando una imagen cargada de la Web en el componente imgCover usando una dirección URL
        binding.contentMain.etUrl.onFocusChangeListener = View.OnFocusChangeListener { _, focused ->
            var errorStr: String? = null
            val url = binding.contentMain.etUrl.text.toString()
            loadImage()
            if (!focused) {
                when {
                    url.isEmpty() -> {
                        //errorStr = getString(R.string.card_required)
                        binding.contentMain.tilUrl.error = getString(R.string.card_required)
                    }
                    URLUtil.isValidUrl(url) -> {
                        loadImage(url)
                    }
                    else -> {
                        errorStr = getString(R.string.card_invalid_url)
                    }
                }
            }
            binding.contentMain.tilUrl.error = errorStr
        }
        //CheckBox: habilita los botones de toggleGroup
        binding.contentMain.chbEnableToggle.setOnClickListener {
            binding.contentMain.toggleGroup.isEnabled = !binding.contentMain.toggleGroup.isEnabled
        }
        binding.contentMain.chbEnableToggle.isChecked
        //Eventos para los tres botones en toggleGroup. Cambia el del fondo
        binding.contentMain.toggleGroup.addOnButtonCheckedListener { _, checkedId, _ ->
            binding.contentMain.root.setBackgroundColor(
                when (checkedId) {
                    R.id.btnRed -> Color.RED
                    R.id.btnBlue -> Color.BLUE
                    else -> Color.GREEN
                }
            )
        }
        //Oculta/muestra el botón FAB
        binding.contentMain.swFab.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked) {
                button.text = getString(R.string.hide_fab)
                binding.fab.show()
            } else {
                button.text = getString(R.string.show_fab)
                binding.fab.hide()
            }
        }
        //Muestra el valor del slider cuando se modifica
        binding.contentMain.sldVol.addOnChangeListener { slider, value, fromUser ->
            val sliderVal = getString(R.string.slider_value) + " $value"
            Snackbar.make(binding.root, sliderVal, Snackbar.LENGTH_LONG)
                .setAnchorView(binding.fab)
                .show()
        }
        //Muestra el metodo de pago seleccionado
        binding.contentMain.rgCardPay.setOnCheckedChangeListener { group, i ->
            var str: String = getString(R.string.payment_selected)
            if (i == binding.contentMain.rbtnVisa.getId()) {
                str += binding.contentMain.rbtnVisa.text.toString()
            } else if (i == binding.contentMain.rbtnMastercard.getId())
                str += binding.contentMain.rbtnMastercard.text.toString()
            else str += binding.contentMain.rbtnPayPal.text.toString()

            Snackbar.make(binding.root, str, Snackbar.LENGTH_LONG)
                .setAnchorView(binding.contentMain.tvPaymentMethod)
                .show()
        }
    } //end OnCreate
    //Metodo que carga una imagen de la Web (URL)
    private fun loadImage(url:
                          String = "https://viajes.nationalgeographic.com.es/medio/2016/08/01/causa_95ca5ee7.jpg") {
        Glide.with(this)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(binding.contentMain.imgCover)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}//end
