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
