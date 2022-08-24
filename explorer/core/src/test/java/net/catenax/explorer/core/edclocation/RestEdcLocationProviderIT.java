package net.catenax.explorer.core.edclocation;

import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import net.catenax.explorer.core.edclocation.model.SelfDescription;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class RestEdcLocationProviderIT {
    @RegisterExtension
    static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort().notifier(new ConsoleNotifier(false)))
            .build();

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("self.description.hub.url", () -> "localhost:%s/self-desc-hub/".formatted(wireMockServer.getPort()));
    }

    @Autowired
    private RestEdcLocationProvider restEdcLocationProvider;

    @Test
    void shouldReturnEdcLocations() {
        wireMockServer.stubFor(
                get(urlMatching("/self-desc-hub/*"))
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("edc-locations.json")));

        final List<SelfDescription> knownEdcLocations = restEdcLocationProvider.getKnownEdcLocations();
        Assertions.assertNotNull(knownEdcLocations);
        Assertions.assertEquals(2, knownEdcLocations.size());
        Assertions.assertEquals("companyNumber1", knownEdcLocations.get(0).getCompanyNumber());
    }
}
