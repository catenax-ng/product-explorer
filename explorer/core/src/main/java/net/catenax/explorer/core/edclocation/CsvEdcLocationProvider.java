package net.catenax.explorer.core.edclocation;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

@Slf4j
class CsvEdcLocationProvider implements EdcLocationProvider {

  private final String fileLocation;

  public CsvEdcLocationProvider(String fileLocation) {
    this.fileLocation = getLocationOfResourcesConfigFile(fileLocation);

    log.info("CsvEdcLocationProvider initialized with file: %s".formatted(this.fileLocation));
  }

  private String getLocationOfResourcesConfigFile(String fileLocation) {
    if (fileLocation.startsWith("file://")) {
      return fileLocation.substring("file://".length());
    }

    if (fileLocation.startsWith("resource://")) {
      return Optional.ofNullable(
              ClassLoader.getSystemResource(fileLocation.substring("resource://".length())))
          .map(URL::getPath)
          .orElseThrow(() -> new EdcLocationProviderException(
              "EDC locations (%s) file in resources not found".formatted(fileLocation)));
    }

    throw new EdcLocationProviderException(
        "EDC location csv file should be prefixed with \"file://\" or \"resource://\" protocol");
  }

  @Override
  public List<EdcLocation> getKnownEdcLocations() {
    try (Reader in = new FileReader(fileLocation)) {
      Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);

      List<EdcLocation> locations = new ArrayList<>();
      for (CSVRecord record : records) {
        locations.add(EdcLocation.builder().url(record.get(0)).build());
      }
      return locations;
    } catch (FileNotFoundException e) {
      throw new EdcLocationProviderException("EDC locations file not found", e);
    } catch (IOException e) {
      throw new EdcLocationProviderException("Cannot parse edc locations file, not csv format?", e);
    }
  }
}
