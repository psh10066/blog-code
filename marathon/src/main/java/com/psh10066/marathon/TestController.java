package com.psh10066.marathon;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@RestController
public class TestController {

    @GetMapping("")
    public void getInfo() throws IOException, InterruptedException {
        System.out.println("====================================================");

        String baseUrl = "http://www.roadrun.co.kr/schedule/";
        Document baseDoc = Jsoup.connect(baseUrl + "list.php").get();
        List<Node> nodeList = baseDoc.getElementsByTag("tbody").get(8).childNodes();
        for (int i = 0; i < nodeList.size(); i++) {
            if (i % 4 != 0 || i + 2 >= nodeList.size()) {
                continue;
            }
            String no = nodeList.get(i).childNode(3).childNode(0).childNode(0).childNode(0).attributes().toString().split("'")[3];
            String url = baseUrl + no;

            Document doc = Jsoup.parse(new URL(url).openStream(), "euc-kr", url);
            List<Node> childNodes = doc.getElementsByTag("tbody").get(1).childNodes();
//            for (int j = 0; j < childNodes.size(); j++) {
//                if (j % 2 == 1) {
//                    continue;
//                }
//                Node node = childNodes.get(j);
//                String title = node.childNode(1).childNode(1).childNode(0).toString();
//                String body = node.childNode(3).toString();
//                System.out.println(title + ", " + body);
//            }
            String 대회명 = childNodes.get(0).childNode(3).childNode(1).childNode(0).childNode(0).toString();
            String 대회일시 = childNodes.get(6).childNode(3).childNode(1).childNode(0).toString();
            String 대회종목 = childNodes.get(10).childNode(3).childNode(0).toString();
            String 대회지역 = childNodes.get(12).childNode(3).childNode(0).toString();
            String 주최단체 = childNodes.get(16).childNode(3).childNode(1).childNode(0).toString();
            String 접수기간 = childNodes.get(18).childNode(3).childNode(0).toString();
            String 홈페이지 = "http://" + childNodes.get(20).childNode(3).childNode(1).childNode(1).childNode(0);

            // 장소가 없는 경우는 가져온다.
            String 대회장소;
            try {
                대회장소 = doc.getElementById("code").toString().split("'")[5].trim();
            } catch (Exception e) {
                대회장소 = childNodes.get(14).childNode(3).childNode(1).childNode(0).toString();
            }

            System.out.println(대회명 + "|" + 대회일시 + "|" + 대회종목 + "|" + 대회지역 + "|" + 주최단체 + "|" + 접수기간 + "|" + 홈페이지 + "|" + 대회장소);
            Thread.sleep(1000);
        }
    }
}
