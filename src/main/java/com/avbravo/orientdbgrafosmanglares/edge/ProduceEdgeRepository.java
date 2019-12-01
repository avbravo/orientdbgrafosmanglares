package com.avbravo.orientdbgrafosmanglares.edge;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OType;

/**
 *
 * @author avbravo
 */
public class ProduceEdgeRepository {
    public void createSchema(ODatabaseSession db) {
        try {

                if (db.getClass("Produce") == null) {
                    db.createEdgeClass("Produce");
            }

        } catch (Exception e) {
            System.out.println("createSchema() "+e.getLocalizedMessage());
        }
    }

}
