package fr.epitechnancy;


import org.apache.commons.mail.util.MimeMessageParser;
import sun.nio.ch.FileKey;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {

    public static void display(File emlFile) throws Exception{
        Properties props = System.getProperties();
        Session mailSession = Session.getDefaultInstance(props, null);
        InputStream source = new FileInputStream(emlFile);
        MimeMessage message = new MimeMessage(mailSession, source);
        MimeMessageParser mimeMessageParser = new MimeMessageParser(message);

        String c = mimeMessageParser.parse().getHtmlContent();
        if (c == null) {
            System.out.println("None");
            return;
        }
        Pattern pattern = Pattern.compile("(?<=<a href=\"http://www).*?(?=</a>)");
        Matcher m = pattern.matcher(c);
        int i = 0;

        while (m.find()) {
            String cc = m.group();
            String[] sp = cc.split(" ");
            for (String str : sp) {
                Pattern p_c = Pattern.compile("(?<=style=\"color:).*?(?=\")");
                Matcher m_c = p_c.matcher(str);
                for (; m_c.find(); i++);
            }
        }
        if (i > 1) {
            System.out.println("Find " + i);
            System.exit(i);
        } else System.out.println("None");
    }

    public static void main(String[] args) throws Exception {
        if (args.length <= 0) {
            System.err.println("No args given !");
            return;
        }
        File f = new File(args[0]);
        if (!f.exists()) {
            System.err.println("No file found !");
            return;
        }
        display(f);
        System.exit(0);
    }

}
