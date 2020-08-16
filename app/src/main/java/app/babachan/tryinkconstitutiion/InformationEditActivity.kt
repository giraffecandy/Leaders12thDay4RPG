package app.babachan.tryinkconstitutiion

import android.app.Activity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner

class InformationEditActivity: Activity() {

    //Realmの変数を宣言
    private val realm: Realm = Realm.getDefaultInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information_edit)

        val spinner: Spinner = findViewById<Spinner>(R.id.spinner)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.list,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val spinner2: Spinner = findViewById<Spinner>(R.id.spinner2)
        val adapter2 = ArrayAdapter.createFromResource(
            this,
            R.array.colorList,
            android.R.layout.simple_spinner_item
        )
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner2.adapter = adapter2

        //inkという変数に取得したデータを代入
        val ink: Ink? = read()

        if (ink != null) {
            makerNameEditText.setText(ink.makerName)
            // inkColorEditText.setText(ink.inkColor)
            thicknessEditText.setText("" + ink.thickness)
            stockEditText.setText("" + ink.stock)
        }
        var inkColor: String = "Red"
        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                inkColor = adapter2.getItem(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        var speed: String = "3か月"
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                speed = adapter.getItem(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        saveButton.setOnClickListener {
            //          if (realm.where(Ink::class.java).equalTo("id", id) == null) {
            val makerName: String = makerNameEditText.text.toString()
            // val inkColor: String = inkColorEditText.text.toString()
            val thickness: String = thicknessEditText.text.toString()
            val stock: String = stockEditText.text.toString()

//            println(speed).toString()
            //現在時刻の取得
            val calendar: Calendar =
                Calendar.getInstance()
            //月の取得
            //calendar.get(Calendar.MONTH) == Calendar.MARCH
            val month: Int = calendar.get(Calendar.MONTH)
            //日の取得
            val dat: Int = calendar.get(Calendar.DAY_OF_MONTH)
            //calendar->data->stringに変更
            println(month)
            println(dat)

            save(
                makerName,
                inkColor,
                thickness,
                stock,
                speed,
                month,
                dat
            )
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            // finish()

            //          } else if (realm.where(Ink::class.java).equalTo("id", id) != null){
            //        //データの挿入
            fun update(
                id: String,
                makerName: String,
                inkColor: String,
                thickness: String,
                stock: String,
                speed: String
            ) {
                realm.executeTransaction {
                    val ink: Ink = realm.where(Ink::class.java).equalTo("id", id).findFirst()
                        ?: return@executeTransaction
                    ink.makerName = makerName
                    ink.inkColor = inkColor
                    ink.thickness = thickness.toFloat()
                    ink.stock = stock.toInt()
                    ink.speed = spinner.toString()

                }
            }

        }
        deleteButton.setOnClickListener {

            if (ink != null) {
                realm.executeTransaction {
                    ink.deleteFromRealm()
                }
            }
        }

    }

    //画面終了時にRealmを終了する
    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    //メソッド名readと返り値の型Ink?を指定
    private fun read(): Ink? {
        //Inkリストからデータを取得
        return realm.where(Ink::class.java).findFirst()
    }

    fun save(
        makerName: String,
        inkColor: String,
        thickness: String,
        stock: String,
        speed: String,
        month: Int,
        dat: Int
    ) {
        realm.executeTransaction {
            val ink = it.createObject(Ink::class.java, UUID.randomUUID().toString())
            ink.makerName = makerName
            ink.inkColor = inkColor
            ink.thickness = thickness.toFloat()
            ink.stock = stock.toInt()
            ink.speed = speed
            ink.month = month
            ink.dat = dat
        }
    }

}