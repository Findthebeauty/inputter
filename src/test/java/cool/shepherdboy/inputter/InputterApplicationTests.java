package cool.shepherdboy.inputter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootTest
class InputterApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void writeFile() throws IOException {
        FileWriter writer = new FileWriter("C:\\Users\\Administrator\\Downloads\\nssm-2.24\\nssm-2.24\\win64\\inputter.log");
        writer.write("e.getMessage()");
        writer.flush();
        writer.close();
    }

    @Test
    void getLocalHost() throws UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();
        System.out.println(localHost.getHostAddress());
    }
}
