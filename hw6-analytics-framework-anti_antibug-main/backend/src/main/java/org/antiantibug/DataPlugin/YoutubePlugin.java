package org.antiantibug.DataPlugin;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;

import com.google.api.services.youtube.model.CommentSnippet;
import com.google.api.services.youtube.model.CommentThread;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import com.google.api.services.youtube.model.VideoListResponse;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.common.collect.Lists;
import org.antiantibug.framework.core.DataPlugin;
import org.antiantibug.framework.core.Framework;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YoutubePlugin implements DataPlugin {
    private static final String NAME = "Youtube";
    private static final int PARAM_NUM = 1;
    private static final String[] PARAM_TEXT = new String[]{"Video-ID"};

    private String title;
    private String type;
    private Framework framework;
    private final Map<String, Object> rawData = new HashMap<>();
    private final List<String> commentText = new ArrayList<>();
    private final List<DateTime> publishedTime = new ArrayList<>();

    private String videoId;
    private List<CommentThread> videoComments;

    private static YouTube youtube;
    private List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube.force-ssl");

    private void getCommentInfo() {
        try {
            Credential credential = Auth.authorize(scopes, "Youtube_Plugin");

            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
                    .setApplicationName("Youtube_Plugin")
                    .build();

            CommentThreadListResponse videoCommentsListResponse = youtube.commentThreads()
                    .list("snippet").setVideoId(videoId).setTextFormat("plainText").execute();
            videoComments = videoCommentsListResponse.getItems();

            if (videoComments == null) {
                System.out.println("Can not get video comments");
            }

            getCommentText();
            getPublishedTime();

        } catch (GoogleJsonResponseException e) {
            System.err.println("GoogleJsonResponseException code: " + e.getDetails().getCode()
                    + " : " + e.getDetails().getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void getCommentText() {
        for (CommentThread comment : videoComments) {
            CommentSnippet snippet = comment.getSnippet().getTopLevelComment().getSnippet();
            commentText.add(snippet.getTextDisplay());
        }
    }

    private void getPublishedTime() {
        for (CommentThread comment : videoComments) {
            CommentSnippet snippet = comment.getSnippet().getTopLevelComment().getSnippet();
            publishedTime.add(snippet.getPublishedAt());
        }
    }

    private void getVideoInfo() {
        try {
            YouTube.Videos.List listVideosRequest = youtube.videos().list("snippet").setId(videoId);
            VideoListResponse listResponse = listVideosRequest.execute();

            List<Video> videoList = listResponse.getItems();
            if (videoList == null) {
                System.out.println("Can not find video with ID: " + videoId);
                return;
            }

            Video video = videoList.get(0);
            VideoSnippet snippet = video.getSnippet();
            title = snippet.getTitle();
            type = snippet.getCategoryId();

        } catch (GoogleJsonResponseException e) {
            System.err.println("GoogleJsonResponseException code: " + e.getDetails().getCode()
                    + " : " + e.getDetails().getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        }
    }

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
        return title;
    }

    @Override
    public String getURL() {
        return "";
    }

    @Override
    public String getMediaType() {
        return "youtube";
    }

    @Override
    public String getContentType() {
        return "comments";
    }

    @Override
    public long getCount() {
        return rawData.size();
    }

    @Override
    public Map<String, Object> provideData(Map<String, String> params) {
        videoId = params.get(PARAM_TEXT[0]);
        getCommentInfo();
        getVideoInfo();
        addToMap();
        return rawData;
    }

    private void addToMap() {
        rawData.put("title", title);
        rawData.put("content", commentText);
        rawData.put("time", publishedTime);
        rawData.put("contentType", "comments");
        rawData.put("mediaType", "youtube");
        rawData.put("count", (long) commentText.size());
    }
}
