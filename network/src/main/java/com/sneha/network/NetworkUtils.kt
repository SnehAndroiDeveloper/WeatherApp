package com.sneha.network

import android.util.Base64
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.nio.charset.Charset
import java.security.InvalidKeyException
import java.security.KeyFactory
import java.security.NoSuchAlgorithmException
import java.security.PublicKey
import java.security.spec.InvalidKeySpecException
import java.security.spec.X509EncodedKeySpec
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException

/**
 * @author Created by Sneha on 15/08/2024
 */
object NetworkUtils {
    const val NETWORK_TIMEOUT_TIME = 100L
    val gson = Gson()

    fun encryptWithPublicKey(textToEncrypt: String, publicKeyText: String): String {
        try {
            val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
            val publicKeyBytes = Base64.decode(publicKeyText, Base64.DEFAULT)
            var text = String(publicKeyBytes, Charset.defaultCharset())
            text = text.replace("-----BEGIN PUBLIC KEY-----", "")
            text = text.replace("-----END PUBLIC KEY-----", "")
            val kf = KeyFactory.getInstance("RSA")
            val keySpec = X509EncodedKeySpec(Base64.decode(text, Base64.DEFAULT))
            val publicKey = kf.generatePublic(keySpec) as PublicKey
            cipher.init(Cipher.ENCRYPT_MODE, publicKey)
            val encryptedBytes = cipher.doFinal(textToEncrypt.toByteArray())
            return String(
                Base64.encode(encryptedBytes, Base64.DEFAULT)
            ).replace("(\\r|\\n)".toRegex(), "")
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: NoSuchPaddingException) {
            e.printStackTrace()
        } catch (e: InvalidKeySpecException) {
            e.printStackTrace()
        } catch (e: InvalidKeyException) {
            e.printStackTrace()
        } catch (e: IllegalBlockSizeException) {
            e.printStackTrace()
        } catch (e: BadPaddingException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    //convert a data class to a map
    fun <T> T.serializeToMap(): Map<String, String> {
        return convert()
    }

    //convert a map to a data class
    inline fun <reified T> Map<String, String>.toDataClass(): T {
        return convert()
    }

    //convert an object of type I to type O
    inline fun <I, reified O> I.convert(): O {
        val json = gson.toJson(this)
        return gson.fromJson(json, object : TypeToken<O>() {}.type)
    }
}