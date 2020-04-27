// 

import java.util.BitSet;
import java.util.Random;

public class BlommFilterMurmur {
	private static BitSet filter;
	private static int hashFNumber;
	private static long[] hashFunctions;
	private static int added;
	
	public static void BloomFilterMurmur(int setSize, int bitsPerElement) {
		filter = new BitSet(findPrime(setSize * bitsPerElement));
		hashFNumber = (int) Math.ceil(Math.log(2) * filterSize() / setSize);
		added = 0;
		hashFunctions = new long[hashFNumber];
		for (int i = 0; i < hashFNumber; i++) {
			hashFunctions[i] = hashFunction();
		}
	}
	
	public static void add (String s) {
		s = s.toLowerCase();
		for (int i = 0; i < hashFNumber; i++) {
			filter.set((int) mod(hash64(s, hashFunctions[i]), filterSize()));
		}
		added ++;
	}
	
	public static boolean appears(String s)
    {
        s = s.toLowerCase();
        for (int i = 0; i < hashFNumber; i++)
        {

            if (filter.get((int) mod(hash64(s, hashFunctions[i]), filterSize()))  == false)
                return false;
        }
        return true; // probably the string exists in the set
    }

    public static int filterSize()
    {
        return filter.size();
    }

    public static int dataSize()
    {
        return added;
    }

    public static int numHashes()
    {
        return hashFunctions.length;
    }

	
	private static int findPrime(int approxFilterSize) {
		for (;!isPrime(approxFilterSize); approxFilterSize++);
		return approxFilterSize;
	}
	
	private static boolean isPrime(int number) {
		if (number == 2) return true;
		if (number % 2 == 0) return false;
		for (int i = 3; i * i <= number; i += 2) {
			if (number % 2 == 0) return false;
		}
		return true;
	}
	
	private static int mod (long x, int y) {
		int remain = (int)Long.remainderUnsigned(x, y);
		return remain;
	}
	
	private static long hashFunction() {
		Random rand = new Random();
		long init = rand.nextLong();
		return init;
	}
	
	
	public static long hash64( final byte[] data, int length, Long seed) {
        final long m = 0xc6a4a7935bd1e995L;
        final int r = 47;

        long h = (seed&0xffffffffl)^(length*m);

        int length8 = length/8;

        for (int i=0; i<length8; i++) {
            final int i8 = i*8;
            long k =  ((long)data[i8+0]&0xff)      +(((long)data[i8+1]&0xff)<<8)
                    +(((long)data[i8+2]&0xff)<<16) +(((long)data[i8+3]&0xff)<<24)
                    +(((long)data[i8+4]&0xff)<<32) +(((long)data[i8+5]&0xff)<<40)
                    +(((long)data[i8+6]&0xff)<<48) +(((long)data[i8+7]&0xff)<<56);

            k *= m;
            k ^= k >>> r;
            k *= m;

            h ^= k;
            h *= m;
        }

        switch (length%8) {
            case 7: h ^= (long)(data[(length&~7)+6]&0xff) << 48;
            case 6: h ^= (long)(data[(length&~7)+5]&0xff) << 40;
            case 5: h ^= (long)(data[(length&~7)+4]&0xff) << 32;
            case 4: h ^= (long)(data[(length&~7)+3]&0xff) << 24;
            case 3: h ^= (long)(data[(length&~7)+2]&0xff) << 16;
            case 2: h ^= (long)(data[(length&~7)+1]&0xff) << 8;
            case 1: h ^= (long)(data[length&~7]&0xff);
                h *= m;
        };

        h ^= h >>> r;
        h *= m;
        h ^= h >>> r;

        return h;
    }


    private static long hash64( final String text, Long init) {
        final byte[] bytes = text.getBytes();
        return hash64( bytes, bytes.length, init);
    }
}
