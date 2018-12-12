import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Service {

    public void printCoordinates() throws Exception {

        URL urlForGet = new URL("https://yandex.ru/maps");

        URLConnection connectionForGet = urlForGet.openConnection();

        String cookie = "ys=wprid.1544609848637180-92994030429291809006919-vla1-0265-p2; device_id=a764f43e6b9ac7c9333178644248d45c349bd8b09; sessionid2=3:1544430062.5.0.1541581077717:OkuFTw:87.1|55985981.0.2|1130000023085575.-1.0|191724.567069.YePYLU12gkA61vbqMu28mJ7f9Sg; Session_id=3:1544430062.5.0.1541581077717:OkuFTw:87.1|55985981.425212.2.2:425212|1130000023085575.425224.2.2:425224|191724.117385.-ntAbLiPVwTWFD2JcarFQFnCcbg; yp=1547201849.shlos.0#1544684806.ysl.1#1858840647.udn.cDpSZW5kYWxpbmE%3D#1857366271.multib.1#1546153618.csc.1#1560378247.szm.1:1920x1080:1858x853; zm=m-white_404.css%3Awww_frdeGanV3_VkhWv6WoT1k6rUEKY%3Al; yc=1543734406.zen.cach%3A1543478802; _ym_isad=2; L=R1V5flkNUQ5HW3sAQwN5AWZ3BnhDVltyZDQgKCshXgwQ.1543480647.13699.316942.8dc50dd9a02a73757098b51db57c6f74; _ym_d=1543475203; yandexuid=3578600651541576481; mda=0; fuid01=5be2a5ed265de361.5RzgvkTxnaKaOHpsHmcathLh3MlIgDmey68uYW_Yzw1EA6l7-FWzf1_2XXTh5_1Z6Wo6o59uUCu3fr8fdjWJdHlB6Oq6Vn3jGRNSJTFu6qWeN8TXhsRvh7T3SfU4SghZ; my=YwA=; _ym_uid=1541577879117453291; yabs-frequency=/4/0m0100fj3bpFblzR/urMmS0mp8TzLi70CCs71qk9k33CW87roS0mpG000/; i=T5QFcWAmzvasBEBNnOa6tNqBy0IN8pmIdr1gXRl1MEfuaFKuZELu9pZCScDCq4XYLzVX0BIotOtu5B4XI4ZZR4azEEE=; yandex_login=Rendalina";
        connectionForGet.addRequestProperty("Cookie", cookie);

        printHeaders(connectionForGet);

        String body = printBody(connectionForGet);

        if (body != null) {

            int pos1 = body.indexOf("\"csrfToken\":\"") + 13;
            int pos2 = body.indexOf('"', pos1);
            String csrfToken = body.substring(pos1, pos2);
            System.out.println();
            System.out.println("csrfToken: " + csrfToken);

            String addressRu = "Ижевск, Советская 49";
            String address = URLEncoder.encode(addressRu, "UTF-8");
            URL url2 = new URL("https://yandex.ru/maps?text=" + address + "&lang=ru_RU&csrfToken=" + csrfToken);

            System.out.println("URL: "+url2);
            connectionForGet = url2.openConnection();

            connectionForGet.addRequestProperty("Cookie", cookie);

            String responceBody = printBody(connectionForGet);

            String regExp = "\"coordinates\":";

            Pattern pattern = Pattern.compile(regExp);
            Matcher matcher = pattern.matcher(responceBody);

            if (matcher.find()) {
                int position = matcher.start();
                String coord1 = responceBody.substring(position + 15, position + 24);
                String coord2 = responceBody.substring(position + 25, position + 34);

                System.out.println("Адрес: "+addressRu);
                System.out.println("Координаты: " + coord2 + ", " + coord1);
            }
        }
    }

    private void printHeaders(URLConnection connection) {
        System.out.println("ЗАГОЛОВКИ: ");

        Map<String, java.util.List<String>> map;
        map = connection.getHeaderFields();

        for (Map.Entry<String, java.util.List<String>> param : map.entrySet()) {
            System.out.println(param.getKey() + " " + param.getValue());
        }
        System.out.println();
    }

    private String printBody(URLConnection connection) {

        System.out.println("СОДЕРЖИМОЕ: ");

        try (InputStream inputStream = connection.getInputStream()) {

            Reader in = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
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
}
