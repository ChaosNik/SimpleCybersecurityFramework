Potrebno je kreirati .jar fajl projekta.
Kreirani .jar fajl treba ubaciti u lib folder servera.
Treba dodati u config/server.xml sljedeću liniju 
<Valve className="org.apache.catalina.connector.MyValve"/>
da bismo registrovali Valve.
Fajlovi configuration.properties i parameters.properties treba da se nalaze u root folderu servera.
Valve će kreirati attackAttempts.log fajl u root folderu servera.