package si.fri.rso.samples.imagecatalog.models.entities;


import javax.persistence.*;

@Entity
@Table(name = "uporabnik_metadata")
@NamedQueries(value =
        {
                @NamedQuery(name = "UporabnikMetadataEntity.getAll",
                        query = "SELECT im FROM UporabnikMetadataEntity im")
        })
public class UporabnikMetadataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ime")
    private String ime;

    @Column(name = "priimek")
    private String priimek;

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

    @Column(name = "email")
    private String email;

}
