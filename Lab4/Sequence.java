public class Sequence {
    public LList sequence;
    DNAList.SeqType type;
    public Sequence(DNAList.SeqType type, LList sequence) {
        this.type = type;
        this.sequence = sequence;
    }

    @Override
    public String toString() {
        return type.toString() + "\t" + sequence.toString();
    }
}
