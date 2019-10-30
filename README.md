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

Run the wordscounterapp-jar-with-dependencies.jar with below parameters

```
java –Xmx8192m -jar wordscounterapp-jar-with-dependencies.jar –source dump.xml –mongo hostname:port
```
here dump.xml is any file of the text located in the computer (e.g. D:/testfile/dump.xml)
<br>
hostname:port is name of the MongoDB server host and the port. (e.g. localhost:27017  or localhost:27017,localhost:27020)


### Contributions
