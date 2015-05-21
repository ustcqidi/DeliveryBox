package client;


public class boxMain {

	public static void main(String[] args) {
		new TomcatRunner("/boxWebService", 8088).run();
	}
}
