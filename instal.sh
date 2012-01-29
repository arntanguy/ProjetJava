javac -d bin/ -cp bin/ src/graphique/admin/*.java src/graphique/client/*.java src/graphique/models/*.java src/graphique/widgets/*.java src/logiqueMetier/*.java src/objets/*.java src/tools/*.java
mkdir doc
javadoc -d  doc -encoding UTF-8 -docencoding "UTF-8" -charset UTF-8 src/graphique/admin/*.java src/graphique/client/*.java src/graphique/models/*.java src/graphique/widgets/*.java src/logiqueMetier/*.java src/objets/*.java src/tools/*.java
