package ch.zhaw.ti.productRaw;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProductRaw {
    @Id
    @Getter @Setter
    private String url;

    @Getter @Setter
    private String hersteller;

    @Getter @Setter
    private String verlag;

    @Getter @Setter
    private String productTyp;

    @Getter @Setter
    private String verkaufsrang;

    @Getter @Setter
    private String farbgruppe;

    @Getter @Setter
    private String genaueFarbbezeichnung;

    @Getter @Setter
    private String materialUeberzelt;

    @Getter @Setter
    private String materialInnenzelt;

    @Getter @Setter
    private String materialBoden;

    @Getter @Setter
    private String materialGestaenge;

    @Getter @Setter
    private String zeltTyp;

    @Getter @Setter
    private String zeltAbmessung;

    @Getter @Setter
    private String packmass;

    @Getter @Setter
    private String maxAnzahlPersonen;

    @Getter @Setter
    private String anzahlRaeume;

    @Getter @Setter
    private String gewicht;

    @Getter @Setter
    private String laenge;

    @Getter @Setter
    private String breite;

    @Getter @Setter
    private String hoehe;

    @Getter @Setter
    private String stehHoehe;

    @Getter @Setter
    private String wasserSaeule;

    @Getter @Setter
    private String preis;

}
