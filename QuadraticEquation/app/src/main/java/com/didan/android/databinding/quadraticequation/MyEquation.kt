package com.didan.android.databinding.quadraticequation

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.didan.android.databinding.quadraticequation.databinding.ActivityMainBinding
import kotlin.math.sqrt

class MyEquation(var binding: ActivityMainBinding) : BaseObservable() {

    @Bindable
    var a: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.a)
        }

    @Bindable
    var b: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.b)
        }

    @Bindable
    var c: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.c)
        }

    fun solveEquation(view: View) {
        var a = a.toInt()
        var b = b.toInt()
        var c = c.toInt()

        var discriminant = b * b - 4 * a * c
        val result: String = if (discriminant > 0) {
            val root1 = (-b + sqrt(discriminant.toDouble())) / (2 * a)
            val root2 = (-b - sqrt(discriminant.toDouble())) / (2 * a)
            "X1: %.2f, X2: %.2f".format(root1, root2)
        } else if (discriminant == 0) {
            val root = -b / (2 * a)
            "X: %.2f".format(root)
        } else {
            "No real roots"
        }

        binding.resultText.text = result
    }
}