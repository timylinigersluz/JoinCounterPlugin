package ch.ksrminecraft.joincounter.utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

public class MongoDBUtils {

    // Attribute
    private MongoClient mongoClient;
    private MongoCollection<Document> collection;

    // Konstruktor
    public MongoDBUtils(String connctionString, String databaseName, String collectionName) {
        this.mongoClient = MongoClients.create(connctionString);
        this.collection = mongoClient.getDatabase(databaseName).getCollection(collectionName);
        System.out.println("Connected to database");
    }

    // Methoden
    // Methode um die Anzahl der Einträge in der Datenbank auszugeben
    public int getPlayerJoinCount(String playerName) {
        Document playerDocument = collection.find(Filters.eq("name", playerName)).first();
        if (playerDocument != null) {
            return playerDocument.getInteger("joinCount", 0); // 0 ist der Standardwert, falls der Eintrag nicht exisitert
        }
        return 0; //Spieler existiert nicht -> 0 zurückgeben
    }
    // Methode, um die Anzahl der Einträge in der Datenbank zu erhöhen
    public void incrementPlayerJoinCount(String playerName) {
        // Suchkriterium: name = playerName
        Bson filter = Filters.eq("name", playerName);
        // Update: joinCount = joinCount + 1
        Bson update = Updates.inc("joinCount", 1);
        // Wenn der Spieler in der Datenbank existiert, dann die Anzahl der Beitritte erhöhen
        // UpdateOne wendet das Update auf das erste Dokument an, das den Filterbedingungen entspricht
        // Wenn kein Dokument gefunden wird, gibt getMatchedCount() 0 zurück, und der folgende Block wird ausgeführt.
        if (collection.updateOne(filter, update).getMatchedCount() == 0) {
            // Spielerdokument existiert noch nicht, also erstellen
            Document newPlayerDocument = new Document("name", playerName).append("joinCount", 1);
            collection.insertOne(newPlayerDocument);
        }
    }

    // Methode, um die Verbindung zur Datenbank zu schliessen
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }

}
