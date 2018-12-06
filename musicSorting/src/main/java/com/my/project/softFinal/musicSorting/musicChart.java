package com.my.project.softFinal.musicSorting;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import vo.Music;

public class musicChart 
{
	public static String yearS;
	public static String monthS;
	public static String dateS;
	public static String curDate;
	public static String hour;
	public static int cnt = 0;
	
	public static void main(String[] args)
	{
		try 
		{
			setTime();
			
			ArrayList<Music> melonList = getMelonChart();
			ArrayList<Music> bugsList = getBugsChart();
			ArrayList<Music> genieList = getGenieChart();
			ArrayList<Music> mnetList = getMnetChart();
			
			melonList = setTitle(melonList);
			bugsList = setTitle(bugsList);
			genieList = setTitle(genieList);
			mnetList = setTitle(mnetList);
			
//			for(Music m : melonList)
//			{
//				print_all(m,1);
//			}
//			for(Music m : bugsList)
//			{
//				print_all(m,1);
//			}
//			for(Music m : genieList)
//			{
//				print_all(m,1);
//			}
//			for(Music m : mnetList)
//			{
//				print_all(m,1);
//			}
			
			sorting sort = new sorting(melonList, bugsList, genieList, mnetList);
			ArrayList<Music> sortTop100List = sort.sortTop100();
		
			Collections.sort(sortTop100List, new Comparator<Music>() {
	            public int compare(Music s1, Music s2) {
	            	Integer s1Rank = Integer.parseInt(s1.getRank());
	            	Integer s2Rank = Integer.parseInt(s2.getRank());
	            	
	                if (s1Rank < s2Rank) {
	                    return -1;
	                } else if (s1Rank > s2Rank) {
	                    return 1;
	                }
	                return 0;
	            }
	        });

			for(Music m : sortTop100List)
			{
				print_all(m,0);
			}
			
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void setTime()
	{
		TimeZone jst = TimeZone.getTimeZone ("JST");
		Calendar cal = Calendar.getInstance ( jst ); // 주어진 시간대에 맞게 현재 시각으로 초기화된 GregorianCalender 객체를 반환.// 또는 Calendar now = Calendar.getInstance(Locale.KOREA);

		yearS = cal.get ( Calendar.YEAR ) + "";
		monthS = "";
		dateS = "";
		
		if(cal.get ( Calendar.DATE ) < 10)
		{
			dateS = "0" + cal.get ( Calendar.DATE );
		}
		else
		{
			dateS = cal.get(Calendar.DATE) + "";
		}
		
		if((cal.get(Calendar.MONTH)+1) < 10)
		{
			monthS = "0" + (cal.get(Calendar.MONTH)+1);
		}
		else
		{
			monthS = "" + (cal.get(Calendar.MONTH)+1);
		}
		
		curDate = yearS + monthS + dateS;
		
		hour = cal.get(Calendar.HOUR_OF_DAY) + "";
	}
	
	public static ArrayList<Music> setTitle(ArrayList<Music> musicList)
	{
		ArrayList<Music> mList = musicList;
		for(Music m : musicList)
		{
			String title = m.getTitle();
//			System.out.println(title);
			if(title.contains("("))
			{
			   title = title.substring(0, title.indexOf("("));
			}
			title = title.trim();
			m.setTitle(title);
		}
		return mList;
	}
	
	public static ArrayList<Music> getMnetChart() throws IOException
	{
		ArrayList<Music> musicList = new ArrayList<Music>();
		Connection.Response MnetChartPage50 = Jsoup.connect("http://www.mnet.com/chart/top100")
                .timeout(3000)
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36")
                .header("Host", "www.mnet.com")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                .ignoreContentType(true)
                .method(Connection.Method.GET)
                .execute();
		
		Document MnetDoc = MnetChartPage50.parse();
		Elements Mnet50 = MnetDoc.select("tbody tr");
		
		for (Element ele : Mnet50) 
		{
			String rank = ele.select("td div.MMLIRankNum_Box").text();
			rank = rank.replace("위", "");
//			System.out.print("rank : " + rank);
			String title = ele.select("a.MMLI_Song").text();
//			System.out.print("	title : " + title);
			String artist = ele.select("a.MMLIInfo_Artist").text();
//			System.out.println("	artist : " + artist);
			
			Music music = new Music(rank, title, artist, "mnet");
			musicList.add(music);
		}
		
		String url = "http://www.mnet.com/chart/TOP100/";
		url += curDate + hour;
//		System.out.println("mnet`s pg2 : " + url);
		
		Map<String, String> data = new HashMap<String, String>();
		data.put("pNum", "2");
		
		Connection.Response MnetChartPage100 = Jsoup.connect(url)
                .timeout(3000)
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36")
                .header("Host", "www.mnet.com")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                .data(data)
                .ignoreContentType(true)
                .method(Connection.Method.GET)
                .execute();
		
		Document MnetDoc100 = MnetChartPage100.parse();
		Elements Mnet100 = MnetDoc100.select("tbody tr");
		
		for (Element ele : Mnet100) 
		{
			String rank = ele.select("td div.MMLIRankNum_Box").text();
			rank = rank.replace("위", "");
//			System.out.print("rank : " + rank);
			String title = ele.select("a.MMLI_Song").text();
//			System.out.print("	title : " + title);
			String artist = ele.select("a.MMLIInfo_Artist").text();
//			System.out.println("	artist : " + artist);
			
			Music music = new Music(rank, title, artist, "mnet");
			musicList.add(music);
		}
		
		return musicList;
	}
	
	public static ArrayList<Music> getGenieChart() throws IOException
	{
		ArrayList<Music> musicList = new ArrayList<Music>();
		
		Connection.Response GenieChartPage50 = Jsoup.connect("http://www.genie.co.kr/chart/top200")
                .timeout(3000)
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .header("Upgrade-Insecure-Request", "1")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36")
                .header("Host", "www.genie.co.kr")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                .ignoreContentType(true)
                .method(Connection.Method.GET)
                .execute();
		
		Document GenieDoc50 = GenieChartPage50.parse();
//		System.out.println("GenieDoc : " + GenieDoc.html());
		
		Elements Rank50 = GenieDoc50.select("tbody tr.list");
//		System.out.println(Rank50.html());
		for (Element ele : Rank50) 
		{
//			System.out.println(ele.html());
			String rank = ele.select("td.number").text();
			rank = (String)rank.subSequence(0, 2);
			rank = rank.trim();
//			System.out.print("rank : " + rank);
			
			String title = ele.select("td.info a").first().text();
//			System.out.print("	title : " + title);
			
			String artist = ele.select("td.info a").get(1).text();
//			System.out.println("	artist : " + artist);
			
			Music music = new Music(rank, title, artist, "genie");
			musicList.add(music);
		}
		
		Map<String, String> data = new HashMap<String, String>();
		data.put("ditc", "D");
		data.put("hh", hour);
		data.put("pg", "2");
		data.put("rtm", "Y");
		data.put("ymd", curDate);
		
		Connection.Response GenieChartPage100 = Jsoup.connect("http://www.genie.co.kr/chart/top200")
                .timeout(3000)
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .header("Upgrade-Insecure-Request", "1")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36")
                .header("Host", "www.genie.co.kr")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                .data(data)
                .ignoreContentType(true)
                .method(Connection.Method.GET)
                .execute();
		
		
		Document GenieDoc100 = GenieChartPage100.parse();
//		System.out.println("GenieDoc : " + GenieDoc.html());
		
		Elements Rank100 = GenieDoc100.select("tbody tr.list");
//		System.out.println(Rank50.html());
		for (Element ele : Rank100) 
		{
//			System.out.println(ele.html());
			String rank = ele.select("td.number").text();
			rank = (String)rank.subSequence(0, 3);
			rank = rank.trim();
//			System.out.print("rank : " + rank);
			
			String title = ele.select("td.info a").first().text();
//			System.out.print("	title : " + title);
			
			String artist = ele.select("td.info a").get(1).text();
//			System.out.println("	artist : " + artist);
			Music music = new Music(rank, title, artist, "genie");
			musicList.add(music);
		}
		
		return musicList;
	}
	
	public static ArrayList<Music> getBugsChart() throws IOException
	{
		ArrayList<Music> musicList = new ArrayList<Music>();
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
//				System.out.print("rank : " + rank);
//				System.out.print("	title : " + title);
//				System.out.println("	artist : " + artist);
				
				Music music = new Music(rank, title, artist, "bugs");
				musicList.add(music);
			}
		}
		
		return musicList;
	}
	
	public static ArrayList<Music> getMelonChart() throws IOException
	{
		ArrayList<Music> musicList = new ArrayList<Music>();
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
//			System.out.print("rank : " + rank);
			String title = ele.select("span a").first().text();
//			System.out.print("	title : " + title);
			String artist = ele.select("span a").get(1).text();
//			System.out.println("	artist : " + artist);
			
			Music music = new Music(rank, title, artist, "melon");
			musicList.add(music);
		}
		
		for (Element ele : in100) 
		{
			String rank = ele.select("span.rank").text();
//			System.out.print("rank : " + rank);
			String title = ele.select("span a").first().text();
//			System.out.print("	title : " + title);
			String artist = ele.select("span a").get(1).text();
//			System.out.println("	artist : " + artist);
			
			Music music = new Music(rank, title, artist, "melon");
			musicList.add(music);
		}
		return musicList;
	}
	
	public static void print_all(Music m, int swi)
	{
		switch (swi) {
		case 0:
			if(m.isSuit())
			{
				cnt+=1;
				System.out.println(cnt + "	" + m.getTitle() +"	" + m.getArtist() + "	" + m.getRank());
			}
			break;

		case 1:
			System.out.println(cnt + "	" + m.getFrom() + "	" + m.getTitle() +"	" + m.getArtist() + "	" + m.getRank());
			break;
		}
	}

}
