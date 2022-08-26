package net.catenax.explorer.core;

import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import net.catenax.explorer.core.shell.ShellDescriptorResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ExplorerServiceIT {
    @RegisterExtension
    static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().port(8888).notifier(new ConsoleNotifier(false)))
            .build();

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("self.description.hub.url", () -> "localhost:8888/self-desc-hub/");
        registry.add("edc.extension.url", () -> "http://localhost:8888/extension/");
    }

    @Autowired
    private ExplorerService explorerService;

    @Test
    void shouldProcessTwoShellDescriptions() {
        wireMockServer.stubFor(
                get(urlMatching("/self-desc-hub/*"))
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("edc-locations.json")));

        wireMockServer.stubFor(
                get(urlEqualTo("/extension/1?providerUrl=serviceProvider1/api/v1/ids/data"))
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("extension-provider1-result.json")));

        wireMockServer.stubFor(
                get(urlEqualTo("/extension/1?providerUrl=serviceProvider2/api/v1/ids/data"))
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("extension-provider2-result.json")));

        wireMockServer.stubFor(
                get(urlEqualTo("/data1"))
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("data1.json")));

        wireMockServer.stubFor(
                get(urlEqualTo("/data2"))
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("data2.json")));

        final Flux<ShellDescriptorResponse.ShellDescriptor> descriptorFlux = explorerService.search("1");

        StepVerifier
                .create(descriptorFlux)
                .expectNextMatches(descriptor -> descriptor.getIdShort().equals("Polonez 520e Plugin-Hybrid"))
                .expectNextMatches(descriptor -> descriptor.getIdShort().equals("Polonez 520e Plugin-Hybrid V2"))
                .expectComplete()
                .verify();
    }

    @Test
    void shouldProcessSecondShellDescription() {
        wireMockServer.stubFor(
                get(urlMatching("/self-desc-hub/*"))
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("edc-locations.json")));

        wireMockServer.stubFor(
                get(urlEqualTo("/extension/1?providerUrl=serviceProvider1/api/v1/ids/data"))
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.NO_CONTENT.value())));

        wireMockServer.stubFor(
                get(urlEqualTo("/extension/1?providerUrl=serviceProvider2/api/v1/ids/data"))
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("extension-provider2-result.json")));

        wireMockServer.stubFor(
                get(urlEqualTo("/data2"))
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("data2.json")));

        final Flux<ShellDescriptorResponse.ShellDescriptor> descriptorFlux = explorerService.search("1");

        StepVerifier
                .create(descriptorFlux)
                .expectNextMatches(descriptor -> descriptor.getIdShort().equals("Polonez 520e Plugin-Hybrid V2"))
                .expectComplete()
                .verify();
    }

    @Test
    void shouldNotFindAnything() {
        wireMockServer.stubFor(
                get(urlMatching("/self-desc-hub/*"))
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("edc-locations.json")));

        wireMockServer.stubFor(
                get(urlEqualTo("/extension/1?providerUrl=serviceProvider1/api/v1/ids/data"))
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.NO_CONTENT.value())));

        wireMockServer.stubFor(
                get(urlEqualTo("/extension/1?providerUrl=serviceProvider2/api/v1/ids/data"))
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.NO_CONTENT.value())));

        final Flux<ShellDescriptorResponse.ShellDescriptor> descriptorFlux = explorerService.search("1");

        StepVerifier
                .create(descriptorFlux)
                .expectComplete()
                .verify();
    }
}
