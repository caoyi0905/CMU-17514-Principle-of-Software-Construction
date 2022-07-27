package edu.cmu.webgen.project;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class DirectoryBuilder {
    private final String directoryName;
    private final LocalDateTime created;
    private final LocalDateTime lastUpdate;
    private final boolean isTopLevelDirectory;
    private final boolean isProjectDirectory;
    private final List<AbstractContent> content = new ArrayList<>();
    //        private final List<Article> innerSubSubArticles = new ArrayList<>();
//        private final List<Article> innerSubArticles = new ArrayList<>();
    private final List<Article> innerArticles = new ArrayList<>();
    private final List<Event> innerEvents = new ArrayList<>();
    private Metadata metadata = new Metadata();

    DirectoryBuilder(@NotNull String directoryName, @NotNull LocalDateTime created, @NotNull LocalDateTime lastUpdate, boolean isProjectDirectory, boolean isTopLevelDirectory) {
        this.directoryName = directoryName;
        this.created = created;
        this.lastUpdate = lastUpdate;
        this.isProjectDirectory = isProjectDirectory;
        this.isTopLevelDirectory = isTopLevelDirectory;
    }

//        private boolean isEvent(Metadata metadata) {
//            return metadata.isDate("startdate") || metadata.isDate("enddate");
//        }
//
//        Event buildEvent() {
//            var startDate = metadata.isDate("startdate") ? metadata.getDate("startdate") : metadata.getDate("enddate");
//            var endDate = metadata.isDate("enddate") ? metadata.getDate("enddate") : metadata.getDate("startdate");
//            var newEvent = new Event(content, innerSubEvents, innerSubArticles, directoryName, created, lastUpdate, startDate, endDate);
//
//            List<SubEvent> allSubEvents = findSubEvents(newEvent);
//
//            for (SubEvent innerEvent : allSubEvents)
//                if (!innerEvent.hasParentEvent())
//                    innerEvent.setParentEvent(newEvent);
//            for (SubArticle subArticle : innerSubArticles)
//                subArticle.setParent(newEvent);
//            for (SubEvent subEvent : innerSubEvents)
//                subEvent.setParent(newEvent);
//
//            return newEvent;
//        }
//
//
//        Event buildSubEvent() {
//            var startDate = metadata.isDate("startdate") ? metadata.getDate("startdate") : metadata.getDate("enddate");
//            var endDate = metadata.isDate("enddate") ? metadata.getDate("enddate") : metadata.getDate("startdate");
//            var newSubEvent = new SubEvent(content, innerSubEvents, innerSubArticles, directoryName, created, lastUpdate, startDate, endDate);
//
//            List<SubEvent> allSubEvents = findSubEvents(newSubEvent);
//
//            for (SubEvent innerEvent : allSubEvents)
//                if (!innerEvent.hasParentEvent())
//                    innerEvent.setParentEvent(newSubEvent);
//            for (SubArticle subArticle : innerSubArticles)
//                subArticle.setParent(newSubEvent);
//            for (SubEvent subEvent : innerSubEvents)
//                subEvent.setParent(newSubEvent);
//
//            return newSubEvent;
//        }


    Article buildArticle() {
        assert this.isTopLevelDirectory;
        if (this.innerArticles == null){
            var n = new Article(this.content, null, this.directoryName, this.created, this.lastUpdate);
            n.addMetadata(this.metadata);
            return n;
        }else{
            var newArticle = new Article(this.content, this.innerArticles, this.directoryName, this.created, this.lastUpdate);
            for (Article subArticle : this.innerArticles)
                subArticle.setParent(newArticle);
//            for (SubEvent subEvent : innerSubEvents)
//                subEvent.setParent(newArticle);
            newArticle.addMetadata(this.metadata);
            return newArticle;
        }

    }

//        Article buildSubArticle() {
//            assert !this.isTopLevelDirectory;
//            var newSubArticle = new Article(
//                    this.content, this.innerSubSubArticles, this.directoryName, this.created, this.lastUpdate);
//            for (Article subArticle : this.innerSubSubArticles)
//                subArticle.setParent(newSubArticle);
////            for (SubEvent subEvent : innerSubEvents)
////                subEvent.setParent(newSubArticle);
//            newSubArticle.addMetadata(this.metadata);
//            return newSubArticle;
//        }
//
//        Article buildSubSubArticle() {
//            assert !this.isTopLevelDirectory;
//            if (this.innerArticles.size() > 0)
//                throw new ProjectFormatException("Not supporting sub-sub-sub-articles (technical limitation)");
//            var n = new Article(this.content, null, this.directoryName, this.created, this.lastUpdate);
//            n.addMetadata(this.metadata);
//            return n;
//        }


    Project buildProject(HashMap<Object, Set<Topic>> topics) throws ProjectFormatException {
        assert this.isProjectDirectory;
        if (!this.metadata.has("title"))
            throw new ProjectFormatException("Project has no title. Provide a .yml file with a \"title\" entry in the project directory.");
        if (!this.metadata.has("organization"))
            throw new ProjectFormatException("Project has no organization. Provide a .yml file with an \"organization\" entry in the project directory.");

        return new Project(this.metadata.get("title"), this.metadata.get("organization"), this.innerArticles,
                this.innerEvents, topics);
    }


    public void addArticle(Article article) {
        this.innerArticles.add(article);
    }

//        public void addEvent(Event entry) {
//            innerEvents.add(entry);
//        }

//        public void addSubArticle(Article subArticle) {
//            this.innerSubArticles.add(subArticle);
//        }
//
//        public void addSubSubArticle(Article subsubarticle) {
//            this.innerSubSubArticles.add(subsubarticle);
//        }

    public void addMetadata(Metadata thatMetadata) {
        this.metadata = this.metadata.concat(thatMetadata);
    }

    public void addContent(AbstractContent node) throws ProjectFormatException {
        if (this.isProjectDirectory)
            throw new ProjectFormatException("Project may not contain content other than articles and events, found " + node);
        this.content.add(node);
    }
}