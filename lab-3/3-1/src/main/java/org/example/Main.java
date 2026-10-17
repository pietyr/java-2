package org.example;


import org.example.model.Observation;
import org.example.model.ObservationList;
import org.example.service.RandomObservation;
import org.example.service.XmlFileWriter;
import org.example.service.XsdFileGenerator;

public class Main {
    public static void main(String[] args) {
// Zapis struktury i reguł danych do plików xsd, nazwy same się wygenerują
        XsdFileGenerator.generate(Observation.class, ObservationList.class);

        // Pojedynczy pomiar do xml
        Observation observation = RandomObservation.generate();
        XmlFileWriter.write(observation, "observation.xml");

        // Zapis 1000 pomiarów do xml
        ObservationList observationList = new ObservationList();
        for (int i = 0; i < 1000; i++)
            observationList.addObservation(RandomObservation.generate());

        XmlFileWriter.write(observationList, "observations.xml");
    }
}