/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.damanzano.mongohw2_2;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import java.net.UnknownHostException;

/**
 *
 * @author David Andrés Maznzano Herrera <damanzano>
 */
public class MongoHW2_2 {

    public static void main(String[] args) throws UnknownHostException {
        MongoClient client= new MongoClient();
        DB db = client.getDB("students");
        DBCollection collection = db.getCollection("grades");
        
        DBCursor allGrades = collection.find(new BasicDBObject("type","homework")).sort(new BasicDBObject("student_id", 1).append("score",1));
        try{
            String studentId="";
            while(allGrades.hasNext()){
                DBObject grade = allGrades.next();
                System.out.println(grade);
                if(!studentId.equalsIgnoreCase(((Integer)grade.get("student_id")).toString())){
                    System.out.println("Deleting grade "+grade.get("_id").toString()+" for student "+grade.get("student_id").toString());
                    collection.remove(grade);
                }
                studentId=((Integer)grade.get("student_id")).toString();
            }
        }finally{
            allGrades.close();
        }
    }
    
}
