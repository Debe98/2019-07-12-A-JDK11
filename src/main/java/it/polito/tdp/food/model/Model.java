package it.polito.tdp.food.model;

import java.util.*;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.*;
import org.jgrapht.traverse.*;

import it.polito.tdp.food.db.FoodDao;

public class Model {

	
	private Map <Integer, Portion> porzioni;
	private Map <Integer, Food> foods;
	private FoodDao dao;
	private Graph <Food, DefaultWeightedEdge> grafo;
	private Simulazione sim;

	public Model() {
		porzioni = new HashMap <> ();
		foods = new HashMap<Integer, Food>();
		dao = new FoodDao();
		sim = new Simulazione();
	}
	
	public void creaGrafo(int n) {
		grafo = new SimpleWeightedGraph<Food, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		dao.listAllPortions(porzioni);
		dao.listAllFoods(foods);
		
		//Get Vertici
		Graphs.addAllVertices(grafo, dao.getVertex(foods, n));
		System.out.println("#Vertici: "+grafo.vertexSet().size());
		
		//Get Archi 
		List <Arco> archi = dao.getArchi(foods, n);
		System.out.println("#Archi-pre: "+archi.size());
		
		for (Arco a : archi) {
			Food v1 = a.getVertice1();
			Food v2 = a.getVertice2();
			if (grafo.containsVertex(v1) && grafo.containsVertex(v2)) {
				
				if (!grafo.containsEdge(v1, v2)) {
					Graphs.addEdge(grafo, v1, v2, a.getPeso());
				}
				else {
					
				}
			}
		}
		System.out.println("#Archi-post: "+grafo.edgeSet().size());
	}
	
	public List <Food> getVertex() {
		Set <Food> vertici = grafo.vertexSet();
		List <Food> Verticiutili = new LinkedList<> ();
		for (Food f : vertici) {
			if (grafo.degreeOf(f) > 0) {
				Verticiutili.add(f);
			}
				
		}
		return Verticiutili;
	}

	public List<Arco> getVicini(Food f) {
		List <Food> vicini = Graphs.neighborListOf(grafo, f);
		System.out.println(vicini);
		List <Arco> viciniArco = new LinkedList<Arco>();
		
		for (Food fv : vicini) {
			DefaultWeightedEdge e = grafo.getEdge(f, fv);
			viciniArco.add(new Arco(fv, f, grafo.getEdgeWeight(e)));
		}
		viciniArco.sort(null);
		return viciniArco;
	}
	
	
	public void simula(int k, Food origine) {
		if(this.grafo != null) {
			sim.setGrafo(this.grafo);
			sim.init(k, origine);
			sim.run();
		}
	}

	public List<Food> getLista() {
		return sim.getCibiPreparati();
	}

	public Double getTempo() {
		// TODO Auto-generated method stub
		return sim.getTempo();
	}
}
