package org.anasko;
import org.anasko.client.cli.UserClientCLI;

public class Main {
    public static void main(String[] args) {
        var userClientCLI = new UserClientCLI();

        new Thread(userClientCLI).start();
    }
}
