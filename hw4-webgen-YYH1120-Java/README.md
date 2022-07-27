*Use this file to document the detected design problems and your fixes. For instructions see the main branch of this repository.*

# Design Fixes

In each subsection, provide a short answer, typically not longer than a few bullet points or sentences, addressing all parts of the question indicated in square brackets. Where appropriate point to specific files/classes/methods and *use the design terminology* from the lecture (referring to specific design goals/principles/heuristics/patterns).
 
We ask for exactly one instance of a problem. The code often has more problems, so you may chose which ones you describe.

Also, your discussions may overlap in that you may have addressed several design problems with the same change. This is okay if a change indeed relates to multiple design issues or questions. However, keep in mind that you must make **at least 4** unique refactorings overall. 



## SubSubSubSubSubArticles

The way nested articles are represented in the implementation is problematic. Briefly describe why it is problematic and how you fixed it:



[why a problem]

The implementation of nested articles is problematic since it makes the project with very poor extensibility and efficiency. Each time when the user wants to change the layer number of directory, the designed must create the new class (e.g. Subsubsubarticle) and re-implement the corresponding functions in all relevant documents which reduce the flexibility and make the program cumbersome.


[how fixed] 

To fix it, we implement a new design pattern named composite pattern. This pattern make the article with a tree data structure and work with these structures as if they were individual objects. Each node in the tree is an article class. Each article has a new field named Parent and new methods of get and set Parent. If the article is the root of the tree, it has no parent. And if the article is a leaf of the tree, it has no inner articles. In this way, we substitute the other subarticle class. Only one class could achieve all the implementations. 

In the other classes which involved SubArticle and SubsubArticle class, we need to rewrite the function. Instead of using nested loop, we implement recursive algorithm to traverse all the nodes in the article tree (e.g. DFS) and perform the corresponding operations on the nodes (e.g. render the article or find the article by topics). In this way, the program can implement a file system of any depth for the construction of web pages. It improves the extensibility and avoids some unnecessary coupling. 


## Responsibility Assignment

Describe a problem with responsibility assignment where fields or methods were in inappropriate places, why this was problematic, and how you fixed it:



[problem] 

The method of findArticlesByTopic in WebGen is assigned wrongly. When the Renderer needs to call the method, it must create a new class of WebGen and then call the function. 

[location] 

The findArticlesByTopic method in WebGen is assigned wrongly.

[why a problem] 

The Renderer could directly call the function in itself instead of create a new class only for calling this method. This would increase the coupling and redundancy of the code. And also it would waste the memory of the system.

[how fixed]

Move the function findArticlesByTopic to Renderer class to create a new method. And the Renderer could call it directly instead of creating some unnecessary classes.


## Code Duplication

Describe an instance of duplicate code that could be abstracted and reused using methods, inheritance, delegation, or design patterns and how you fixed it:



[problem]

The classes of Subclass and Subsubclass are duplicated. They could be substituted by the Article class. For the article class, we add the fields of Article.parent and the methods of get and set parent. In the Renderer class, the method of rendersubarticle and rendersubsubarticle are redundant. We could achieve these function with just one method named renderarticle.

[location] 

The class Renderer has two duplicated methods which are rendersubarticle and rendersubsubarticle.

[how fixed]

In short, we implemented the composite pattern. First, we delete the class of Subarticle and Subsubarticle. And then we add the parent field in Article and get parent, set parent methods in Article class. For the renderarticle function, we implemented the recursive method to traverse and render the articles in tree structure.


## Coupling

Describe an instance of unnecessary coupling, describe why this coupling is bad, and how you fixed it:



[problem] 

The findArticlesByTopic method in WebGen would make the project more coupling. The WebGen should not do the function. The function should be responsible for the Renderer class that is why the method is only called by Renderer class. 

[location] 

The findArticlesByTopic method in WebGen class.

[why a problem] 

Without change, when the designed wants to modify the method, two classes need to make changes and be influenced. But if we move the method to Renderer class, it is decoupled with the WebGen class. In this way, if we want to make some changes to it, we only focus on the Render class, and it would not influence the implementation of any other classes.

[how fixed]

Move the findArticlesByTopic method from WebGen class to Renderer class.



## Cohesion

Describe an instance of objects, classes, or methods that severely violate cohesion (e.g., god class). Describe what concerns are mixed and how you fixed it:



[location] 

The DirectoryBuilder is in ProjectBuilder class but it seems nothing to do with the ProjectBuilder.

[concerns] 

The ProjectBuilder is a god class which knows and does too much that not be responsible for. The DirectoryBuilder in ProjectBuilder is unnecessary. It could be implemented as a new class independently.

[how fixed]

Remove the DirectoryBuilder in ProjectBuilder and create a class named DirectoryBuilder to do the same work.

## Extensibility

Describe an aspect of the implementation that is unnecessarily difficult to extend (i.e., require modifications of existing methods, possibly in multiple places) and how you improved it:



[location] 

The class of SubArticle and SubSubArticle and the methods related with them. 

[why a problem] 

It makes the project with a poor extensibility. When we want to build a deeper file system, we need to add the SubsubsubArticle class and write a lot of new methods.

[how fixed]

So we need to remove these two classes and rewrite the methods related with then. Instead of using nested loop, we should implement the recursive algorithm to these methods to make the project more extensible.


## Encapsulation

Describe an instance where an object/class violates encapsulation principles. Describe why it is problematic and how you fixed it:



[location] 

The encapsulation problem is located in the Topic Class. The fields of name and id are public.

[why a problem] 

It is a problem because it violates the principle of information hiding in software construction. The fields of name and id should not be access to the client directly in case of making some change accidently. 

[how fixed]

It should modify the type from public to private to hide the information of fields.



## Instanceof

The use of `instanceof` is often an indication of a design problem. Provide an example of a method that uses `instanceof`, discuss why this use is problematic, and how you fixed it.



[location] 

The printSize function in CLI class has implemented instanceof to judge the specific class of input.

[why a problem] 

Using instanceof means you are treating subclasses which come from the same superclass in different way. And the type is not an efficient abstract class then. The abstract class should be modified.

[how fixed]

Change the name of getTextSize(), getImageSize(), getVideoSize() to getSize(). Add the getSize() in AbstractContent class and overrides the method of getSize() in subclasses. Therefore, for different subclasses, we could just call the getSize to return the size of different types of content.



## Events (Challenge Problem)

If you want to claim bonus points for implementing events, briefly describe your design and how you ensure that you can nest events in articles and vice versa:

[design description, one paragraph]

