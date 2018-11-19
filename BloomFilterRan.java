import java.util.BitSet;
import java.util.Random;


public class BloomFilterRan {
	private static BitSet filter;
	private static int hashFNumber;
	private static int [][] hashFunctions;
	private static int added;
	private Random rand;
	private static int prime;//This line has been added

	public BloomFilterRan(int setSize, int bitsPerElement) {
		prime = findNextPrime(setSize * bitsPerElement) ;
		filter = new BitSet();
		hashFNumber = (int) Math.ceil(Math.log(2) * filterSize() / setSize);
		added = 0;
		hashFunctions = new int[hashFNumber][2];
		rand = new Random();//This line hash been added
		for (int i = 0; i < hashFNumber; i++) {
			hashFunction(i, prime);
		}
	}

	public static int filterSize() {
		return filter.size();
	}

	private static int mod(long x, int y) {
		return (int) Long.remainderUnsigned(x, y);
	}

	public static void add(String s)
    {
        s = s.toLowerCase();
        int h = s.hashCode();
        for (int i = 0; i < hashFNumber; i++)
        {
        	long hh = mod ((long)hashFunctions[i][0] * h + hashFunctions [i][1], prime) ;
            filter.set((int) hh);
        }
        added ++;
    }
	
    public static boolean appears(String s)
    {
        s = s.toLowerCase();
        int h = s.hashCode();
        for (int i = 0; i < hashFNumber; i++)
        {
            long hh = mod ((long)hashFunctions[i][0] * h + hashFunctions [i][1], prime) ;
            if (filter.get((int) hh) == false)
                return false;
        }
        return true; // probably the string exists in the set
    }

    public static int dataSize()
    {
        return added;
    }

    public static int numHashes()
    {
        return hashFunctions.length;
    }
    
	private static int findNextPrime(int approxTableSize) {
		for (; !isPrime(approxTableSize); approxTableSize++);
		return approxTableSize;
	}

	private static boolean isPrime(int number) {
		if (number == 2)
			return true;
		if (number % 2 == 0)
			return false;
		for (int i = 3; i * i <= number; i += 2) {
			if (number % i == 0)
				return false;
		}
		return true;
	}

	private void hashFunction(int idx, int p) {
		int a = rand.nextInt(p) ;
		int b = rand.nextInt(p) ;
		hashFunctions [idx][0] = a;
		hashFunctions [idx][1] = b;
		return ;
	}
}
