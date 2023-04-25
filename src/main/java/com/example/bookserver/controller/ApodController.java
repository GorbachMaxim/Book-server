package com.example.bookserver.controller;

import com.example.bookserver.dto.Apod;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;


@RestController
@RequestMapping("/api/apod")
@CrossOrigin
@RequiredArgsConstructor
public class ApodController {

    private static final String key = "kNAFiMT8pFXeAOvjaEDaTdCNGDmgEq1AMcubiL5n";
    private static final String urlString = "https://api.nasa.gov/planetary/apod?api_key=" + key;

    private static Apod apod;


    private static Date date;

    private static final long DIFFERENCE_IN_TIME = 43200000;

    @GetMapping
    public Apod getApod() throws IOException {

        Date currentDate = new Date();

        if (date == null)
            date = currentDate;

        long difference = currentDate.getTime() - date.getTime();
        System.out.println(difference);

        if (apod == null || difference > DIFFERENCE_IN_TIME) {
            date = currentDate;
            HttpURLConnection connection;
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            System.out.println(reader);
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }
            apod = new Gson().fromJson(buffer.toString(), Apod.class);
        }
        return apod;
    }

//    public static void getAverageColor() throws IOException {
//
//        HttpURLConnection connection;
//        URL url = new URL(urlString);
//        connection = (HttpURLConnection) url.openConnection();
//        connection.connect();
//        InputStream stream = connection.getInputStream();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
//        StringBuffer buffer = new StringBuffer();
//        System.out.println(reader);
//        String line = "";
//        while ((line = reader.readLine()) != null) {
//            buffer.append(line).append("\n");
//        }
//        apod = new Gson().fromJson(buffer.toString(), Apod.class);
//
//
//
//        URL imageUrl = new URL(apod.getUrl());
//        InputStream is = imageUrl.openStream();
//        OutputStream os = new FileOutputStream("images/image.jpg");
//
//        byte[] b = new byte[2048];
//        int length;
//
//        while ((length = is.read(b)) != -1) {
//            os.write(b, 0, length);
//        }
//
//        is.close();
//        os.close();
//
//
//
//        BufferedImage image = ImageIO.read(new File("images/image.jpg"));
//
//
//        int[] color = new int[3];
//        int x = (image.getWidth() * image.getHeight());
//
//
//
//
//        for (int i = 0; i < image.getHeight(); ++i){
//            for (int j = 0; j < image.getWidth(); ++j){
//                Color r = new Color(image.getRGB(j, i));
//                color[0] += r.getRed();
//                color[1] += r.getGreen();
//                color[2] += r.getBlue();
//            }
//        }
//        System.out.println(color[0]/x+ ":" + color[1]/x + ":" + color[2]/x);
//    }


}
