form http://hannesdorfmann.com/annotation-processing/annotationprocessing101/

**Q:**
Hi Hannes, Thanks for your tutorial. I just wanted to follow your example. I couldn't find javax.annotation.processing.jar, abstractprocessor can't be find in my android studio. I also searched on google, no right answer, could you please share this jar with me? or give me a link I can download. thanks in advance.

**A:**
Hi, what kind of module type have you chosen? You can't choose Android Application nor Android library, because for those types not the full java jdk will be used, but the android sdk (which is a striped version of the jdk + Android specific classes like Activiy, but as you already have seen some packages of the jdk like javax.annotation.* are missing). Furthermore, Android application (.apk) and Android library (.aar) are not packed as .jar file which is necessary. So the only module you could use in Android Studio is "Java Library". In my opinion it's easier to switch to a plain old Java IDE since Annotation Processing is kind of writing a "java application" and not Android specific, i.e. IntelliJ which powers Android Studio as well so you should be familiar with IntelliJ's UI as well and you are more flexible to choose from different build systems like ant, maven, gradle, etc. to generate the .jar file from your annotation processor code.

**Q:**
Hi Hannes,
Thanks for your help. it worked fine with executing your command 'mvn -q package exec:java' after I changed it to Eclipse IDE. I almost understood the whole process on how to write a customized annotation processor.
one more thing is how can I generate the code with my customized processor in PizzaStore maven project? It is very useful to developers who want to know how to generate MealFactory class in another project which want to use @factory annotation? and the same problem in android studio? How can I apply our customized annotation processor in our android studio project? Did I miss something?

**A:**
Hi, so you have packed your annotation processor in a .jar file like MyProcessor.jar . Well done so far! If you want to your annotation processor in another project you have to ensure that MyProcessor.jar is part of your other projects build patch. That may sound more complicated as it actually is. For instance. you have an Android App. So now in your apps.build gradle file you can specify dependencies, right? And here you have to add the dependency to MyProcessor.jar file. Usually android studio generates a build.gradle file with the following dependency:

compile fileTree(dir: 'libs', include: ['\*.jar'])

So what does that means? It tells gradle to include all .jar files from "libs" folder. You simply have to place your MyProcessor.jar file in the "libs" folder and everything should work. If you decide to publish MyProcessor.jar to maven central you could also use

compile 'com.mydomain:myprocessor:1.0.0'

I guess you get the point: You have to include "MyProcessor.jar file" as dependency somehow. This is nothing android studio nor gradle specific. if you would use maven or ant as build system for your java desktop application you are currently working on, then you also have to include the dependency to "MyProcessor.jar file".
