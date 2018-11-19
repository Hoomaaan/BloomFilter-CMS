import java.util.*;

public class CMS {
   private static int fx;
   private static ArrayList<String> str;
   private static int[][] cmsTable;
   private static long[] hashFunctions;
   private static final long FNV1_PRIME_64 = 1099511628211L;
   private static int rowNumber;
   private int colNumber;
   
   public CMS(float epsilon, float delta, ArrayList<String> s) {
      rowNumber = (int) Math.ceil(Math.log(1.0/delta));
      colNumber = (int) Math.ceil(2.0/epsilon);
      str = new ArrayList<String>() ;
      for (int i = 0; i < s.size(); i++) {
         if (s.get(i).length() >= 3 && !s.get(i).toLowerCase().equals("the")) {
            str.add(s.get(i)) ;
         }
      }
      cmsTable = new int[rowNumber][colNumber];
      
      for (int i = 0; i < rowNumber; i++) {
         for (int j = 0; j < colNumber; j++) {
            cmsTable [i][j] = 0;
         }
      }
      
      hashFunctions = new long[rowNumber];
      for (int i = 0; i <rowNumber; i++) {
         hashFunctions[i] = hashFunction();
      }
      
      for (int i = 0; i < str.size(); i++) {
         for (int j = 0; j < rowNumber; j++) {
            int k = (int) mod(hash64b(hashFunctions[j], str.get(i)), colNumber);
            cmsTable[j][k] ++;
         }        
      }
   }
   
   public int approximateFrequency(String x) {
      int fx = -1 ;
      for (int i = 0; i < rowNumber; i++) {
         int k = (int) mod(hash64b(hashFunctions[i], x), colNumber);
         if (fx == -1 || fx > cmsTable[i][k])
            fx = cmsTable [i][k] ;
      }
      return fx;
   }
   
   public ArrayList<String> approximateHeavyHitter(float q, float r) {
      ArrayList<String> HeavyHitter = new ArrayList<>();
      int higherThanQ = 0;
      int lessThanQinL = 0 ;
      int lessThanR = 0;
      for (int i = 0; i < str.size(); i++) {
         int hit = approximateFrequency(str.get(i));
         if(r * str.size() <= hit) {
            HeavyHitter.add(str.get(i));
         }

         if (r * str.size() > hit) {
            lessThanR++;
         }else if (q * str.size() <= hit) {
            higherThanQ ++ ;
         } else if (r * str.size() <= hit && hit <= q * str.size()) {
            lessThanQinL ++ ;
         }
      }
      System.out.printf("Number of %.2f heavyhitter are in L: %d\n", q, higherThanQ);
      System.out.printf("Number of 0.025 heavyhitter are in L: %d\n", HeavyHitter.size());
      System.out.printf("Number of items in L that are not %.2f in L: %d\n", q, lessThanQinL);
      System.out.printf("Number of items in L: %d\n", HeavyHitter.size());
      HashSet<String> set = new HashSet<>(HeavyHitter) ;
      System.out.printf("Number of distinct items in L: %d\n", set.size());
      return HeavyHitter;
   }
   
   private static long hashFunction() {
      Random rand = new Random();
      long init  = rand.nextLong();
      return init;
   }
   
   private static int mod (long x, int y) {
      int remain = (int)Long.remainderUnsigned(x, y);
      return remain;
   }
   
   private static long hash64b(long init, final String k) {
       long hash = init;
       for (int i = 0; i < k.length(); i++) {
         hash ^= k.charAt(i);
         hash *= FNV1_PRIME_64;
       }
       return hash;
   }
}