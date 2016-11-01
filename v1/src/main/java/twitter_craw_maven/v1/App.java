package twitter_craw_maven.v1;

import java.util.List;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws TwitterException
    {
    	ConfigurationBuilder confBuilder = new twitter4j.conf.ConfigurationBuilder();
    	
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
    	    	processor.processTweet(status);
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
    			
    			public void processTweet(Status status){
    				
    		    	Status st=status;
    		        System.out.println(st.getUser().getName()+"......"+st.getText());
    		    	
    				
    			}
    			
    		}
    		
    
    
  
	
 }
   
    
    
    

