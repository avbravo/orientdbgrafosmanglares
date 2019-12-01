/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.orientdbgrafosmanglares;


import com.avbravo.orientdbgrafosmanglares.edge.AfectaEdgeRepository;
import com.avbravo.orientdbgrafosmanglares.edge.FactorEdgeRepository;
import com.avbravo.orientdbgrafosmanglares.edge.LocalizadoEdgeRepository;
import com.avbravo.orientdbgrafosmanglares.edge.ProduceEdgeRepository;
import com.avbravo.orientdbgrafosmanglares.repository.DeforestacionRepository;
import com.avbravo.orientdbgrafosmanglares.repository.ExitoRepository;
import com.avbravo.orientdbgrafosmanglares.repository.FracasoRepository;
import com.avbravo.orientdbgrafosmanglares.repository.LimitesRepository;
import com.avbravo.orientdbgrafosmanglares.repository.ManglesRepository;
import com.avbravo.orientdbgrafosmanglares.repository.ObjetosRepository;
import com.avbravo.orientdbgrafosmanglares.repository.OceanosRepository;
import com.avbravo.orientdbgrafosmanglares.repository.SalinidadRepository;
import com.avbravo.orientdbgrafosmanglares.repository.SombraRepository;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.record.OEdge;
import com.orientechnologies.orient.core.record.OVertex;

/**
 *
 * @author avbravo
 */
public class MyOrientdb {

    ManglesRepository manglesRepository = new ManglesRepository();
    OceanosRepository oceanosRepository = new OceanosRepository();
    SombraRepository sombraRepository = new SombraRepository();
    ExitoRepository exitoRepository = new ExitoRepository();
    DeforestacionRepository deforestacionRepository = new DeforestacionRepository();
    SalinidadRepository salinidadRepository = new SalinidadRepository();

    FracasoRepository fracasoRepository = new FracasoRepository();
    ObjetosRepository objetosRepository = new ObjetosRepository();
    LimitesRepository limitesRepository = new LimitesRepository();

    
    AfectaEdgeRepository afectaEdgeRepository = new AfectaEdgeRepository();
    ProduceEdgeRepository produceEdgeRepository = new ProduceEdgeRepository();
    FactorEdgeRepository factorEdgeRepository = new FactorEdgeRepository();
    LocalizadoEdgeRepository localizadoEdgeRepository = new LocalizadoEdgeRepository();
    

    public void createSchema(ODatabaseSession db) {
        try {
            manglesRepository.createSchema(db);
            oceanosRepository.createSchema(db);
            sombraRepository.createSchema(db);
            exitoRepository.createSchema(db);
            deforestacionRepository.createSchema(db);

            fracasoRepository.createSchema(db);
            objetosRepository.createSchema(db);
              limitesRepository.createSchema(db);
              salinidadRepository.createSchema(db);

//Edge
            afectaEdgeRepository.createSchema(db);
            produceEdgeRepository.createSchema(db);
            factorEdgeRepository.createSchema(db);
            localizadoEdgeRepository.createSchema(db);
            
          
          

//
        } catch (Exception e) {
            System.out.println("createSchema() " + e.getLocalizedMessage());
        }
    }

    public void insertRecords(ODatabaseSession db) {
        try {

            title("   Insertando Mangles");

            OVertex mangleRojo = manglesRepository.insert(db, "Mangle Rojo", "Rhizophora mangle");
            OVertex mangleBlanco = manglesRepository.insert(db, "Mangle Blanco", "Laguncularia racemosa ");
            OVertex mangleNegro = manglesRepository.insert(db, "Mangle Negro", "Avicennia germinans");

//Oceanos
            title("   Insertando Oceanos");

            OVertex pacifico = oceanosRepository.insert(db, "Pacifico");
            OVertex atlantico = oceanosRepository.insert(db, "Atlantico");

            //Sombra
            title("   Insertando Sombra");
            OVertex sombra = sombraRepository.insert(db, "Sombra");
            
            OVertex pocasombra = sombraRepository.insert(db, "Poca Sombra");
            //Salinidad
            title("   Insertando Salinidad");
            OVertex salinidad= salinidadRepository.insert(db, "Salinidad 0-65 ups");
            
            OVertex salinidadBlanco = sombraRepository.insert(db, "Salinidad  (0-80)ups");
            
            
            
            title("   Insertando Deforestacion");
            OVertex deforestacion = deforestacionRepository.insert(db, "Deforestacion");
            
                       

            //Exito
            title("   Insertando exito");
            OVertex exito = exitoRepository.insert(db, "Exito");

            title("   Insertando fracaso");
            OVertex fracaso = fracasoRepository.insert(db, "Fracaso");

            title("   Insertando fimites");
            OVertex limitesTerrestres = limitesRepository.insert(db, "Limites Terrestres");
            OVertex limitesMarinos = limitesRepository.insert(db, "Limites Marinos");

            //Objetos
            title("   Insertando objetos");
            OVertex plantadocerca = objetosRepository.insert(db, "Plantado cerca del mar");
           
            OVertex rutasagua = objetosRepository.insert(db, "Rutas de agua");
            OVertex subidanivelmar = objetosRepository.insert(db, "Subida nivel mar");          
            OVertex enfermedadsemillas = objetosRepository.insert(db, "Enfermedad Semillas");

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
