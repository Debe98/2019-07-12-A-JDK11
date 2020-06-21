package it.polito.tdp.food.model;


public class Event implements Comparable <Event> {

	public enum EventType {
		INIZIO, FINE;
	}

	private EventType type ;
	private Food cibo;
	private Double tempoPreparazione ;
	private Double tempo;
	
	public Event(EventType type, Food cibo, Double tempoPreparazione, Double tempo) {
		super();
		this.type = type;
		this.cibo = cibo;
		this.tempoPreparazione = tempoPreparazione;
		this.tempo = tempo;
	}

	//COSTRUTTORE, GETTERS E SETTERS

	public EventType getType() {
		return type;
	}



	public void setType(EventType type) {
		this.type = type;
	}



	public Food getCibo() {
		return cibo;
	}



	public void setCibo(Food cibo) {
		this.cibo = cibo;
	}



	public Double getTempoPreparazione() {
		return tempoPreparazione;
	}



	public void setTempoPreparazione(Double tempoPreparazione) {
		this.tempoPreparazione = tempoPreparazione;
	}



	public Double getTempo() {
		return tempo;
	}



	public void setTempo(Double tempoInizio) {
		this.tempo = tempoInizio;
	}



	@Override
	public int compareTo(Event o) {
		return this.tempo.compareTo(o.tempo);
	}
}
