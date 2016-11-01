package twitter_craw_maven.v1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import twitter4j.FilterQuery;
import twitter4j.GeoLocation;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.util.TimeSpanConverter;


/**
 * Hello world!
 *
 */
public class App 
{
    public static int FileNumber = 0;

	public static void main( String[] args ) throws TwitterException
    {
    	ConfigurationBuilder confBuilder = new twitter4j.conf.ConfigurationBuilder();
    	FileNumber=Integer.valueOf(args[0]).intValue();
    	
    	confBuilder.setDebugEnabled(true)
    		.setOAuthConsumerKey("1oZKJhXaEV6XKOrgeRk3jkGVj")
    		.setOAuthConsumerSecret("zhMFqjDZnyGHKFT8gNjw6HfG1zpQ2JdsIIggA2k8ka8c9XicOo")
    		.setOAuthAccessToken("780871500903559169-aDLQ12mUVopKB7S06XhNsNwudCgd2ky")
    		.setOAuthAccessTokenSecret("sAULr1rfDJeey1jfuCVzl2ssunQ8bCyRx4xnjln0Jsb5z");
    	Configuration config = confBuilder.build();
    	
    	/*TwitterFactory tf = new TwitterFactory(confBuilder.build());
    	twitter4j.Twitter twitter= tf.getInstance();
    	
    	
    	
    	//Get user name, status, try to get data from twitter
    	List<Status> status =twitter.getHomeTimeline();
    	for(Status st :status)
    	{
        System.out.println(st.getUser().getName()+"......"+st.getText());
    	}*/
    	
    	//Twitter stream and listener
    	 TwitterStream twitterStream = new TwitterStreamFactory(config).getInstance();
    	 TweetListener listener = new TweetListener();
    	 twitterStream.addListener(listener);
    
    
    
    	 //Geolocated bounding 	
    	    double[][] boundingbox = {{-124.47,24.0},{-66.56,49.3843}};
    	    FilterQuery filter = new FilterQuery();
    	    filter.locations(boundingbox);
    	    twitterStream.filter(filter);  
    }
    	    
    	    
    	    //TweetListener
    	    public static class TweetListener implements StatusListener {
    	    	TweetProcessor processor = new TweetProcessor();
    	    	
    	    	
    	   
    	    	public void onStatus(Status status) {
    	    	try {
					processor.processTweet(status);
				 	
				} catch (JsonGenerationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	    	}
    	    	
    	    	public void onStallWarning(StallWarning warning) {
    	    	System.out.println("Stall Warning :" + warning);
    	    	}
    	    	
    	    	//more methods
    			public void onException(Exception arg0) {
    				// TODO Auto-generated method stub
    				
    			}
    			public void onDeletionNotice(StatusDeletionNotice arg0) {
    				// TODO Auto-generated method stub
    				
    			}
    			public void onScrubGeo(long arg0, long arg1) {
    				// TODO Auto-generated method stub
    				
    			}
    			
    			public void onTrackLimitationNotice(int arg0) {
    				// TODO Auto-generated method stub
    				
    			}
    		}
    			
    		public static class TweetProcessor {
    			
    			
    			
    			
    			public static int i=0;
    			
    			public class UserInformation {
        			
        			private String _text;
        			private String _timestamp;
        			private String _Geolocation;
        			private String _User;
        			private String _title;
        			
        			
        			public class Text{
        				private String _Text;
        				
        			}
        			public class Timestamp{
        				private String _Timestamp;
        				
        			}
        			public class Geolocation{
        				private String _GeoLocation;
        				
        			}
        			public class UserName{
        				private String _User;
        				
        			}
        			public class Title{
        				private String _title;
        				
        			}
        			
    			}
    			
    			
    			public void processTweet(Status status) throws JsonGenerationException, JsonMappingException, IOException {
    				
    		    	
    			
    				Status st=status;
    				
 
    		        UserInformation user=new UserInformation();
    		        user._User=st.getUser().getName();
    		        user._timestamp=formatDate(st.getCreatedAt());
    		        user._Geolocation=st.getGeoLocation().toString();
    		        user._text=st.getText();
    		        
    		        //Extract links
    		        String links=ExtractLinks(user._text);
    		        if(links!=null){
    		        Document doc = Jsoup.connect(links).get();
    		        user._title = doc.title();
    		        }
    		        System.out.println(st.getUser().getName()+"......"+st.getText()+"......"+user._title);
    		        
    		        
    		       
    		        ObjectMapper mapper = new ObjectMapper();
    		        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
    		        String temp=mapper.writeValueAsString(user);
    		        
    		        String filename="";
    		        
    		        
    		        filename = "0" + i + ".json";
    		        File file = new File("G:\\InformationRetrieval\\"+filename);
    		        
    		        if(FileCounter(file)>10485760){		//10485760
    		        	i++;
    		        }
    		        if(i>=FileNumber){
	    	    		System.exit(0);
	    	    	}
    		        

    		        // if file doesnt exists, then create it
    		        if (!file.exists()) {
    		         file.createNewFile();
    		        }
    		        
    		        FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
    		        BufferedWriter bw = new BufferedWriter(fw);
    		        bw.write(temp+"\n");
    		        bw.close();
    		        

    		      
    			}
    			
    			
    			public String ExtractLinks(String args) {
        	        CharSequence s = args;
        	        
        	        Matcher m = Pattern.compile("(http:|https:)//[^[A-Za-z0-9\\._\\?%&+\\-=/#]]*").matcher(s);
        	        while(m.find()){
        	        	
        	        	return(m.group());
        	        }
        	        return null;
        	    }
    			
    			public String formatDate(Date create_date){

    				//twitter date format from json response:  Wed Jul 31 13:15:10 EDT 2013
    				TimeSpanConverter converter = new TimeSpanConverter(); 
    				return converter.toTimeSpanString(create_date);

    			}
    			
    			public long FileCounter(File f) {  
    			    
    			    if (f.exists() && f.isFile()){  
    			        return f.length(); 
    			    }else{  
    			        return 0;
    			    }  
    			}   	   		
	
    		}
}  
    
    
    

