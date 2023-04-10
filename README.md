# Blackjack
## A simplified version of the *Blackjack* game at casinos
- This application is named as Blackjack. It is literally a simplified version of Blackjack game which simulates
the classic casino banking game **without the splitting practice**. 
- The user will play the role of ***player*** and the program will play the role of ***dealer***. Any user looking for simulated gambling will play this game. 
- I find this application interesting because this game is more complex than a blog program and the GUI design for card game is practical for my 
current knowledge of Java.Swing. I personally also like playing Blackjack game.

## User Stories
In the context of a Blackjack application:
- As a user, I want to be able to create a new player profile and save my game progress to the game records list (add unlimited number of game records to the game records list)
- As a user, I want to be able to load the game record that I choose from the game records list
- As a user, I want to be able to delete the game record that I choose from the game records list
- As a user, I want to be able to see the ranking of all players on the game records list sorted according to their assets
- As a user, I want to be able to 
- As a user, I want to be able to ***raise*** my bet for multiple times which means I can raise my bet $100 a time until my assets turns $0
- As a user, I want to be able to choose ***all in*** which means bet all of my current chips
- As a user, I want to be able to ***stop*** betting which means I can move on to the dealing stage of the game
- As a user, I want to be able to take an ***insurance*** on my bet when one of dealer's face-up card is A which means I can earn bonus back when dealer is blackjack
- As a user, I want to be able to ***skip*** the insurance and move on to the dealing stage of the game
- As a user, I want to be able to keep ***hitting*** for another card in addition to my original cards until I bust (keep adding a new card to player's cards)
- As a user, I want to be able to choose ***double*** which means hit for another card and double my initial bet
- As a user, I want to be able to choose ***stand*** when I do not want for hit for another card
- As a user, I want to be able to choose ***withdraw*** all my bets and bonus when I have 5-card Charlie
- As a user, I want to be able to start a ***new round*** after the current round of game ends
- As a user, I want to be able to ***save record*** which means adding a new game record to the game records list if this is a new player or overwriting a typical game record in the game records list if I loaded this game record in the beginning
- As a user, I want to be able to go back to ***main menu*** which means ending all the game phase rendering and render the original navigation phase

# Instructions for Grader
- You can add a new game record to the game records list by clicking ***Start New Game*** at the main menu, typing in a random name, clicking ***Submit*** to create a new player, and clicking ***Save Record*** at the end of each round of game if you do not lose all your assets
- You can add new cards to player's cards by clicking ***Hit*** or ***Double*** during the dealing stage
- You can save the game progress by clicking ***Save Record*** at the end of each round of game if you do not lose all your assets
- You can reload the game progress by clicking ***Resume Game*** at the main menu, choosing a typical game record from the scroll panel, and clicking the ***Load Game Record***
- You can delete a game record by clicking ***Resume Game*** at the main menu, choosing a typical game record from the scroll panel, and clicking the ***Delete Game Record***
- You can render the player ranking by clicking ***Player Ranking*** at the main menu
- You can exit the game by clicking ***Exit Game*** at the main menu

# Phase 4: Task 2
- Sun Apr 09 12:45:16 PDT 2023 A new card dealt to the Dealer.
- Sun Apr 09 12:45:16 PDT 2023 A new card dealt to the Dealer.
- Sun Apr 09 12:45:16 PDT 2023 A new card dealt to the Player.
- Sun Apr 09 12:45:16 PDT 2023 A new card dealt to the Player.
- Sun Apr 09 12:45:21 PDT 2023 Benny is added to the game records list.
- Sun Apr 09 12:45:24 PDT 2023 A new card dealt to the Dealer.
- Sun Apr 09 12:45:24 PDT 2023 A new card dealt to the Dealer.
- Sun Apr 09 12:45:24 PDT 2023 A new card dealt to the Player.
- Sun Apr 09 12:45:24 PDT 2023 A new card dealt to the Player.
- Sun Apr 09 12:45:26 PDT 2023 A new card dealt to the Player.
- Sun Apr 09 12:45:29 PDT 2023 Benny's old game record is overwritten by the new one.
- Sun Apr 09 12:45:33 PDT 2023 Benny is removed from the game records list.

# Phase 4: Task 3
- If you look at my code in detail, you will notice there are many code duplicates among dealerCard classes and playersCard 
classes. The reason why I did not let them extend the abstract classes that I have already designed is that when they extend 
the same class in Java Swing, they share the same timer. However, in the context of this BlackJackGame, they shall not share
the same timer so that they must be separate classes despite the code duplicates.
- If I want to adhere to the Single Responsibility Principle more strictly, I would call for the Booing class and Cheering class 
directly in the BlackJackGame Class since the MessageAreaTextJLabel class shall only act like a message rendering manager. 
The reason why I chose to couple them together is that it is easier for other programmers to understand when the text instructions 
are coupled with the corresponding sound effect.