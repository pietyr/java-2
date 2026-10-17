package org.example;

import org.example.model.Observation;
import org.example.model.ObservationList;
import org.example.service.XmlFileReader;
import org.example.service.XmlUrlReader;
import org.example.service.XmlValidator;
import java.util.List;
import java.util.Comparator;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class App {

    // Dane w głównym katalogu projektu:
    final static String[] schemaFiles = {"schema1.xsd", "schema2.xsd"};
    final static String dataObservationFile = "observation.xml";
    final static String dataObservationsFile = "observations.xml";

    // Dane dostępne przez API
    final static String url = "https://nowicki.house/java2/labs/observations.xml";

    public static void main(String[] args) {
        // Sprawdzanie zgodności ze schematem
        boolean result_1 = XmlValidator.validate(dataObservationFile, schemaFiles);
        boolean result_list = XmlValidator.validate(dataObservationsFile, schemaFiles);
        System.out.println("Validate: " + dataObservationFile + " -> " + result_1);
        System.out.println("Validate: " + dataObservationsFile + " -> " + result_list);

        System.out.println("---");

        // Przeczytanie pojedynczej wartości
        Observation observation = XmlFileReader.readObservation(dataObservationFile, Observation.class);
        System.out.println(observation);

        System.out.println("---");

        // Wczytanie listy wartości
        ObservationList observationList = XmlFileReader.readObservation(dataObservationsFile, ObservationList.class);

        // Przefiltrowanie tych od -1C do +1C
        List<Observation> observationListFiltered = observationList.getObservations().stream()
                .filter(o -> o.getComment() != null)
                .map(o -> {
                    double valueInCelsius = switch (o.getUnit()) {
                        case Celsius -> o.getValue();
                        case Fahrenheit -> (o.getValue() - 32) * 5.0 / 9.0;
                        case Kelvin -> o.getValue() - 273.15;
                    };
                    return new Observation(o.getTimeStamp(), Observation.TemperatureUnit.Celsius, valueInCelsius, o.getComment());
                })
                .filter(o -> o.getValue() > -1 && o.getValue() < 1)
                .sorted(Comparator.comparingDouble(Observation::getValue))
                .toList();

        observationListFiltered.forEach(System.out::println);
    }
}