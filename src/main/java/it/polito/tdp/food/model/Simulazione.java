package it.polito.tdp.food.model;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.*;

import it.polito.tdp.food.model.Event.EventType;

public class Simulazione {
	private PriorityQueue<Event> queue = new PriorityQueue<>();

	// VARIABILI
	private Graph <Food, DefaultWeightedEdge> grafo;
	private int k;
	private int stazioniLibere;
	private Food origine;
	private double tempo;
	private List <Food> cibiPreparati;

	// SETTER PER I PARAMETRI ESTERNI
	
	public void setGrafo(Graph <Food , DefaultWeightedEdge> grafo) {
		this.grafo = grafo;
	}
	
	public void init (int k, Food origine) {
		this.k = k;
		this.origine = origine;
	}
	
	// GETTER PER I RISULTATI
	
	
	// FUNZIONI DI UTILITA'
	

	// SIMULAZIONE VERA E PROPRIA

	public void run() {
		// preparazione iniziale (mondo + coda eventi)
		tempo = 0;
		queue = new PriorityQueue<Event>();
		stazioniLibere = k;
		cibiPreparati = new LinkedList<Food>();
		
		if (grafo == null) {
			return;
		}
		
		// inizializzazione coda eventi
		
		for (int i = 0; i< k; i++) {
			Arco next = getNext(origine);
			if (next == null)
				break;
			Event e1 = new Event(EventType.INIZIO, next.getVertice1(), next.getPeso(), tempo);
			queue.add(e1);
			stazioniLibere--;
		}
		
		
		// esecuzione del ciclo di simulazione
		while(!queue.isEmpty()) {
			Event e = this.queue.poll();
			//System.out.println(e);
			processEvent(e);
		}
		
	}
		
	public double getTempo() {
		return tempo;
	}

	public List<Food> getCibiPreparati() {
		return cibiPreparati;
	}

	private Arco getNext(Food origine) {
		List <Food> vicini = Graphs.neighborListOf(grafo, origine);
		vicini.removeAll(cibiPreparati);
		System.out.println(vicini);
		List <Arco> viciniArco = new LinkedList<Arco>();
		
		for (Food fv : vicini) {
			DefaultWeightedEdge e = grafo.getEdge(origine, fv);
			viciniArco.add(new Arco(fv, origine, grafo.getEdgeWeight(e)));
		}
		viciniArco.sort(null);
		
		if (viciniArco.isEmpty())
			return null;
		
		Arco next = viciniArco.get(0);
		cibiPreparati.add(next.getVertice1());
		return next;
	}

	private void processEvent(Event e) {
		switch(e.getType()) {

		case INIZIO:
			tempo = e.getTempo();
			queue.add(new Event(EventType.FINE, e.getCibo(), e.getTempoPreparazione(), tempo+e.getTempoPreparazione()));
			break;

		case FINE:
			tempo = e.getTempo();
			stazioniLibere++;
			Arco next = getNext(e.getCibo());
			if (next == null)
				break;
			queue.add(new Event(EventType.INIZIO, next.getVertice1(), next.getPeso(), tempo));
			stazioniLibere--;
			break;
		}

	}
}
