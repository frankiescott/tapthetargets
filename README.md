# cs441-project6

This application is an interface shell for a game that will be developed during Project 7. The application consists of a splash screen and a main menu screen that links to the game screen, a how to play screen, and a leaderboard screen.

I started by creating a generic splash screen image to display upon application startup. I then created a menu screen for the splash screen to switch to and programmed the transition to take place once all game assets have been loaded, and if the splash screen displayed for at least three seconds. From here, I created the button interface using the `glassy-ui` skin for the menu screen featuring a 'Play' button to launch the game, a 'How to Play' button to launch the instructions screen, and a 'Leaderboard' button to launch the leaderboard screen. I then created a 'Back' button for the how to play and leaderboard screens so the user has fluent control over the menu interface.

For the leaderboard screen, I used a scene2d library object called `ScrollPane` within a table container to display a scrollable list of entries. I loaded in placeholder text to dial in desired parameters, then began implementing the loading procedure to dynamically populate the leaderboard. At first, I was going to use an internal data text file to save and load leaderboard information, but I discovered the ability to utilize the internal game preferences provided by the LibGDX library. I created a `leaderboard` entry in the preferences to store 10 `<key, value>` pairs with the key respective to the player's position on the leaderboard. The value stored is the player's name and their score. For example, I can access the #1 listed player and their respective high score with the key `"1"` to return a value such as `Frankie 922`

For the how to play screen, I learned how to use a library object called `FreeTypeFontGenerator` to generate fonts that scale smoothly within the application. I imported `.ttf` font files to the project and used them to draw placeholder text to the screen that will be updated once implementation of the game is finished.

The game screen consists of placeholder code that bounces the default `badlogic` image back and fourth against the boundaries of the screen. I imported my object and interface structure from Project 2 that I will use to implement the code for the player and enemies at a later time.

Finally, I created custom graphics to use for a splash screen, background, and a logo for the menu screen. 

# Development Schedule

July 19th - Project initialization & splash screen

July 20th - Main menu screen & splash screen transition

July 21st - Game screen & how to play screen

July 23rd - Leaderboard screen

July 24th - Leaderboard data setup

July 26th - Leaderboard data loading, graphics, and positioning final touches

# Project 7

Project 7 is an extension of Project 6 that deals with the implementation of the game.

I started by implementing the logic behind how scores were submitted to the leaderboard. I created a simple score submission screen with a text box for the player to enter their name, paired with a submit button. I then wrote an algorithm that compares the player's score to the last leaderboard entry. If the player's score is higher, the algorithm searches for the position on the leaderboard the player's score should be submitted to, pushes down all leaderboard entries by one slot (the 10th entry gets removed), then saves the score. Lastly, I created a function that initialized the end-of-game process.

Next was the implementation of the game. I wanted to create a game that was simple to play, but with a complex score system that produces varied results and gives the game a bit of a skill curve. I started by spawning black boxes randomly across the game screen every second. I coded the objects so when they are tapped, they disappear. I gave tapping a black box add a score of 50. It is also possible for boxes to spawn on top of each other, and if the player is able to destroy two boxes with one tap, they receive bonus points. If the player taps on the screen but does not tap a box, they lose 5 points. Frequent mistaps increase the point loss. The first time the player mistaps, they lose 5 points. The second time the player mistapes, they lose 10 points, etc. This ensures that players can not spam the screen with taps. They will be penalized heavily if they do so. Taps must be aimed and have a purpose! Once I had some basic gameplay implemented, I created a simple countdown system so the player has some time to prepare before the game starts. 

I also added a streak count that keeps track of successful taps. Players receive a point bonus for maintaining streaks. The player earns 50 points plus their streak value for every successful tap. Upon a mistap, the streak resets to zero. I then added some variation to how the blocks spawned on screen. In the beginning of the game, the blocks spawn every 800 milliseconds. Every time a block is spawned, the spawn time decreases by 10 milliseconds. This causes the blocks to spawn quicker and quicker as the game progresses. Once the block spawn decreases to 100 milliseconds from 800, the game ends. 

I then changed the black boxes to targets so the game was more aesthetically pleasing as a final touch to complete development of the game. Lastly, I implemented an HTTP get request to the leaderboard saving method so scores are posted to the school's database.

# Development Schedule

July 27th - Saving scores to the leaderboard

July 28th - Bug fixing

July 31st - Created score screen & basic code cleanup

August 2nd - Added widets to score screen & wrote end game function process

August 5th - Basic implementation of the game & countdown process

August 6th - Score calculation system and score display

August 7th - Finishing touches

August 8th - Score posting to web server
