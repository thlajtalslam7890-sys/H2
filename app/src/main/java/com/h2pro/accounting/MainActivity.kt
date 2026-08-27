package com.h2pro.accounting

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val prefs by lazy { getSharedPreferences("h2pro", Context.MODE_PRIVATE) }
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

    private fun field(hint: String, number: Boolean = false): EditText = EditText(this).apply {
        this.hint = hint
        inputType = if (number) InputType.TYPE_CLASS_NUMBER else InputType.TYPE_CLASS_TEXT
        setPadding(16, 12, 16, 12)
    }

    private fun showLogin() {
        val root = base()
        val title = TextView(this).apply {
            text = "H2 Pro\nالنظام المحاسبي"
            textSize = 28f
            gravity = Gravity.CENTER
            setPadding(0, 0, 0, 32)
        }
        val year = field("السنة المالية", true)
        val user = field("رقم المستخدم", true)
        val password = field("كلمة المرور").apply { inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD }
        val login = Button(this).apply { text = "دخول" }
        val cancel = Button(this).apply { text = "إلغاء" }
        root.addView(title)
        root.addView(year)
        root.addView(user)
        root.addView(password)
        root.addView(login)
        root.addView(cancel)
        login.setOnClickListener {
            if (year.text.isNullOrBlank() || user.text.isNullOrBlank() || password.text.isNullOrBlank()) {
                Toast.makeText(this, "أكمل بيانات الدخول", Toast.LENGTH_SHORT).show()
            } else showDashboard()
        }
        cancel.setOnClickListener { finish() }
        setContentView(root)
    }

    private fun showDashboard() {
        val root = base()
        root.addView(TextView(this).apply {
            text = "H2 Pro\nلوحة التحكم"
            textSize = 26f
            gravity = Gravity.CENTER
            setPadding(0, 0, 0, 24)
        })
        val buttons = listOf(
            "تهيئة النظام" to { showSetup() },
            "دليل الحسابات" to { info("دليل الحسابات") },
            "الأصناف والمخزون" to { info("الأصناف والمخزون") },
            "العملاء والموردون" to { info("العملاء والموردون") },
            "المبيعات والمشتريات" to { info("المبيعات والمشتريات") },
            "القيود والتقارير" to { info("القيود والتقارير") },
            "المستخدمون والإعدادات" to { info("المستخدمون والإعدادات") }
        )
        buttons.forEach { (label, action) ->
            root.addView(Button(this).apply { text = label; setOnClickListener { action() } })
        }
        setContentView(root)
    }

    private fun showSetup() {
        val root = base()
        root.addView(TextView(this).apply { text = "تهيئة النظام"; textSize = 26f; gravity = Gravity.CENTER; setPadding(0,0,0,24) })
        root.addView(Button(this).apply { text = "بيانات السنة المالية"; setOnClickListener { financialYear() } })
        root.addView(Button(this).apply { text = "بيانات الشركة"; setOnClickListener { companyData() } })
        root.addView(Button(this).apply { text = "المناطق"; setOnClickListener { info("المناطق") } })
        root.addView(Button(this).apply { text = "رجوع"; setOnClickListener { showDashboard() } })
        setContentView(root)
    }

    private fun financialYear() {
        val root = base()
        root.addView(TextView(this).apply { text = "بيانات السنة المالية"; textSize = 24f; gravity = Gravity.CENTER })
        val year = field("السنة المالية", true)
        val start = field("تاريخ البداية")
        val end = field("تاريخ النهاية")
        year.setText(prefs.getString("year", "")); start.setText(prefs.getString("start", "")); end.setText(prefs.getString("end", ""))
        root.addView(year); root.addView(start); root.addView(end)
        root.addView(Button(this).apply { text = "حفظ"; setOnClickListener { prefs.edit().putString("year",year.text.toString()).putString("start",start.text.toString()).putString("end",end.text.toString()).apply(); Toast.makeText(this@MainActivity,"تم حفظ السنة المالية",Toast.LENGTH_SHORT).show() } })
        root.addView(Button(this).apply { text = "رجوع"; setOnClickListener { showSetup() } })
        setContentView(root)
    }

    private fun companyData() {
        val root = base()
        root.addView(TextView(this).apply { text = "بيانات الشركة"; textSize = 24f; gravity = Gravity.CENTER })
        val name = field("اسم الشركة"); val phone = field("رقم الهاتف"); val address = field("العنوان")
        name.setText(prefs.getString("company_name", "")); phone.setText(prefs.getString("company_phone", "")); address.setText(prefs.getString("company_address", ""))
        root.addView(name); root.addView(phone); root.addView(address)
        root.addView(Button(this).apply { text = "حفظ"; setOnClickListener { prefs.edit().putString("company_name",name.text.toString()).putString("company_phone",phone.text.toString()).putString("company_address",address.text.toString()).apply(); Toast.makeText(this@MainActivity,"تم حفظ بيانات الشركة",Toast.LENGTH_SHORT).show() } })
        root.addView(Button(this).apply { text = "رجوع"; setOnClickListener { showSetup() } })
        setContentView(root)
    }

    private fun info(title: String) = Toast.makeText(this, "سيتم تطوير شاشة $title في المرحلة التالية", Toast.LENGTH_SHORT).show()
}
