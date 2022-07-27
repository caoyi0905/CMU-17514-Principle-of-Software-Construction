package org.antiantibug.DataPlugin;

import io.github.redouane59.twitter.TwitterClient;
import io.github.redouane59.twitter.dto.endpoints.AdditionalParameters;
import io.github.redouane59.twitter.dto.tweet.Tweet;
import io.github.redouane59.twitter.dto.tweet.TweetList;
import io.github.redouane59.twitter.signature.TwitterCredentials;
import org.antiantibug.framework.core.DataPlugin;
import org.antiantibug.framework.core.Framework;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;


public class QueryPlugin implements DataPlugin {
    private static final String NAME = "Twitter_query";
    private static final int PARAM_NUM = 3;
    private static final String[] PARAM_TEXT = new String[]{"Query", "Start-Time(yyyy-MM-dd HH:mm:ss)",
            "End-Time(yyyy-MM-dd HH:mm:ss)"};

    private String title;
    private Framework framework;
    private final Map<String, Object> rawData = new HashMap<>();
    private final List<String> tweetText = new ArrayList<>();
    private final List<Long> createdTime = new ArrayList<>();

    private String query;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private TweetList tweetList;
    // private List<String> tweetIds;
    private AdditionalParameters additionalParameters;

    private TwitterClient twitterClient = new TwitterClient(TwitterCredentials.builder()
            .accessToken("1512313989765681156-sXSkdqptOGy5HJI9EmZjImVA6WBmrK")
            .accessTokenSecret("4IP9SrnL4VOCQkXj7qlI2BgMdFte0GMZgO9xKDY80qeBF")
            .apiKey("QV4vVBYzljvOxE6UP2RHJBcAq")
            .apiSecretKey("Tv9FMejzaVn2mVZZ7Y9LbBeLL1pSYj0Oy0QkOOOnV5mnauFNXC")
            .build());

    @Override
    public String getPluginName() {
        return NAME;
    }

    @Override
    public void register(Framework framework) {
        this.framework = framework;
    }

    @Override
    public int getParamNum() {
        return PARAM_NUM;
    }

    @Override
    public String getParamText(int paramNum) {
        return PARAM_TEXT[paramNum - 1];
    }

    @Override
    public String getTitle() {
        title = "Searched tweets of " + query;
        return title;
    }

    @Override
    public String getURL() {
        return "";
    }

    @Override
    public String getMediaType() {
        return "twitter";
    }

    @Override
    public String getContentType() {
        return "tweets";
    }

    @Override
    public long getCount() {
        return (long) tweetText.size();
    }

    @Override
    public Map<String, Object> provideData(Map<String, String> params) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        this.query = params.get(PARAM_TEXT[0]);
        this.startTime = LocalDateTime.parse(params.get(PARAM_TEXT[1]), formatter);
        this.endTime = LocalDateTime.parse(params.get(PARAM_TEXT[2]), formatter);
        additionalParameters = AdditionalParameters.builder()
                .startTime(startTime)
                .endTime(endTime)
                .build();
        tweetList = searchTweets();
        getTweetText();
        getCreatedTime();
        addToMap();
        return rawData;
    }

    private TweetList searchTweets() {
        TweetList list = twitterClient.searchTweets(query, additionalParameters);
        if (list == null) {
            System.out.println("Can not get user's tweets.");
            return null;
        }
        return list;
    }

    private void getTweetText() {
        for (Tweet tweet : tweetList.getData()) {
            tweetText.add(tweet.getText());
        }
        Collections.reverse(tweetText);
    }

    private void getCreatedTime() {
        for (Tweet tweet : tweetList.getData()) {
            long longTimeStamp = tweet.getCreatedAt().toInstant(ZoneOffset.ofHours(20)).toEpochMilli();
            createdTime.add(longTimeStamp);
            // createdTime.add(tweet.getCreatedAt());
        }
        Collections.reverse(createdTime);
    }

    private void addToMap() {
        rawData.put("title", getTitle());
        rawData.put("content", tweetText);
        rawData.put("time", createdTime);
        rawData.put("contentType", "tweets");
        rawData.put("mediaType", "twitter");
        rawData.put("count", (long) tweetText.size());
    }
}
