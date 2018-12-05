package com.my.project.softFinal.musicSorting;
import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class musicChart 
{
	public static void main(String[] args)
	{
		try 
		{
			getBugsChart();
			getMelonChart();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void getBugsChart() throws IOException
	{
		Connection.Response BugsChartPage = Jsoup.connect("https://music.bugs.co.kr/chart")
                .timeout(3000)
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .header("Upgrade-Insecure-Request", "1")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36")
                .header("Host", "music.bugs.co.kr")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                .ignoreContentType(true)
                .method(Connection.Method.GET)
                .execute();
		
		Document BugsDoc = BugsChartPage.parse();
//		System.out.println("BugsDoc : " + BugsDoc.html());
		Elements Rank100 = BugsDoc.select("tr").attr("rowType", "track");
		for (Element ele : Rank100) 
		{
//			System.out.println("================================================");
//			System.out.println(ele.html());
			String rank = ele.select("td div strong").text();
			String title = ele.select("th p a").text();
			String artist = ele.select("td p a").text();
			
			if(rank.length() > 0)
			{
				System.out.print("rank : " + rank);
				System.out.print("	title : " + title);
				System.out.println("	artist : " + artist);
			}
			
		}
	}
	
	public static void getMelonChart() throws IOException
	{
		Connection.Response MelonChartPage = Jsoup.connect("https://www.melon.com/chart/index.htm")
                .timeout(3000)
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36")
                .header("Host", "www.melon.com")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                .ignoreContentType(true)
                .method(Connection.Method.GET)
                .execute();
		
		Document MelonDoc = MelonChartPage.parse();
		Elements in50 = MelonDoc.select("tr.lst50");
		Elements in100 = MelonDoc.select("tr.lst100");
		
		for (Element ele : in50) 
		{
			String rank = ele.select("span.rank").text();
			System.out.print("rank : " + rank);
			String title = ele.select("span a").first().text();
			System.out.print("	title : " + title);
			String artist = ele.select("span a").get(1).text();
			System.out.println("	artist : " + artist);
		}
		
		for (Element ele : in100) 
		{
			String rank = ele.select("span.rank").text();
			System.out.print("rank : " + rank);
			String title = ele.select("span a").first().text();
			System.out.print("	title : " + title);
			String artist = ele.select("span a").get(1).text();
			System.out.println("	artist : " + artist);
		}
	}

}
