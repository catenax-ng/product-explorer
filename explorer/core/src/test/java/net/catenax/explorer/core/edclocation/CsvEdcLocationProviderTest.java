package net.catenax.explorer.core.edclocation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;

class CsvEdcLocationProviderTest {

  @Test
  void shouldReadLocationsFromResourcesFile() {
    // given
    final EdcLocationProvider edcLocationProvider = new CsvEdcLocationProvider(
        "resource://edc-locations.csv");

    // when
    final List<EdcLocation> knownEdcLocations = edcLocationProvider.getKnownEdcLocations();

    // then
    assertNotNull(knownEdcLocations);
    assertEquals(2, knownEdcLocations.size());
    assertEquals("http://example.com", knownEdcLocations.get(0).getUrl());
  }

  @Test
  void shouldThrowExceptionThatFileNotFound() {
    assertThrows(EdcLocationProviderException.class, () -> {
      // given
      final EdcLocationProvider edcLocationProvider = new CsvEdcLocationProvider("file://xyz");

      // when
      edcLocationProvider.getKnownEdcLocations();

      // then - exception
    });
  }

  @Test
  void shouldThrowExceptionWhenNoProtocolUsed() {
    assertThrows(EdcLocationProviderException.class, () -> {
      // given
      // when
      new CsvEdcLocationProvider("xyz");
      // then - exception
    });
  }
}
