import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class NaiveDifferential {
    ArrayList <Record> dbList ;

    NaiveDifferential() {
        this.dbList = new ArrayList<Record>() ;
    }

    private class Record {
        String a, b ;
        Record(String a, String b) {
            this.a = a ;
            this.b = b ;
        }
        String getKey() {
            return this.a ;
        }
        String getRecord() {
            return this.b ;
        }
    }
    private ArrayList <Record> readRecord(String fileAddress) {
        File file = new File("src/" + fileAddress);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList <Record> arr = new ArrayList<Record>() ;
        while (sc.hasNext()) {
            String line = sc.nextLine() ;
            StringBuilder key = new StringBuilder("") ;
            int space = 0 ;
            for (int i = 0 ; i < line.length() ; i ++) {
                if (line.charAt(i) == ' ') {
                    space++;
                }
                if (space < 4) {
                    key.append(line.charAt(i)) ;
                }else
                    break ;
            }
            arr.add(new Record(key.toString(), line)) ;
        }
        return arr ;
    }

    public String retrieveRecord(String key) {
        if (this.dbList.isEmpty()) {
            this.dbList = this.readRecord("database.txt");
        }
        for (Record r : this.dbList) {
            if (r.getKey().equals(key)) {
                return r.getRecord() ;
            }
        }
        return "not found in diffFile nor in database" ;
    }
}