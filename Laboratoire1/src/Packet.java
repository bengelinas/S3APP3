import java.text.DecimalFormat;

public class Packet {

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMin() {
        return min;
    }

    public void setMin(long min) {
        this.min = min;
    }

    public long getMax() {
        return max;
    }

    public void setMax(long max) {
        this.max = max;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    long id;
    long min;
    long max;
    String type;
    String data;

    public Packet(long id, long min, long max, String type, String data)
    {
        this.id = id;
        this.min = min;
        this.max = max;
        this.type = type;
        this.data = data;
    }

    public String toString()
    {
        String message="";
        //format header
        DecimalFormat df = new DecimalFormat("0000");
        message = message.concat(df.format(id) + "-");
        message = message.concat(df.format(min) + "-");
        message = message.concat(df.format(max) + type);
        message = message.concat(data);
        return message;
    }
}