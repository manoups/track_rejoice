package one.breece.track_rejoice.command.service.impl

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;


class QRCodeGeneratorUtil {
    companion object {
        @Throws(WriterException::class, IOException::class)
        fun generateQRCodeImage(text: String, width: Int, height: Int, filePath: String) {
            val qrCodeWriter = QRCodeWriter()
            val bitMatrix: BitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height)

            val path: Path = FileSystems.getDefault().getPath(filePath)
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path)
        }


        @Throws(WriterException::class, IOException::class)
        fun getQRCodeImage(text: String, width: Int, height: Int): ByteArray {
            val qrCodeWriter = QRCodeWriter()
            val bitMatrix: BitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height)

            val pngOutputStream = ByteArrayOutputStream()
//            val con = MatrixToImageConfig(-0xfffffe, -0x3fbf)

            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream)
            val pngData: ByteArray = pngOutputStream.toByteArray()
            return pngData
        }
    }
}