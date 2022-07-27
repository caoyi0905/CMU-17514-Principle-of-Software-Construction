package edu.cmu.webgen;

import edu.cmu.webgen.project.*;
import edu.cmu.webgen.rendering.Renderer;
import edu.cmu.webgen.rendering.TemplateEngine;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Command-line interface processing all actions
 */
public class CLI {

    private final Project project;

    public CLI(Project project) {
        this.project = project;
    }

    /**
     * run the program with the provided options
     *
     * @param options parsed command-line arguments
     */
    public void run(WebGenArgs options) {

        if (options.isListArticles())
            printArticles(options.isListAll(), options.isListTopics(), options.getArticleSorting());
        if (options.isListEvents())
            printEvents(options.isListAll(), options.isListTopics());
        if (options.isListTopics())
            printTopics();
        if (options.printSize())
            printSize();

        if (options.isRender()) {
            if (options.cleanTargetDirectory() && options.getTargetDirectory().exists()) {
                cleanTargetDirectory(options.getTargetDirectory());
            }
            options.getTargetDirectory().mkdirs();
            try {
                new Renderer(options.getTargetDirectory(), options.getArticleSorting(), new TemplateEngine())
                        .renderProject(this.project);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * get size of the story's content files in byte
     */
    private void printSize() {
        List<AbstractContent> allContent = new ArrayList<>();
        for (Article a : this.project.getArticles()) {
            allContent.addAll(a.getContent());
            for (Article sa : a.getInnerArticles()) {
                allContent.addAll(sa.getContent());
                for (Article ssa : sa.getInnerArticles()) {
                    allContent.addAll(ssa.getContent());
                }
            }
        }
        long size = 0;
        for (AbstractContent c : allContent) {
            size += c.getSize();
        }
        System.out.println("Project size in bytes: %d".formatted(size));
    }

    private void cleanTargetDirectory(File targetDirectory) {
        if (targetDirectory.exists()) {
            for (File f : targetDirectory.listFiles()) {
                if (f.isDirectory()) {
                    try {
                        FileUtils.deleteDirectory(f);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    f.delete();
                }
            }
        }
    }

    private void printTopics() {
        System.out.println("Topics: ");

        findAllTopics().stream().sorted().forEach(t ->
                System.out.println(" - %s".formatted(t.getName())));
    }

    private Set<Topic> findAllTopics() {
        Set<Topic> topics = new HashSet<>();
        for (Article a : this.project.getArticles()) {
            topics.addAll(this.project.getTopics(a));
            for (Article sa : a.getInnerArticles()) {
                topics.addAll(this.project.getTopics(sa));
                for (Article ssa : sa.getInnerArticles()) {
                    topics.addAll(this.project.getTopics(ssa));
                }
            }
        }
        return topics;
    }

    private void printArticles(boolean all, boolean topics, WebGenArgs.ArticleSorting sorting) {
        List<Article> topLeveLArticles = project.getArticles();
        topLeveLArticles.sort((o1, o2) -> {
            if (sorting == WebGenArgs.ArticleSorting.PINNED) {
                if (this.project.isArticlePinned(o1) && !this.project.isArticlePinned(o2)) return -1;
                if (!this.project.isArticlePinned(o1) && this.project.isArticlePinned(o2)) return 1;
            }
            if (sorting == WebGenArgs.ArticleSorting.PUBLISHED_FIRST)
                if (!o1.getPublishedDate().equals(o2.getPublishedDate()))
                    return 0 - o1.getPublishedDate().compareTo(o2.getPublishedDate());
            if (sorting == WebGenArgs.ArticleSorting.PUBLISHED_LAST)
                if (!o1.getPublishedDate().equals(o2.getPublishedDate()))
                    return o1.getPublishedDate().compareTo(o2.getPublishedDate());
            if (sorting == WebGenArgs.ArticleSorting.EDITED)
                if (!o1.getLastUpdate().equals(o2.getLastUpdate()))
                    return o1.getLastUpdate().compareTo(o2.getLastUpdate());
            return o1.getTitle().compareTo(o2.getTitle());
        });

        System.out.println("Articles: ");
        for (Article topLeveLArticle : topLeveLArticles) {
            String topicStr = topics ? getTopicsStr(this.project.getTopics(topLeveLArticle)) : "";
            System.out.println(" - %s (%s) %s".formatted(topLeveLArticle.getTitle(),
                    WebGen.readableFormat(topLeveLArticle.getPublishedDate()), topicStr));
            if (all) {
//                for (Article sa : topLeveLArticle.getInnerArticles()) {
//                    String topicStr2 = topics ? getTopicsStr(this.project.getTopics(sa)) : "";
//                    System.out.println("   - %s (%s) %s".formatted(sa.getTitle(),
//                            WebGen.readableFormat(sa.getPublishedDate()), topicStr2));
//                    for (Article ssa : sa.getInnerArticles()) {
//                        String topicStr3 = topics ? getTopicsStr(this.project.getTopics(ssa)) : "";
//                        System.out.println("     - %s (%s) %s".formatted(ssa.getTitle(),
//                                WebGen.readableFormat(ssa.getPublishedDate()), topicStr3));
//                    }
//                }
                recursiveprintarticle(topLeveLArticle,topics);
            }
        }
    }

    public void recursiveprintarticle ( Article article, boolean topics){

        if (article.getInnerArticles() == null){
            return ;
        }
        for (Article subarticle : article.getInnerArticles()){
            String topicStr2 = topics ? getTopicsStr(this.project.getTopics(subarticle)) : "";
            System.out.println("   - %s (%s) %s".formatted(subarticle.getTitle(),
                    WebGen.readableFormat(subarticle.getPublishedDate()), topicStr2));
            recursiveprintarticle(subarticle,topics);
        }
    }

    private void printEvents(boolean all, boolean topics) {
        System.out.println("Events not yet supported");
        List<Event> events = project.getEvents();

//        System.out.println("Events: ");
//        events.stream().sorted(new EventComparator()).forEach(event ->
//                printEvent(event, all, topics)
//        );
    }


//    private void printSubEvent(Event event, int indent, boolean printInner,  boolean topics) {
//        String prefix = "  ".repeat(indent);
//        String topicStr = topics ? getTopicsStr(event.getTopics()) : "";
//        System.out.println(prefix + " - %s-%s: %s%s".formatted(
//                DateUtils.readableFormat(event.getStartDate()),
//                DateUtils.readableFormat(event.getEndDate()),
//                event.getTitle(),
//                topicStr));
//        if (printInner)
//        for (SubEvent innerEvent : findSubEvents(event))
//            if (innerEvent.getParentEvent() == event)
//                printSubEvent(innerEvent, indent + 1, topics);
//    }

    private String getTopicsStr(Set<Topic> topics) {
        return topics.stream().sorted().
                map(Topic::getName).collect(Collectors.joining(", ", " [", "]"));
    }
}
