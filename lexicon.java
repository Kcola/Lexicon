import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class lexicon {
    char[] A = null;
    hashT[] hashTable;

    public static class hashT {
        private Integer key;
        private Integer value;

        public hashT(int key, int value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public int getValue() {
            return value;

        }
    }

    private static int hash(char[] x) {
        int asciiW = 0;

        for (int i = 0; i < x.length; i++) {
            asciiW = asciiW + (int) x[i];
        }
        return asciiW;

    }

    private static int hashIndex(int asciiW, int iteration, int length) {// single step of hash function with an
                                                                         // iteration parameter for search operation
        int index = (asciiW + (iteration * iteration)) % length;
        return index;
    }

    private static int hashProb(lexicon L, int hash, int size) {// quadratic probing for insert operation
        int index = hash % size;
        int i = 0;
        while (L.hashTable[index] != null && i <= L.hashTable.length - 1) {
            index = (hash + (i * i)) % size;
            i++;
            if (i > L.hashTable.length - 1) { // after n-1 probing operations with no open slot T is doubled in size and
                                              // approximated to the nearest prime number
                HashIncreaseSize(L);
                index = hashProb(L, hash, L.hashTable.length);
                return index;
            }
        }

        return index;
    }

    public static void HashCreate(lexicon L, int m) {
        L.A = new char[15 * m];
        L.hashTable = new hashT[m];
    }

    public static void HashEmpty(lexicon L) {
        boolean empty = true;
        for (int i = 0; i < L.hashTable.length; i++) {
            if (L.hashTable[i] != null) {
                empty = false;
                break;
            }
        }
        if (empty == true) {
            System.out.println("Lexicon is empty");
        } else
            System.out.println("Lexicon is not empty");
    }

    public static boolean HashFull(lexicon L) {
        for (int j = 0; j < L.hashTable.length; j++) {
            if (L.hashTable[j] == null)
                return false;
        }
        return true;
    }
    public static boolean CanWordFit(lexicon L, char[] word){
        int i=0;
        while(L.A[i]+L.A[i+1]!=0)
        i++;

        if(word.length>L.A.length - i)
        return false;
        else
        return true;
    }
    public static void HashInsert(lexicon L, String wordS) {
        char[] word = wordS.toCharArray();
        int asciiW = hash(word);
        int start = 0;
        while (L.A[start] + L.A[start + 1] != 0) {
            start++;
        }
        if (start != 0)
            start = start + 1;
        int key = hashProb(L, asciiW, L.hashTable.length);
        boolean CanFit = CanWordFit(L,word);
        while(!CanFit){
        char[] array = new char[L.A.length * 2];
        System.arraycopy(L.A, 0, array, 0, L.A.length);
        L.A = array;
        CanFit = CanWordFit(L,word);
        }
        for (int i = start; i < word.length + start; i++)
                L.A[i] = word[i - start];
        
        L.hashTable[key] = new hashT(key, start);
    }

    public static int HashSearch(lexicon L, String wordS) {
        char[] word = wordS.toCharArray();
        int asciiW = hash(word);

        int key = hashIndex(asciiW, 0, L.hashTable.length);
        try {
            int j = 0;
            while (j < L.hashTable.length) {// probe hashtable at most n-1 times
                boolean found = true;
                int i = 0;
                if (L.hashTable[key] != null ) {// if computed hashtable index is not null check if word in that slot is
                                               // the key we need
                    while (L.A[L.hashTable[key].value + i] != 0) {
                        found = found && (word[i] == L.A[L.hashTable[key].value + i]);
                        i++;
                    }
                    if (found) {
                        System.out.println(wordS + " was found in slot " + key);
                        return key;
                    }

                    else {
                        j++;
                        key = hashIndex(asciiW, j, L.hashTable.length);

                    }
                } else {// this if-else allows iteration to continue even if a previous hash index is
                        // null in case of deletion
                    j++;
                    key = hashIndex(asciiW, j, L.hashTable.length);
                }

            }
            System.out.println(wordS + " was not found");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;

    }

    public static int HashSearchNoPrint(lexicon L, String wordS) {// A copy of the search operation for deletion.
        char[] word = wordS.toCharArray();
        int asciiW = hash(word);

        int key = hashIndex(asciiW, 0, L.hashTable.length);
        try {
            int j = 0;
            while (j < L.hashTable.length) {// probe hashtable at most n-1 times
                boolean found = true;
                int i = 0;
                if (L.hashTable[key] != null ) {// if computed hashtable index is not null check if word in that slot is
                                               // the key we need
                    while (L.A[L.hashTable[key].value + i] != 0) {
                        found = found && (word[i] == L.A[L.hashTable[key].value + i]);
                        i++;
                    }
                    if (found) {
                        return key;
                    }

                    else {
                        j++;
                        key = hashIndex(asciiW, j, L.hashTable.length);

                    }
                } else {// this if-else allows iteration to continue even if a previous hash index is
                        // null in case of deletion
                    j++;
                    key = hashIndex(asciiW, j, L.hashTable.length);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;

    }

    public static void HashDelete(lexicon L, String wordS) {
        int key = HashSearchNoPrint(L, wordS);
        int i = 0;
        if (key != -1) {
            while (L.A[L.hashTable[key].value + i] != 0) {
                L.A[L.hashTable[key].value + i] = '*';
                i++;
            }
            L.hashTable[key] = null;
            System.out.println(wordS + " deleted from slot " + key + ".");
        } else
            System.out.println(wordS + " was not found hence no deletion.");
    }

    public static void HashPrint(lexicon L) {
        if (L.hashTable[0] != null)
            System.out.print(0 + ": " + L.hashTable[0].value);
        else
            System.out.print(0 + ": ");
        System.out.print("               A: ");
        for (int j = 0; j < L.A.length - 1; j++) {
            if (L.A[j] == 0) {
                if (L.A[j + 1] != 0)
                    System.out.print("/");
                System.out.print("");
            } else
                System.out.print(L.A[j]);
        }
        System.out.println();
        for (int i = 1; i < L.hashTable.length; i++) {
            if (L.hashTable[i] != null)
                System.out.println(i + ": " + L.hashTable[i].value);
            else
                System.out.println(i + ": ");

        }

    }

    public static void HashBatch(lexicon L, String FileName) {
        String[] lineArray = new String[2];
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(FileName));
            String line = reader.readLine();
            while (line != null) {
                lineArray = line.split(" ");
                switch (Integer.parseInt(lineArray[0])) {
                case 10:
                    HashInsert(L, lineArray[1]);
                    break;
                case 11:
                    HashDelete(L, lineArray[1]);
                    break;
                case 12:
                    HashSearch(L, lineArray[1]);
                    break;
                case 13:
                    HashPrint(L);
                    break;
                case 14:
                    HashCreate(L, Integer.parseInt(lineArray[1]));
                    break;
                case 15:

                    break;

                default:
                    break;
                }
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean prime(int newSize) {// check if number is a prime number
        if (newSize % 2 == 0 || newSize % 3 == 0)
            return false;
        if (newSize <= 3)
            return true;
        for (int i = 5; i * i <= newSize; i = i + 6)
            if (newSize % i == 0 || newSize % (i + 2) == 0)
                return false;

        return true;
    }

    public static void HashIncreaseSize(lexicon L) {// set hashtable new size to nearest prime number after doubling
                                                    // initial size.
        int newSize = L.hashTable.length * 2;
        int i = 0;
        while (!prime(newSize + i))
            i++;
        newSize = newSize + i;
        lexicon L2 = new lexicon();
        HashCreate(L2, newSize);
        List<String> strings = charArrayToStrings(L);
        for (String string : strings) {
            HashInsert(L2, string);
        }
        L.hashTable = L2.hashTable;
        System.out.println("Hashtable size increased to " + newSize + " and contents rehashed");
    }

    public static List<String> charArrayToStrings(lexicon L) {
        int i = 0;
        List<String> strings = new ArrayList<String>();
        while (L.A[i] + L.A[i + 1] != 0) {
            if (L.A[i] == 0) {
                i++;
                continue;
            }

            String currString = "";
            int j = i;
            while (L.A[j] != 0) {
                currString = currString + L.A[j];
                j++;
            }
            i = j;
            boolean deleted = false;
            for (int k = 0; k < currString.length(); k++) {
                deleted = deleted || currString.charAt(k) == '*';
            }
            if (!deleted)
                strings.add(currString);

            i++;
        }
        return strings;
    }
}
