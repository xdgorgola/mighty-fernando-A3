public class Test {

    public void erwebo(int woo) throws NoSuchFieldError{
        int xd = woo;
        try {
            if (xd == 0){
                throw new NoSuchFieldError();
            }
            System.out.println("woosito");
        } catch (OutOfMemoryError e) {
            System.out.println("ripazo");
        }
    }

    public static void main(String[] args) {
        Test jejexd = new Test();
        
        try {
            jejexd.erwebo(1);
            jejexd.erwebo(0);
        } catch (NoSuchFieldError e) {
            System.out.println("fuerita feo");
        }
        
    }
}