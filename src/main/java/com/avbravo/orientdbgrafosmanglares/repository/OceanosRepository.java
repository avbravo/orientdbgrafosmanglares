/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.orientdbgrafosmanglares.repository;

import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.OVertex;

/**
 *
 * @author avbravo
 */
public class OceanosRepository {
    public void createSchema(ODatabaseSession db) {
        try {

           
            /**
             *
             */
            OClass oceanos = db.getClass("Oceanos");

            if (oceanos == null) {
                oceanos = db.createVertexClass("Oceanos");

            }

            if (oceanos.getProperty("name") == null) {
                oceanos.createProperty("name", OType.STRING);
                oceanos.createIndex("Oceanos_name_index", OClass.INDEX_TYPE.NOTUNIQUE, "name");
            }
          
        } catch (Exception e) {
            System.out.println("createSchema() "+e.getLocalizedMessage());
        }
    }
    
    
      public OVertex insert(ODatabaseSession db, String name) {
        OVertex result = db.newVertex("Oceanos");
        try {
            result.setProperty("name", name);
            result.save();
        } catch (Exception e) {
            System.out.println("insertOceano(s) " + e.getLocalizedMessage());
        }
        return result;
    }


}
