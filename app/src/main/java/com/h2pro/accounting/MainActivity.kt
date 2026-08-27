package com.h2pro.accounting

import android.content.ContentValues
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.h2pro.accounting.data.H2Database

class MainActivity : AppCompatActivity() {
    private val db by lazy { H2Database(this).writableDatabase }
    private val pad = 24

    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); showLogin() }

    private fun base() = LinearLayout(this).apply { orientation=LinearLayout.VERTICAL; gravity=Gravity.CENTER; setPadding(pad,pad,pad,pad); layoutDirection=LinearLayout.LAYOUT_DIRECTION_RTL }
    private fun field(h: String, number:Boolean=false) = EditText(this).apply { hint=h; inputType=if(number) InputType.TYPE_CLASS_NUMBER else InputType.TYPE_CLASS_TEXT; setPadding(16,12,16,12) }
    private fun title(t:String) = TextView(this).apply { text=t; textSize=25f; gravity=Gravity.CENTER; setPadding(0,0,0,24) }

    private fun showLogin() {
        val r=base(); r.addView(title("H2 Pro\nالنظام المحاسبي")); val y=field("السنة المالية",true); val u=field("رقم المستخدم",true); val p=field("كلمة المرور").apply{inputType=129}; r.addView(y);r.addView(u);r.addView(p)
        r.addView(Button(this).apply{text="دخول";setOnClickListener{if(y.text.isNullOrBlank()||u.text.isNullOrBlank()||p.text.isNullOrBlank()) Toast.makeText(this@MainActivity,"أكمل بيانات الدخول",Toast.LENGTH_SHORT).show() else showDashboard()}})
        r.addView(Button(this).apply{text="إلغاء";setOnClickListener{finish()}});setContentView(r)
    }
    private fun showDashboard(){val r=base();r.addView(title("H2 Pro\nلوحة التحكم")); listOf("تهيئة النظام" to ::showSetup,"دليل الحسابات" to ::accounts,"الأصناف والمخزون" to ::coming,"العملاء والموردون" to ::coming,"المبيعات والمشتريات" to ::coming,"القيود والتقارير" to ::coming,"المستخدمون والإعدادات" to ::coming).forEach{(s,a)->r.addView(Button(this).apply{text=s;setOnClickListener{a()}})};setContentView(r)}
    private fun showSetup(){val r=base();r.addView(title("تهيئة النظام"));r.addView(Button(this).apply{text="بيانات السنة المالية";setOnClickListener{financialYear()}});r.addView(Button(this).apply{text="بيانات الشركة";setOnClickListener{companyData()}});r.addView(Button(this).apply{text="المناطق";setOnClickListener{coming("المناطق")}});r.addView(Button(this).apply{text="رجوع";setOnClickListener{showDashboard()}});setContentView(r)}
    private fun financialYear(){val r=base();r.addView(title("بيانات السنة المالية"));val y=field("السنة المالية",true);val s=field("تاريخ البداية");val e=field("تاريخ النهاية");r.addView(y);r.addView(s);r.addView(e);r.addView(Button(this).apply{text="حفظ";setOnClickListener{if(y.text.isNullOrBlank())return@setOnClickListener;val v=ContentValues().apply{put("year",y.text.toString().toInt());put("start_date",s.text.toString());put("end_date",e.text.toString())};db.insert("financial_year",null,v);Toast.makeText(this@MainActivity,"تم حفظ السنة المالية",Toast.LENGTH_SHORT).show()}});r.addView(Button(this).apply{text="رجوع";setOnClickListener{showSetup()}});setContentView(r)}
    private fun companyData(){val r=base();r.addView(title("بيانات الشركة"));val n=field("اسم الشركة");val p=field("رقم الهاتف");val a=field("العنوان");r.addView(n);r.addView(p);r.addView(a);r.addView(Button(this).apply{text="حفظ";setOnClickListener{if(n.text.isNullOrBlank())return@setOnClickListener;val v=ContentValues().apply{put("name",n.text.toString());put("phone",p.text.toString());put("address",a.text.toString())};db.insert("company",null,v);Toast.makeText(this@MainActivity,"تم حفظ بيانات الشركة",Toast.LENGTH_SHORT).show()}});r.addView(Button(this).apply{text="رجوع";setOnClickListener{showSetup()}});setContentView(r)}
    private fun accounts(){val r=base();r.addView(title("دليل الحسابات"));val code=field("رمز الحساب");val name=field("اسم الحساب");val type=field("نوع الحساب");r.addView(code);r.addView(name);r.addView(type);r.addView(Button(this).apply{text="إضافة حساب";setOnClickListener{if(code.text.isNullOrBlank()||name.text.isNullOrBlank())return@setOnClickListener;val v=ContentValues().apply{put("code",code.text.toString());put("name",name.text.toString());put("type",type.text.toString().ifBlank{"عام"});put("level",1)};db.insert("accounts",null,v);Toast.makeText(this@MainActivity,"تمت إضافة الحساب",Toast.LENGTH_SHORT).show()}});r.addView(Button(this).apply{text="رجوع";setOnClickListener{showDashboard()}});setContentView(r)}
    private fun coming(){coming("هذه الشاشة")}; private fun coming(s:String)=Toast.makeText(this,"سيتم تطوير $s لاحقًا",Toast.LENGTH_SHORT).show()
}
