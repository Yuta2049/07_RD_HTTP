import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class Service {

    public void printCoordinates() throws Exception {

        //URL urlForGet = new URL("https://yandex.ru/");
        URL urlForGet = new URL("https://yandex.ru/maps");

        URLConnection connectionForGet = urlForGet.openConnection();

        connectionForGet.



        System.out.println("URL: " + urlForGet);
        System.out.println();

        printHeaders(connectionForGet);

        printBody(connectionForGet);

        System.out.println();

        String yandexuid = getYandexUID(connectionForGet);
        System.out.println("yandexuid: "+yandexuid);

        //yandexuid=9926425281544451325;
        //"csrfToken":"de1f2622ab7ead2d5975973922d2c78a9a775331:1544451325216"

    }

    private static void printHeaders(URLConnection connection) {
        System.out.println("ЗАГОЛОВКИ: ");

        Map<String, java.util.List<String>> map;
        map = connection.getHeaderFields();

        for (Map.Entry<String, java.util.List<String>> param : map.entrySet()) {
            System.out.println(param.getKey() + " " + param.getValue());
        }
        System.out.println();
    }

    private static void printBody(URLConnection connection) {

        System.out.println("СОДЕРЖИМОЕ: ");

        try (InputStream inputStream = connection.getInputStream()) {

            Reader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            for (int c; (c = in.read()) >= 0; )
                System.out.print((char) c);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();
    }


    private String getYandexUID(URLConnection connection) {

        Map<String, java.util.List<String>> map;
        map = connection.getHeaderFields();

        for (Map.Entry<String, java.util.List<String>> param : map.entrySet()) {
            if (param.getKey().equals("SetCookie")) {
                System.out.println("Нашли Куки");
                List cookies = param.getValue();
                for (Object currentCookie : cookies) {
                    System.out.println(currentCookie);
                }
            }
        }
        return null;
    }
}
