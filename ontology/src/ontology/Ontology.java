/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ontology;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.EnumeratedClass;
import org.apache.jena.ontology.FunctionalProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.InverseFunctionalProperty;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.SymmetricProperty;
import org.apache.jena.ontology.TransitiveProperty;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.XSD;

/**
 *
 * @author hatice
 */
public class Ontology {

    /**
     * @param args the command line arguments
     */
    public static OntModel ontModel = ModelFactory.createOntologyModel();
    
    public static List<String> classNames, objectPropertyNames, indObjProp, individualNames, dataTypePropertyNames;
    
    public static List<OntClass> ontClasses = new ArrayList<OntClass>();
    public static List<ObjectProperty> objectProperties = new ArrayList<ObjectProperty>();
    public static List<DatatypeProperty> dataProperties = new ArrayList<DatatypeProperty>();
    public static List<Individual> individuals = new ArrayList<Individual>();
    public static List<Individual> newIndividuals = new ArrayList<Individual>();
    
    
    public static void createClass(List<String> classNames){ 
        classNames.forEach(
                (c)->{
                    ontClasses.add(ontModel.createClass(Config.NS+c));
                }
        );
    }
    
    public static void createObjectProperties(List<String> objectPropertyNames){
        objectPropertyNames.forEach(
                (o)->{
                    objectProperties.add(ontModel.createObjectProperty(Config.NS+o));
                }
        );
    }
    
    public static void createDataTypeProperty(List<String> dataTypePropertyNames){
        dataTypePropertyNames.forEach(
                (d)->{
                    dataProperties.add(ontModel.createDatatypeProperty(Config.NS+d));
                }
        );
    }
    
    public static void createIndividual(List<String> individualNames){
        individualNames.forEach(
                (i)->{
                    String[] parts = i.split("-");
                    
                    individuals.add(ontModel.createIndividual(Config.NS+parts[0], ontClasses.get(Integer.parseInt(parts[1]))));
                }
        );
    }
    
    public static void addObjIndv(List<String> indObjProps){
        indObjProps.forEach(
                (io)->{
                    String[] parts = io.split("-");
                    individuals.get(Integer.parseInt(parts[0])).setPropertyValue(objectProperties.get(Integer.parseInt(parts[1])), individuals.get(Integer.parseInt(parts[2])));
                }
        );
    }
    
    public static Individual addIndividual(String ID, String typeName, String propName, Individual indv){
        Individual individual = ontModel.createIndividual(Config.NS+ID, ontClasses.get(classNames.indexOf(typeName)));
        
        individual.setPropertyValue(objectProperties.get(objectPropertyNames.indexOf(propName)), indv);
        
        return individual;
    }
    
    public static List<Individual> removeIndividuals(String TypeName){
        OntClass deleted = ontClasses.get(classNames.indexOf(TypeName));
        individuals.forEach(
            (i)->{
                if(i.hasOntClass(deleted)){
                    i.remove();
                }
                else{
                    newIndividuals.add(i);
                }
            }
        );
        return newIndividuals;
    }
    
    public static void removeAxiom(Individual ind, ObjectProperty prop){
        System.out.println(individuals.indexOf(ind));
        System.out.println(objectProperties.indexOf(prop));
        indObjProp.forEach(
               (o)->{
                   String[] parts = o.split("-");
                   if(Integer.parseInt(parts[0]) == individuals.indexOf(ind) && Integer.parseInt(parts[1]) == objectProperties.indexOf(prop)){
                       individuals.get(Integer.parseInt(parts[2])).remove();
                   }
               }
       );
    }
    
    public static void main(String[] args) throws FileNotFoundException {
        
        
        classNames = Arrays.asList("Adresler", "CalisanTelefonu", "CalismaZamani", "Indirimler", "Kisiler", "Magaza", "Pozisyonlar", "Satis", "Urun", "UrunKategorileri", "CalisanAdresi", "MagazaAdresi",
        "CepTelefonu", "EvTelefonu", "IsTelefonu", "TamZamanli", "YariZamanli", "MusteriyeOzel", "OzelGunler", "OgretmenlerGunu", "SevgililerGunu", "Yilbasi", "Calisan","Musteri", "Engelli", "Gazi", "Normal",
        "Kasiyer", "Mudur", "Satici", "Temizlikci", "AksesuarKategorisi", "AltGiyimKategorisi", "AyakkibiKategorisi", "IcGiyimKategorisi", "UstGiyimKategorisi", "Kadın", "Erkek");
        
        objectPropertyNames = Arrays.asList("alir", "calisanTipi", "calisir", "icerir", "icindir", "indirimGunleri", "ozeldir", "pozisyonu", "sahiptir", "satilir", "telefonu", "yapar", "adresi", "aittir", "rakibi", "vardir", "cinsiyeti");
        
        dataTypePropertyNames = Arrays.asList("acikAdresi", "adi", "barkodu", "calisanSayisi", "fiyati", "ilcesi", "ili", "indirimMiktari", "kimligi", "magazaKodu", "numarasi", "soyadi", "stok", 
                "telefonNumarasi", "toplamUcreti", "urunTuru");
        
        individualNames = Arrays.asList("magaza1-5", "magaza2-5", "calisan1-22", "calisan2-22", "calisan3-22", "calisan4-22", "calisan5-22", "magazaAdresi1-11", "calisanAdresi1-10", "calisanAdresi2-10", "calisanAdresi3-10", "calisanAdresi4-10",
                "calisanAdresi5-10", "calismaZamani1-2", "calismaZamani2-2", "cepTelefonu1-12", "cepTelefonu2-12", "evTelefonu1-13", "isTelefonu1-14", "isTelefonu2-14", "indirim1-17", "indirim2-19", "indirim3-20", "indirim4-21", "musteri1-24", "musteri2-25",
                "musteri3-26", "musteri4-26", "pozisyon1-28", "pozisyon2-27", "pozisyon3-29", "pozisyon4-30", "satis1-7", "satis2-7", "satis3-7", "satis4-7", "urun1-8", "urun2-8", "urun3-8", "urun4-8", "urun5-8", "urun6-8", "urun7-8", "urun8-8", "urunKategorisi1-35",
                "urunKategorisi2-32", "urunKategorisi3-33", "urunKategorisi4-34", "urunKategorisi5-31", "cinsiyet1-36", "cinsiyet2-37");
        
        indObjProp = Arrays.asList("0-8-2", "0-8-3", "0-15-44", "0-12-32", "0-12-33", "0-12-7", "0-14-1", "0-5-20", "0-5-21", "2-10-18", "2-7-28", "2-12-8", "2-1-13", "2-2-0", "2-16-49", "3-7-29", "3-10-12", "3-2-0", "3-12-9", "3-1-13", "3-16-50", "4-12-10", "4-7-30", "4-10-16", "4-2-1", "4-1-13", "4-16-50", "5-12-11",
                "5-10-19", "5-7-31", "5-2-1", "5-1-13", "5-16-49", "6-1-13", "6-3-1", "6-7-28","6-12-12", "6-10-17", "6-16-49", "20-6-24", "20-6-25", "21-4-24", "21-4-25", "21-4-26", "21-4-27", "22-4-24", "22-4-25", "22-4-26", "22-4-27", "23-4-24", "23-4-25", "23-4-26", "23-4-27", "24-0-32", "25-0-34", "26-0-33", "27-0-35",
                "32-3-36", "32-3-39", "32-3-24", "32-9-24", "33-3-25", "33-3-39", "33-9-25", "24-3-40", "24-3-41", "24-3-42", "24-3-26", "24-9-26", "24-16-50", "25-3-41", "25-3-27", "25-9-27", "25-16-50", "26-16-49", "27-16-49", "36-13-44","37-13-44", "38-13-45", "39-13-45", "40-13-46", "41-13-46", "42-13-47", "42-13-48", "44-15-36", "44-15-37",
                "45-15-38", "45-15-39", "46-15-40", "46-15-41", "47-15-42", "48-15-43");
        
        createClass(classNames);
        
        EnumeratedClass Cinsiyet = ontModel.createEnumeratedClass(Config.NS+"Cinsiyet", null);
        Cinsiyet.addOneOf(ontClasses.get(36));
        Cinsiyet.addOneOf(ontClasses.get(37));
        //classNames.add(38, "Cinsiyet");
        ontClasses.add(Cinsiyet);
        
/***********************************SUBCLASSES*****************************************************/        
        ontClasses.get(0).addSubClass(ontClasses.get(10));
        ontClasses.get(0).addSubClass(ontClasses.get(11));
        ontClasses.get(1).addSubClass(ontClasses.get(12));
        ontClasses.get(1).addSubClass(ontClasses.get(13));
        ontClasses.get(1).addSubClass(ontClasses.get(14));
        ontClasses.get(2).addSubClass(ontClasses.get(15));
        ontClasses.get(2).addSubClass(ontClasses.get(16));
        ontClasses.get(3).addSubClass(ontClasses.get(17));
        ontClasses.get(3).addSubClass(ontClasses.get(18));
        ontClasses.get(18).addSubClass(ontClasses.get(19));
        ontClasses.get(18).addSubClass(ontClasses.get(20));
        ontClasses.get(18).addSubClass(ontClasses.get(21));
        ontClasses.get(4).addSubClass(ontClasses.get(22));
        ontClasses.get(4).addSubClass(ontClasses.get(23));
        ontClasses.get(23).addSubClass(ontClasses.get(24));
        ontClasses.get(23).addSubClass(ontClasses.get(25));
        ontClasses.get(23).addSubClass(ontClasses.get(26));
        ontClasses.get(6).addSubClass(ontClasses.get(27));
        ontClasses.get(6).addSubClass(ontClasses.get(28));
        ontClasses.get(6).addSubClass(ontClasses.get(29));
        ontClasses.get(6).addSubClass(ontClasses.get(30));
        ontClasses.get(9).addSubClass(ontClasses.get(31));
        ontClasses.get(9).addSubClass(ontClasses.get(32));
        ontClasses.get(9).addSubClass(ontClasses.get(33));
        ontClasses.get(9).addSubClass(ontClasses.get(34));
        ontClasses.get(9).addSubClass(ontClasses.get(35));
        ontClasses.get(38).addSubClass(ontClasses.get(36));
        ontClasses.get(38).addSubClass(ontClasses.get(37));
/***********************************SUBCLASSES*****************************************************/        

/***********************************DisjointWith of Classes****************************************/        
        ontClasses.get(10).addDisjointWith(ontClasses.get(11));
        ontClasses.get(12).addDisjointWith(ontClasses.get(13));
        ontClasses.get(12).addDisjointWith(ontClasses.get(14));
        ontClasses.get(17).addDisjointWith(ontClasses.get(18));
        ontClasses.get(19).addDisjointWith(ontClasses.get(20));
        ontClasses.get(19).addDisjointWith(ontClasses.get(21));
        ontClasses.get(22).addDisjointWith(ontClasses.get(23));
        ontClasses.get(24).addDisjointWith(ontClasses.get(25));
        ontClasses.get(24).addDisjointWith(ontClasses.get(26));
        ontClasses.get(27).addDisjointWith(ontClasses.get(28));
        ontClasses.get(27).addDisjointWith(ontClasses.get(29));
        ontClasses.get(27).addDisjointWith(ontClasses.get(30));
        ontClasses.get(31).addDisjointWith(ontClasses.get(32));
        ontClasses.get(31).addDisjointWith(ontClasses.get(33));
        ontClasses.get(31).addDisjointWith(ontClasses.get(34));
        ontClasses.get(31).addDisjointWith(ontClasses.get(35));
        ontClasses.get(37).addDisjointWith(ontClasses.get(38));
/***********************************DisjointWith of Classes****************************************/        

/***********************************Object Properties****************************************/
        createObjectProperties(objectPropertyNames);
        
        InverseFunctionalProperty adresi = objectProperties.get(12).convertToInverseFunctionalProperty();  
        //InverseFunctionalProperty adresi = ontModel.createInverseFunctionalProperty(Config.NS+"adresi");
        adresi.addDomain(ontClasses.get(5));
        adresi.addDomain(ontClasses.get(22));
        adresi.addRange(ontClasses.get(10));
        adresi.addRange(ontClasses.get(11));
        
        FunctionalProperty aittir = objectProperties.get(13).convertToFunctionalProperty();
        //ObjectProperty aittir = ontModel.createObjectProperty(Config.NS+"aittir", true);
        aittir.addDomain(ontClasses.get(8));
        aittir.addRange(ontClasses.get(9));  
        
        objectProperties.get(0).addInverseOf(objectProperties.get(9));
        objectProperties.get(0).addDomain(ontClasses.get(23));
        objectProperties.get(0).addRange(ontClasses.get(7));
        
        objectProperties.get(1).addDomain(ontClasses.get(22));
        objectProperties.get(1).addRange(ontClasses.get(2));
        
        objectProperties.get(2).addInverseOf(objectProperties.get(8));
        objectProperties.get(2).addDomain(ontModel.createAllValuesFromRestriction(null, objectProperties.get(2), ontClasses.get(22)));
        objectProperties.get(2).addRange(ontClasses.get(5));

        objectProperties.get(3).addDomain(ontClasses.get(7));
        objectProperties.get(3).addRange(ontClasses.get(23));
        objectProperties.get(3).addRange(ontModel.createSomeValuesFromRestriction(null, objectProperties.get(3), ontClasses.get(8)));
        
        objectProperties.get(4).addDomain(ontClasses.get(18));
        objectProperties.get(4).addRange(ontClasses.get(23));
        
        objectProperties.get(5).addDomain(ontClasses.get(5));
        objectProperties.get(5).addRange(ontClasses.get(3));
        
        objectProperties.get(6).addDomain(ontClasses.get(17));
        objectProperties.get(6).addRange(ontModel.createAllValuesFromRestriction(null, objectProperties.get(6), ontClasses.get(24)));
        objectProperties.get(6).addRange(ontModel.createAllValuesFromRestriction(null, objectProperties.get(6), ontClasses.get(25)));

        objectProperties.get(7).addDomain(ontModel.createAllValuesFromRestriction(null, objectProperties.get(7), ontClasses.get(22)));
        objectProperties.get(7).addRange(ontModel.createAllValuesFromRestriction(null, objectProperties.get(7), ontClasses.get(6)));
        
        SymmetricProperty rakibi = objectProperties.get(14).convertToSymmetricProperty();
        //SymmetricProperty rakibi = ontModel.createSymmetricProperty(Config.NS+"rakibi");
        rakibi.addDomain(ontClasses.get(5));
        rakibi.addRange(ontClasses.get(5));
        
        objectProperties.get(8).addInverseOf(objectProperties.get(2));
        objectProperties.get(8).addDomain(ontClasses.get(5));
        objectProperties.get(8).addRange(ontClasses.get(22));
        
        objectProperties.get(9).addInverseOf(objectProperties.get(0));
        objectProperties.get(9).addDomain(ontClasses.get(7));
        objectProperties.get(9).addRange(ontClasses.get(23));
        
        objectProperties.get(10).addDomain(ontModel.createAllValuesFromRestriction(null, objectProperties.get(10), ontClasses.get(22)));
        objectProperties.get(10).addRange(ontModel.createAllValuesFromRestriction(null, objectProperties.get(10), ontClasses.get(12)));
        
        TransitiveProperty vardir = objectProperties.get(15).convertToTransitiveProperty();
        //TransitiveProperty vardir = ontModel.createTransitiveProperty(Config.NS+"vardir");
        vardir.addDomain(ontClasses.get(5));
        vardir.addRange(ontClasses.get(9));
        vardir.addDomain(ontClasses.get(9));
        vardir.addRange(ontClasses.get(8));
        
        objectProperties.get(11).addDomain(ontClasses.get(5));
        objectProperties.get(11).addRange(ontModel.createAllValuesFromRestriction(null, objectProperties.get(11), ontClasses.get(7)));
        
        objectProperties.get(16).addDomain(ontClasses.get(4));
        objectProperties.get(16).addRange(Cinsiyet);
        
/***********************************Object Properties****************************************/

/***********************************Datatype Properties****************************************/
        
        createDataTypeProperty(dataTypePropertyNames);
        
        dataProperties.get(0).addDomain(ontClasses.get(10));
        dataProperties.get(0).addDomain(ontClasses.get(11));
        dataProperties.get(0).addRange(XSD.xstring);
        
        dataProperties.get(1).addDomain(ontClasses.get(8));
        dataProperties.get(1).addDomain(ontClasses.get(22));
        dataProperties.get(1).addDomain(ontClasses.get(23));
        dataProperties.get(1).addDomain(ontClasses.get(5));
        dataProperties.get(1).addRange(XSD.xstring);
        
        dataProperties.get(2).addDomain(ontClasses.get(8));
        dataProperties.get(2).addRange(XSD.xstring);
        
        dataProperties.get(3).addDomain(ontClasses.get(5));
        dataProperties.get(3).addRange(XSD.nonNegativeInteger);
        
        dataProperties.get(4).addDomain(ontClasses.get(8));
        dataProperties.get(4).addRange(XSD.xdouble);
        
        dataProperties.get(5).addDomain(ontClasses.get(10));
        dataProperties.get(5).addDomain(ontClasses.get(11));
        dataProperties.get(5).addRange(XSD.xstring);
        
        dataProperties.get(6).addDomain(ontClasses.get(10));
        dataProperties.get(6).addDomain(ontClasses.get(11));
        dataProperties.get(6).addRange(XSD.xstring);
        
        dataProperties.get(7).addDomain(ontClasses.get(3));
        dataProperties.get(7).addRange(XSD.xstring);
        
        dataProperties.get(8).addDomain(ontClasses.get(23));
        dataProperties.get(8).addRange(XSD.xstring);
        
        dataProperties.get(9).addDomain(ontClasses.get(5));
        dataProperties.get(9).addRange(XSD.xstring);
        
        dataProperties.get(10).addDomain(ontModel.createAllValuesFromRestriction(null, dataProperties.get(11), ontClasses.get(12)));
        dataProperties.get(10).addDomain(ontModel.createAllValuesFromRestriction(null, dataProperties.get(11), ontClasses.get(13)));
        dataProperties.get(10).addDomain(ontModel.createAllValuesFromRestriction(null, dataProperties.get(11), ontClasses.get(14)));
        dataProperties.get(10).addRange(XSD.xstring);
        
        dataProperties.get(11).addDomain(ontClasses.get(22));
        dataProperties.get(11).addRange(XSD.xstring);
        
        dataProperties.get(12).addDomain(ontClasses.get(8));
        dataProperties.get(12).addRange(XSD.nonNegativeInteger);
        
        dataProperties.get(13).addDomain(ontClasses.get(23));
        dataProperties.get(13).addRange(XSD.xstring);
        
        dataProperties.get(14).addDomain(ontClasses.get(7));
        dataProperties.get(14).addRange(XSD.xdouble);
        
        dataProperties.get(15).addDomain(ontClasses.get(8));
        dataProperties.get(15).addRange(XSD.xstring);
                
/***********************************Datatype Properties****************************************/

        createIndividual(individualNames);
        addObjIndv(indObjProp);

/***********************************Datatype Properties Individuals****************************************/
    
        individuals.get(0).setPropertyValue(dataProperties.get(9), ontModel.createLiteral("001"));
        individuals.get(0).setPropertyValue(dataProperties.get(1), ontModel.createLiteral("HM Waikiki"));
        individuals.get(0).setPropertyValue(dataProperties.get(3), ontModel.createLiteral("2"));
        
        individuals.get(2).setPropertyValue(dataProperties.get(1), ontModel.createLiteral("Derya"));
        individuals.get(2).setPropertyValue(dataProperties.get(11), ontModel.createLiteral("Deniz"));
        individuals.get(3).setPropertyValue(dataProperties.get(1), ontModel.createLiteral("Buğra"));
        individuals.get(3).setPropertyValue(dataProperties.get(11), ontModel.createLiteral("Taba"));
        individuals.get(4).setPropertyValue(dataProperties.get(1), ontModel.createLiteral("Mert"));
        individuals.get(4).setPropertyValue(dataProperties.get(11), ontModel.createLiteral("Akasya"));
        individuals.get(5).setPropertyValue(dataProperties.get(1), ontModel.createLiteral("Gülten"));
        individuals.get(5).setPropertyValue(dataProperties.get(11), ontModel.createLiteral("Biricik"));
        individuals.get(6).setPropertyValue(dataProperties.get(1), ontModel.createLiteral("Leyla"));
        individuals.get(6).setPropertyValue(dataProperties.get(11), ontModel.createLiteral("Altınel"));
        
        individuals.get(7).setPropertyValue(dataProperties.get(6), ontModel.createLiteral("Bursa"));
        individuals.get(7).setPropertyValue(dataProperties.get(5), ontModel.createLiteral("Orhangazi"));
        individuals.get(7).setPropertyValue(dataProperties.get(0), ontModel.createLiteral("Cumhuriyet Caddesi İpek Sokak No:3"));
        
        individuals.get(8).setPropertyValue(dataProperties.get(6), ontModel.createLiteral("Bursa"));
        individuals.get(8).setPropertyValue(dataProperties.get(5), ontModel.createLiteral("Osmangazi"));
        individuals.get(8).setPropertyValue(dataProperties.get(0), ontModel.createLiteral("Asya Cad. Milli Sk. No:3"));
        
        individuals.get(9).setPropertyValue(dataProperties.get(6), ontModel.createLiteral("Bursa"));
        individuals.get(9).setPropertyValue(dataProperties.get(5), ontModel.createLiteral("Merkez"));
        individuals.get(9).setPropertyValue(dataProperties.get(0), ontModel.createLiteral("Avrupa Cad. Derya Sk. No:1 Yıldırım"));
        
        individuals.get(10).setPropertyValue(dataProperties.get(6), ontModel.createLiteral("Ankara"));
        individuals.get(10).setPropertyValue(dataProperties.get(5), ontModel.createLiteral("Merkez"));
        individuals.get(10).setPropertyValue(dataProperties.get(0), ontModel.createLiteral("Asya Cad. Milli Sk. No:3"));
        
        individuals.get(11).setPropertyValue(dataProperties.get(6), ontModel.createLiteral("Ankara"));
        individuals.get(11).setPropertyValue(dataProperties.get(5), ontModel.createLiteral("Akyurt"));
        individuals.get(11).setPropertyValue(dataProperties.get(0), ontModel.createLiteral("Avrupa Cad. Duran Sk. No:2"));
        
        individuals.get(12).setPropertyValue(dataProperties.get(6), ontModel.createLiteral("Ankara"));
        individuals.get(12).setPropertyValue(dataProperties.get(5), ontModel.createLiteral("Ayaş"));
        individuals.get(12).setPropertyValue(dataProperties.get(0), ontModel.createLiteral("Berivan Cad. Saz Sk. No:2"));
        
        individuals.get(15).setPropertyValue(dataProperties.get(10), ontModel.createLiteral("(542)-222-22-22"));
        individuals.get(16).setPropertyValue(dataProperties.get(10), ontModel.createLiteral("(531)-222-22-22"));
        individuals.get(17).setPropertyValue(dataProperties.get(10), ontModel.createLiteral("(312)-444-44-44"));
        individuals.get(18).setPropertyValue(dataProperties.get(10), ontModel.createLiteral("(531)-111-11-11"));
        individuals.get(19).setPropertyValue(dataProperties.get(10), ontModel.createLiteral("(543)-333-33-33"));
        
        individuals.get(20).setPropertyValue(dataProperties.get(7), ontModel.createLiteral("%50"));
        individuals.get(21).setPropertyValue(dataProperties.get(7), ontModel.createLiteral("%20"));
        individuals.get(22).setPropertyValue(dataProperties.get(7), ontModel.createLiteral("%10"));
        individuals.get(23).setPropertyValue(dataProperties.get(7), ontModel.createLiteral("%5"));
        
        individuals.get(24).setPropertyValue(dataProperties.get(1), ontModel.createLiteral("Arda Sazan"));
        individuals.get(24).setPropertyValue(dataProperties.get(8), ontModel.createLiteral("24135763212"));
        individuals.get(24).setPropertyValue(dataProperties.get(13), ontModel.createLiteral("(532)-145-21-45"));
        
        individuals.get(25).setPropertyValue(dataProperties.get(1), ontModel.createLiteral("Kenan Deliyürek"));
        individuals.get(25).setPropertyValue(dataProperties.get(8), ontModel.createLiteral("55555555555"));
        individuals.get(25).setPropertyValue(dataProperties.get(13), ontModel.createLiteral("(538)-115-20-42"));
        
        individuals.get(26).setPropertyValue(dataProperties.get(1), ontModel.createLiteral("Aslı Şahin"));
        individuals.get(26).setPropertyValue(dataProperties.get(8), ontModel.createLiteral("55544667721"));
        individuals.get(26).setPropertyValue(dataProperties.get(13), ontModel.createLiteral("(505)-471-23-65"));
        
        individuals.get(27).setPropertyValue(dataProperties.get(1), ontModel.createLiteral("Didem Tok"));
        individuals.get(27).setPropertyValue(dataProperties.get(8), ontModel.createLiteral("20113453234"));
        individuals.get(27).setPropertyValue(dataProperties.get(13), ontModel.createLiteral("(542)-888-88-88"));
        
        individuals.get(32).setPropertyValue(dataProperties.get(14), ontModel.createTypedLiteral(60.90));
        individuals.get(33).setPropertyValue(dataProperties.get(14), ontModel.createTypedLiteral(25.00));
        individuals.get(34).setPropertyValue(dataProperties.get(14), ontModel.createTypedLiteral(400.99));
        individuals.get(35).setPropertyValue(dataProperties.get(14), ontModel.createTypedLiteral(40.00));
        
        individuals.get(36).setPropertyValue(dataProperties.get(1), ontModel.createLiteral("Kazak"));
        individuals.get(36).setPropertyValue(dataProperties.get(2), ontModel.createLiteral("1234567891021"));
        individuals.get(36).setPropertyValue(dataProperties.get(12), ontModel.createTypedLiteral(45));
        individuals.get(36).setPropertyValue(dataProperties.get(4), ontModel.createTypedLiteral(35.90));
        individuals.get(36).setPropertyValue(dataProperties.get(15), ontModel.createLiteral("Erkek"));
        
        individuals.get(37).setPropertyValue(dataProperties.get(1), ontModel.createLiteral("Gömlek"));
        individuals.get(37).setPropertyValue(dataProperties.get(2), ontModel.createLiteral("9128651237564"));
        individuals.get(37).setPropertyValue(dataProperties.get(12), ontModel.createTypedLiteral(100));
        individuals.get(37).setPropertyValue(dataProperties.get(4), ontModel.createTypedLiteral(81.99));
        individuals.get(37).setPropertyValue(dataProperties.get(15), ontModel.createLiteral("Kadın"));
        
        individuals.get(38).setPropertyValue(dataProperties.get(1), ontModel.createLiteral("Pantalon"));
        individuals.get(38).setPropertyValue(dataProperties.get(2), ontModel.createLiteral("5544221133667"));
        individuals.get(38).setPropertyValue(dataProperties.get(12), ontModel.createTypedLiteral(20));
        individuals.get(38).setPropertyValue(dataProperties.get(4), ontModel.createTypedLiteral(81.99));
        individuals.get(38).setPropertyValue(dataProperties.get(15), ontModel.createLiteral("Kadın"));
        
        individuals.get(39).setPropertyValue(dataProperties.get(1), ontModel.createLiteral("Şort"));
        individuals.get(39).setPropertyValue(dataProperties.get(2), ontModel.createLiteral("9999900000888"));
        individuals.get(39).setPropertyValue(dataProperties.get(12), ontModel.createTypedLiteral(50));
        individuals.get(39).setPropertyValue(dataProperties.get(4), ontModel.createTypedLiteral(25.00));
        individuals.get(39).setPropertyValue(dataProperties.get(15), ontModel.createLiteral("Çocuk"));
        
        individuals.get(40).setPropertyValue(dataProperties.get(1), ontModel.createLiteral("Bot"));
        individuals.get(40).setPropertyValue(dataProperties.get(2), ontModel.createLiteral("4356789872145"));
        individuals.get(40).setPropertyValue(dataProperties.get(12), ontModel.createTypedLiteral(45));
        individuals.get(40).setPropertyValue(dataProperties.get(4), ontModel.createTypedLiteral(350.99));
        individuals.get(40).setPropertyValue(dataProperties.get(15), ontModel.createLiteral("Erkek"));
        
        individuals.get(41).setPropertyValue(dataProperties.get(1), ontModel.createLiteral("Babet"));
        individuals.get(41).setPropertyValue(dataProperties.get(2), ontModel.createLiteral("1009908070600"));
        individuals.get(41).setPropertyValue(dataProperties.get(12), ontModel.createTypedLiteral(100));
        individuals.get(41).setPropertyValue(dataProperties.get(4), ontModel.createTypedLiteral(40.00));
        individuals.get(41).setPropertyValue(dataProperties.get(15), ontModel.createLiteral("Kadın"));
        
        individuals.get(42).setPropertyValue(dataProperties.get(1), ontModel.createLiteral("Atlet"));
        individuals.get(42).setPropertyValue(dataProperties.get(2), ontModel.createLiteral("1212121212121"));
        individuals.get(42).setPropertyValue(dataProperties.get(12), ontModel.createTypedLiteral(200));
        individuals.get(42).setPropertyValue(dataProperties.get(4), ontModel.createTypedLiteral(10.00));
        individuals.get(42).setPropertyValue(dataProperties.get(15), ontModel.createLiteral("Kadın"));
        individuals.get(42).setPropertyValue(dataProperties.get(15), ontModel.createLiteral("Çocuk"));
        individuals.get(42).setPropertyValue(dataProperties.get(15), ontModel.createLiteral("Erkek"));
        
        individuals.get(43).setPropertyValue(dataProperties.get(1), ontModel.createLiteral("Kolye"));
        individuals.get(43).setPropertyValue(dataProperties.get(2), ontModel.createLiteral("1313131313131"));
        individuals.get(43).setPropertyValue(dataProperties.get(12), ontModel.createTypedLiteral(30));
        individuals.get(43).setPropertyValue(dataProperties.get(4), ontModel.createTypedLiteral(25.00));
        individuals.get(43).setPropertyValue(dataProperties.get(15), ontModel.createLiteral("Kadın"));
        
        
/***********************************Datatype Properties Individuals****************************************/
        

/***********************************Functions****************************************/

        //Individual magazaAdresi = ontModel.createIndividual(Config.NS+"magazaAdresi", ontClasses.get(11));
        //addIndividual("magaza", "Magaza", "adresi", magazaAdresi);
        //removeIndividuals("Musteri");
        //removeAxiom(individuals.get(0), objectProperties.get(8));
/***********************************Functions****************************************/
        
        File file = new File("ont.owl");
        //ontModel.write(new FileOutputStream(file));
        ontModel.write(System.out,"RDF/XML-ABBREV");  
    }
    
}
