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
public class SalinidadRepository {
    public void createSchema(ODatabaseSession db) {
        try {

          
            /**
             *
             */
            OClass sombra = db.getClass("Salinidad");

            if (sombra == null) {
                sombra = db.createVertexClass("Salinidad");

            }

            if (sombra.getProperty("name") == null) {
                sombra.createProperty("name", OType.STRING);
                sombra.createIndex("Salinidad_name_index", OClass.INDEX_TYPE.NOTUNIQUE, "name");
            }

          

        } catch (Exception e) {
            System.out.println("createSchema() "+e.getLocalizedMessage());
        }
    }
    
      public OVertex insert(ODatabaseSession db, String name) {
        OVertex result = db.newVertex("Salinidad");
        try {
            result.setProperty("name", name);
            result.save();
        } catch (Exception e) {
            System.out.println("insert() " + e.getLocalizedMessage());
        }
        return result;
    }

}
