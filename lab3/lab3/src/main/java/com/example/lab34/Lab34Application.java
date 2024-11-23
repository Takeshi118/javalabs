package com.example.lab34;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MongoDBService {
    private com.example.lab34.MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public MongoDBService(String uri, String dbName, String collectionName) {
        mongoClient = new MongoClient(new com.example.lab34.MongoClientURI(uri));
        database = mongoClient.getDatabase(dbName);
        collection = database.getCollection(collectionName);
    }

    // Create
    public void create(Document document) {
        collection.insertOne(document);
        System.out.println("Document inserted successfully.");
    }

    // Read
    public List<Document> readAll() {
        List<Document> documents = new ArrayList<>();
        collection.find().into(documents);
        return documents;
    }

    public Document readOne(String key, Object value) {
        return collection.find(new Document(key, value)).first();
    }

    // Update
    public void update(String key, Object value, Document newDocument) {
        UpdateResult result = collection.replaceOne(new Document(key, value), newDocument);
        System.out.println(result.getMatchedCount() + " document(s) matched and " + result.getModifiedCount() + " document(s) updated.");
    }

    // Delete
    public void delete(String key, Object value) {
        DeleteResult result = collection.deleteOne(new Document(key, value));
        System.out.println(result.getDeletedCount() + " document(s) deleted.");
    }

    // Close connection
    public void close() {
        mongoClient.close();
        System.out.println("MongoDB connection closed.");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String uri = "mongodb://localhost:27017";
        String dbName = "testDatabase";
        String collectionName = "testCollection";

        MongoDBService service = new MongoDBService(uri, dbName, collectionName);

        while (true) {
            System.out.println("\nSelect an operation:");
            System.out.println("1 - Create document");
            System.out.println("2 - Show all documents");
            System.out.println("3 - Find document");
            System.out.println("4 - Update document");
            System.out.println("5 - Delete document");
            System.out.println("6 - Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Read newline after number

            switch (choice) {
                case 1:
                    System.out.println("Enter name:");
                    String name = scanner.nextLine();
                    System.out.println("Enter age:");
                    int age = scanner.nextInt();
                    scanner.nextLine();  // Read newline after number
                    System.out.println("Enter city:");
                    String city = scanner.nextLine();

                    Document doc = new Document("name", name)
                            .append("age", age)
                            .append("city", city);
                    service.create(doc);
                    break;

                case 2:
                    List<Document> documents = service.readAll();
                    documents.forEach(System.out::println);
                    break;

                case 3:
                    System.out.println("Enter key to search by:");
                    String searchKey = scanner.nextLine();
                    System.out.println("Enter value to search for:");
                    String searchValue = scanner.nextLine();
                    Document foundDoc = service.readOne(searchKey, searchValue);
                    System.out.println(foundDoc != null ? foundDoc : "Document not found.");
                    break;

                case 4:
                    System.out.println("Enter key to update by:");
                    String updateKey = scanner.nextLine();
                    System.out.println("Enter value to update by:");
                    String updateValue = scanner.nextLine();

                    System.out.println("Enter new name:");
                    String newName = scanner.nextLine();
                    System.out.println("Enter new age:");
                    int newAge = scanner.nextInt();
                    scanner.nextLine();  // Read newline after number
                    System.out.println("Enter new city:");
                    String newCity = scanner.nextLine();

                    Document updatedDoc = new Document("name", newName)
                            .append("age", newAge)
                            .append("city", newCity);
                    service.update(updateKey, updateValue, updatedDoc);
                    break;

                case 5:
                    System.out.println("Enter key to delete by:");
                    String deleteKey = scanner.nextLine();
                    System.out.println("Enter value to delete by:");
                    String deleteValue = scanner.nextLine();
                    service.delete(deleteKey, deleteValue);
                    break;

                case 6:
                    service.close();
                    System.out.println("Exiting program.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}

