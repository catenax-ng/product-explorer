package net.catenax.explorer.core.retriever;

import java.util.List;

public interface AssetRetriever {

  //TODO see how many DARs could be retrived
  AssetResponse retrieve(String edcEndpoint);
}