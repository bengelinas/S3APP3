public class PacketBuilder{

    private long id;
    private long min;
    private long max;
    private String type;
    private String data;

    /**
     * Instanciation d'un packet Builder pour la construction d'un packet
     * @param id Id du current packet
     * @param min Id du premier packet
     * @param max Id du dernier packet
     * @param type Type de packet
     * @param data Data contenu dans le packet
     */

    public PacketBuilder(long id, long min, long max,String type,String data){
        this.id = id;
        this.min = min;
        this.max = max;
        this.type = type;
        this.data = data;
    }

    public PacketBuilder setId(long id){
        this.id = id;
        return this;
    }

    public PacketBuilder setMin(long min){
        this.min = min;
        return this;
    }

    public PacketBuilder setMax(long max){
        this.max = max;
        return this;
    }

    public PacketBuilder setType(String Type){
        this.type = Type;
        return this;
    }


    public PacketBuilder setData(String Data){
        this.data = Data;
        return this;
    }

    /**
     * Permet de build un packet et d'instancier celui-ci
     * @return Un packet complet
     */

    public Packet build() {
        return new Packet(id,min,max,type,data);
    }

}