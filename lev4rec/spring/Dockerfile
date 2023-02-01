FROM maven

COPY . . 
CMD mvn install:install-file -Dfile=org.lev4rec.xtext.dsl-1.0.0.jar -DgroupId=org.lev4rec -DartifactId=org.lev4rec.xtext.dsl -Dversion=1.0.0 -Dpackaging=jar; mvn install:install-file -Dfile=code_template.jar -DgroupId=org.lev4rec -DartifactId=org.lev4rec.code.generator -Dversion=1.0.1 -Dpackaging=jar; mvn -f demo/pom.xml spring-boot:run & java -jar gateway-0.0.1-SNAPSHOT.jar & java -jar xtext_dsl_editor.jar


