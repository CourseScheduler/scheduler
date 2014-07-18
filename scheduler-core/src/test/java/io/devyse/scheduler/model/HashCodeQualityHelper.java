package io.devyse.scheduler.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.testng.asserts.SoftAssert;

/**
 * Helper class for confirming if the hashcode calculation for a given
 * Object stands up to basic collision tests and requirements. These
 * requirements are simply:
 * 		a maximum number of unique source object can generate a single hashcode
 * 		the average number of collisions per unique hashcode
 *
 * @author Mike Reinhold
 *
 */
public class HashCodeQualityHelper {
	
	/**
	 * Funtional interface defining an object generator based on a Random
	 * source for initializing fields. This will yield varietous, if not
	 * realistic, object data for testing hashcode implementations
	 *
	 * @author Mike Reinhold
	 */
	public interface RandomGenerator{
		public Object generate(Random source);
	}

	/**
	 * PRNG seed for the hash code generator used in the hashcode quality test
	 */
	public static final long RANDOM_GENERATOR_SEED = 1024L;

	/**
	 * Number of DateTimeBlock objects to generate in testing the hashcode quality
	 */
	public static final int SAMPLE_SIZE = 1000000;

	/**
	 * Maximum number of per hashcode collisions. If any hashcode occurs more than
	 * the number specified here, the test will fail
	 */
	public static final int MAX_COLLISIONS_PER_HASH = 3;

	/**
	 * Maximum number of average hashcode collisions. If the average number of collisions
	 * is greater than the number specified here, the test will fail
	 */
	public static final int AVG_COLLISIONS_PER_SET = 2;
	
	/**
	 * Verify that the hashCode method of the objects under test exhibit some level
	 * of desired distribution and collision resistance. 
	 *
	 * @param instanceSupplier the RandomGenerator method to create instances
	 * @param seed the PRNG seed
	 * @param sampleSize the number of objects to hash
	 * @param maxHashCollisionLimit maximum number of per hash collisions 
	 * @param avgHashCollisionLimit average number of per set collisions
	 */
	public static void confirmHashCodeQuality(RandomGenerator instanceSupplier, long seed, int sampleSize, int maxHashCollisionLimit, int avgHashCollisionLimit){
		Random generator = new Random(seed);
		Map<Integer, Integer> hashCodes = new HashMap<Integer, Integer>();
		Set<Object> instances = new HashSet<Object>();
		
		for(int bottom = 0; bottom < sampleSize; bottom++){
			Object block = instanceSupplier.generate(generator);
			
			if(!instances.contains(block)){
				Integer hashCode = Integer.valueOf(block.hashCode());
				Integer occurrences = hashCodes.get(hashCode);
				if(occurrences == null){
					occurrences = 0;
				}
				hashCodes.put(hashCode, occurrences+1);
				instances.add(block);
			}
		}
		
		Collection<Integer> occurrenceCount = hashCodes.values();
		int maxCollisions = occurrenceCount.stream().mapToInt(Integer::intValue).max().getAsInt();
		double averageCollisions = occurrenceCount.stream().mapToInt(Integer::intValue).average().getAsDouble();
		
		SoftAssert hcq = new SoftAssert();
		
		hcq.assertTrue(maxCollisions < maxHashCollisionLimit, "Maximum number of collisions for individual hashcode exceeded allowed value");
		hcq.assertTrue(averageCollisions < avgHashCollisionLimit, "Average number of collisions per hashcode exceeded allowed value");
		
		hcq.assertAll();
	}
	
	/**
	 * Verify that the hashCode method of the objects under test exhibit some level
	 * of desired distribution and collision resistance. 
	 *
	 * @param collection an iterator of the objects to test
	 * @param seed the PRNG seed
	 * @param maxHashCollisionLimit maximum number of per hash collisions 
	 * @param avgHashCollisionLimit average number of per set collisions
	 */
	public static void confirmHashCodeQuality(Collection<Object> collection, long seed, int maxHashCollisionLimit, int avgHashCollisionLimit){
		Iterator<Object> iterator = collection.iterator();
		confirmHashCodeQuality((Random r) -> {return iterator.next();}, seed, collection.size(), maxHashCollisionLimit, avgHashCollisionLimit);
	}
	
	/**
	 * Verify that the hashCode method of the objects under test exhibit some level
	 * of desired distribution and collision resistance. 
	 *
	 * @param instanceSupplier the RandomGenerator method
	 */
	public static void confirmHashCodeQuality(RandomGenerator instanceSupplier){
		confirmHashCodeQuality(instanceSupplier, RANDOM_GENERATOR_SEED, SAMPLE_SIZE, MAX_COLLISIONS_PER_HASH, AVG_COLLISIONS_PER_SET);
	}
	
	/**
	 *Verify that the hashCode method of the objects under test exhibit some level
	 * of desired distribution and collision resistance. 
	 *
	 * @param iterator an iterator of the objects to test
	 */
	public static void confirmHashCodeQuality(Collection<Object> collection){
		confirmHashCodeQuality(collection, RANDOM_GENERATOR_SEED, MAX_COLLISIONS_PER_HASH, AVG_COLLISIONS_PER_SET);
	}
	
}
