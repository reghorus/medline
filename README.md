1) Install on server java RE 8+ http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html
2) Install on serer Tomcat 8+ http://tomcat.apache.org/download-80.cgi?spm=5176.doc51376.2.9.muOQM3&file=download-80.cgi
3) Install postgresql server:
        sudo apt-get update
        sudo apt-get install postgresql postgresql-contrib
4) apply database.sql sql script
5) Clone project from github : git clone https://github.com/reghorus/medline.git
6) Enter to the project root directory and execute in command line "./mvnw clean package"
7) Go to target directory of your project and copy newly created war file to webapps folder of tomcat
8) Run tomcat by executing startup.sh file inside bin tomcat folder
9) Open http://localhost:8080/medline-task-1.0-SNAPSHOT/ url in you browser

As an option to use tomcat maven plugin or jenkins.

