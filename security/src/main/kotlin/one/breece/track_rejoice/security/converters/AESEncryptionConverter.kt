package one.breece.track_rejoice.security.converters
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import org.springframework.beans.factory.annotation.Value
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

@Converter
class AESEncryptionConverter : AttributeConverter<String, String> {
    @Value("\${encryption.secret}")
    private lateinit var secretKey: String
    private val aesCipher = "AES/ECB/PKCS5Padding"

    override fun convertToDatabaseColumn(attribute: String?): String? {
        return attribute?.let {
            val cipher = Cipher.getInstance(aesCipher)
            cipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(secretKey.toByteArray(), "AES"))
            Base64.getEncoder().encodeToString(cipher.doFinal(it.toByteArray()))
        }
    }

    override fun convertToEntityAttribute(dbData: String?): String? {
        return dbData?.let {
            val cipher = Cipher.getInstance(aesCipher)
            cipher.init(Cipher.DECRYPT_MODE, SecretKeySpec(secretKey.toByteArray(), "AES"))
            String(cipher.doFinal(Base64.getDecoder().decode(it)))
        }
    }
}

