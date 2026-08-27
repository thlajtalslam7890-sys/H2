package com.h2pro.accounting

import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val pad = 24

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showLogin()
    }

    private fun base(): LinearLayout = LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL
        gravity = Gravity.CENTER
        setPadding(pad, pad, pad, pad)
        layoutDirection = LinearLayout.LAYOUT_DIRECTION_RTL
    }

    private fun showLogin() {
        val root = base()
        val title = TextView(this).apply {
            text = "H2 Pro\nالنظام المحاسبي"
            textSize = 28f
            gravity = Gravity.CENTER
            setPadding(0, 0, 0, 32)
        }
        val year = EditText(this).apply { hint = "السنة المالية"; inputType = 2 }
        val user = EditText(this).apply { hint = "رقم المستخدم"; inputType = 2 }
        val password = EditText(this).apply { hint = "كلمة المرور"; inputType = 0x81 }
        val login = Button(this).apply { text = "دخول" }
        val cancel = Button(this).apply { text = "إلغاء" }
        root.addView(title)
        root.addView(year, LinearLayout.LayoutParams(-1, -2))
        root.addView(user, LinearLayout.LayoutParams(-1, -2))
        root.addView(password, LinearLayout.LayoutParams(-1, -2))
        root.addView(login)
        root.addView(cancel)
        login.setOnClickListener { showDashboard() }
        cancel.setOnClickListener { finish() }
        setContentView(root)
    }

    private fun showDashboard() {
        val root = base()
        val title = TextView(this).apply {
            text = "H2 Pro\nلوحة التحكم"
            textSize = 26f
            gravity = Gravity.CENTER
            setPadding(0, 0, 0, 24)
        }
        root.addView(title)
        val buttons = listOf(
            "تهيئة النظام", "دليل الحسابات", "الأصناف والمخزون",
            "العملاء والموردون", "المبيعات والمشتريات", "القيود والتقارير", "المستخدمون والإعدادات"
        )
        buttons.forEach { label ->
            root.addView(Button(this).apply {
                text = label
                setOnClickListener { Toast.makeText(this@MainActivity, "سيتم تطوير شاشة $label في المرحلة التالية", Toast.LENGTH_SHORT).show() }
            }, LinearLayout.LayoutParams(-1, -2))
        }
        setContentView(root)
    }
}
