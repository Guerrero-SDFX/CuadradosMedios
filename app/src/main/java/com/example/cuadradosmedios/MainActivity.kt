package com.example.cuadradosmedios

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cuadradosmedios.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var x: Array<String>
    private lateinit var y: Array<String>
    private lateinit var r: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView: RecyclerView = findViewById(R.id.rv_nR)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val listaDeDatos: MutableList<String> = ArrayList()
        val adaptador = MiAdaptador(listaDeDatos)
        recyclerView.adapter = adaptador

        binding.btnGenerar.setOnClickListener {
            listaDeDatos.clear()
            var datosValidos = true
            val seed = binding.txtInputSeed.text.toString()
            val iteraciones = binding.txtInputNR.text.toString()

            binding.tiSeed.error = null
            binding.tiNR.error = null

            if (seed.length < 3) {
                binding.tiSeed.error = "Ingrese más de 3 dígitos"
                datosValidos = false
            }

            if (iteraciones.isEmpty()) {
                binding.tiNR.error = "Ingrese el número de iteraciones"
                datosValidos = false
            }

            if (datosValidos) {
                val d = seed.length
                val nI = iteraciones.toIntOrNull() ?: 0
                x = Array(nI+1) { "" }
                y = Array(nI+2) { "" }
                r = Array(nI+2) { "" }


                x[0] = seed
                r[0] = " "


                generarR(d, nI)

                for(i in 0 until nI){
                    listaDeDatos.add("Y$i=${y[i]} X${i+1}=${x[i+1]} R${i+1}=${r[i+1]}")
                }
                adaptador.notifyDataSetChanged()

                Toast.makeText(this, "Generación completada", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun generarR(d: Int, nI: Int) {
        for (i in 0 until nI) {
            val xInt = x[i].toLong()
            val yTemp: Long =xInt*xInt
            if(yTemp > 0){
                y[i] = yTemp.toString()
                var dY = y[i].length

                if (d % 2 == 0) {
                    if (dY % 2 != 0) {
                        y[i] = "0" + y[i]
                    }
                } else {
                    if (dY % 2 == 0) {
                        y[i] = "0" + y[i]
                    }
                }

                dY = y[i].length
                val index = (dY - d) / 2
                val xTemp = y[i].substring( index , index + d)
                var j = i+1
                if (j < nI+1) { // Verifica que j sea menor que nI
                    x[j] = xTemp
                    r[j] = "0.$xTemp"
                }
            } else {
                Toast.makeText(this, "Seed fuera de rango", Toast.LENGTH_SHORT).show()
            }

        }
    }
}