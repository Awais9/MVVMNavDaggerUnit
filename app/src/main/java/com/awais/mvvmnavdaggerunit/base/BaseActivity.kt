package com.awais.mvvmnavdaggerunit.base

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    private var _binding: VB? = null
    protected val binding: VB get() = _binding!!
    abstract val bindingInflater: (LayoutInflater) -> VB

    protected fun setBinding(){
        _binding = bindingInflater(layoutInflater)
    }
}