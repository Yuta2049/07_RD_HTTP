import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Service {

    String cookie;
    //CookieStore cookieStore;
    List<String> cookies;


    public void printCoordinates() throws Exception {

        //URL urlForGet = new URL("https://yandex.ru/");
        URL urlForGet = new URL("https://yandex.ru/maps");


        URLConnection connectionForGet = urlForGet.openConnection();

        cookie = "ys=wprid.1544517454997127-1786455002022994223708365-vla1-4308; device_id=a764f43e6b9ac7c9333178644248d45c349bd8b09; sessionid2=3:1544430062.5.0.1541581077717:OkuFTw:87.1|55985981.0.2|1130000023085575.-1.0|191724.567069.YePYLU12gkA61vbqMu28mJ7f9Sg; Session_id=3:1544430062.5.0.1541581077717:OkuFTw:87.1|55985981.425212.2.2:425212|1130000023085575.425224.2.2:425224|191724.117385.-ntAbLiPVwTWFD2JcarFQFnCcbg; yp=1547109456.shlos.0#1544684806.ysl.1#1858840647.udn.cDpSZW5kYWxpbmE%3D#1857366271.multib.1#1546153618.csc.1#1544612189.szm.1:1920x1080:1858x1003#1544520343.nps.2514436709%3Aclose; zm=m-white_bender.css-https%3Awww_ufL7szPUIbW7180WrVaRjXhzbbo%3Al; yc=1543734406.zen.cach%3A1543478802; _ym_isad=2; L=R1V5flkNUQ5HW3sAQwN5AWZ3BnhDVltyZDQgKCshXgwQ.1543480647.13699.316942.8dc50dd9a02a73757098b51db57c6f74; _ym_d=1543475203; yandexuid=3578600651541576481; mda=0; fuid01=5be2a5ed265de361.5RzgvkTxnaKaOHpsHmcathLh3MlIgDmey68uYW_Yzw1EA6l7-FWzf1_2XXTh5_1Z6Wo6o59uUCu3fr8fdjWJdHlB6Oq6Vn3jGRNSJTFu6qWeN8TXhsRvh7T3SfU4SghZ; my=YwA=; _ym_uid=1541577879117453291; yabs-frequency=/4/0m0100fj3bpFblzR/tbMmS0WpOS7Iucu8Co0WVN9m23D0/; i=T5QFcWAmzvasBEBNnOa6tNqBy0IN8pmIdr1gXRl1MEfuaFKuZELu9pZCScDCq4XYLzVX0BIotOtu5B4XI4ZZR4azEEE=; yandex_login=Rendalina; yabs-sid=175236661544512059";

        /*for (String cookie : cookies) {
            connectionForGet.addRequestProperty("Cookie", cookie.split(";", 2)[0]);
        }*/
        connectionForGet.addRequestProperty("Cookie", cookie);


        System.out.println("URL: " + urlForGet);
        System.out.println();

        printHeaders(connectionForGet);

        String body = printBody(connectionForGet);

        int pos1 = body.indexOf("\"csrfToken\":\"")+13;
        int pos2 = body.indexOf("\"", pos1);
        String csrfToken = body.substring(pos1, pos2);
        System.out.println();
        System.out.println("csrfToken: "+csrfToken);


        //System.out.println("Получили куки: ");
        //System.out.println(cookie);


        //yandexuid=9926425281544451325;
        //"csrfToken":"de1f2622ab7ead2d5975973922d2c78a9a775331:1544451325216"
        //"csrfToken":"2b6e354918d8a909f3d92666cec54a90f1bf0df9:1544511798778"
//        csrfToken: 9979eaf98e1dbe243fb98c5da877bd2f369fa82d:1544518241372

        //https://yandex.ru/maps/api/search?text=Ижевск, карла маркса, 246&lang=ru_RU&csrfToken=<csrfToken>


        System.out.println();
        System.out.println("Адрес для GET-запроса с токеном:");

        String address = URLEncoder.encode("Ижевск, Советская 49", "UTF-8");

        URL url2 = new URL("https://yandex.ru/maps?text="+address+"&lang=ru_RU&csrfToken="+csrfToken);


        //String address = "https://yandex.ru/maps?text=Ижевск,%20Советская%2049&lang=ru_RU&csrfToken="+csrfToken;
        //String address = "https://yandex.ru/maps?text=%D0%98%D0%B6%D0%B5%D0%B2%D1%81%D0%BA%2C%20%D0%A1%D0%BE%D0%B2%D0%B5%D1%82%D1%81%D0%BA%D0%B0%D1%8F%2049&lang=ru_RU&csrfToken="+csrfToken;
        //%D0%98%D0%B6%D0%B5%D0%B2%D1%81%D0%BA%2C%20%D0%A1%D0%BE%D0%B2%D0%B5%D1%82%D1%81%D0%BA%D0%B0%D1%8F%2049
        //https://yandex.ru/maps?text=Ижевск, Советская 49&csrfToken=f8d76406ea1b2d30cb992ea772213398642fbd
        //https://yandex.ru/maps?text=Ижевск,%20Советская%2049&csrfToken=f8d76406ea1b2d30cb992ea772213398642fbd


        System.out.println(url2);
        //urlForGet = new URL(address);
        connectionForGet = url2.openConnection();
        connectionForGet.addRequestProperty("Cookie", cookie);
        String responceBody = printBody(connectionForGet);

        //"coordinates":[53.237667,56.848355],
        //56.848355, 53.237667

        //String regExp = "\"coordinates\":\\[\\d\\d";
        String regExp = "\"coordinates\":";

        Pattern pattern = Pattern.compile(regExp);

        Matcher matcher = pattern.matcher(responceBody);

        matcher.find();
        int position = matcher.start();
        String coord1 = responceBody.substring(position+15, position+24);
        String coord2 = responceBody.substring(position+25, position+34);

        System.out.println("Координаты: "+coord2+", "+coord1);


//        Map<String, Object> params = new LinkedHashMap<>();
//        String urlForShortening = "https://docs.oracle.com/javase/8/docs/";
//        params.put("url", urlForShortening);
//
//        StringBuilder postData = new StringBuilder();
//
//        for (Map.Entry<String, Object> param : params.entrySet()) {
//            if (postData.length() != 0) postData.append('&');
//            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
//            postData.append('=');
//            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
//        }
//
//        byte[] postDataBytes = postData.toString().getBytes("UTF-8");


//        printHeaders(connectionForGet);

  //      printBody(connectionForGet);


    }

    private void printHeaders(URLConnection connection) {
        System.out.println("ЗАГОЛОВКИ: ");

        Map<String, java.util.List<String>> map;
        map = connection.getHeaderFields();

        for (Map.Entry<String, java.util.List<String>> param : map.entrySet()) {
            System.out.println(param.getKey() + " " + param.getValue());
        }
        System.out.println();

        // Grab Set-Cookie headers:
        //cookies = connection.getHeaderFields().get("Set-Cookie");

    }

    /*private void printHeaders(URLConnection connection) {
        System.out.println("ЗАГОЛОВКИ: ");

        Map<String, java.util.List<String>> map;
        map = connection.getHeaderFields();

        for (Map.Entry<String, java.util.List<String>> param : map.entrySet()) {
            System.out.println(param.getKey() + " " + param.getValue());
        }
        System.out.println();


        cookie = connection.getHeaderField("Set-Cookie");

    }*/


    private String printBody(URLConnection connection) {

        System.out.println("СОДЕРЖИМОЕ: ");

        try (InputStream inputStream = connection.getInputStream()) {

            Reader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            StringBuilder body = new StringBuilder();

            for (int c; (c = in.read()) >= 0; ) {
                System.out.print((char) c);
                body.append((char) c);
            }
            System.out.println();
            return body.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();

        return null;
    }


    /*private String getYandexUID(URLConnection connection) {

        Map<String, java.util.List<String>> map;
        map = connection.getHeaderFields();


        String cookie = connection.getHeaderField("Set-Cookie");

        for (Map.Entry<String, java.util.List<String>> param : map.entrySet()) {
            if (param.getKey() != null) {
                if (param.getKey().equals("Set-Cookie")) {
                    System.out.println("Нашли Куки");
                    List cookies = param.getValue();
                    for (Object currentCookie : cookies) {
                        System.out.println(currentCookie);
                        //if (currentCookie.toString().substring(0, 8) == "yandexuid") {

                        }
                    }
                }
            }
        }
        return null;
    }*/
}
