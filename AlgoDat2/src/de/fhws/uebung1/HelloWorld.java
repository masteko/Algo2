package de.fhws.uebung1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class HelloWorld {
	static ArrayList<int[]> times = new ArrayList<>();

	public static void main(String[] args) {
		print(10);
		print(100);
		print(1000);
		print(10000);
		saveTimes();
	}
	
	public static void print(int amount) {
		long before;
		long after;
		int[] time = new int[2];
		time[0] = amount;
		
		System.out.println();
		before = System.nanoTime();

		for (int i = 1; i <= amount; i++) {
			System.out.println("Hello World");	
		}

		after = System.nanoTime();
		time[1] = (int) (after - before);
		times.add(time);
		System.out.println(amount + "x Zeit: " + (after - before));			
	}
	
	public static void saveTimes() {
		try(FileWriter fw = new FileWriter("times.csv");
			BufferedWriter bf = new BufferedWriter(fw)) {
			bf.write("Anzahl der Ausgaben;Dauer in Nanosekunden\n");
			for (int[] time : times) {
				bf.write(time[0] + ";" + time[1] + "\n");
			}
			
			bf.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
