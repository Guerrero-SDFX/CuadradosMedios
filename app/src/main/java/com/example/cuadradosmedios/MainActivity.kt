package com.example.cuadradosmedios

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cuadradosmedios.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //Los arreglos que se usarán para hacer las iteraciones
    private lateinit var x: Array<String>
    private lateinit var y: Array<String>
    private lateinit var r: Array<String>

    private var indice = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Variables para mostrar la lista
        val recyclerView: RecyclerView = findViewById(R.id.rv_nR)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val listaDeDatos: MutableList<String> = ArrayList()
        val adaptador = MiAdaptador(listaDeDatos)
        recyclerView.adapter = adaptador

        //Aqui se obtiene el botón generar
        binding.btnGenerar.setOnClickListener {
            listaDeDatos.clear() //Se limpia la lista de datos
            var datosValidos = true
            val seed = binding.txtInputSeed.text.toString()
            val iteraciones = binding.txtInputNR.text.toString()

            binding.tiSeed.error = null
            binding.tiNR.error = null

            if (seed.length < 4) {
                binding.tiSeed.error = "Ingrese más de 3 dígitos"
                datosValidos = false
            }

            if (iteraciones.isEmpty()) {
                binding.tiNR.error = "Ingrese el número de iteraciones"
                datosValidos = false
            }

            if (datosValidos) { //Si los datos son validos, realiza el procedimiento
                val d = seed.length //Se obtiene la semilla que de el usuario
                val nI = iteraciones.toIntOrNull() ?: 0
                //Se inicializan los arreglos
                x = Array(nI+1) { "" }
                y = Array(nI+2) { "" }
                r = Array(nI+2) { "" }
                //Se le da el valor a x[0] y r[0]
                x[0] = seed
                r[0] = " "

                generarR(d, nI) //se llama a una función que hace el procedimiento

                //Despues del procedimiento, simplemente se muestran los datos en una cadena
                for(i in 0 until indice+1){
                    listaDeDatos.add("Y$i=${y[i]} X${i+1}=${x[i+1]} R${i+1}=${r[i+1]}")
                }
                adaptador.notifyDataSetChanged()
                //Se muestra un mensaje que indica que la generación se ha completado
                Toast.makeText(this, "Generación completada", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun generarR(d: Int, nI: Int) {
        for (i in 0 until nI) {
            if(x[i] != "0000"){ //Verifica que la semilla no sea puro 0000
                val xInt = x[i].toLong()
                val yTemp: Long =xInt*xInt
                y[i] = yTemp.toString()
                var dY = y[i].length //Con esto se obtiene la longitud de Yi

                if (d % 2 == 0) { //Si la cantidad de digitos de la semilla es par
                    if (dY % 2 != 0) { //y la cantidad de digitos de Yi es impar
                        y[i] = "0" + y[i] //Se le añade un 0 a la izquierda
                    }
                } else { //Si la cantidad de digitos de la semilla es impar
                    if (dY % 2 == 0) { //Y la cantidad de digitos de Yi es par
                        y[i] = "0" + y[i] //Se le añade un 0 a la izquierda
                    }
                }

                dY = y[i].length
                val index = (dY - d) / 2 //Con esto se encuentra la cantidad sobrante de los extremos
                val xTemp = y[i].substring( index , index + d) //Y se corta la parte de en medio de la cadena
                var j = i+1
                if (j < nI+1) { // Verifica que j sea menor que nI
                    x[j] = xTemp
                    r[j] = "0.$xTemp" //se le añade un 0. al numero
                    indice = i
                }
            } else {
                Toast.makeText(this, "Fuera de rango", Toast.LENGTH_SHORT).show() //Si la semilla contiene 0, se indica que supero el rango
                break
            }

        }
    }
}