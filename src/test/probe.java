package test;

import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class probe {

    public static void main(String[] args) throws Exception {

        String tester1 = "2018-04-18";
        String tester2 = "2018-04-19";

        tester1 = tester1.replaceAll("-", "");
        tester2 = tester2.replaceAll("-", "");

        System.out.println(Integer.parseInt(tester1));

        int first = Integer.parseInt(tester1);
        int last = Integer.parseInt(tester2);

        if (first < last) System.out.println("I have no experience!");


    }
}
