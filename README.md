# wordscounterapp

wordscounterapp analyses a file and counts unique words. it saves word count in the database.

## Getting Started

### Prerequisites

JDK 8 : [Here is a guide on how to install JDK8](https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html).
<br>
MongoDB 4.2.0 [Here is a guide on how to install MongoDB 4.2 on windows](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-windows/).

Open Task.docx file to  for steps to create sharded cluster

### Usage
Download wordscounterapp.jar file and run it with below parameters
```
java –Xmx8192m -jar wordscounterapp.jar –source dump.xml –mongo hostname:port
```
### How to run
Fork the repository
<br>
Clone the repository
```
git clone https://github.com/jigneshcoder/wordscounterapp.git
```

Open wordscounterapp in Eclipse (Import existing Maven project)
<br>
From eclipse, build the project using goals "clean package"
<br>
If the build is successful it will create two executable jar files
```
wordscounterapp.jar
wordscounterapp-jar-with-dependencies.jar
```



### Contributions
