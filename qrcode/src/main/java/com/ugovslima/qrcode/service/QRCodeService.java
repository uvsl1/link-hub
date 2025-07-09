package com.ugovslima.qrcode.service;

import com.ugovslima.qrcode.util.QRCodeGenerator;
import org.springframework.stereotype.Service;

@Service
public class QRCodeService {

    public byte[] generateQRCodeImage(String text, int width, int height) throws Exception {
        return QRCodeGenerator.generateQRCodeImage(text, width, height);
    }
}
