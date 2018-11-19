import java.io.FileNotFoundException;

public class EmpericalComparison {
    EmpericalComparison () {
        String [] keys = {"Arabella 's boy walking", "ARTICLE_DET 1_NUM Section_NOUN 1_NUM"};

        for (int i = 0 ; i < keys.length ; i ++) {
            Long start = System.currentTimeMillis();
            BloomDifferential bd = new BloomDifferential();
            bd.createFilter();
            String recordBd = bd.retrieveRecord(keys [i]) ;
            Long end = System.currentTimeMillis();
            double time_elapsed = (end - start)/1000.0 ;
            System.out.printf("For BloomDifferential and for the record %s the diffFile.txt takes %.2f seconds \n",(i == 0? "not in" : "in"),time_elapsed);

            start = System.currentTimeMillis();
            NaiveDifferential nd = new NaiveDifferential();
            String recordNd = nd.retrieveRecord(keys [i]) ;
            end = System.currentTimeMillis();
            time_elapsed = (end - start)/1000.0 ;
            System.out.printf("For NaiveDifferential and for the record %s the diffFile.txt takes %.2f seconds \n",(i == 0? "not in" : "in"), time_elapsed);
            System.out.printf("Found record %s\n", recordNd);
        }
    }
}
