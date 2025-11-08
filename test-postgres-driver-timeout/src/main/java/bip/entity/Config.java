
package bip.entity;



/**
 *  sabaDB.Config
 *  09/14/2025 20:11:20
 * 
 */
public class Config {

    private String key;
    private String type;
    private String name;
    private String json_value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJson_value() {
        return json_value;
    }

    public void setJson_value(String json_value) {
        this.json_value = json_value;
    }

    @Override
    public String toString() {
        return "Config{" +
                "key='" + key + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", json_value='" + json_value + '\'' +
                '}';
    }
}
