public class lexicon {
    int[] A;
    hashT[] hashTable;

    public class hashT {
        private int key;
        private int value;

        HashEntry(int key, int value){
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

    void HashCreate(lexicon L, int m) {
        L.A = new int[15 * m];
        L.hashTable = new hashT[m];
    }

    void HashEmpty(lexicon L) {
        boolean empty = true;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null) {
                empty = false;
                break;
            }
        }
        if (empty == true) {
            System.out.println("Lexicon is empty");
        }
        else
            System.out.println("Lexicon is not empty");
    }

}
