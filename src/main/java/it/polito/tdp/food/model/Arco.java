package it.polito.tdp.food.model;

public class Arco implements Comparable<Arco>{
	private Food vertice1;
	private Food vertice2;
	private Double peso;
	
	public Arco(Food vertice1, Food vertice2, double peso) {
		super();
		this.vertice1 = vertice1;
		this.vertice2 = vertice2;
		this.peso = peso;
	}

	public Food getVertice1() {
		return vertice1;
	}

	public void setVertice1(Food vertice1) {
		this.vertice1 = vertice1;
	}

	public Food getVertice2() {
		return vertice2;
	}

	public void setVertice2(Food vertice2) {
		this.vertice2 = vertice2;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	@Override
	public String toString() {
		return "Cibo: " + vertice1 + " calorie: " + peso + " ("+vertice2+").";
	}

	@Override
	public int compareTo(Arco o) {
		return -this.peso.compareTo(o.peso);
	}
	
	
}
