package ca.cmpt213.asn5_1.model;

public class Tokimon {
    public static long idCounter = 0;
    private Long id;
    private String name;
    private String type;
    private int rarity;
    private String pictureUrl;
    private int hp;

    public Tokimon() {}

    public Tokimon(String name, String type, int rarity, String pictureUrl, int hp) {
        this.id = ++idCounter;
        this.name = name;
        this.type = type;
        this.rarity = rarity;
        this.pictureUrl = pictureUrl;
        this.hp = hp;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRarity() {
        return rarity;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        if (hp < 0) {
            throw new IllegalArgumentException("HP cannot be negative");
        }
        this.hp = hp;
    }

    public static long getIdCounter(){
        return ++idCounter;
    }
}
