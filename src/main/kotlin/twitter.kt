import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder

class TwitterTalk{

    lateinit var tf : TwitterFactory

    constructor(){
        val cb = ConfigurationBuilder()
        cb.setDebugEnabled(true)
            .setOAuthConsumerKey(Config.CONSUMER_KEY)
            .setOAuthConsumerSecret(Config.CONSUMER_SECRET)
            .setOAuthAccessToken(Config.ACCESS_KEY)
            .setOAuthAccessTokenSecret(Config.ACCESS_SECRET)
        this.tf = TwitterFactory(cb.build())
    }

    fun getInstance() : Twitter {
        return tf.getInstance()
    }

}