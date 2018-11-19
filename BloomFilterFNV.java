import java.util.BitSet;
import java.util.Random;

public class BloomFilterFNV {
	private static BitSet filter;
	private static final long FNV1_PRIME_64 = 1099511628211L;
	private static long[] hashFunctions;
	private static int hashFNumber;
	private static int added = 0;
	
	public BloomFilterFNV(int setSize, int bitsPerElement) {
		filter = new BitSet(findPrime(setSize * bitsPerElement));
		hashFNumber = (int) Math.ceil(Math.log(2) * filterSize() / setSize);
		hashFunctions = new long[hashFNumber];
		for (int i = 0; i <hashFNumber; i++) {
			hashFunctions[i] = hashFunction();
		}
	}
	
	public static void add(String s) {
		s = s.toLowerCase();
		for (int i = 0; i < hashFNumber; i++) {
			filter.set((int) mod(hash64b(hashFunctions[i], s), filterSize()));
		}
		added++;
	}
	
	public static boolean appears(String s) {
		s = s.toLowerCase();
		for (int i = 0; i < hashFNumber; i++) {
			if (filter.get((int) mod(hash64b(hashFunctions[i], s), filterSize())) == false)
				return false;
		}
		return true;
	}
	
	public static int filterSize() {
		return filter.size();
	}
		
	public static int dataSize() {
		return added;
	}
	
	public static int numHashes() {
		return hashFunctions.length;
	}
	private static long hash64b(long init, final String k) {
	    long hash = init;
	    for (int i = 0; i < k.length(); i++) {
	      hash ^= k.charAt(i);
	      hash *= FNV1_PRIME_64;
	    }
	    return hash;
	}
	
	private static int findPrime(int approxFilterSize) {
		for (;!isPrime(approxFilterSize); approxFilterSize++);
		return approxFilterSize;
	}
	
	private static boolean isPrime(int number) {
		if (number == 2) return true;
		if (number % 2 == 0) return false;
		for (int i = 3; i * i <= number; i = i + 2) {
			if (number % i == 0) return false;
		}
		return true;
	}
	
	//used Random generator to make k different hash functions
	private static long hashFunction() {
		Random rand = new Random();
		long init  = rand.nextLong();
		return init;
	}
	
	private static int mod (long x, int y) {
		int remain = (int)Long.remainderUnsigned(x, y);
		return remain;
	}
	
	
}
