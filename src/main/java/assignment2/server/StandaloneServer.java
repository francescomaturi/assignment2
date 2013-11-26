package assignment2.server;

import java.io.IOException;
import java.net.InetAddress;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

public class StandaloneServer {
	public static void main(String[] args) throws IllegalArgumentException,
			IOException {
		
		
		String protocol = "http://"; // of course...
		String port = ":5900/"; // you can use any other as long as it is not in
								// use
		String hostname = InetAddress.getLocalHost().getHostAddress();
		if (hostname.equals("127.0.0.1")) {
			hostname = "localhost";
		}
		String baseUrl = protocol + hostname + port;
		final HttpServer server = HttpServerFactory.create(baseUrl);
		server.start();
		System.out.println("Server starts on " + baseUrl
				+ "\n [kill the process to exit]");
	}
}