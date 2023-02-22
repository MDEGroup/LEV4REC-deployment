FROM maven:3.8.7-openjdk-18

COPY . .
RUN mvn install:install-file -Dfile=code_template.jar -DgroupId=org.lev4rec -DartifactId=org.lev4rec.code.generator -Dversion=1.0.3 -Dpackaging=jar
RUN mvn install:install-file -Dfile=xtext_dsl_editor.jar -DgroupId=org.lev4rec -DartifactId=org.lev4rec.xtext.dsl -Dversion=1.0.2 -Dpackaging=jar
CMD mvn -f lev4rec.spring/pom.xml spring-boot:run & java -jar gateway-0.0.1-SNAPSHOT.jar & java -jar xtext_dsl_editor.jar


