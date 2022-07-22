package com.zjh.java.captchaTest;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
public class CaptchaController {
//    @Qualifier("captchaProducer")
//    @Autowired
//    DefaultKaptcha defaultKaptcha;

//    @GetMapping("/img")
//    public void captchaImg(HttpServletResponse resp) throws IOException {
//        String text = defaultKaptcha.createText();
//        BufferedImage image = defaultKaptcha.createImage(text);
//        ImageIO.write(image,"jpg",resp.getOutputStream());
//
//    }

    @GetMapping("/session")
    public void hello (HttpSession session){
        System.out.println("session.getAttribute(\"kaptchaCode\") = " + session.getAttribute("kaptchaCode"));
    }
}
