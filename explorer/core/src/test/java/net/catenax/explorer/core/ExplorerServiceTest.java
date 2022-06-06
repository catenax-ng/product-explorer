package net.catenax.explorer.core;

import static net.catenax.explorer.core.retriever.AssetDataMother.getAssetResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import net.catenax.explorer.core.edclocation.EdcLocation;
import net.catenax.explorer.core.edclocation.EdcLocationProvider;
import net.catenax.explorer.core.exception.ResourceNotFoundException;
import net.catenax.explorer.core.retriever.AssetResponse;
import net.catenax.explorer.core.retriever.AssetRetriever;
import net.catenax.explorer.core.submodel.twinregistry.SubmodelResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Disabled
class ExplorerServiceTest {

  private ExplorerService sut;

  @Mock private EdcLocationProvider provider;

  @Mock private AssetRetriever assetRetriever;

  @BeforeEach
  void init() {
//    sut = new ExplorerService(provider, assetRetriever);
  }

  @Test
  void shouldRetrieveAsset() {
    //given
    final EdcLocation edcLocation = EdcLocation.builder().url("some url").build();
    when(provider.getKnownEdcLocations()).thenReturn(List.of(edcLocation));
    final AssetResponse assetResponse = getAssetResponse();
    when(assetRetriever.retrieve(any())).thenReturn(assetResponse);
    // when
    final SubmodelResponse result = sut.search(assetResponse.getIdentification());
    //then
//    assertEquals(assetResponse.getIdentification(), result()); //TODO
  }

  @Test
  void shouldNotFindAssetWhenNotExisting() {
    //given
    final EdcLocation edcLocation = EdcLocation.builder().url("some url").build();
    when(provider.getKnownEdcLocations()).thenReturn(List.of(edcLocation));
    final AssetResponse assetResponse = getAssetResponse();
    when(assetRetriever.retrieve(any())).thenReturn(assetResponse);
    // then
    assertThrows(ResourceNotFoundException.class, () -> sut.search("some id"));
  }
}