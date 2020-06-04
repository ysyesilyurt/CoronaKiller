# Builds the subprojects into a folder named "executables"
mkdir -p executables
cd server/CoronaKillerBackend/
mvn clean install package
mv target/CoronaKillerBackend-Group18.war ../../executables
cd ../../client/CoronaKillerFrontend/
mvn clean install package
mv target/CoronaKillerFrontend-Group18.jar ../../executables
