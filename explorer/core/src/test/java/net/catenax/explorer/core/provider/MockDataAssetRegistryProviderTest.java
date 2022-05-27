package net.catenax.explorer.core.provider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class MockDataAssetRegistryProviderTest {

  @Test
  void shouldReturnDAR() {
    //given
    var provider = new MockDataAssetRegistryProvider();
    //when
    final var result = provider.retrieveDars("any");
    //then
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("serialPartTypization", result.get(0).getIdShort());
  }
}