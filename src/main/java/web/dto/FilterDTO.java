package web.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FilterDTO {

    private String uid;
    private String bundesland;
    private String ort;
    private String bezirk;
    private String preis_from;
    private String preis_to;
    private String area_from;
    private String area_to;
    private String preis_sq_2_from;
    private String preis_sq_2_to;
    private String kategorie;






    // Getters and Setters
    public String getBundesland() { return bundesland; }
    public void setBundesland(String bundesland) { this.bundesland = bundesland; }

    public String getOrt() { return ort; }
    public void setOrt(String ort)
    {
        this.ort = (bundesland.equals("Wien")) ? "Wien" :  ort;
    }

    public String getBezirk() { return bezirk; }
    public void setBezirk(String bezirk) {
        this.bezirk = bezirk;
    }

    public String getPreis_from() { return preis_from; }
    public void setPreis_from(String preis_from) { this.preis_from = preis_from; }

    public String getPreis_to() { return preis_to; }
    public void setPreis_to(String preis_to) { this.preis_to = preis_to; }

    public String getArea_from() { return area_from; }
    public void setArea_from(String area_from) { this.area_from = area_from; }

    public String getArea_to() { return area_to; }
    public void setArea_to(String area_to) { this.area_to = area_to; }

    public String getPreis_sq_2_from() { return preis_sq_2_from; }
    public void setPreis_sq_2_from(String preis_sq_2_from) { this.preis_sq_2_from = preis_sq_2_from; }

    public String getPreis_sq_2_to() { return preis_sq_2_to; }
    public void setPreis_sq_2_to(String preis_sq_2_to) { this.preis_sq_2_to = preis_sq_2_to; }

    public String getKategorie() { return kategorie; }
    public void setKategorie(String kategorie) { this.kategorie = kategorie; }

    public String getUid() {  return uid;   }

    public void setUid(String uid) {  this.uid = uid;    }
    String randomPart = UUID.randomUUID().toString().substring(0, 4);


    // toMap method to convert DTO to Map
    public Map<String, Object> toMap()
    {
        if (uid == null || uid.isEmpty())
        {
            uid = (this.bundesland != null && this.bundesland.equals("Wien")) ? "Wien"+ "-" + randomPart : ort + "-" + randomPart;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("bundesland", bundesland);
        map.put("ort", ort);
        map.put("bezirk", bezirk);
        map.put("preis_von", preis_from);
        map.put("preis_bis", preis_to);
        map.put("fläche_von", area_from);
        map.put("fläche_bis", area_to);
        map.put("preis_sq_2_von", preis_sq_2_from);
        map.put("preis_sq_2_bis", preis_sq_2_to);
        map.put("kategorie", kategorie);
        return map;
    }


}
