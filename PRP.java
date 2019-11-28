
public class PRP{
    public static void main(String[] args) {
        lexicon L = new lexicon();
        lexicon.HashCreate(L,15);
        lexicon.HashEmpty(L);
        lexicon.HashInsert(L, "yer");
        lexicon.HashInsert(L, "eyr");
        lexicon.HashInsert(L, "rey");
        lexicon.HashSearch(L, "rey");
        //lexicon.HashDelete(L, "yer");
        //lexicon.HashSearch(L, "yer");
        return;
    }
    
}