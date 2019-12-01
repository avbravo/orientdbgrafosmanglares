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
public class ManglesRepository {

    public void createSchema(ODatabaseSession db) {
        try {

            OClass mangles = db.getClass("Mangles");

            if (mangles == null) {
                mangles = db.createVertexClass("Mangles");
            }

            if (mangles.getProperty("name") == null) {
                mangles.createProperty("name", OType.STRING);
                mangles.createIndex("Mangles_name_index", OClass.INDEX_TYPE.NOTUNIQUE, "name");
            }
            if (mangles.getProperty("nombrecientifico") == null) {
                mangles.createProperty("nombrecientifico", OType.STRING);
            }

        } catch (Exception e) {
            System.out.println("createSchema() " + e.getLocalizedMessage());
        }
    }

    public OVertex insert(ODatabaseSession db, String name, String nombrecientifico) {
        OVertex result = db.newVertex("Mangles");
        try {

            result.setProperty("nombre", name);
            result.setProperty("nombrecientifico", nombrecientifico);
            result.save();
        } catch (Exception e) {
            System.out.println("insertMangle() " + e.getLocalizedMessage());
        }

        return result;
    }

}
