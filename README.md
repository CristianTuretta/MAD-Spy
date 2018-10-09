# MAD SPY
#### Malware Design
**Motivation**: As a top secret agent for NSA fighting to save the world, your mission is to gather intelligence on a terrorist network that uses a secret chat program for plotting terrorist attacks. We have already identified one of the group members. Your mission is to secretly record the password when the terrorist is logging in and also take screenshots from the terrorists smartphone to figure out other members of the group.


**Description**: You have to use your skills from the MAD course and inject malicious code into messaging app that will be implanted onto the terrorist’s smartphone and so you can monitor the target right here from HQ. Create a spyware ( a malware with the goal to spy ) that when infects a target, is able to identify when the specific chat program is started, record the keystrokes to get the messages/password, take screenshots and email the collected information to yourself.

**Project Implementation**: Assume the target smartphone has operating system of your choice (Android/IOS) installed and your program (spyware) is running with required privileges (if required). Choose a target app: Snapchat, Whatsapp, skype, messenger, hangouts. You have to create a malicious app to demonstrate three key behaviors of a mobile spyware:

* Piggybacking: Decompile the target app. Add malicious modules and recompile to create a piggybacked malware. 

* Spy modules: Once the malicious app is running, you start recording the keystrokes (on a text file). Also, you take 5 snapshots of the screen, one every 30 seconds.

* Exfiltration: Now that you have all you need, you send the data as an email to yourself. Alternatively, you may choose any other method to send the data collected to a server you can access.
