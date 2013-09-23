		function testPause() {
			alert('Hi there');
		}
/*
 *  Title Page For Pig Party
 *
*/
		var GameStart = 1;
		
		function titlepage() {
				// this is the same as document.getElementById('canvas');
				var canvas = $('#canvas')[0]; 
				// different browsers support different contexts. All support 2d
				var context = canvas.getContext('2d');			
			
			var NumMiniGames = 1; //Right now only one game (Wall Dodge). if two games change to 2, etc.
			
			function drawtitleimg(){              
				context.drawImage(titleimg, 0, 0, 800, 500);
			}
			
			//Draw the initial title screen
			drawtitleimg();
			context.drawImage(settingsimg, 750, 450, 50, 50);
			context.drawImage(hiscoresimg, 700, 450, 50, 50);
			
			//Events needed for mouse click
			var mouseclickX, mouseclickY;
			
			function canvasclick (e) {
				mouseclickX = e.layerX;
				mouseclickY = e.layerY;
				
				if (mouseclickX > 700 && mouseclickX < 800 && mouseclickY > 450 && mouseclickY < 500) {
					//Stop Following Mouse Actions
					canvas.removeEventListener('click', canvasmousemove);
					canvas.removeEventListener('mousemove', canvasmousemove);
					canvas.removeEventListener('mouseout', canvasmouseout);
					if ( mouseclickX < 750 )
					{
						context.drawImage(hiscoresclickimg, 700, 450, 50, 50);
						setTimeout(drawtitleimg, 500);//change to hi scores instead of drawtitleimg when it is built
					}
					else if ( mouseclickX < 800 ) {
						context.drawImage(settingsclickimg, 750, 450, 50, 50);
						setTimeout(drawtitleimg, 500);//change to settings instead of drawtitleimg when it is built
					}
				}
				
				return false
			};
			
			//Events needed for mouse movements to gather hover information
			var mousemoveX, mousemoveY;
			var hiscoreshover = 0;
			var settingshover = 0;
			
			function canvasmousemove (e) {
				mousemoveX = e.offsetX;
				mousemoveY = e.offsetY;
				
				if (mousemoveX > 700 && mousemoveX < 800 && mousemoveY > 450 && mousemoveY < 500) {
					if ( mousemoveX < 750 )//if mouse is over hi scores button
					{
						context.drawImage(hiscoresmouseoverimg, 700, 450, 50, 50);
						hiscoreshover = 1;
						if ( settingshover == 1 ) { //if mouse moved from settings button
							context.drawImage(settingsimg, 750, 450, 50, 50);
							settingshover = 0;
						}
					}
					else if ( mousemoveX < 800 ) // if mouse is over settings button
					{
						context.drawImage(settingsmouseoverimg, 750, 450, 50, 50);
						settingshover = 1;
						if ( hiscoreshover == 1 ) { //if mouse moved from hi scores button
							context.drawImage(hiscoresimg, 700, 450, 50, 50);
							hiscorehover = 0;
						}
					}
				} 
				//if mouse leaves both buttons redraw original title screen
				else if ( hiscoreshover == 1 || settingshover == 1) {
					context.drawImage(hiscoresimg, 700, 450, 50, 50);
					context.drawImage(settingsimg, 750, 450, 50, 50);
					hiscoreshover = 0;
					settingshover = 0;
				}
				
				return false
			};
			
			
			//Events for when mouse leaves canvas - redraw original title screen and make hover values 0
			function canvasmouseout (e) {
				context.drawImage(hiscoresimg, 700, 450, 50, 50);
				context.drawImage(settingsimg, 750, 450, 50, 50);
				hiscoreshover = 0;
				settingshover = 0;	
				
				return false
			};
			
			//Allow canvas to listen to mouse movements and mouse clicks
			canvas.addEventListener('click', canvasclick);
			canvas.addEventListener('mousemove', canvasmousemove);
			canvas.addEventListener('mouseout', canvasmouseout);
			
			
			//Initializes Pig Party MiniGame - Must take data from Android to start it (Right now it is up arrow)
			$(document).keydown(function(evt) {
					if (evt.keyCode == 38 && GameStart) { 
						GameStart = 0; 
						canvas.removeEventListener('click', canvasmousemove);
						canvas.removeEventListener('mousemove', canvasmousemove);
						canvas.removeEventListener('mouseout', canvasmouseout);
						
						var lives = 3;
						var score = 0;
						return PigPartyStartGame(lives, score); 
					} 
					return false
			});	
			
			$(document).keyup(function(evt) {
					if (evt.keyCode == 38) {
						carMove = 'NONE';
					} 
					return false
			});
			
			return false;
			
		};
		
/*
 *  Starts Game for Pig Party: Initializes Game - Is NOT the game
 * 
 *  Additional Information:
 *  	Keeps track of score
 *		Keeps track of lives
 *
 *  Games List (value of firstMiniGame and minigame:
 *		1: Wall Dodge
 *		2: Balloon Pop
 *		3: Poke the Piggy
 *		4: Coming Soon...
*/

		function PigPartyStartGame(lives, score) {
				// this is the same as document.getElementById('canvas');
				var canvas = $('#canvas')[0]; 
				// different browsers support different contexts. All support 2d
				var context = canvas.getContext('2d');
				
			function WallDodgeLoadScreen(count) {
				context.drawImage(LoadScreenWall, 0, 0, 800, 500);
				context.fillText(count,canvas.width/2-20,canvas.height/2+20);
			}
			
			function BalloonPopLoadScreen(count) {
				context.drawImage(LoadScreenPop, 0, 0, 800, 500);
				context.fillText(count,canvas.width/2-20,canvas.height/2+20);
			}
			
			function PokePiggyLoadScreen(count) {
				context.drawImage(LoadScreenPoke, 0, 0, 800, 500);
				context.fillText(count,canvas.width/2-20,canvas.height/2+20);
			}
			
			function minigamestart() {
				context.fillStyle = "red";
				context.font = "bold 75px Tahoma";
				
				//coundown and load game
				if ( minigame == 1) {
					WallDodgeLoadScreen(3);
					setTimeout(function(){ WallDodgeLoadScreen(2) }, 750);
					setTimeout(function(){ WallDodgeLoadScreen(1) }, 1500);
					return setTimeout(function(){WallDodge(lives, score)},2250);
				}
				else if ( minigame == 2 ) {
					BalloonPopLoadScreen(3);
					setTimeout(function(){ BalloonPopLoadScreen(2) }, 750);
					setTimeout(function(){ BalloonPopLoadScreen(1) }, 1500);
					return setTimeout(function(){BalloonPop(lives, score)},2250);
				}
				else if ( minigame == 3 ) {
					PokePiggyLoadScreen(3);
					setTimeout(function(){ PokePiggyLoadScreen(2) }, 750);
					setTimeout(function(){ PokePiggyLoadScreen(1) }, 1500);
					return setTimeout(function(){PokethePiggy(lives, score)},2250);			
				}
			
			}
			
			//var minigame;
			if (lives > 0) {
				var minigame = Math.ceil(Math.random() * 3); //Right now it is one but this should be
				var loaded = 0; //has android side finished loading
				
				//Android: send minigame # to Android
				//wait till zach says its ready
				
				context.drawImage(LoadScreen, 0, 0, 800, 500);
				
				//Android: receive confirmation that Android side has loaded
				$(document).keydown(function(evt) {
					if (evt.keyCode == 38 && loaded == 0) { 
						loaded = 1;
						return minigamestart(); 
					} 
				});
				
			}
			else {
			//Game Over: Record final score and display (from that page you can go back to title page)
				GameStart = 1;
				setTimeout(titlepage, 500);//this should be removed later
			}
				
		};
		
/*
 *  1. Wall Dodge Mini Game
 *
*/
		function WallDodge(lives, score) {
				// this is the same as document.getElementById('canvas');
				var canvas = $('#canvas')[0]; 
				// different browsers support different contexts. All support 2d
				var context = canvas.getContext('2d');
				
			function testPause() {
				alert('Hi there');
			}
			
			var showlevel;
			
			if (score > 3) { //level 3 -- change back to score > 9 with when more minigames
				level = 120;
				showlevel = 3;
			}
			else if (score < 2) { // level 1 -- change back to score < 5 with when more minigames
				level = 100;
				showlevel = 1;
			}
			else { // level 2
				level = 150; 
				showlevel = 2;
			}
			
			function drawroad() {
			  context.fillStyle   = '#000';

			  context.beginPath();
			  // Start from the top-left point.
			  context.moveTo(253, 0); // give the (x,y) coordinates
			  context.lineTo(547, 0);
			  context.lineTo(786, 500);
			  context.lineTo(14, 500);
			  context.lineTo(253, 0);

			  // Done! Now fill the shape, and draw the stroke.
			  // Note: your shape will not be visible until you call any of the two methods.
			  context.fill();
			  context.closePath();			
			}
			
			function drawleftgrass (){
			  context.fillStyle   = '#090';

			  context.beginPath();
			  // Start from the top-left point.
			  context.moveTo(0, 0); // give the (x,y) coordinates
			  context.lineTo(250, 0);
			  context.lineTo(0, 500);
			  context.lineTo(0, 0);

			  // Done! Now fill the shape, and draw the stroke.
			  // Note: your shape will not be visible until you call any of the two methods.
			  context.fill();
			  context.closePath();
			}
			
			function drawrightgrass (){
			  context.fillStyle   = '#090';

			  context.beginPath();
			  // Start from the top-left point.
			  context.moveTo(550, 0); // give the (x,y) coordinates
			  context.lineTo(800, 0);
			  context.lineTo(800, 500);//*Add another coordinate for out of  box
			  context.lineTo(550, 0);

			  // Done! Now fill the shape, and draw the stroke.
			  // Note: your shape will not be visible until you call any of the two methods.
			  context.fill();
			  context.closePath();
			}
			
			function drawleftboundary (){
			  context.fillStyle   = '#f10';

			  context.beginPath();
			  // Start from the top-left point.
			  context.moveTo(250, 0); // give the (x,y) coordinates
			  context.lineTo(253, 0);
			  context.lineTo(14, 500);
			  context.lineTo(0, 500);
			  context.lineTo(250, 0);

			  // Done! Now fill the shape, and draw the stroke.
			  // Note: your shape will not be visible until you call any of the two methods.
			  context.fill();
			  context.closePath();
			}
			
			function drawrightboundary (){
			  context.fillStyle   = '#f10';

			  context.beginPath();
			  // Start from the top-left point.
			  context.moveTo(547, 0); // give the (x,y) coordinates
			  context.lineTo(550, 0);
			  context.lineTo(800, 500);
			  context.lineTo(786, 500);
			  context.lineTo(547, 0);

			  // Done! Now fill the shape, and draw the stroke.
			  // Note: your shape will not be visible until you call any of the two methods.
			  context.fill();
			  context.closePath();
			}
			
			//Add a placeholder function for browsers that don't have setLineDash()
			//It only works on Chrome
			if (!context.setLineDash) {
				context.setLineDash = function () {}
			}
			
				var lanemovement = 0;
			
			function drawleftlane (){
			  context.strokeStyle = '#fff';
			  //context.setLineDash([30,35,40,45,50,55,70,75,80,85,90,95,100,105]);
			  context.setLineDash([100,60]);
			  context.lineDashOffset = lanemovement;

			  context.beginPath();
			  context.moveTo(262, 500); context.lineTo(349, 0); // Beginning to End of line
			  context.moveTo(263, 500); context.lineTo(349, 0); // Beginning to End of line
			  context.moveTo(264, 500); context.lineTo(349, 0);// Beginning to End of line
			  context.moveTo(265, 500); context.lineTo(349, 0);// Beginning to End of line
			  context.moveTo(266, 500); context.lineTo(349, 0); // Beginning to End of line
			  
			  context.moveTo(267, 500); context.lineTo(350, 0); // Beginning to End of line
			  context.moveTo(268, 500); context.lineTo(350, 0); // Beginning to End of line
			  context.moveTo(269, 500); context.lineTo(350, 0); // Beginning to End of line
			  context.moveTo(270, 500); context.lineTo(350, 0); // Beginning to End of line
			  context.moveTo(271, 500); context.lineTo(350, 0); // Beginning to End of line
			  
			  context.moveTo(272, 500); context.lineTo(351, 0); // Beginning to End of line
			  context.moveTo(273, 500); context.lineTo(351, 0); // Beginning to End of line
			  context.moveTo(274, 500); context.lineTo(351, 0); // Beginning to End of line
			  context.moveTo(275, 500); context.lineTo(351, 0); // Beginning to End of line
			  context.moveTo(276, 500); context.lineTo(351, 0); // Beginning to End of line
			  
			  context.stroke();
			  context.closePath();
			}
			
			function drawrightlane (){
			  context.strokeStyle = '#fff';
			  //context.setLineDash([30,35,40,45,50,55,70,75,80,85,90,95,100,105]);
			  context.setLineDash([100,60]);
			  context.lineDashOffset = lanemovement;

			  context.beginPath();
			  context.moveTo(524, 500); context.lineTo(448, 0); // Beginning to End of line
			  context.moveTo(525, 500); context.lineTo(448, 0); // Beginning to End of line
			  context.moveTo(526, 500); context.lineTo(448, 0); // Beginning to End of line
			  context.moveTo(527, 500); context.lineTo(448, 0); // Beginning to End of line
			  context.moveTo(528, 500); context.lineTo(448, 0); // Beginning to End of line
			  
			  context.moveTo(529, 500); context.lineTo(449, 0); // Beginning to End of line
			  context.moveTo(530, 500); context.lineTo(449, 0); // Beginning to End of line
			  context.moveTo(531, 500); context.lineTo(449, 0); // Beginning to End of line
			  context.moveTo(532, 500); context.lineTo(449, 0); // Beginning to End of line
			  context.moveTo(533, 500); context.lineTo(449, 0); // Beginning to End of line
			  
			  context.moveTo(534, 500); context.lineTo(450, 0); // Beginning to End of line
			  context.moveTo(535, 500); context.lineTo(450, 0); // Beginning to End of line
			  context.moveTo(536, 500); context.lineTo(450, 0); // Beginning to End of line
			  context.moveTo(537, 500); context.lineTo(450, 0); // Beginning to End of line
			  context.moveTo(538, 500); context.lineTo(450, 0); // Beginning to End of line
			  
			  context.stroke();
			  context.closePath();
			}
			
			
			var centerX = 720;
			var centerY = 80;
			
			function drawuserinfo() {
				
				context.beginPath();
				context.arc(centerX, centerY, 70, 0, 2 * Math.PI, false);
				context.fillStyle = 'purple';
				context.fill();
				context.lineWidth = 1;
				context.strokeStyle = '#003300';
				context.stroke();
				context.closePath();
				
				context.beginPath();
				context.arc(centerX, centerY, 50, 0, 2 * Math.PI, false);
				context.fillStyle = 'yellow';
				context.fill();
				context.lineWidth = 1;
				context.strokeStyle = '#003300';
				context.stroke();
				context.closePath();
				
				context.fillStyle = "blue";
				context.font = "bold 25px Tahoma";
				if (totaltime > 9999)
					context.fillText(totaltime/1000, centerX-35, centerY+10);
				else
					context.fillText(totaltime/1000, centerX-27, centerY+10);
				
				context.fillStyle = "red";
				context.font = "bold 20px Tahoma";
				context.fillText("Score: " + score, 5, 20);
				context.fillText("Level: " + showlevel, 5, 40);
				context.fillText("Lives: " + lives, 5, 60);
			}
			
				var carX = 300;
				var carY = 400;

				var carWidth = 200;
				var carHeight = 100;

				var carDeltaX = 0;
				var carDeltaY = 0;

			  function drawcar(){
				context.drawImage(drawcarback,carX,carY);
			  }
			  
				var wallpositions = [
					[0,0,0,0,0,0,0,0], //lanes
					[0,0,0,0,0,0,0,0], //wallX
					[0,0,0,0,0,0,0,0], //wallY
					[0,0,0,0,0,0,0,0], //wallWidth
					[0,0,0,0,0,0,0,0]  //wallHeight
					
				];
				
			function placewall(lane){
				//See if we have a spot open (Only 8 walls max at a time)
				for (var i=0; i < wallpositions[0].length; i++) {
					//spot is open if a spot on the first row = 0
					if ( wallpositions[0][i] == 0 ) {
						if ( lane == 1 ) {
							wallpositions[0][i] = 1; //lane
							wallpositions[1][i] = 254; //wallX
							wallpositions[2][i] = 0; //wallY
							wallpositions[3][i] = 94; //wallWidth
							wallpositions[4][i] = 15; //wallHeight
						}
						if ( lane == 2 ) {
							wallpositions[0][i] = 2; //lane
							wallpositions[1][i] = 353; //wallX
							wallpositions[2][i] = 0; //wallY
							wallpositions[3][i] = 94; //wallWidth
							wallpositions[4][i] = 15; //wallHeight
						}
						if ( lane == 3 ) {
							wallpositions[0][i] = 3; //lane
							wallpositions[1][i] = 452; //wallX
							wallpositions[2][i] = 0; //wallY
							wallpositions[3][i] = 94; //wallWidth
							wallpositions[4][i] = 15; //wallHeight
						}
						break
					}
				}
			}
			
			function drawwalls(){
				//only if there is a wall in the matrix will we draw it (first row ~= 0)
				for (var i=0; i < wallpositions[0].length; i++) {
					if ( wallpositions[0][i] != 0 ) {
						context.drawImage(bricksimg,wallpositions[1][i],wallpositions[2][i],wallpositions[3][i],wallpositions[4][i]);
						
						//Check if game is over
						// if either edge of car is between the wall edges(since car is smaller than wall)
						if ( ( carX+33 > wallpositions[1][i] && carX+33 < ( wallpositions[1][i] + wallpositions[3][i] ) ) ||
						   ( ( carX + carWidth - 33 ) > wallpositions[1][i] && ( carX + carWidth - 33 ) < ( wallpositions[1][i] + wallpositions[3][i] ) ) )
						   // if top of car is in same spot as where the wall is
							if ( wallpositions[2][i] + wallpositions[4][i] >= carY + 15 ) {
								context.fillStyle = 'blue';
								context.fillText('You Lose!!!!',canvas.width/2,canvas.height/2);
								endGame(true);
							}
						
						//Continue if game not over
						wallpositions[2][i] = wallpositions[2][i] + 1;
						//destroy wall if it left screen to allow more walls to come
						if ( wallpositions[2][i] > 499 ) {
							wallpositions[0][i] = 0;
						}
						//wall increases in width and height as it comes down for perspective
						else if ( wallpositions[2][i] % 7 == 0 ) {
						
							//wall must change it's wallX start value differently depending on the lane it is in
							if ( wallpositions[0][i] == 1 ) {
								wallpositions[1][i] = wallpositions[1][i] - 3.4;
							}
							else if ( wallpositions[0][i] == 2 ) {
								wallpositions[1][i] = wallpositions[1][i] - 1;
							}
							else {
								wallpositions[1][i] = wallpositions[1][i] + 1.3;							
							}
							
							
							//width increases by 2 pixels for every 7 pixels that the wall drops
							wallpositions[3][i] = wallpositions[3][i] + 2;
							if ( wallpositions[2][i] % 56 == 0 ) {
							//Height increases by 1 pixels for every 56 pixels that the wall drops
								wallpositions[4][i] = wallpositions[4][i] + 1;
							}
						}
					}
				}
			}
			
			function chooselane() {
					//Wall comes up if this random number from 1-50 is equal to 1
					if (Math.ceil(Math.random() * level) == 1) {
						if (timer > 1200)
							WallCount = 0;
					
						RandLane = Math.ceil(Math.random() * 3);
						while ( lastlane == RandLane ){
							RandLane = Math.ceil(Math.random() * 3);						
						}
						
						lastlane = RandLane;
						WallCount = WallCount + 1;
						timer = -10;
						//Now put Randomly generated lane in Randlane = 1, 2, or 3
						placewall(RandLane);						
					}				
			}
			  
			function movelanes (){
				lanemovement = lanemovement + 1;
			}
			
				var carDeltaX;
				var carSpeedX = 10;

			function movecar(){
				if (carMove == 'LEFT'){
					carDeltaX = -carSpeedX;
				} else if (carMove == 'RIGHT'){
					carDeltaX = carSpeedX;
				} else {
					carDeltaX = 0;
				}
				// If car reaches the ends, then don't let it move 
				if (carX + carDeltaX < 0 || carX + carDeltaX +carWidth >canvas.width){
					carDeltaX = 0; 
				}
				carX = carX + carDeltaX;
			}
			
			drawroad();
			drawleftgrass();
			drawrightgrass();
			drawleftboundary();
			drawrightboundary();
			drawleftlane();
			drawrightlane();
			drawcar();
			
			//WallCount keeps track of how many walls are up. No more than two walls at once in a given time
			var WallCount = 0;
			
			function animate (){
				if (pause)
					return;
			
				context.clearRect(0,0,canvas.width,canvas.height);
				drawroad();
				drawleftgrass();
				drawrightgrass();
				drawleftboundary();
				drawrightboundary();
				drawuserinfo();
				movelanes();
				drawleftlane();
				drawrightlane();
				movecar();
				
				//Wall can only generate when more than 1 wall is up and enough time (750ms
				//or .75 seconds) is given for car to dodge another wall
				if (showlevel == 1) {
					if ((WallCount == 0) || (WallCount > 0 && timer > 1500))
						chooselane();
				} else {
					if ((WallCount < 2) || (WallCount > 1 && timer > 1500))
						chooselane();
				}
				timer = timer + 10;
				totaltime = totaltime - 10;
				
				drawwalls();
				drawcar();
				
				if (totaltime <= 0) {
					context.fillStyle = 'blue';
					context.fillText('Success!!!!',canvas.width/2,canvas.height/2);
					endGame(false); // successful at completing minigame
				}					

			}
			
			var RandLane;
			var lastlane = 0;
			var gameLoop;
			var timer;
			var totaltime;
			var pause = 0; //Needed to make sure proper pause called
				
			function startGame(){
				carMove = 'NONE';
				carDeltaX = 0;
				wallX = 0;
				
				//Walls cannot be greater than 2 with not enought time to move over
				WallCount = 0;
				timer = 760;
				totaltime = 30000;
				
				// call the animate() function every 10ms until clearInterval(gameLoop) is called
				gameLoop = setInterval(animate,10);

				// Start Tracking Keystokes
				$(document).keydown(function(evt) {
					if (evt.keyCode == 39) {
						carMove = 'RIGHT';
					} else if (evt.keyCode == 37){
						carMove = 'LEFT';
					} else if (evt.keyCode == 40) {
						if (pause)
							pause = 0;
						else
							pause = 1;
					} else if (evt.keyCode == 77) {
						clearInterval(gameLoop);
						GameStart = 1;
						lives = 0;
						pause = 0;
						titlepage();
					}
				});			

				$(document).keyup(function(evt) {
					if (evt.keyCode == 39) {
						carMove = 'NONE';
					} else if (evt.keyCode == 37){
						carMove = 'NONE';
					} 
				}); 
			}

			function endGame(winorloss){
				clearInterval(gameLoop);
				pause = 0;
				if (winorloss){ // true is a loss for WallDodge so subtract a life
					lives = lives - 1;
					console.log('User lost.')
				}
				else
					score = score + 1;
					
				//Android: Send Message to Prepare Next Minigame
				return setTimeout( function() { PigPartyStartGame(lives, score) }, 500 );
			}
				
			startGame();
			
		};

/*
 *  2. Balloon Pop Mini Game
 *
*/
		
		function BalloonPop(lives, score) {
				// this is the same as document.getElementById('canvas');
				var canvas = $('#canvas')[0]; 
				// different browsers support different contexts. All support 2d
				var context = canvas.getContext('2d');
				
			var showlevel;
			
			if (score > 3) { //level 3 -- change back to score > 9 with when more minigames
				level = 20;
				showlevel = 3;
			}
			else if (score < 2) { // level 1 -- change back to score < 5 with when more minigames
				level = 30;
				showlevel = 1;
			}
			else { // level 2
				level = 25; 
				showlevel = 2;
			}
			
			context.drawImage(BalloonBackground, 0, 0, 800, 500);
		
			var balloonColor = { r : 192, g : 192, b : 192 };
			var balloonX = 397;
			var balloonY = 335;
			var balloonR = 20;
			
			var balloon = new CANVASBALLOON.Balloon('canvas', balloonX, balloonY, balloonR, balloonColor);
            balloon.draw();
			
			var centerX = 720;
			var centerY = 80;
			
			function drawuserinfo() {
				
				context.beginPath();
				context.arc(centerX, centerY, 70, 0, 2 * Math.PI, false);
				context.fillStyle = 'purple';
				context.fill();
				context.lineWidth = 1;
				context.strokeStyle = '#003300';
				context.stroke();
				context.closePath();
				
				context.beginPath();
				context.arc(centerX, centerY, 50, 0, 2 * Math.PI, false);
				context.fillStyle = 'yellow';
				context.fill();
				context.lineWidth = 1;
				context.strokeStyle = '#003300';
				context.stroke();
				context.closePath();
				
				context.fillStyle = "blue";
				context.font = "bold 25px Tahoma";
				if (totaltime > 9999)
					context.fillText(totaltime/1000, centerX-35, centerY+10);
				else
					context.fillText(totaltime/1000, centerX-27, centerY+10);
				
				context.fillStyle = "red";
				context.font = "bold 20px Tahoma";
				context.fillText("Score: " + score, 5, 20);
				context.fillText("Level: " + showlevel, 5, 40);
				context.fillText("Lives: " + lives, 5, 60);
			}
			
			function animate (){
				if (pause)
					return;
							
				if (pump > 0) { //each pump is done through many animations to stop instantaneous large increase in balloon
					balloonR = balloonR + 1;
					pump = pump - 1;
				}
					
				balloonY = 363 - 1.4 * balloonR; //keep bottom of balloon same throughout
				balloon = new CANVASBALLOON.Balloon('canvas', balloonX, balloonY, balloonR, balloonColor);
				balloon.draw();
				drawuserinfo();
				
				if (pump > 0)
					pump = pump - 1; //continues animation of pump. during animation user can't pump more. millisecond wait for user
				
				if ( balloonR >= 140 ) { //pop
					context.fillStyle = 'blue';
					context.fillText('Success!!!!',canvas.width/2,canvas.height/2);
					endGame(false); // success at completing minigame
				}
					 
				totaltime = totaltime - 10;
				
				if (totaltime <= 0) {
					context.fillStyle = 'blue';
					context.fillText('You Lose!!!!',canvas.width/2,canvas.height/2);
					endGame(true); // failure to complete minigame
				}
			}
			
			var totaltime;
			var pump = 0;
			var pause = 0; //Needed to make sure proper pause called
			
			function startGame(){
				totaltime = 15000; //5 seconds for game
				
				// call the animate() function every 10ms until clearInterval(gameLoop) is called
				gameLoop = setInterval(animate,10);

				// Start Tracking Keystokes
				$(document).keydown(function(evt) {
					if (evt.keyCode == 38)  { //if (# > 0 && # < 101) PRAVEEN
						if (pump == 0) {
							pump = level; //pump = level * #;PRAVEEN
						}
					} 
					else if (evt.keyCode == 40) {
						if (pause)
							pause = 0;
						else
							pause = 1;
					} else if (evt.keyCode == 77) {
						clearInterval(gameLoop);
						GameStart = 1;
						lives = 0;
						pause = 0;
						titlepage();
					}
				});			

			}

			function endGame(winorloss){
				clearInterval(gameLoop);
				pause = 0;
				if (winorloss) // true is a loss for WallDodge so subtract a life
					lives = lives - 1;
				else
					score = score + 1;
					
				//Android: Send Message to Prepare Next Minigame
				return setTimeout( function() { PigPartyStartGame(lives, score) }, 500 );
			}
				
			startGame();
			
		};
		
/*
 *  3. Poke the Piggy Mini Game
 *
*/

		function PokethePiggy(lives, score) {
				// this is the same as document.getElementById('canvas');
				var canvas = $('#canvas')[0]; 
				// different browsers support different contexts. All support 2d
				var context = canvas.getContext('2d');
				
			var showlevel;
			
			if (score > 3) { //level 3 -- change back to score > 9 with when more minigames
				level = 5000;
				showlevel = 3;
			}
			else if (score < 2) { // level 1 -- change back to score < 5 with when more minigames
				level = 15000;
				showlevel = 1;
			}
			else { // level 2
				level = 10000; 
				showlevel = 2;
			}
			
			randcolor = Math.ceil(Math.random() * 3);
			if (randcolor == 1)
				context.drawImage(BluePoke, 0, 0, 800, 500);
			else if (randcolor == 2)
				context.drawImage(PinkPoke, 0, 0, 800, 500);
			else
				context.drawImage(YellowPoke, 0, 0, 800, 500);
				
			//Android: Send the value of randcolor to Zac
			
			var centerX = 720; //For timer
			var centerY = 80; //For timer
			
			function drawuserinfo() {
				
				context.beginPath();
				context.arc(centerX, centerY, 70, 0, 2 * Math.PI, false);
				context.fillStyle = 'purple';
				context.fill();
				context.lineWidth = 1;
				context.strokeStyle = '#003300';
				context.stroke();
				context.closePath();
				
				context.beginPath();
				context.arc(centerX, centerY, 50, 0, 2 * Math.PI, false);
				context.fillStyle = 'yellow';
				context.fill();
				context.lineWidth = 1;
				context.strokeStyle = '#003300';
				context.stroke();
				context.closePath();
				
				context.fillStyle = "blue";
				context.font = "bold 25px Tahoma";
				if (totaltime > 9999)
					context.fillText(totaltime/1000, centerX-35, centerY+10);
				else
					context.fillText(totaltime/1000, centerX-27, centerY+10);
				
				context.fillStyle = "red";
				context.font = "bold 20px Tahoma";
				context.fillText("Score: " + score, 5, 20);
				context.fillText("Level: " + showlevel, 5, 40);
				context.fillText("Lives: " + lives, 5, 60);
			}
			
			function animate (){
				if (pause)
					return;
				
				drawuserinfo();
				
				if ( complete ) { //Win Minigame
					context.fillStyle = 'blue';
					context.fillText('Success!!!!',canvas.width/2,canvas.height/2);
					endGame(false); // successful minigame completion
				}
					 
				totaltime = totaltime - 10;
				
				if (totaltime <= 0) {
					context.fillStyle = 'blue';
					context.fillText('You Lose!!!!',canvas.width/2,canvas.height/2);
					endGame(true); // failure to complete minigame
				}
			}
			
			var totaltime;
			var pause = 0; //Needed to make sure proper pause called
			var complete = 0;
			
			function startGame(){
				totaltime = level; //seconds change depending on level
				
				// call the animate() function every 10ms until clearInterval(gameLoop) is called
				gameLoop = setInterval(animate,10);

				// Start Tracking Keystokes
				$(document).keydown(function(evt) {
					if (evt.keyCode == 39)  { //Successful completion of poking the pigs
						complete = 1;
					} 
					if (evt.keyCode == 37) { //Wrong pig poked: Game Over, life lost
						totaltime = 10; // game over on next animate loop
					}
					else if (evt.keyCode == 40) {
						if (pause)
							pause = 0;
						else
							pause = 1;
					} else if (evt.keyCode == 77) {
						clearInterval(gameLoop);
						GameStart = 1;
						lives = 0;
						pause = 0;
						titlepage();
					}
				});			

			}

			function endGame(winorloss){
				clearInterval(gameLoop);
				pause = 0;
				if (winorloss) // true is a loss for WallDodge so subtract a life
					lives = lives - 1;
				else
					score = score + 1;
					
				//Android: Send Message to Prepare Next Minigame
				return setTimeout( function() { PigPartyStartGame(lives, score) }, 500 );
			}
				
			startGame();
			
		};