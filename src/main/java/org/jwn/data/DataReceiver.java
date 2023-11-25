package org.jwn.data;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataReceiver {
    public static final String site = "http://hiy9.net/ms-ost/";
    public static String root = "C:/JWN3/music/maplestory0/";
    public static List<String> heads = new ArrayList<>();
    public static Map<String, List<BGMData>> bgm_map = new HashMap<>();

    static {
        try {
            Document document = Jsoup.connect(site).get();
            document.select("table").forEach(
                    table -> {
                        String head = table.previousElementSibling().text();
//                        System.out.println(head);     // print head
                        heads.add(head);

                        List<BGMData> musicDataList = new ArrayList<>();
                        Elements rows = table.select("tbody > tr");
                        rows.forEach(row -> {
                            if (row.selectFirst("td") != null) {
                                String title = row.selectFirst("td").text();
//                                System.out.println(title);        // print title
                                if (!title.equals("")) {
                                    String src = row.select("td").last().select("audio").attr("src");
//                                    System.out.println(src);
                                    musicDataList.add(new BGMData(title, src));
                                }
                            }
                        });
                        bgm_map.put(head, musicDataList);
                    }
            );
        } catch (IOException e) {
            System.err.println("receive failed");
            e.printStackTrace();
        }
    }

    public static void downloadBgm() {
        bgm_map.forEach((head, bgmDataList) -> {
            Path path = Paths.get(root + head.replaceAll("[\\\\/:*?\"<>|]", "-"));
            if (Files.exists(path) && Files.isDirectory(path)) {
//                System.out.println("디렉토리가 이미 존재합니다.");
            } else {
                try {
                    Files.createDirectories(path);
//                    System.out.println("디렉토리가 생성되었습니다.");
                } catch (Exception e) {
                    System.err.println("create directory failed");
                    e.printStackTrace();
                }
            }
            // audio download
            bgmDataList.forEach(bgmData -> {
                String fileUrl = site + bgmData.getSrc().replace(" ", "%20");
                String filePathString = path + "\\" + bgmData.getTitle().replaceAll("[\\\\/:*?\"<>|]", "-") + ".mp3";
                Path filePath = Paths.get(filePathString);

                if (Files.exists(filePath)) {
                    System.out.println("파일 이미 존재");
                } else {
                    try {
                        CloseableHttpClient httpClient = HttpClients.createDefault();
                        HttpGet httpGetFl = new HttpGet(fileUrl);
                        CloseableHttpResponse responseFl = httpClient.execute(httpGetFl);

                        BufferedInputStream bInStr = new BufferedInputStream(responseFl.getEntity().getContent());
                        System.out.println("getFileDown> finalPath: " + fileUrl);
                        BufferedOutputStream bOutStr = new BufferedOutputStream(new FileOutputStream(new File(filePathString)));

                        int inpByte;
                        while ((inpByte = bInStr.read()) != -1) {
                            bOutStr.write(inpByte);
                        }
                        bInStr.close();
                        bOutStr.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        });
    }
}
