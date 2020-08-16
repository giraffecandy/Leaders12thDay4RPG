package app.babachan.tryinkconstitutiion

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Ink (
    open var id: String = UUID.randomUUID().toString(),
    @PrimaryKey open var makerName: String = "",
    open var inkColor: String = "",
    open var thickness: Float = 0f,
    open var stock: Int = 0,
    open var speed: String = "",
    open var month: Int = 0,
    open var dat: Int = 0,
    open var createdAt: Date = Date(System.currentTimeMillis())
): RealmObject()
