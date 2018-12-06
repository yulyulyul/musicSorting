package com.my.project.softFinal.musicSorting;

import java.util.ArrayList;
import vo.Music;

public class sorting 
{
	public ArrayList<Music> melonList;
	public ArrayList<Music> bugsList;
	public ArrayList<Music> genieList;
	public ArrayList<Music> mnetList;

	public sorting(ArrayList<Music> _melonList, ArrayList<Music> _bugsList, ArrayList<Music> _genieList, ArrayList<Music> _mnetList) 
	{
		super();
		this.melonList = _melonList;
		this.bugsList = _bugsList;
		this.genieList = _genieList;
		this.mnetList = _mnetList;
	}
	
	/*
	 * 멜론을 기준으로 나머지 3개의 음원 사이트를 비교한다.
	 * (어차피 4곳 모두 곡이 존재해야하므로 어떤것을 기준으로 비교하든 상관 없음.)
	 * 
	 * 멜록 객체 m, 벅스 객체 b, 지니 객체 g, 엠넷 n => Music 객체.
	 * Music(곡명(title), 아티스트(artist), 랭킹(rank), 출처(from)[멜론인지 지니인지 벅스인지..], 4곳 모두가 존재하는지 판별하는 객체(isSuit))
	 * 
	 * for(멜론 객체 m)
	 * [
	 * 		isPass => 초기에는 false로 설정한다.
	 * 
	 * 		for(벅스 객체 b)
	 * 		[
	 * 			1차적으로 m와 b의 곡명을 비교한다.
	 * 				곡명이 같다면 랭킹 값을 서로 더해서 멜론의 랭킹값을 업데이트 한다.(setRank부분)
	 * 				isPass를 true로 바꿔준다.
	 * 		]
	 * 		벅스 for문이 처음부터 끝까지 다 돌아갈 동안 곡명이 같은게 존재하지 않았다면 isPass는 바뀐 적이 없기 때문에
	 * 		isPass는 false일 수 밖에 없다.
	 * 		만약 isPass가 false다 => 벅스에는 해당 곡이 존재하지 않는다 => m의 isSuit 객체를 false로 바꾼다.(setSuit) 
	 *         => 다시 또 다른 음원 사이트랑 비교해야하기 때문에 isPass를 false로 바꿔준다.
	 *         
	 *      if(m.isSuit) => m.isSuit이 false면 어차피 벅스에서 존재하지 않았기 때문에 검사할 필요가 없다.
	 *      [
	 *      	for(지니 객체 g)
	 *      	[
	 *      		위와 루틴은 같음.
	 *     	 	]	
	 *      
	 *      	for(엠넷 객체 n)
	 *      	[
	 *      		위와 루틴은 같음.
	 *      	]
	 *      ]
	 * ]
	 * 
	 * 멜론List를 반환한다.
	 * 
	 */
	public ArrayList<Music> sortTop100()
	{
		
		for(Music melon : melonList)
		{
			boolean isPass = false;
			for(Music bugs : bugsList)
			{
				if(melon.getTitle().equals(bugs.getTitle()))
				{
					String mrank = melon.getRank();
					Integer mrankI = Integer.parseInt(mrank);
					
					String brank = bugs.getRank();
					Integer brankI = Integer.parseInt(brank);
					
					mrankI += brankI;
					melon.setRank(mrankI+"");
					isPass = true;
				}
			}
			
			if(isPass == false)
			{
				melon.setSuit(false);
			}
			else
			{
				isPass = false;
			}
			
			if(melon.isSuit())
			{
				for(Music genie : genieList)
				{
					if(melon.getTitle().equals(genie.getTitle()))
					{
						String mrank = melon.getRank();
						Integer mrankI = Integer.parseInt(mrank);
						
						String grank = genie.getRank();
						Integer grankI = Integer.parseInt(grank);
						
						mrankI += grankI;
						melon.setRank(mrankI+"");
						isPass = true;
					}
				}
				
				if(isPass == false)
				{
					melon.setSuit(false);
				}
				else
				{
					isPass = false;
				}				
			}
			
			if(melon.isSuit())
			{
				for(Music mnet : mnetList)
				{
					if(melon.getTitle().equals(mnet.getTitle()))
					{
						
						String mrank = melon.getRank();
						Integer mrankI = Integer.parseInt(mrank);
						
						String nrank = mnet.getRank();
						Integer nrankI = Integer.parseInt(nrank);
						
						mrankI += nrankI;
						melon.setRank(mrankI+"");
						isPass = true;
					}
				}
				
				if(isPass == false)
				{
					melon.setSuit(false);
				}
				else
				{
					isPass = false;
				}
			}
		}
		return this.melonList;
	}
}
