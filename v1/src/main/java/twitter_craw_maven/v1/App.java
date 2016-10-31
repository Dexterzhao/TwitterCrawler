package twitter_craw_maven.v1;

import java.util.List;

import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
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
    	
    	confBuilder.setOAuthConsumerKey("1oZKJhXaEV6XKOrgeRk3jkGVj");
    	confBuilder.setOAuthConsumerSecret("zhMFqjDZnyGHKFT8gNjw6HfG1zpQ2JdsIIggA2k8ka8c9XicOo");
    	confBuilder.setOAuthAccessToken("780871500903559169-aDLQ12mUVopKB7S06XhNsNwudCgd2ky");
    	confBuilder.setOAuthAccessTokenSecret("sAULr1rfDJeey1jfuCVzl2ssunQ8bCyRx4xnjln0Jsb5z");
    	//Configuration config = confBuilder.build();
    	
    	TwitterFactory tf = new TwitterFactory(confBuilder.build());
    	twitter4j.Twitter twitter= tf.getInstance();
    	
    	List<status> status =twitter.getHomeTimeline();

    	
        
        System.out.println( "Git try" );
    }
}
