#!/bin/bash
#export JAVA_HOME=/Library/Java/JavaVirtualMachines/amazon-corretto-21.jdk/Contents/Home
java --version
mvn clean install
chmod 400 ParkeyBackend.pem
scp -i ParkeyBackend.pem target/ReadRDS-0.0.1-SNAPSHOT.jar ubuntu@ec2-13-201-151-71.ap-south-1.compute.amazonaws.com:~/spring-boot-rds/executable.jar
#This step is to connect to EC2 server
#This command can be directly used in Terminal to connect to EC2
ssh -i "ParkeyBackend.pem" ubuntu@ec2-13-201-151-71.ap-south-1.compute.amazonaws.com
#These are the commands to be executed manually to deploy the latest build on EC2 Server
#cd spring-boot-rds/
#tmux attach -t 0
# chmod +x executable.jar
#- java -Xmx512m -Xmx768m  --add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.base/java.time=ALL-UNNAMED -jar executable.jar
# You can use ls -ltr to check the latest build.
#tmux attach -t 0
# for prod ls
# clear
#./executable.jar

#Deployment issue
#if you get Bad Interceptop : Text file bus error then do following steps
#1. reach spring-boot-rds forlder (cd spring-boot-rds/)
#2. list number of folders (ls-ltr)
#3.  remove executable.jar(rm executable.jar)
#4. Run this deployment file again

#Dev Command
#Command to Attach Tmux
#tmux attach-session -t 0 or Just tmux attach-session
#Command Kill tmux
#kill -9 $(lsof -t -i:8080)
#tmux kill-server -t 0 (To kill specific tmux)
# or If  you want to keep 1 tmux and kill others then be in active tmux and :
# tmux kill-session -a (To kill all Tmux)
#tmux list-sessions


#Install -> $ sudo dnf -y install tmux
#To start using tmux, type tmux on your terminal. This command launches a tmux server, creates a default session (number 0) with a single window, and attaches to it.
#$ tmux
#3. You can detach from your tmux session by pressing Ctrl+B then D. Tmux operates using a series of keybindings (keyboard shortcuts) triggered by pressing the "prefix" combination. By default, the prefix is Ctrl+B. After that, press D to detach from the current session.
#4. You can list active tmux sessions with tmux ls .
#$ tmux ls
#0: 1 windows (created Sat Aug 27 20:54:58 2022)
#5. When you're ready, reconnect to the server and reattach to the existing tmux session to resume where you left off:
#$ tmux attach -t 0
#6. First, create a new tmux session if you're not already in one. You can name your session by passing the parameter -s {name} to the tmux new command when creating a new session:
#$ tmux new -s Session1