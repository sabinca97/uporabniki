package si.fri.rso.samples.imagecatalog.lib;

public class UporabnikMetadata {
    private Integer id;
    private String ime;
    private String priimek;
    private String email;
    private Integer stObjav;

    public Integer getStObjav() { return stObjav; }

    public void setStObjav(Integer stObjav) { this.stObjav = stObjav; }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPriimek() {
        return priimek;
    }

    public void setPriimek(String priimek) {
        this.priimek = priimek;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
