package com.automation.platform.dbtesting.service;

import com.automation.platform.dbtesting.model.CustomerCsv;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Profile("dbtest")
public class CsvProcessorService {

    public List<CustomerCsv> readCsvFile() {
        ClassPathResource resource = new ClassPathResource("customer-details.csv");
        List<CustomerCsv> customerDetails = new ArrayList<>();
        try (CSVReader csvReader = new CSVReaderBuilder(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))
                .withSkipLines(1).build()) {
            customerDetails = csvReader.readAll().stream()
                    .map(data -> CustomerCsv.builder()
                            .cid(getValue(data[0]))
                            .title(data[1])
                            .firstName(data[2])
                            .lastName(data[3])
                            .dob(data[4])
                            .gender(data[5])
                            .phoneNumber(getValue(data[6]))
                            .emailId(data[7])
                            .eFirstName(data[8])
                            .eLastName(data[9])
                            .eMobileNumber(getValue(data[10]))
                            .eRelationship(data[11])
                            .sFirstName(data[12])
                            .sLastName(data[13])
                            .sDob(data[14])
                            .sMobileNumber(getValue(data[15]))
                            .sEmailId(data[16])
                            .build())
                    .collect(Collectors.toList());
            return customerDetails;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int getValue(Object val) {
        return Integer.parseInt(String.valueOf(val));
    }
}
