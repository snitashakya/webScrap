/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leapfrog.webscrapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author ASUS
 */
public class program {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            URL url = new URL("http://hamrobazaar.com/c130-events-and-happenings");
            URLConnection conn = url.openConnection();
            BufferedReader reader;
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder content = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {

                content.append(line).append("\r\n");
                //  System.out.println(line);
            }
            String regex = "<div class=\".*<a href=\"(.*?)\"> <img src='(.*?)'(.*)\\s+(.*)";
            Pattern pattern = Pattern.compile(regex);
            Matcher match = pattern.matcher(content.toString());
            while (match.find()) {
                //System.out.println("Link:");
                System.out.println("Link:" + match.group(2));
                String imgurl = match.group(2);
                URL url1 = new URL(match.group(2));
                URLConnection conn1 = url1.openConnection();
                String[] tokens = imgurl.split("/");

                FileOutputStream fos;
                fos = new FileOutputStream(tokens[tokens.length - 1]);
                byte[] data = new byte[1024 * 10];
                int i = 0;
                InputStream is = conn1.getInputStream();
                while ((i = is.read(data)) != -1) {
                    fos.write(data, 0, i);
                }
                is.close();

            }
            reader.close();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());

        }
    }
}
//<a href='(.*?)'><font class="txt">&nbsp;&nbsp;\s<b>(.*?)</b></font><font class="txtsmall"\scolor="(.*?)">\s
