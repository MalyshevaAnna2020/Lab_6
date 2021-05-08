import java.io.Serializable;
import java.util.Date;

public class SpaceMarine implements Serializable {
    private static final long serialVersionUID = 7460446497926355485L;
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long health = null; //Поле может быть null, Значение поля должно быть больше 0
    private int heartCount; //Значение поля должно быть больше 0, Максимальное значение поля: 3
    private String achievements = null; //Поле может быть null
    private AstartesCategory category = null; //Поле может быть null
    private Chapter chapter = null; //Поле может быть null

    public void setId(int id){this.id = id;}
    public void setName(String name){this.name = name;}
    public void setCoordinates(Coordinates coordinates){ this.coordinates = coordinates; }
    public void setCreationDate(Date creationDate){
        this.creationDate = creationDate;
    }
    public void setHealth(String health){
        try {
            this.health = Long.parseLong(health.trim());
            if (this.health <= 0) this.health = 1L;
        }catch (NumberFormatException e){
            this.health = 1L;
        }
    }
    public void setHeartCount(String heartCount){
        try{
            this.heartCount = Integer.parseInt(heartCount.trim());
            if (this.heartCount > 3) this.heartCount = 3;
            if (this.heartCount < 1) this.heartCount = 1;
        }catch (NumberFormatException e){
            this.heartCount = 1;
        }
    }
    public void setAchievements(String achievements){this.achievements = achievements;}
    public void setCategory(String category){
        if (category.equals("SUPPRESSOR")){
            this.category = AstartesCategory.SUPPRESSOR;
        }
        if (category.equals("TERMINATOR")){
            this.category = AstartesCategory.TERMINATOR;
        }
        if (category.equals("LIBRARIAN")){
            this.category = AstartesCategory.LIBRARIAN;
        }
        if (category.equals("APOTHECARY")){
            this.category = AstartesCategory.APOTHECARY;
        }
    }
    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public Coordinates getCoordinates(){
        return coordinates;
    }
    public Date getCreationDate(){
        return creationDate;
    }
    public Long getHealth(){
        return health;
    }
    public int getHeartCount(){
        return heartCount;
    }
    public String getAchievements(){
        return achievements;
    }
    public AstartesCategory getCategory(){
        return category;
    }
    public Chapter getChapter(){
        return chapter;
    }
    @Override
    public String toString(){
        return this.getName() + " " + this.getId();
    }

    public void setSpaceMarine(String s, int id) {

        GetValue getValue = (st, subst) -> st.substring(st.indexOf(subst))
                .substring(st.substring(st.indexOf(subst)).indexOf("\"") + 1, st.substring(st.indexOf(subst))
                .substring(st.substring(st.indexOf(subst)).indexOf("\"") + 1)
                        .indexOf("\"") + st.substring(st.indexOf(subst)).indexOf("\"") + 1);

        this.setId(id);

        if (s.contains("name")) {
            String name = getValue.getValue(s, "name");
            this.setName(name);
        } else {
            this.setName("id_" + this.getId());
        }

        Coordinates coordinates = new Coordinates();
        if (s.contains("coordinates")) {
            String strcoordinates = getValue.getValue(s, "coordinates");
            coordinates.setX(strcoordinates.substring(0, strcoordinates.indexOf(" ")));
            coordinates.setY(strcoordinates.substring(strcoordinates.indexOf(" ") + 1));
        } else {
            coordinates.setX("0");
            coordinates.setY("0");
        }
        this.setCoordinates(coordinates);

        this.setCreationDate(new Date());

        if (s.contains("health")) {
            this.setHealth(getValue.getValue(s,"health"));
        } else {
            this.setHealth("0");
        }

        if (s.contains("heartCount")) {
            this.setHeartCount(getValue.getValue(s,"heartCount"));
        } else {
            this.setHeartCount("0");
        }

        if (s.contains("achievements")) {
            this.setAchievements(getValue.getValue(s,"achievements"));
        }

        if (s.contains("category")) {
            this.setCategory(getValue.getValue(s, "category"));
        }

        if (s.contains("chapter")) {
            String chapter = getValue.getValue(s,"chapter");
            String name = chapter;
            String marinesCount = "0";
            if (chapter.contains(".")) {
                name = chapter.substring(0, chapter.indexOf("."));
                marinesCount = chapter.substring(chapter.indexOf(".") + 1);
            }
            Chapter chapter1 = new Chapter();
            chapter1.setName(name);
            chapter1.setMarinesCount(marinesCount);
            this.setChapter(chapter1);
        }
    }

}