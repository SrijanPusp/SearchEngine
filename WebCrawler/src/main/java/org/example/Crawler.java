package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

public class Crawler {
    HashSet<String> urlSet;
    int MAX_DEPTH = 2;
    Crawler(){
        urlSet = new HashSet<String>();
    }
    public void getPageTextAndLinks(String url, int depth){
        if(urlSet.contains(url)){
            return;
        }
        if(depth>MAX_DEPTH){
            return;
        }
        depth++;
        try
        {
              Document document = Jsoup.connect(url).timeout(5000).get();
                //ye wala kaam ab indexer ke liye karenge
                Indexer indexer = new Indexer(document, url);
                System.out.println(document.title());
                Elements availableLinksOnPage = document.select("a[href]");
                for(Element currentLink: availableLinksOnPage){
                    getPageTextAndLinks(currentLink.attr("abs:href"), depth);
                }
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }
    }
    public static void main(String[] args){
        Crawler crawler = new Crawler();
        crawler.getPageTextAndLinks("https://roadmap.sh/", 1);
    }
}