import java.util.HashSet;
import java.util.Random;


public class FalsePositive {

	private static int bitPerElement = 4;
	private static int num = 500 * 1000;

	
	public static void main(String[] args) throws Exception {
		
		HashSet<String> inputStringSet = new HashSet<String>();
		long start = System.nanoTime();
		
		inputStringSet = generateFile(num);

		////////// Create Bloom Filters//////////////////
        BloomFilterFNV filterFNV = new BloomFilterFNV(num, bitPerElement);
        BloomFilterMurmur filterMurmur = new BloomFilterMurmur(num, bitPerElement);
        BloomFilterRan filterRan = new BloomFilterRan(num, bitPerElement);

        ///////// Add to FNVBloomFilter //////////////////
        System.out.println("Time Taken To add to Bloom filters: "); 
       
		start = System.nanoTime();
        for (String sCurrentLine : inputStringSet) {
           	filterFNV.add(sCurrentLine);
        }
		time("FNVBloomFilter", (System.nanoTime() - start) / num);
        
        
        ///////// Add to MurmurBloomFilter //////////////////
		start = System.nanoTime();
        for (String sCurrentLine : inputStringSet) {
        	filterMurmur.add(sCurrentLine);
        }
		time("MurmurBloomFilter", (System.nanoTime() - start) / num);

        ///////// Add to RanBloomFilter //////////////////
		start = System.nanoTime();
        for (String sCurrentLine : inputStringSet) {
        	filterRan.add(sCurrentLine);
        }
		time("RanBloomFilter", (System.nanoTime() - start) / num);

        /////////// False Positive ////////////////////
        System.out.println();
        System.out.println("*********************************");
        System.out.println();
        System.out.println("Time Taken To Search in Bloom Filters: "); 

        int falseFNV = 0;
        int falseMurmur = 0;
        int falseRan = 0;
        int falseDynamic = 0;
        
        long timeFNV = 0;
        long timeMurmur = 0;
        long timeRan = 0;
        long timeDynamic = 0;
        
        int i = 0;
        //int test = 1000;
        int test = 1000 ;
        int [][] fp = new int [test][4] ;
        //for (int test = 1 ; test <= 2000 ; test ++) {
            while (i < test) {
                String str = generateString(bitPerElement);
                if (!inputStringSet.contains(str)) {
                    i++;
                    start = System.nanoTime();
                    if (filterFNV.appears(str)) {
                        falseFNV++;
                     //   fp [test][0] ++ ;
                    }
                    timeFNV += (System.nanoTime() - start);

                    start = System.nanoTime();
                    if (filterMurmur.appears(str)) {
                        falseMurmur++;
                       // fp [test][1] ++ ;
                    }
                    timeMurmur += (System.nanoTime() - start);

                    start = System.nanoTime();
                    if (filterRan.appears(str)) {
                        falseRan++;
                    }
                    timeRan += (System.nanoTime() - start);
                }
            }

        time("FNVBloomFilter", timeFNV / test);
        time("MurmurBloomFilter", timeMurmur / test);
        time("RanBloomFilter", timeRan / test);

        System.out.println();
        System.out.println("*********************************");
        System.out.println();
        
        System.out.println("False Positive Rate for Bloom Filters: ");
        System.out.println("BloomFilterFNV : " + (double) (100. * falseFNV)/test + " %");
        System.out.println("BloomFilterMurmur : " + (double) (100. * falseMurmur)/test + " %");
        System.out.println("BloomFilterRan : " + (double) (100. * falseRan)/test + " %");
        System.out.printf(" %.5f \n", (double) (100. * falseFNV)/test);
        System.out.printf(" %.5f \n", (double) (100. * falseMurmur)/test);
        System.out.printf(" %.5f \n", (double) (100. * falseRan)/test);
	}


	

	private static HashSet<String> generateFile(int numberOfStrings) {

        HashSet<String> input = new HashSet<String>();
        for (int i = 0; i < numberOfStrings; i++) {
            input.add(generateString(bitPerElement));
        }

        return input;

    }
	
	private static String generateString(int numberOfCharacters) {
        //1234567890
        String SALTCHARS = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < numberOfCharacters; i++) {
            char character = SALTCHARS.charAt((int)rnd.nextInt(SALTCHARS.length()));
            salt.append(SALTCHARS.charAt(rnd.nextInt(SALTCHARS.length()))) ;
            //salt.append((char)(rnd.nextInt(256))) ;
        }
        return salt.toString();

    }
	
	private static void time (String name, long inputTime)
	{
        System.out.println(name + " : " + inputTime + " nano seconds");		
	}
}
