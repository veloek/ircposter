IrcPoster
=========

Small service running as a proxy to push messages to IRC

Usage:
--------

Start the service in the background on the git server:

~$ java -jar IrcPoster.jar 1337 irc.homelien.no 6667 GitPoster \#dev-chan &

Copy the post-receive file in this repo to the hooks folder of your server repo and make it executable:

~/hooks$ chmod +x post-receive

Note: Remember to change the telnet command in the post-receive script if you run the service on another host and/or another port.

This project uses [PircBot](http://www.jibble.org/pircbot.php)
