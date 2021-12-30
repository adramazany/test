mvn -DarchetypeGroupId=org.openimaj -DarchetypeArtifactId=openimaj-quickstart-archetype -DarchetypeVersion=1.3.9 archetype:generate

cd OpenIMAJ-Tutorial01

mvn assembly:assembly

java -jar target/OpenIMAJ-Tutorial01-1.0-SNAPSHOT-jar-with-dependencies.jar

