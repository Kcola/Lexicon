
public class PRP{
    public static void main(String[] args) {
        lexicon L = new lexicon();
        lexicon.HashCreate(L,15);
        
        lexicon.HashInsert(L, "yer");
        lexicon.HashInsert(L, "eyr");
        lexicon.HashInsert(L, "yer");
        lexicon.HashDelete(L, "yer");
        lexicon.HashDelete(L, "yer");
        lexicon.HashDelete(L, "y");
        lexicon.HashEmpty(L);
        return;
    }
    
}