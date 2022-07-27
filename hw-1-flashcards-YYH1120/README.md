**Note:** this repository contains 2 folders, one containing the starter code written in Typescript, one containing the starter code written in Java. Everyone is expected to complete all of the tasks twice, once for each language. 

# Homework 1: Warm-Up with Flash Cards

In this assignment, you will complete a simple [flashcard](https://en.wikipedia.org/wiki/Flashcard) learning system. For now we are keeping things simple and use an interactive command-line interface, one of the simplest and oldest user interfaces for computer programs. The goals of this assignment are to familiarize you with our course infrastructure, let you practice object-oriented programming in Java and TypeScript, and start building on public libraries. 

## Provided Implementation

Like often common in practice, you won't start entirely from scratch but start with an existing implementation. Fortunately, it's even somewhat documented and clean, so you should be able to figure out what's happening by reading documentation and code. You may change existing code if you like.

With the GitHub classroom link on Canvas create a Git repository with the provided code. The repository has 1 folder for Java and 1 folder for TypeScript. You will complete the tasks for this homework once **in each language**. 

You should use an IDE to load and edit the projects, such as [IntelliJ](https://www.jetbrains.com/idea/) or [VSCode](https://code.visualstudio.com/).  You may prefer to use IntelliJ for Java and VSCode for Typescript (though note you can get free access to Enterprise IntelliJ via an Education discount, which will give you access to Typescript support). 

## Tasks

**Task 1: Implement new card organizer.** Implement a new card organizer *RecentMistakesFirstSorter* by creating a class that implements the `CardOrganizer` interface. The organizer should work as follows: *"Orders the cards so that those that were answered incorrectly in the last round appear first. This reordering should be stable: it does not change the relative order of any pair of cards that were both answered correctly or incorrectly. So, if no cards were answered incorrectly in the last round, this does not change the cards' ordering."* 

Starting points: try to dissect the specification into its main components.  What should the behavior be under "typical" inputs (e.g., one card with a recent failure, one without; cards with several successes and failures), and what scenarios does it outline as exceptions? Do avoid implementing anything extra that is not part of the specification.  

Note that only a relatively small amount of code is necessary to implement this new class, regardless of language. Only minimal changes will be required outside of your new class, in particular to test the new sorter by using it in place of the sorter the code starts with (`CardShuffler`/`newCardShuffler`). For Java, you don't need to use Java streams if you are not comfortable with them; you can use regular `for`-loops if you prefer.

**Task 2: Command-line interface.** There is already some implementation of a textual user interface that prints questions and reads answers.  But, the codebase implements a number of different card ordering and filtering mechanisms; the UI does not take advantage of them.  It also doesn't read in a filename for the card file; this is hard-coded.  This isn't very usable!

Your task is to develop a proper command-line interface that parses provided arguments and sets up the right card deck and the organizing mechanisms. Parsing command-line options is a standard task that has been done many times before, no there's need to start entirely from scratch! Instead, you should find and use an **open-source library** for command-line options and use it to provide an interface comparable to this (does not need to be exactly the same, but should have the same functionality):

```
flashcard <cards-file> [options]

Options:
  --help          Show this help                                                                        
  --order <order> The type of ordering to use, default "random"
   						[choices: "random", "worst-first", "recent-mistakes-first"] 
  --repetitions <num> The number of times to each card should be answered
                  successfully. If not provided, every card is presented once,
                  regardless of the correctness of the answer.          
  --invertCards   If set, it flips answer and question for each card. That is, it 
                  prompts with the card's answer and asks the user
                  to provide the corresponding question. 
                  Default: false

```

(The program does not need to be runnable using the `flashcard` keyword as above; we just used that to illustrate concisely.)

Your code should provide these options and check that valid values are provided, reporting errors (and exiting) otherwise. When your program is started, parse these parameters with your library and then start the user interface with suitable parameters. For example, the program should read the cards from the file provided as command line argument and should flip the cards when `--invertCards` is provided. You should make your *RecentMistakesFirstSorter* from Task 1 available through this command line interface (this corresponds to the `"recent-mistakes-first"` option).  

**All** of these options can be configured using the existing codebase, meaning you **do not need** to add any new functionality to the program. Rather, the only changes you need to make are to the program's dependencies, and to its entry point, so that it functions as a command-line interface described above. All of your code for this task will therefore most likely be within `index.ts` and `Main.java`. 

You are free to use any open source library on *Maven Central* or *npm* for this project. There are many many choices with different levels of quality and documentation (e.g., [Apache Commons CLI](https://commons.apache.org/proper/commons-cli/), [jopt](https://github.com/jopt-simple/jopt-simple) [JArgs](http://jargs.sourceforge.net/), [yargs](https://yargs.js.org/), [args](https://www.npmjs.com/package/args), [commander](https://www.npmjs.com/package/commander)), explore them and pick one.

**Infrastructure and quality requirements.** 

* Push all your code to GitHub using good practices (e.g., cohesive commits, descriptive commit messages). 
* Your code should compile and pass automated checks when executed with the build tool (*maven build* or *npm build*). Your code will automatically be executed on [GitHub Actions](https://github.com/features/actions), a continuous integration service. Your build should succeed on GitHub Actions, however, GitHub Actions is not configured as an auto-grader for this assignment and does not perform any tests. Passing GitHub Actions is just a minimum bar, not a sufficient condition for completing the homework. You can find the results of the automated checks in the _Actions_ tab of your GitHub repository.
* In order to keep runtime minimum for the automated checks, all checks are run with a timeout of 2 minutes, which should be plenty of time to run the checks for this assignment. If you do run into an issue where your build fails and you think it was an internal GitHub issue with running the automated checks, you can manually rerun that test. If you go to the _Actions_ tab of your GitHub repository and click the failed build, at the top right you should see a button that says _Re-run all jobs_. Clicking that will rerun the test. If you still get a timeout issue even after this, try waiting a bit (maybe 15 minutes or so) then rerun again, or come to office hours.
* Follow good design practices as discussed: Hide information where appropriate. Program against interfaces, not against classes.
* For all new code that you write, follow the style guidelines of the language you are working in ([Java code conventions](https://www.oracle.com/java/technologies/javase/codeconventions-introduction.html), [StandardJS guidelines](https://standardjs.com/)). We have installed tools (CheckStyle and ESlint) that will automatically check conformance to many style guidelines in your repository.
* If you add libraries, add them as *maven* or *npm* dependencies. Do *not* copy library code into the repository.


**Hints.** The first recitation covers some basics of working with Git and provides guidance on how to set up your development environment. The second lecture covers basic design principles for object-oriented design, especially encapsulation. The subsequent readings provides pointers to relevant language concepts, but you will probably need to engage with language documentation beyond the presented basic concepts yourself (for example, the provided code uses recent Java features and ES6 features). The second recitation will cover best principles when working with Git at a more in depth level. 


## Checkpoint 

This assignment spans 2 weeks. We will be requiring a checkpoint submission containing the following after 1 week: 

* A reasonable implementation of Task 1 in *one* language (can be either TypeScript or Java).
* Screenshot evidence of the starter code running in the other language **saved in the top level directory** of the repository as `checkpoint.jpg`. 

You do not need to have fully completed code in the other language to satisfy the checkpoint requirements; just show us that you have it set up.  We will go over setup in Recitation 1, so this should be straightforward!

## Submitting your work

Always submit all code to GitHub. Once you have pushed your final code there, submit a link to your final commit on Canvas. A link will look like `https://github.com/CMU-17-214/<reponame>/commit/<commitid>`. You can get to this link easily when you click on the last commit (above the list of files) in the GitHub web interface.

## Evaluation

The assignment is worth 150 points. We expect to grade the assignment approximately with this rubric:

**Checkpoint (30pt):**
- [ ] 30: The first task is reasonably implemented in *one* language and a screenshot demonstrates the starter code running in the other language.
 
**New card organizer (40pt):**

- [ ] 30: The implementation implements the above specification correctly and nothing more
  - [ ] 20: In *one* language
  - [ ] 10: In *both* languages 
- [ ] 5: The implementation is reasonably well documented, using the API documentation style of the language.
  - [ ] 2.5: TypeScript
  - [ ] 2.5: Java
- [ ] 5: The implementation is clean & concise. It does not introduce unnecessary variables, dead or out-commented code, particularly strange indentation. It follows coding conventions of the language.
  - [ ] 2.5 TypeScript
  - [ ] 2.5 Java 

**Command-line processing (60pt):**

- [ ] 10: The implementation makes use of an external library, imported through a package manager
- [ ] 25: The implementation parses target files for card decks and all 5 options listed above. It rejects invalid options or arguments with an error message.
  - [ ] 15: In *one* language
  - [ ] 10: In *both* languages 
- [ ] 15: The implementation responds correctly to the command-line options (opens the right card deck, uses the right organization strategies, lists help, etc)
  - [ ] 10: In *one* language
  - [ ] 5: In *both* languages 
- [ ] 5: The implementation is well organized and does not expose unnecessary implementation details (encapsulation) and it programs against interfaces, not classes. 
  - [ ] 2.5: TypeScript
  - [ ] 2.5: Java 
- [ ] 5: The implementation does not introduce unnecessary variables, dead or out-commented code, particularly strange indentation. It follows coding conventions of the language. 
  - [ ] 2.5: TypeScript
  - [ ] 2.5: Java  

**Infrastructure and style (20pt):**

* [ ] 5: Commits are reasonably cohesive
* [ ] 5: Commit messages are reasonable
* [ ] 10: The build passes on GitHub Actions, including the automated style checks
