package net.catenax.explorer.core;

import static net.catenax.explorer.core.retriever.AssetDataMother.getAssetResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import net.catenax.explorer.core.edclocation.EdcLocationProvider;
import net.catenax.explorer.core.edclocation.model.SelfDescription;
import net.catenax.explorer.core.retriever.AssetResponse;
import net.catenax.explorer.core.retriever.AssetRetriever;
import net.catenax.explorer.core.submodel.ShellDescriptorProvider;
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

  @Mock
  private EdcLocationProvider provider;

  @Mock
  private AssetRetriever assetRetriever;

  @Mock
  private ShellDescriptorProvider shellDescriptorProvider;

  @BeforeEach
  void init() {
//    sut = new ExplorerService(provider, assetRetriever, shellDescriptorProvider);
  }

  @Test
  void shouldRetrieveAsset() {
    //given
    final SelfDescription edcLocation = SelfDescription.builder().serviceProvider("some url").build();
    when(provider.getKnownEdcLocations()).thenReturn(List.of(edcLocation));
    final AssetResponse assetResponse = getAssetResponse();
    when(assetRetriever.retrieve(any())).thenReturn(assetResponse);
//    when(shellDescriptorProvider.search(any(), any())).thenReturn(mock(ShellDescriptorResponse.class));
    // when
//    final List<ShellDescriptorResponse> result = sut.search(assetResponse.getIdentification());
    //then
//    assertEquals(1, result.size()); //TODO
  }

  @Test
  void shouldNotFindAssetWhenNotExisting() {
    //given
    final SelfDescription edcLocation = SelfDescription.builder().serviceProvider("some url").build();
    when(provider.getKnownEdcLocations()).thenReturn(List.of(edcLocation));
    final AssetResponse assetResponse = getAssetResponse();
    when(assetRetriever.retrieve(any())).thenReturn(assetResponse);
    // when
//    final List<ShellDescriptorResponse> result = sut.search("some id");
    // then
//    assertEquals(0, result.size());
  }
}