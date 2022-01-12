package global.ui.navigation.router

class Bundle {

    private val values = mutableMapOf<String, Any?>()

    fun clear() = values.clear()

    fun remove(key: String) {
        values.remove(key)
    }

    fun putAll(bundle: Bundle) = values.putAll(bundle.values)

    val size: Int = values.size

    fun putByte(key: String, value: Byte) {
        values[key] = value
    }

    fun putChar(key: String, value: Char) {
        values[key] = value
    }

    fun putShort(key: String, value: Short) {
        values[key] = value
    }

    fun putFloat(key: String, value: Float) {
        values[key] = value
    }

    fun putCharSequence(key: String, value: CharSequence) {
        values[key] = value
    }

    fun putByteArray(key: String, value: ByteArray?) {
        values[key] = value
    }

    fun putShortArray(key: String, value: ShortArray?) {
        values[key] = value
    }

    fun putCharArray(key: String, value: CharArray?) {
        values[key] = value
    }

    fun putFloatArray(key: String, value: FloatArray?) {
        values[key] = value
    }

    fun putCharSequenceArray(key: String, value: Array<CharSequence?>?) {
        values[key] = value
    }

    fun putBundle(key: String, value: Bundle?) {
        values[key] = value
    }

    fun getByte(key: String): Byte? = values[key] as Byte?

    fun getByte(key: String, defaultValue: Byte): Byte = values[key] as Byte? ?: defaultValue

    fun getChar(key: String): Char? = values[key] as Char?

    fun getChar(key: String, defaultValue: Char): Char = values[key] as Char? ?: defaultValue

    fun getShort(key: String): Short? = values[key] as Short?

    fun getShort(key: String, defaultValue: Short): Short = (values[key] as Short?) ?: defaultValue
/*
    fun getFloat(key: String): Float {
        return super.getFloat(key)
    }

    fun getFloat(key: String, defaultValue: Float): Float {
        return super.getFloat(key, defaultValue)
    }

    fun getCharSequence(key: String): CharSequence {
        return super.getCharSequence(key)
    }

    fun getCharSequence(key: String, defaultValue: CharSequence?): CharSequence {
        return super.getCharSequence(key, defaultValue)
    }

    fun getIntegerArrayList(key: String): ArrayList<Int> {
        return super.getIntegerArrayList(key)
    }

    fun getStringArrayList(key: String): ArrayList<String> {
        return super.getStringArrayList(key)
    }

    fun getCharSequenceArrayList(key: String): ArrayList<CharSequence> {
        return super.getCharSequenceArrayList(key)
    }

    fun getByteArray(key: String): ByteArray {
        return super.getByteArray(key)
    }

    fun getShortArray(key: String): ShortArray {
        return super.getShortArray(key)
    }

    fun getCharArray(key: String): CharArray {
        return super.getCharArray(key)
    }

    fun getFloatArray(key: String): FloatArray {
        return super.getFloatArray(key)
    }*/

    fun getBundle(key: String): Bundle? = values[key] as Bundle?
}