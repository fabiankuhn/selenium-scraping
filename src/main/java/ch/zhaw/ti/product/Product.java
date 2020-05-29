package ch.zhaw.ti.product;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Product {
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
    private int verkaufsrang;

    // @Getter @Setter
    // String farbgruppe;

    // @Getter @Setter
    // String genaueFarbbezeichnung;

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
    private int zeltAbmessungLaenge;

    @Getter @Setter
    private int zeltAbmessungBreite;

    @Getter @Setter
    private double packmassBreite;

    @Getter @Setter
    private double packmassLaenge;

    @Getter @Setter
    private int maxAnzahlPersonen;

    @Getter @Setter
    private int anzahlRaeume;

    @Getter @Setter
    private double gewicht;

    @Getter @Setter
    private double laenge;

    @Getter @Setter
    private double breite;

    @Getter @Setter
    private double hoehe;

    @Getter @Setter
    private int stehHoehe;

    @Getter @Setter
    private double wasserSaeule;

    @Getter @Setter
    private double preis;
}
