package com.nieelz.danielstoryapp.custom

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

class EmailEditText : TextInputLayout {

    private lateinit var textEmail: EditText
    var valid = false

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }


    private fun init() {
        addOnEditTextAttachedListener {
            textEmail = it.editText!!

            textEmail.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (p0!!.isEmpty()) {
                        it.error = "insert email"
                    } else {
                        it.error = null
                        valid = true
                    }
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })
        }

    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        hint = "Email"
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }


}