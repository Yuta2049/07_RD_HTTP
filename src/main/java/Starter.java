public class Starter {

    public static void main(String[] args) {
        Service service = new Service();
        try {
            service.printCoordinates();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
