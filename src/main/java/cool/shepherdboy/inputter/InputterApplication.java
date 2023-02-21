package cool.shepherdboy.inputter;

import cool.shepherdboy.inputter.config.ConfigBeanProp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

@SpringBootApplication(scanBasePackages = {"cool.shepherdboy.inputter"})
@RestController
public class InputterApplication {


    @Autowired
    private static ConfigBeanProp prop;

    @Autowired
    static private Environment environment;

    public static void main(String[] args) throws IOException {
        SpringApplication.run(InputterApplication.class, args);

        startDaemonThread();
    }

    private static void startDaemonThread() {
        try {
            String localIp = InetAddress.getLocalHost().getHostAddress();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        File directory = new File("");//参数为空
                        String courseFile = directory.getCanonicalPath() ;
                        Properties properties = new Properties();
                        properties.load(new FileReader(courseFile + "/src/main/resources/application.properties"));
                        String registryServerUrl = properties.getProperty("registry.url");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }).start();
            //todo 发送心跳
        } catch (UnknownHostException ignored) {
        }
    }

    @GetMapping("tags")
    public String tags(String tags) throws IOException {
        try {
            writeLog(tags);
            copy(tags);
            paste();
        } catch (Exception e) {
            e.printStackTrace();
            writeLog(e.getMessage());
        }
        return "success:" + tags;
    }

    private static void writeLog(String msg) throws IOException {
        FileWriter writer = new FileWriter("inputter.log", true);
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss,SSS");
        writer.write( format.format(date) + ": " + msg + "\n");
        writer.flush();
        writer.close();
    }

    private static void paste() throws AWTException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    private void copy(String tags) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection trans = new StringSelection(tags);
        clipboard.setContents(trans, null);
    }

}
