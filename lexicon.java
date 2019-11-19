public class lexicon {
    char[] A = null;
    hashT[] hashTable;

    public static class hashT {
        private int key;
        private int value;

       public hashT(int key, int value){
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

    private static int HashedIndex(int asciiW, int m) {
        int position = asciiW % m;
        return position;
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
        int asciiW = 0;
        int start = 0;
        while(L.A[start]!=0){
            start++;
        }
        if(start!=0)
        start = start+1;

        for (int i = 0; i < word.length; i++) {
            asciiW = asciiW + (int) word[i];
        }
        for(int i = start; i < word.length + start; i++)
            L.A[i] = word[i-start];
        int key = HashedIndex(asciiW, L.hashTable.length);
        L.hashTable[key] = new hashT(key, start);
    }

}
