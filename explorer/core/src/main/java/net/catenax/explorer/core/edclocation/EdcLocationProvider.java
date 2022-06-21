package net.catenax.explorer.core.edclocation;

import java.util.List;
import net.catenax.explorer.core.edclocation.model.SelfDescription;

public interface EdcLocationProvider {
    List<SelfDescription> getKnownEdcLocations();
}
