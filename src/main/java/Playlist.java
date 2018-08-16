import java.util.ArrayList;
import java.util.List;

public class Playlist {
    public static class Song {
        private String name;
        private Song nextSong;
 
        public Song(String name) {
            this.name = name;
        }
    
        public void setNextSong(Song nextSong) {
            this.nextSong = nextSong;
        }
        

        public boolean isRepeatingPlaylist() {
        	List<String> songNames=new ArrayList<>();
        	return isRepeatingPlaylist(this,songNames);
        }
        protected boolean isRepeatingPlaylist(Song playlistSong, List<String> songNames)
        {
        	if(playlistSong.nextSong==null)
        	{
        		return false;
        	}
        	songNames.add(playlistSong.name);
            if(songNames.contains(playlistSong.nextSong.name))
            {
            	return true;
            }
            return isRepeatingPlaylist(playlistSong.nextSong,songNames);
        }
    }
    
    public static void main(String[] args) {
        Song first = new Song("Hello");
        Song second = new Song("Eye of the tiger");
        Song third = new Song("Happy Birthday");
        first.setNextSong(second);
        second.setNextSong(third);
        third.setNextSong(second);

        System.out.println(first.isRepeatingPlaylist());
    }
}