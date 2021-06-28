import java.util.*;
import java.util.concurrent.*;

public class Main
{
	static int generations = 8000;
	static int populationSize = 20;
	static double mutateRate = 5;
	static String goal = "Hello world!";
	
	static ArrayList<Individual> population = new ArrayList<>();
	
	public static void main(String[] args) throws InterruptedException
	{
		System.out.println("Your first population");
		initialize(population);
		calcFitness(population);
		printResults(population);
		System.out.println();
        
		for(int i = 0; i < generations; i++){
			population = crossover(selection(population));
			calcFitness(population);
			printResults(population);
			System.out.println("Generation - " + i);
		}
	}
	
	static void initialize(ArrayList population){
		for(int i = 0; i < populationSize; i++){
			Individual individ = new Individual(goal.length());
			
			for(int b = 0; b < individ.genes.length; b++){
				individ.genes[b] = (int)Math.round(Math.random() * (126 - 32) + 32);
			}
			
			population.add(individ);
		}
	}
	
	static void calcFitness(ArrayList population){
		for(Individual individ:population){
			for(int b = 0; b < individ.genes.length; b++){
                if(individ.genes[b] == (int)goal.charAt(b)){
                    individ.fitness++;
                }
            }
		}
	}
	
	static ArrayList selection(ArrayList population){
		ArrayList <Individual> parents = new ArrayList<>();	
		
		while(parents.size() < population.size()/2){
			Individual bestInd = new Individual(goal.length());
			for(int i = 0; i < 3; i++){
				Individual ind = (Individual)population.get((int)(Math.random()*population.size()));
				while(parents.contains(ind)){
					ind = (Individual)population.get((int)(Math.random()*population.size()));
				}
				
				if(ind.fitness > bestInd.fitness)
					bestInd = ind;
			}
			parents.add(bestInd);
		}
		
		return parents;
	}
	
	static ArrayList crossover(ArrayList population){
		ArrayList <Individual> childs = new ArrayList<>(populationSize);
		
		while(populationSize > childs.size()){
				Individual child = new Individual(goal.length());
				
				Individual parent1 = (Individual) population.get((int)(Math.random()*population.size()));
				Individual parent2 = (Individual) population.get((int)(Math.random()*population.size()));
				
				int cutLine = (int)(Math.random()*((parent1.genes.length))-1)+1;
			
				
				while(parent1 == parent2){
					parent2 = (Individual)population.get((int)(Math.random()*population.size()));
				}
				
				for(int b = 0; b < cutLine; b++){
					child.genes[b] = parent1.genes[b];
				}
				
				for(int b = cutLine; b < child.genes.length; b++){
					child.genes[b] = parent2.genes[b];
				}
			
				childs.add(child);
		}
		
		mutation(childs);
		return childs;
	}
	
	static void mutation(ArrayList population){
		for(Individual ind:population){
			float chance = (float)(Math.random()*100);
			if(chance <= mutateRate)
				ind.genes[(int)(Math.random()*ind.genes.length)] = (int)Math.round(Math.random()*(126-32)+32);
		}
	}
	
	static void printResults(ArrayList population){
		System.out.println("*************");
		for(Individual ind:population){
            String str = "";
            for(int gene:ind.genes){
                str += Character.toString((char)gene);
            }
            System.out.println(str + " | fittness - " + ind.fitness);
		}
		System.out.println("*************");
	}
}
