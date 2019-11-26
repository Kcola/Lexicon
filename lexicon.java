
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
            asciiW = asciiW + (int)x[i];
        }
        return asciiW;

    }
    private static int hashIndex(int asciiW, int iteration, int length){
        int index = (asciiW+(iteration^2))%length;
        return index;
    }
    private static int hashProb(lexicon L, int hash, int size) {
        int index=hash% size;
        int i = 0;
        while(L.hashTable[index]!=null){
            index = (hash + (i ^ 2)) % size;
            i++;
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

    public static void HashFull() {

    }

    public static void HashInsert(lexicon L, String wordS) {
        char[] word = wordS.toCharArray();
        int asciiW = hash(word);
        int start = 0;
        while (L.A[start] != 0) {
            start++;
        }
        if (start != 0)
            start = start + 1;

        for (int i = start; i < word.length + start; i++)
            L.A[i] = word[i - start];
        int key = hashProb(L,asciiW, L.hashTable.length);
        L.hashTable[key] = new hashT(key, start);
    }

    public static void HashSearch(lexicon L, String wordS) {
        char[] word = wordS.toCharArray();
        int asciiW = hash(word);

        int key = hashIndex(asciiW,0, L.hashTable.length);
        try {
            int j = 0;
            while (L.hashTable[key] != null && key < L.hashTable.length) {
                boolean found = true;
                int i = 0;
                while (L.A[L.hashTable[key].value + i] != 0) {
                    found = found && (word[i] == L.A[L.hashTable[key].value + i]);
                    i++;
                }
                if (found) {
                    System.out.println(wordS + " was found in slot " + key);
                    return;
                }

                else {
                    j++;
                key = hashIndex(asciiW, j, L.hashTable.length);

                }
            }
            System.out.println(wordS + " was not found");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void HashDelete(lexicon L, String wordS) {
        char[] word = wordS.toCharArray();
        int asciiW = hash(word);
        int key = hashIndex(asciiW,0, L.hashTable.length);
        L.hashTable[key] = null;
    }

}
