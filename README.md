# 3D-model-of-a-small-part-of-AUI-campus-Computer-Graphics-Class
This project is about implementing a 3D model of a small part of AUI campus using a small graphics engine.


Simulating real world places is always really interesting because it allows experiencing their sight from anywhere in the planet, and if done right it can help solve some interesting optimization problems that otherwise would take a much longer time.


After learning how to manually create a cube and a pyramid during the first part of the course, it became obvious that making any kind of more complex shapes would be extremely hard, the challenge would be more on finding out where each point of an objects geometry is rather than learning new and more complex computer graphics generation techniques.


So, the first question that came into my mind: is there any way to make 3D models using a well-known software, and then rendering them using Java code?


After some research I discovered that it is indeed possible, and that there are multiple files types (such as .obj) dedicated to storing objects information. However, due to their simplicity .obj files are preferred for people that just started to learn openGL.


This is when I found the very good YouTuber ThinMatrix, who made a Java Game Engine from scratch and who explains very nicely how he went through every single step. He made a file called objLoader.java that reads our .obj files and transforms them into Matrices ready to be used in our Java Engine.


Used tools:

• Sketch-UP and Blender for 3D modelling.

• Photoshop to make UV Maps (used for projecting a 2D image to a 3D model's surface for texture mapping)

• Eclipse to run the Java Engine that is used to render the modeled objects, as well as the terrain, lights, water, the sky, etc.


It is important to note that the Java Engine is made by ThinMatrix, a YouTuber that makes a tutorial series on how to create a 3D Java game with OpenGL using the library LWJGL (Light Weight Java Game Library).


Link to his YouTube channel: https://www.youtube.com/user/ThinMatrix

--------------------------------------------------------------------

This project was made using Eclipse IDE. To run the 3D Engine perform he following steps:

1 - Open Eclipse.

2 - Go to the menu bar, click on import, choose Existing Projects into Workspace.

3 - Choose Browse next to Select root directory and locate the folder where this ReadMe.txt file is. You should see under "Projects:" in the rectangular box a name appear titled "3D Game Engine". Select it and click Finish.

4 - You should now see a project in the Package Explorer called "3D Game Engine", right clic on it, go to properties, Java Build Path.

5 - If netbeans doesn't seem to find the jars there, double clic on every jar (4 in total), and go to the folder where this ReadMe.txt file is, then go to lib, then jars, you will find all the jars there, choose the corresponding one.

6 - Under the jars in Java Build Path you will see JRE System Library, expand it, then select Native library location, then clic edit, then External Folder then go to the same folder this ReadMe.txt file is, then lib, then native-win, then keep clicking ok until you're back to the main Eclipse window.

7 - To run the program in the Package Explorer, go to 3D Game Engine, then expand src, then expand engineTester, open MainGameLoop.java and run it.

Instructions written by Aymane Chaoui.
