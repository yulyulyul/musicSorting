package vo;

public class Music 
{
	String rank;
	String title;
	String artist;
	String from;
	boolean isSuit;
	
	public Music(String rank, String title, String artist, String from) {
		super();
		this.rank = rank;
		this.title = title;
		this.artist = artist;
		this.from = from;
		this.isSuit = true;
	}
	
	public boolean isSuit() {
		return isSuit;
	}
	public void setSuit(boolean isSuit) {
		this.isSuit = isSuit;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	
	
}
