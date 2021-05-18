# Noteworthy

## Background
Noteworthy is a music library application developed by Team 09 of our CS - 4063 Human Computer Interaction course. Team members consisted of Stephen Cain, Gretchen Hollrah, Robert Lavender, Austin Mould, and Khoa-chau Nguyen. The objective of the project was to construct an application utilizing User-Centered Design concepts, Gestalt Principles of Design, Usability Principles, and the JavaFX libraries introduced in lectures.

## Introduction to Noteworthy.
Our goal was to construct an fully functional music library application consisting of features that are uniformly present across other music library applications in the market. In order to differentiate our application from application like Spotify or Apple Music, we wanted to design a feature that would allow user's to upload and download sheet music for the songs that the user is listening to. 

## Features
Noteworthy consists of four tabs; a browse tab, a search tab, a library tab, and a settings tab. Like many music library applications, Noteworthy will allow the user to create, save, and customize playlist to their own liking. All playlist will be accompanied by a repeat and shuffle feature. The user will also have access to a typically navigaion bar with the ability to play/pause, and move forward or backward on the playlist.

In the browse tab, the user will be greeted by 2 tables. One that displays the user's recently played songs. The other displays new released songs that the user may want to listen to. The browse currently only has surface level implementation. The recently played and new releases chart display the same list of songs that are loaded into the application. A list is songs is imported on launch. Due to time restraint and the large scale, we determined that implemented a full library with thousands of songs to be impractical. The goal was to focus more on the functionalaity and how it interacts with the user. 

In the library tab, the user is greeted by a list of their currently existing playlists. In the top right corner lies a button with a plus sign, this button allows that user to create a new playlist. When pressed, the user will be prompted to enter the name for the new playlist. The user would then returned to the list of playlist with the newly created playlist included. The user is able to interact with the table by selecting a playlist of their choice. This will open a new view with the name of the playlist and a list of songs in the playlist. While the playlist will automatically play the next song in the playlist and loop the playlist upon completion, there is currently no shuffle feature as we felt that it was not one of th emain objectives of the application's development. 

In the search tab, the user can search for songs by entering the phrase in the search box. The table below it will display songs whose title or artist contains the entered phrase. As mentioned in the browse tab, the lack of a music library means that the table will song display songs from the imported list of songs that meet the criteria. Since the primary goal was surface level functionality for the user's interaction with the application, just having a small list of songs to pull from was enough to show the user what occurs when searching a song/artist. When the user selects a song from the search tab, it will display the users list of existing playlist and prompt the user to select a playlist where the song will be added. 

In the settings tab, the user is greeted with three tabs that when pressed should display information related to "About", "Account/Privacy Settings", and "Connected Devices". Due to time constraints, we wanted to focus our efforts more on the primary objective of the application's development, thus did not implement the display of the information for these tabs. Since the application runs locally, the "Log Out" button prompts the user with "User logged out successfully" message rather than logging the user out. 

When the user selects a song from a playlist or one of the table in the browse pane it will open a view of the song. In the top left lies a button that the user can user to return to the previous window. In the center of the window, the user can find the song's movie poster. Below the song's poster, the user can find the song's name. Below the song's name, the user can find the song's play bar that display the progress of the song as it plays. We were unable to implement a label to represent the current playtime of the song out of the song's total playtime due to time constraints. We were also unable to provide the user with the ability to move the node on the play bar to control the point in time that the song should play from. While this component was not the primary objective of the application, it is a feature that should be implemented as it would greatly improve the user's experience. Below the play bar, the user can find the song's navigation bar which consists of five buttons. The far-left button is the previous button that allows the user to move to the previous song in the playlist. The second button from the left is the play button. The thrid button from the left is the pause button. The fourth button from the left is the next button that allows the user to move the next song in the playlist. The playlist is treated as a loop, thus the last song in the playlist is the previous song for the first song and the first song in the playlist is the next song for the last song in the playlist. Both the previous and next buttons will automatically play the new song so that the user does not need to press the play button again. The last or right-most button is the sheet music button. When pressed by the user, the window changes to display the sheet music for the currently playing song. This new window will also have a back button in the top left to alow the user to return to the song if they lose interest in the music sheet. 

## Final Notes
Due to time restrictions, we were unable to implement the ability for users to upload music sheets for songs. The idea begin this would be to allow for a wide range of diffuculty music sheets for each and every song. Moving forward, there are many basic features that greatly improve the user's experience, such as shuffle, move song play point by the progress bar, and fix some bugs with the media player, would be needed to implemented. Moving forward, it would be ideal to modify the strucxutre of the code to incorporate a Model-View-Controller design. Currently, many of the bugs that occur from the media player are due to how poorly it is constructed in the code. The code currently constructs multiple media players per song and utilizing a controller to control the media player object. 
