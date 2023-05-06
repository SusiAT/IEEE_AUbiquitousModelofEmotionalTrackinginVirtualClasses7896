/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.emociones;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import java.util.Iterator;
import org.mindswap.pellet.jena.PelletReasonerFactory;

/**
 *
 * @author pc
 */
public class NewClass {
    public static void main(String[] args) {
         System.out.println("Results using Jena interface");
        System.out.println("----------------------------");
        runWithJena();
    }
    
     public static void runWithJena() {
        // ontology that will be used
        String ont = "http://www.mindswap.org/2004/owl/mindswappers#";

        // load the ontology with its imports and no reasoning
		OntModel model = ModelFactory.createOntologyModel( PelletReasonerFactory.THE_SPEC );
		model.read( ont );

		// load the model to the reasoner
		model.prepare();
		
		// create property and resources to query the reasoner
		OntClass Person = model.getOntClass("http://xmlns.com/foaf/0.1/Person");
		Property workHomepage = model.getProperty("http://xmlns.com/foaf/0.1/workInfoHomepage");
		Property foafName = model.getProperty("http://xmlns.com/foaf/0.1/name");
		
		// get all instances of Person class
		Iterator<?> i = Person.listInstances();
		while( i.hasNext() ) {
		    Individual ind = (Individual) i.next();
		    
		    // get the info about this specific individual
		    String name = ((Literal) ind.getPropertyValue( foafName )).getString();
		    Resource type = ind.getRDFType();
		    Resource homepage = (Resource) ind.getPropertyValue(workHomepage);
		    
		    // print the results
		    System.out.println("Name: " + name);
		    System.out.println("Type: " + type.getLocalName());
		    if(homepage == null)
		        System.out.println("Homepage: Unknown");
		    else
		        System.out.println("Homepage: " + homepage);
		    System.out.println();
		}
    }
}
