mvn package -Dmaven.test.skip=true
if [ "$?" -ne "0" ]
then
    exit
fi
java -jar "target/purranya_api-1-jar-with-dependencies.jar"
