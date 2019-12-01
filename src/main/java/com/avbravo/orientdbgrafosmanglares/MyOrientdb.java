/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.orientdbgrafosmanglares;


import com.avbravo.orientdbgrafosmanglares.edge.AfectaEdge;
import com.avbravo.orientdbgrafosmanglares.edge.FactorEdge;
import com.avbravo.orientdbgrafosmanglares.edge.LocalizadoEdge;
import com.avbravo.orientdbgrafosmanglares.edge.ProduceEdge;
import com.avbravo.orientdbgrafosmanglares.vertex.DeforestacionVertex;
import com.avbravo.orientdbgrafosmanglares.vertex.ExitoVertex;
import com.avbravo.orientdbgrafosmanglares.vertex.FracasoVertex;
import com.avbravo.orientdbgrafosmanglares.vertex.LimitesVertex;
import com.avbravo.orientdbgrafosmanglares.vertex.ManglesVertex;
import com.avbravo.orientdbgrafosmanglares.vertex.ObjetosVertex;
import com.avbravo.orientdbgrafosmanglares.vertex.OceanosVertex;
import com.avbravo.orientdbgrafosmanglares.vertex.SalinidadVertex;
import com.avbravo.orientdbgrafosmanglares.vertex.SombraVertex;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.record.OEdge;
import com.orientechnologies.orient.core.record.OVertex;

/**
 *
 * @author avbravo
 */
public class MyOrientdb {

    ManglesVertex manglesVertex = new ManglesVertex();
    OceanosVertex oceanosVertex = new OceanosVertex();
    SombraVertex sombraVertex = new SombraVertex();
    ExitoVertex exitoVertex = new ExitoVertex();
    DeforestacionVertex deforestacionVertex = new DeforestacionVertex();
    SalinidadVertex salinidadVertex = new SalinidadVertex();

    FracasoVertex fracasoVertex = new FracasoVertex();
    ObjetosVertex objetosVertex = new ObjetosVertex();
    LimitesVertex limitesVertex = new LimitesVertex();

    
    AfectaEdge afectaEdge = new AfectaEdge();
    ProduceEdge produceEdge = new ProduceEdge();
    FactorEdge factorEdge = new FactorEdge();
    LocalizadoEdge localizadoEdge = new LocalizadoEdge();
    

    public void createSchema(ODatabaseSession db) {
        try {
            manglesVertex.createSchema(db);
            oceanosVertex.createSchema(db);
            sombraVertex.createSchema(db);
            exitoVertex.createSchema(db);
            deforestacionVertex.createSchema(db);

            fracasoVertex.createSchema(db);
            objetosVertex.createSchema(db);
              limitesVertex.createSchema(db);
              salinidadVertex.createSchema(db);

//Edge
            afectaEdge.createSchema(db);
            produceEdge.createSchema(db);
            factorEdge.createSchema(db);
            localizadoEdge.createSchema(db);
            
          
          

//
        } catch (Exception e) {
            System.out.println("createSchema() " + e.getLocalizedMessage());
        }
    }

    public void insertRecords(ODatabaseSession db) {
        try {

            title("   Insertando Mangles");

            OVertex mangleRojo = manglesVertex.insert(db, "Mangle Rojo", "Rhizophora mangle");
            OVertex mangleBlanco = manglesVertex.insert(db, "Mangle Blanco", "Laguncularia racemosa ");
            OVertex mangleNegro = manglesVertex.insert(db, "Mangle Negro", "Avicennia germinans");

//Oceanos
            title("   Insertando Oceanos");

            OVertex pacifico = oceanosVertex.insert(db, "Pacifico");
            OVertex atlantico = oceanosVertex.insert(db, "Atlantico");

            //Sombra
            title("   Insertando Sombra");
            OVertex sombra = sombraVertex.insert(db, "Sombra");
            
            OVertex pocasombra = sombraVertex.insert(db, "Poca Sombra");
            //Salinidad
            title("   Insertando Salinidad");
            OVertex salinidad= salinidadVertex.insert(db, "Salinidad 0-65 ups");
            
            OVertex salinidadBlanco = sombraVertex.insert(db, "Salinidad  (0-80)ups");
            
            
            
            title("   Insertando Deforestacion");
            OVertex deforestacion = deforestacionVertex.insert(db, "Deforestacion");
            
                       

            //Exito
            title("   Insertando exito");
            OVertex exito = exitoVertex.insert(db, "Exito");

            title("   Insertando fracaso");
            OVertex fracaso = fracasoVertex.insert(db, "Fracaso");

            title("   Insertando fimites");
            OVertex limitesTerrestres = limitesVertex.insert(db, "Limites Terrestres");
            OVertex limitesMarinos = limitesVertex.insert(db, "Limites Marinos");

            //Objetos
            title("   Insertando objetos");
            OVertex plantadocerca = objetosVertex.insert(db, "Plantado cerca del mar");
           
            OVertex rutasagua = objetosVertex.insert(db, "Rutas de agua");
            OVertex subidanivelmar = objetosVertex.insert(db, "Subida nivel mar");          
            OVertex enfermedadsemillas = objetosVertex.insert(db, "Enfermedad Semillas");

            /*
            
             */
            title("  Aristas Mangle Rojo");
            generarMangleRojo(mangleRojo, exito, pacifico, sombra, salinidad, plantadocerca, limitesTerrestres, limitesMarinos, fracaso, subidanivelmar, pocasombra, deforestacion, enfermedadsemillas);
            /*
            
             */
            title("  Aristas Mangle Blanco");
            generarMangleBlanco(mangleBlanco, exito, pacifico, salinidadBlanco, plantadocerca, limitesTerrestres, limitesMarinos, fracaso, deforestacion);



        
            /**
             * MANGLE NEGRO
             */
              /*
            
             */
            title("  Aristas Mangle Negro");
             OEdge mangleNegroedge = mangleNegro.addEdge(exito, "Tiene");
            mangleNegroedge.save();
            mangleNegroedge = mangleNegro.addEdge(fracaso, "Tiene");
           mangleNegroedge.save();
            
            
            
        } catch (Exception e) {
            System.out.println("createMangle() " + e.getLocalizedMessage());
        }

//        OEdge edge2 = bob.addEdge(jim, "FriendOf");
//        edge2.save();
    }

    private void title(String titulo) {
        System.out.println("-------------------------------------");
        System.out.println("     " + titulo + "");
        System.out.println("-------------------------------------");
    }

    
    private void generarMangleRojo(OVertex mangleRojo,OVertex exito, OVertex  pacifico, OVertex sombra,
            OVertex menorsanidad, OVertex plantadocerca, OVertex limitesTerrestres, OVertex  limitesMarinos,
            OVertex fracaso,OVertex subidanivelmar,OVertex pocasombra, OVertex  deforestacion,OVertex enfermedadsemillas ){
        try {
              /*
            
             */
            title("  Aristas Mangle Rojo");
            /**
             * MangleRojo
             *
             * Factores para Éxito Especie pionera en los límites terrestres y
             * marinos Menor salinidad en el agua 0-65 ups Plantado mas cerca al
             * mar Sombra Rutas de agua para depositar semillas y proveer agua
             * Asociada al mangle blanco
             */
            OEdge manglerojoedge = mangleRojo.addEdge(exito, "Produce");
            manglerojoedge.save();
          
            manglerojoedge = mangleRojo.addEdge(pacifico, "Localizado");
            manglerojoedge.save();
            
            

            manglerojoedge = mangleRojo.addEdge(sombra, "Factor");
            manglerojoedge.save();

            manglerojoedge = mangleRojo.addEdge(menorsanidad, "Factor");
            manglerojoedge.save();

            manglerojoedge = mangleRojo.addEdge(plantadocerca, "Factor");
            manglerojoedge.save();

            manglerojoedge = mangleRojo.addEdge(limitesTerrestres, "Factor");
            manglerojoedge.save();

            manglerojoedge = mangleRojo.addEdge(limitesMarinos, "Factor");
            manglerojoedge.save();
           

            
            //Exito de mangle rojo
            OEdge sombraEdge = sombra.addEdge(exito, "Produce");
            sombraEdge.save();

            OEdge menorsanidadEdge = menorsanidad.addEdge(exito, "Produce");
            menorsanidadEdge.save();

            OEdge plantadocercaEdge = plantadocerca.addEdge(exito, "Produce");
            plantadocercaEdge.save();

            OEdge limitesTerrestresEdge = limitesTerrestres.addEdge(exito, "Produce");
            limitesTerrestresEdge.save();

            OEdge limitesMarinosEdge = limitesMarinos.addEdge(exito, "Produce");
            limitesMarinosEdge.save();

            
            //--------------- FRACASO
            manglerojoedge = mangleRojo.addEdge(fracaso, "Produce");
            manglerojoedge.save();

            manglerojoedge = mangleRojo.addEdge(subidanivelmar, "Afecta");
            manglerojoedge.save();

            manglerojoedge = mangleRojo.addEdge(pocasombra, "Afecta");
            manglerojoedge.save();

            manglerojoedge = mangleRojo.addEdge(deforestacion, "Afecta");
            manglerojoedge.save();
            
            manglerojoedge = mangleRojo.addEdge(enfermedadsemillas, "Afecta");
            manglerojoedge.save();
            
            

            OEdge subidanivelmarEdge = subidanivelmar.addEdge(fracaso, "Produce");
            subidanivelmarEdge.save();
            OEdge pocasombraEdge = pocasombra.addEdge(fracaso, "Produce");
            pocasombraEdge.save();
            
            OEdge deforestacionEdge = deforestacion.addEdge(fracaso, "Produce");
            deforestacionEdge.save();
            OEdge enfermedadsemillasEdge = enfermedadsemillas.addEdge(fracaso, "Produce");
            enfermedadsemillasEdge.save();


        } catch (Exception e) {
            System.out.println("generarMangleRojo() "+e.getLocalizedMessage());
        }
        
    }
    
    
   private void generarMangleBlanco(OVertex mangleBlanco,OVertex exito, OVertex  pacifico,
            OVertex menorsanidad, OVertex plantadocerca, OVertex limitesTerrestres, OVertex  limitesMarinos,
            OVertex fracaso, OVertex  deforestacion ){
        try {
              /*
            
             */
            title("  Aristas Mangle Blanco");
            /**
             * MangleBlanco
             *
             * Factores para Éxito Especie pionera en los límites terrestres y
             * marinos Menor salinidad en el agua 0-65 ups Plantado mas cerca al
             * mar Sombra Rutas de agua para depositar semillas y proveer agua
             * Asociada al mangle blanco
             */
            OEdge mangleblancoedge = mangleBlanco.addEdge(exito, "Produce");
            mangleblancoedge.save();
          
            mangleblancoedge = mangleBlanco.addEdge(pacifico, "Localizado");
            mangleblancoedge.save();
            
            

           

            mangleblancoedge = mangleBlanco.addEdge(menorsanidad, "Factor");
            mangleblancoedge.save();

            mangleblancoedge = mangleBlanco.addEdge(plantadocerca, "Factor");
            mangleblancoedge.save();

            mangleblancoedge = mangleBlanco.addEdge(limitesTerrestres, "Factor");
            mangleblancoedge.save();

            mangleblancoedge = mangleBlanco.addEdge(limitesMarinos, "Factor");
            mangleblancoedge.save();
           

            
            //Exito de mangle blanco
           

            OEdge menorsanidadEdge = menorsanidad.addEdge(exito, "Produce");
            menorsanidadEdge.save();

            OEdge plantadocercaEdge = plantadocerca.addEdge(exito, "Produce");
            plantadocercaEdge.save();

            OEdge limitesTerrestresEdge = limitesTerrestres.addEdge(exito, "Produce");
            limitesTerrestresEdge.save();

            OEdge limitesMarinosEdge = limitesMarinos.addEdge(exito, "Produce");
            limitesMarinosEdge.save();

            
            //--------------- FRACASO
            mangleblancoedge = mangleBlanco.addEdge(fracaso, "Produce");
            mangleblancoedge.save();
        

            mangleblancoedge = mangleBlanco.addEdge(deforestacion, "Afecta");
            mangleblancoedge.save();
            
           

            
            OEdge deforestacionEdge = deforestacion.addEdge(fracaso, "Produce");
            deforestacionEdge.save();
           

        } catch (Exception e) {
            System.out.println("generarMangleBlanco() "+e.getLocalizedMessage());
        }
        
    }

}
