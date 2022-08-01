package net.catenax.explorer.core.aasregistry.model;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
public class SubmodelDescriptor {
    @Valid
    private List<LangString> description = null;

    @Size(min = 1, max = 100)
    private String idShort;

    @NotNull
    @Size(min = 1, max = 200)
    private String identification;

    @NotNull
    @Valid
    private Reference semanticId;

    @Valid
    private List<Endpoint> endpoints = new ArrayList<>();

    public SubmodelDescriptor description(List<LangString> description) {
        this.description = description;
        return this;
    }

    public SubmodelDescriptor addDescriptionItem(LangString descriptionItem) {
        if (this.description == null) {
            this.description = new ArrayList<>();
        }
        this.description.add(descriptionItem);
        return this;
    }


    public SubmodelDescriptor idShort(String idShort) {
        this.idShort = idShort;
        return this;
    }


    public SubmodelDescriptor identification(String identification) {
        this.identification = identification;
        return this;
    }


    public SubmodelDescriptor semanticId(Reference semanticId) {
        this.semanticId = semanticId;
        return this;
    }

    public SubmodelDescriptor endpoints(List<Endpoint> endpoints) {
        this.endpoints = endpoints;
        return this;
    }

    public SubmodelDescriptor addEndpointsItem(Endpoint endpointsItem) {
        this.endpoints.add(endpointsItem);
        return this;
    }
}
