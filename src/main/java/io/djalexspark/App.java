package io.djalexspark;

import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class App {
    public static void main(String[] args) {





        WhetherEntity wh  = new WhetherEntity();
        try {

            Document doc = Jsoup.connect("https://www.gismeteo.ru/weather-kavalerovo-4876/")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                    .timeout(30000)
                    .get();
            Elements elements = doc.getElementsByClass("weathertab is-active");
            Elements elements2 = doc.getElementsByClass("widget-row widget-row-icon is-important");
            for (Element element : elements) {
                wh.setWhether(element.getElementsByClass(
                        "weathertab is-active").attr(
                                "data-tooltip"));
                wh.setDate(element.getElementsByClass("date date-2").text());
                wh.setTmin(element.getElementsByTag(
                        "temperature-value").first().attr(
                                "value"));
                wh.setTmax(element.getElementsByTag("temperature-value").last().attr("value"));
            }
            for (Element element2 : elements2) {
                if(element2.getElementsByAttribute("data-tooltip").attr("data-tooltip").contains("дождь")||
                        element2.getElementsByAttribute("data-tooltip").attr("data-tooltip").contains("снег")){
                    wh.setRainy("Есть осадки");
                }else {wh.setRainy("");}
            }
            System.out.println(wh.toString());

//            System.out.println(elements.toString());
        } catch (IOException e) {
            System.err.println("Ошибка подключения: " + e.getMessage());
            e.printStackTrace();
        }


    }
    @Data
    public static class WhetherEntity{
        String date;
        String whether;
        String tmin;
        String tmax;
        String rainy;

    }
}
