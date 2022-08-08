package net.catenax.explorer.core.twinregistry.retriever;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class MockAssetRetrieverTest {

  @Test
  void shouldReturnAsset() {
    //given
    var provider = new MockAssetRetriever();
    //when
    final var result = provider.retrieve("any");
    //then
    assertNotNull(result);
    assertEquals("serialPartTypization", result.getIdShort());
  }
}